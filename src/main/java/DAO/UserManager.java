package DAO;

import java.util.List;

/**
 * UserManager inferfaccia del manager con le query create all'iterno dello UserDaoImpl
 */
public interface UserManager {


    public void addUser(User user);

    public List<User> getAllUsers(String email);
    public void  deleteUser(Integer id_user);
    public User getUser(String email);

    public List<User> getUserByAttributes(String nome, String city, double rate, String role);
    public User getUserIfExist(String email, String password);
    public boolean authentication(String email, String password);
    public void updateUser(int id_user, String name, String surname, String email, String role, String city, double rate );

    public List<User> getAllUsersWithMessage(int id_user);

}
