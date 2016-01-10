package Spark;

import DAO.Message.Message;
import DAO.Message.MessageDAOImpl;
import DAO.User.User;
import DAO.UserManager.UserManagerImpl;

import java.util.Date;
import java.util.List;

import static Json.JsonUtil.json;
import static spark.Spark.post;

/**
 * Created by jns on 1/10/16.
 */
public class MessageController {


    public MessageController(final UserManagerImpl userManager, final MessageDAOImpl messageDAO){


        post("/getConversation", ((request, response) -> {
            List<Message> messages;
            User user = userManager.getUser(request.queryParams("user_mail"));
            User me = userManager.getUser(request.queryParams("my_mail"));
            messages = messageDAO.getMessageByTwoUser(user, me);
            return messages;

        }),json());


        post("/getMessages", (request, response) -> {
            List<Message> messageList;

            User user = userManager.getUser(request.queryParams("user_mail"));

            messageList= new MessageDAOImpl().getMessageByUser(user);

            //TODO Ordinare la lista per dei criteri da accordare

            return messageList;
        },json());


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


        post("/getUsersConversation", ((request1, response1) -> {
            List<User> list = new MessageDAOImpl().getAllUsersWithMessage(Integer.parseInt(request1.queryParams("id_user")));
            return list;
        }),json());
    }
}
