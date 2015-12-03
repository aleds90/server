package Spark;

import DAO.*;
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
                halt(403, "403");// torniamo un errore se il token di accesso e' scaduto. Si richiede in questo caso di rieffettuare login con il refresh token per ottenere un nuovo token access
            }
        });

        /**
         * /users :url per ricevere la lista completa degli user presenti in db
         */

        post("/users", (request, response) -> {
            return userManager.getAllUsers();
        }, json());

        after((req, res) -> res.type("application/json"));

        /**
         * /add: url per inserire un nuovo user in db. La chiamata viene effettuata in fase di registrazione.
         */
        post("/add", (request, response) -> {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date bday = formatter.parse(request.queryParams("bday"));
            double rate = Double.parseDouble(request.queryParams("rate"));
            User user = new User(request.queryParams("name"), request.queryParams("surname"), request.queryParams("email"), request.queryParams("password"), bday, request.queryParams("role"), request.queryParams("city"), rate);
            if (userManager.getUser(request.queryParams("email")) == null) {
                userManager.addUser(user);
                return "OK";
            } else {
                return "NO";
            }
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
        /**
         * /update url che gestisce tutte le modifiche rispetto ad un utente. ricevendo come parametri tutti gli attributi tranne l'id
         */
        post("/update", ((request, response) -> {
            //TODO sistemare i parametri che riceve dato che non sono ancora completi.
            userManager.updateUser(Integer.parseInt(request.queryParams(("id_user"))), request.queryParams("name"), request.queryParams("surname"), request.queryParams("email"), request.queryParams("role"), request.queryParams("city"), Double.parseDouble(request.queryParams("rate")));
            return "ok";
        }));

        /**
         * /getFollowers url che dato un id_utente andra' ad individuare tutti gli users che seguono questo utente.
         */
        post("/getFollowers", ((request, response) -> {
            List<User> userList = new FollowDaoImpl().getFollowersByUser(Integer.parseInt(request.queryParams("target_id_user")));
            return userList;
        }), json());


        /**
         * /getFollowed url che dato un id_utente andra' ad individuare tutti gli users che l'utente segue
         */
        post("/getFollowed", ((request, response) -> {
            List<User> userList = new FollowDaoImpl().getFollowedByUser(Integer.parseInt(request.queryParams("id_user")));
            return userList;
        }), json());

        /**
         * /addFollow url che viene chiama quando una persona inizia a seguirne un altra.
         */
        post("/addFollow", (request, response) -> {
            Date date = new Date();
            Follow follow = new Follow(userManager.getUser(request.queryParams("emailUser")), userManager.getUser(request.queryParams("emailTarget")), date);
            if (request.queryParams("emailUser") != request.queryParams("emailTarget")) {
                new FollowDaoImpl().createFollowing(follow);
                return "OK";
            } else return "NO";
        });

        post("/addFollow", (request, response) -> {
            Date date = new Date();
            Follow follow = new Follow(userManager.getUser(request.queryParams("emailUser")), userManager.getUser(request.queryParams("emailTarget")), date);
            User user = userManager.getUser(request.queryParams("emailUser"));
            User target = userManager.getUser(request.queryParams("emailTarget"));
            if (user.getEmail() == target.getEmail()) {
                return "NO";
            } else if (new FollowDaoImpl().getFollow(user, target).equals(null)) {
                new FollowDaoImpl().createFollowing(follow);
                return "null";
            } else {

                return "NO";
            }
            // TODO NON ENTRA NEGLI IF E ELSEIF
        });

        post("/deleteFollow", (request, response) -> {
            Follow follow = new FollowDaoImpl().getFollow(userManager.getUser(request.queryParams("emailUser")), userManager.getUser(request.queryParams("emailTarget")));
            new FollowDaoImpl().removeFollowing(follow);
            return "OK";
        });

        post("/addMessage", ((request, response) -> {
            Message message = new Message();

            User user_sender = userManager.getUser(request.queryParams("email_sender"));
            User user_receiver = userManager.getUser(request.queryParams("email_receiver"));

            message.setId_sender(user_sender);
            message.setId_receiver(user_receiver);
            message.setText(request.queryParams("text"));
            message.setRead(false);
            message.setSendetAt(new Date());

            new MessageDAOImpl().sendMessage(message);

            return message.getText();
        }));

        post("/setRead", (request, response) -> {
            Message message = new Message();

            int id_message = Integer.parseInt(request.queryParams("id_message"));

            message.setId_message(id_message);

            new MessageDAOImpl().setRead(message);

            return "Done";
        });

        post("/getMessages", (request, response) -> {
            List<Message> messageList;

            User user = userManager.getUser(request.queryParams("user_mail"));

            messageList= new MessageDAOImpl().getMessageByUser(user);

            //TODO Ordinare la lista per dei criteri da accordare
            // ( Prima Data Messaggio -> Utente -> Data Messaggi ???)
            // il return é un po strano ma corretto(esempio user_mail= Amail):
            /*[
            {
                "id_message": 1,
                    "text": "qwer    ",
                    "sendetAt": "Nov 30, 2015 7:19:45 PM",
                    "read": true,
                    "id_sender": {
                "id_user": 2,
                        "name": "Aname",
                        "surname": "Asurname",
                        "email": "Amail",
                        "password": "Apass",
                        "bday": "gen 1, 2000",
                        "role": "Arole",
                        "city": "Acity",
                        "rate": 10.1
            },
                "id_receiver": {
                "id_user": 1,
                        "name": "Bname",
                        "surname": "Bsurname",
                        "email": "Bmail",
                        "password": "Bpass",
                        "bday": "gen 1, 2000",
                        "role": "Brole",
                        "city": "Bcity",
                        "rate": 10.1
            }
            },
            {
                "id_message": 2,
                    "text": "ciao, come va?",
                    "sendetAt": "Nov 30, 2015 7:37:19 PM",
                    "read": false,
                    "id_sender": {
                "id_user": 2,
                        "name": "Aname",
                        "surname": "Asurname",
                        "email": "Amail",
                        "password": "Apass",
                        "bday": "gen 1, 2000",
                        "role": "Arole",
                        "city": "Acity",
                        "rate": 10.1
            },
                "id_receiver": {
                "id_user": 1,
                        "name": "Bname",
                        "surname": "Bsurname",
                        "email": "Bmail",
                        "password": "Bpass",
                        "bday": "gen 1, 2000",
                        "role": "Brole",
                        "city": "Bcity",
                        "rate": 10.1
            }
            }
            ]*/

            return messageList;
        },json());


    }

    //metodo utilizzato per gestire le chiamate con un parametro rate all'interno. in particolare se rate non viene compilato questo viene impostato come max value nelle ricerche
    private double check(String rate) {
        if (rate == null) {
            return Double.MAX_VALUE;
        }
        return Double.parseDouble(rate);
    }
}
