package Spark;
import DAO.User;
import DAO.UserManagerImpl;
import Token.TokenManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static Json.JsonUtil.json;
import static spark.Spark.*;

/**
 * UserController e' la classe che si occupa di gestire tutte le richieste ad esclusione di quelle di login.
 */

public class UserController {

    public UserController(final UserManagerImpl userManager, final TokenManager tokenManager) {

        /**
         * Imponiamo di farci dare il token di accesso prima di ogni richiesti /api. In tal modo possiamo gestire le sessioni e far riloggare se i token sono scaduti
         */
        before("/api/*", (request, response) -> {
            if (!tokenManager.isATokenActive(request.headers("Authorization"))) {
                halt(403,"403");// torniamo un errore se il token di accesso e' scaduto. Si richiede in questo caso di rieffettuare login con il refresh token per ottenere un nuovo token access
            }
        });

        /**
         * /users :url per ricevere la lista completa degli user presenti in db
         */
        get("/users", (request, response) -> userManager.getAllUsers(), json());
        after((req, res) -> res.type("application/json"));

        /**
         * /add: url per inserire un nuovo user in db. La chiamata viene effettuata in fase di registrazione.
         */
        post("/add", (request, response) -> {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date bday = formatter.parse(request.queryParams("bday"));
            double rate = Double.parseDouble(request.queryParams("rate"));
            User user = new User(request.queryParams("name"), request.queryParams("surname"), request.queryParams("email"), request.queryParams("password"), bday, request.queryParams("role"), request.queryParams("city"), rate);
            if (userManager.getUser(request.queryParams("email"))==null){
                userManager.addUser(user);
                return "OK";
            }else {return "NO";}
        });
        get("/add", ((request, response) -> "Empty"));

        /**
         * /getuser url per ricevere i dati di un utente tramite il parametro email
         */
        post("/getuser", ((request, response) -> {
            User user = userManager.getUser(request.queryParams("email"));
            if (user != null) {
                return user;
            } else {
                return "No user have this email";
            }
        }), json());

        /**
         * /delete url per cancellare un utente. Sara' effettuata nel profilo utente se si vuole deletare il proprio account
         */
        post("/delete", ((request, response) -> {
            userManager.deleteUser(Integer.parseInt(request.queryParams(("id_user"))));
            return "";
        }));

        /**
         * /getFiltered url che gestisce tutte le ricerche che si possono effetuare tramite i parametri di: name surname city rate role
         */
        post("/getFiltered", (request, response) -> {
            List<User> list = userManager.getUserByAttributes(request.queryParams("name"), request.queryParams("surname"), request.queryParams("city"), check(request.queryParams("rate")), request.queryParams("role"));
            return list;
        }, json());

        post("/update", ((request, response) -> {
            userManager.updateUser(Integer.parseInt(request.queryParams(("id_user"))), request.queryParams("name"), request.queryParams("surname"), request.queryParams("email"), request.queryParams("role"), request.queryParams("city"), Double.parseDouble(request.queryParams("rate")));
            return "";
        }));



    }

    //metodo utilizzato per gestire le chiamate con un parametro rate all'interno. in particolare se rate non viene compilato questo viene impostato come max value nelle ricerche
    private double check(String rate) {
        if (rate == null) {
            return Double.MAX_VALUE;
        }
        return Double.parseDouble(rate);
    }
}
