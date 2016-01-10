import DAO.Feedback.FeedbackDAOImpl;
import DAO.Follow.FollowDaoImpl;
import DAO.Message.MessageDAOImpl;
import DAO.Notice.NoticeDAOImpl;
import DAO.User.UserDaoImpl;
import DAO.UserManager.UserManagerImpl;
import Spark.*;
import DAO.Client.ClientDAOImpl;
import DAO.Token.TokenManager;

public class Main {


    public static void main(String[] args){

        final UserDaoImpl USERDAO = new UserDaoImpl();
        final UserManagerImpl USERMANAGER = new UserManagerImpl(USERDAO);
        final TokenManager TOKENMANAGER = new TokenManager();
        final ClientDAOImpl CLIENTDAO = new ClientDAOImpl();
        final FeedbackDAOImpl FEEDBACK = new FeedbackDAOImpl();
        final FollowDaoImpl FOLLOW = new FollowDaoImpl();
        final MessageDAOImpl MESSAGE = new MessageDAOImpl();
        final NoticeDAOImpl NOTICE = new NoticeDAOImpl();


        new UserController(USERMANAGER, TOKENMANAGER);
        new LoginController(USERMANAGER, CLIENTDAO, TOKENMANAGER);
        new FeedbackController(USERMANAGER, FEEDBACK, FOLLOW);
        new MessageController(USERMANAGER, MESSAGE);
        new FollowController(USERMANAGER, FOLLOW);
        new NoticeController(USERMANAGER, NOTICE);
    }
}
