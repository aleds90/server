package Spark;

import DAO.Follow.Follow;
import DAO.Follow.FollowDaoImpl;
import DAO.Message.Message;
import DAO.Message.MessageDAOImpl;
import DAO.Notice.Notice;
import DAO.Notice.NoticeDAOImpl;
import DAO.User.User;
import DAO.UserManager.UserManagerImpl;
import DAO.Token.TokenManager;

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
            List<User> userList = userManager.getAllUsers(request.queryParams("email"));
            return userList;
        }, json());

        after((req, res) -> res.type("application/json"));

        /**
         * /add: url per inserire un nuovo user in db. La chiamata viene effettuata in fase di registrazione.
         */
        post("/add", (request, response) -> {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date bday = formatter.parse(request.queryParams("bday"));
            double rate = Double.parseDouble(request.queryParams("rate"));
            String name = request.queryParams("name");
            String surname = request.queryParams("surname");
            String email = request.queryParams("email");
            String password = request.queryParams("password");
            String role = request.queryParams("role");
            String city = request.queryParams("city");
            String description = "Inserisci descrizione";
            User user = new User(name, surname, email, password, bday, role, city, rate, true, description);
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

            List<User> list = userManager.getUserByAttributes(request.queryParams("name"), request.queryParams("city"), check(request.queryParams("rate")), request.queryParams("role"));
            return list;

        }, json());
        /**
         * /update url che gestisce tutte le modifiche rispetto ad un utente. ricevendo come parametri tutti gli attributi tranne l'id
         */
        post("/update", ((request, response) -> {
            //TODO sistemare i parametri che riceve dato che non sono ancora completi.
            int id_user = Integer.parseInt(request.queryParams(("id_user")));
            String name = request.queryParams("name");
            String surname = request.queryParams("surname");
            String email = request.queryParams("email");
            String role = request.queryParams("role");
            String city = request.queryParams("city");
            double rate = Double.parseDouble(request.queryParams("rate"));
            boolean status = Boolean.parseBoolean(request.queryParams("status"));
            String description = request.queryParams("description");
            userManager.updateUser(id_user, name, surname, email, role, city,rate, status, description);
            return "ok";
        }));

        /**
         * /getFollowers url che dato un id_utente andra' ad individuare tutti gli users che seguono questo utente.
         */
        post("/getFollowers", ((request, response) -> {
            List<User> userList = new FollowDaoImpl().getFollowersByUser(Integer.parseInt(request.queryParams("target_id_user")));
            return userList;
        }), json());

        post("/getUsersConversation", ((request1, response1) -> {
            List<User> list = new MessageDAOImpl().getAllUsersWithMessage(Integer.parseInt(request1.queryParams("id_user")));
            return list;
        }),json());


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
            FollowDaoImpl followDao = new FollowDaoImpl();
            User user = userManager.getUser(request.queryParams("emailUser"));
            User target = userManager.getUser(request.queryParams("emailTarget"));
            boolean isFollowed = followDao.isFollowed(user, target);
            System.out.print(isFollowed);
            if(isFollowed){
                new FollowDaoImpl().removeFollowing(user, target);
                return "FOLLOW";
            }
            else{
                Follow follow = new Follow(user, target, new Date());
                new FollowDaoImpl().createFollowing(follow);
                return "Unfollow";
            }
            });

        post("/checkFollow", (request, response) -> {
            User user = userManager.getUser(request.queryParams("emailUser"));
            User target = userManager.getUser(request.queryParams("emailTarget"));
            return new FollowDaoImpl().isFollowed(user, target);
        });

        post("/addMessage", ((request, response) -> {
            Message message = new Message();

            User user_sender = userManager.getUser(request.queryParams("my_mail"));
            User user_receiver = userManager.getUser(request.queryParams("user_mail"));

            System.out.println(user_sender.getName());
            message.setId_sender(user_sender);
            message.setId_receiver(user_receiver);
            message.setText(request.queryParams("text"));
            message.setRead(false);
            message.setSendetAt(new Date());

            new MessageDAOImpl().sendMessage(message);

            return message.getText();
        }));

        post("/setRead", (request, response) -> {
            User me = userManager.getUser(request.queryParams("my_mail"));
            User user = userManager.getUser(request.queryParams("user_mail"));
            new MessageDAOImpl().setRead(me, user);
            return "Done";
        });


        post("/getinContactUsers", (request, response) -> {
            return userManager.getAllUsersWithMessage(Integer.parseInt(request.queryParams("id_user")));
        }, json());

        post("/getMessages", (request, response) -> {
            List<Message> messageList;

            User user = userManager.getUser(request.queryParams("user_mail"));

            messageList= new MessageDAOImpl().getMessageByUser(user);

            //TODO Ordinare la lista per dei criteri da accordare

            return messageList;
        },json());

        post("/getConversation", ((request, response) -> {
            List<Message> messages;
            User user = userManager.getUser(request.queryParams("user_mail"));
            User me = userManager.getUser(request.queryParams("my_mail"));
            messages = new MessageDAOImpl().getMessageByTwoUser(user,me);
            return messages;

        }),json());

        post("/addNotice", (request, response) -> {
            String email = request.queryParams("email");
            User user = userManager.getUser(email);
            String text = request.queryParams("notice_text");
            Date notice_date = new Date();
            Notice notice = new Notice(user, text, notice_date);
            new NoticeDAOImpl().insert_notice(notice);
            return "OK";
        });

        post("getNotice", (request, response) -> {
            String email = request.queryParams("email");
            User user = userManager.getUser(email);
            List<Notice> noticeList = new NoticeDAOImpl().get_user_notice(user);
            return noticeList;
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
