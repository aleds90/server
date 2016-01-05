package DAO.UserManager;

import DAO.User.User;
import DAO.User.UserDaoImpl;
import DAO.UserManager.UserManager;

import java.util.List;
/**
 * UserManagerImpl e' la classe che dovra essere chiama per invocare una query all'interno delle nostre classi che
 * ricevono dati.
 */
public class UserManagerImpl implements UserManager {

    private UserDaoImpl userDAO;

    public  UserManagerImpl(UserDaoImpl userDao){
        this.userDAO = userDao;
    }

    public void addUser(User user) {
        userDAO.addUser(user);
    }

    public List<User> getAllUsers(String email) {
        return userDAO.getAllUsers(email);
    }

    public void deleteUser(Integer id_user) {
        userDAO.deleteUser(id_user);
    }

    public User getUser(String email) {
        return  userDAO.getUser(email);
    }

    public List<User> getUserByAttributes(
            String nome, String city, double rate, String role) {
        return userDAO.getUserByAttributes(nome,
                city, rate, role);
    }
    public User getUserIfExist(String email, String password){
        return  userDAO.getUserIfExist(email, password);
    }
    public boolean authentication(String email, String password){
        return userDAO.authentication(email, password);
    }

    public void updateUser(int id_user, String name, String surname, String email, String role, String city,
                           double rate, boolean status, String description ){
        userDAO.updateUser(id_user, name, surname, email, role, city, rate, status, description);
    };

    public List<User> getAllUsersWithMessage(int id_user) {
        return userDAO.getAllUsersWithMessage(id_user);
    }

}
