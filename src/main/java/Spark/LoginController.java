package Spark;

import DAO.Response;
import DAO.User;
import DAO.UserManagerImpl;
import Token.*;

import java.util.ArrayList;
import java.util.List;

import static Json.JsonUtil.json;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;

public class LoginController {

    public LoginController(final UserManagerImpl userManager, final ClientDAOImpl clientDAO, final TokenManager tokenManager) {

        post("/authorization", (request, response) -> {
            Response responseServer = new Response();
            String token = request.headers("Authorization");
            Client client = clientDAO.ClientAuth(request.queryParams("random_id"), request.queryParams("secret_id"), request.queryParams("grant_types"));
            if (client.getGrant_types().equals("Password")) {
                User user = userManager.getUserIfExist(request.queryParams("email"), request.queryParams("password"));

                if (user != null && client != null) {
                    AccessToken accessToken = tokenManager.createAccessToken(user.getId_user(), client.getId());
                    RefreshToken refreshToken = tokenManager.createRefreshToken(user.getId_user(), client.getId());
                    responseServer.setType("2"); // Access e Refresh Token
                    responseServer.setAccess_Token(accessToken.getToken());
                    responseServer.setRefresh_Token(refreshToken.getToken());

                }
                else{
                    responseServer.setType("3"); // Dati errati
                }

            } else {
                if (!tokenManager.isRTokenActive(token)) {
                    responseServer.setType("401"); // Token Refresh non é attivo
                    //halt(401, "401");// di al Client di rifare il Login! ovvero andare su /autho... con Type Password
                } else {
                    AccessToken accessToken = tokenManager.createAccessToken(tokenManager.getUserIdByToken(token), client.getId());
                    RefreshToken refreshToken = tokenManager.createRefreshToken(tokenManager.getUserIdByToken(token), client.getId());
                    responseServer.setType("2"); // Access e Refresh Token
                    responseServer.setAccess_Token(accessToken.getToken());
                    responseServer.setRefresh_Token(refreshToken.getToken());
                }
            }
            return responseServer;
        },json());

    }
}
