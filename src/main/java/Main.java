import DAO.User.UserDaoImpl;
import DAO.UserManager.UserManagerImpl;
import Spark.LoginController;
import Spark.UserController;
import DAO.Client.ClientDAOImpl;
import DAO.Token.TokenManager;

public class Main {
    public static void main(String[] args){

        new UserController(new UserManagerImpl(new UserDaoImpl()), new TokenManager());
        new LoginController(new UserManagerImpl(new UserDaoImpl()), new ClientDAOImpl(), new TokenManager());
    }
}
