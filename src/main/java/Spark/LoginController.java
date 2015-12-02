package Spark;

import DAO.Response;
import DAO.User;
import DAO.UserManagerImpl;
import Token.*;

import static Json.JsonUtil.json;
import static spark.Spark.post;

/**
 * LoginController si occupa di tutte le chiamate che vengono effettuate con lo scopo di effettuare un accesso nell'app.
 * In particolare l'accesso puo essere effettuato in due diversi modi. Tramite l'inserimento di un nome utente e della password
 * oppure tramite un token di refresh attivo. In oltre in entrambi i casi sono richiesti dei dati statici del client.
 */

public class LoginController {

    public LoginController(final UserManagerImpl userManager, final ClientDAOImpl clientDAO, final TokenManager tokenManager) {
        //indica l' indirizzo al quale deve essere fatta questo genere di richiesta
        post("/authorization", (request, response) -> {
            //inizializza la risposta
            Response responseServer = new Response();
            // vieni richiesto un token come headers sotto il nome di Authorization per effettuare una richiesta
            String token = request.headers("Authorization");
            //parametri del client che vengono richiesti in entrambi gli accessi
            Client client = clientDAO.ClientAuth(request.queryParams("random_id"), request.queryParams("secret_id"), request.queryParams("grant_types"));
            //primo ciclo che gestisce l'accesso per password e nomeutente
            if (client.getGrant_types().equals("Password")) {
                // parametri dell'user richiesti in questo tipo di accesso
                User user = userManager.getUserIfExist(request.queryParams("email"), request.queryParams("password"));
                //ciclo che gestisce la risposta nel caso i dati di accesso sono giusti. In particolare forniamo al client:
                //i dati dello user che fa accesso, un refresh token e un access token nuovi
                if (user != null && client != null) {
                    AccessToken accessToken = tokenManager.createAccessToken(user, client);
                    RefreshToken refreshToken = tokenManager.createRefreshToken(user, client);
                    responseServer.setUser(user);
                    responseServer.setType("2"); // Il tipo di risposta serve a far capire al client che tipo di dati deve aspettarsi come risposta
                    responseServer.setAccess_Token(accessToken.getToken());
                    responseServer.setRefresh_Token(refreshToken.getToken());

                }//risposta nel caso i dati sono errati
                else{
                    responseServer.setType("3");
                }
            // ciclo che gestisce l'accesso tramite token di regresh
            } else   {
                // controlliamo se il token e' attivo. Se non e' attivo rispondiamo con error 301
                if (!tokenManager.isRTokenActive(token)) {
                    responseServer.setType("401");
                }//se il token e' attivo forniamo come risposta due nuovi token di accesso e refresh
                else {
                    AccessToken accessToken = tokenManager.createAccessToken(tokenManager.getUserIdByToken(token), client);
                    RefreshToken refreshToken = tokenManager.createRefreshToken(tokenManager.getUserIdByToken(token), client);
                    responseServer.setType("4"); // Access e Refresh Token
                    responseServer.setAccess_Token(accessToken.getToken());
                    responseServer.setRefresh_Token(refreshToken.getToken());
                }
            }
            return responseServer;
        },json());

    }
}