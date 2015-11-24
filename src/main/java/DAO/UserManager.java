package DAO;

import java.util.List;

/**
 * UserManager inferfaccia del manager con le query create all'iterno dello UserDaoImpl
 */
public interface UserManager {


    public void addUser(User user);
    public List<User> getAllUsers();
    public void  deleteUser(Integer id_user);
    public User getUser(String email);
    public List<User> getUserByAttributes(String nome, String surname, String city, double rate, String role);
    public User getUserIfExist(String email, String password);
    public boolean authentication(String email, String password);
    public void updateUser(int id_user, String name, String surname, String email, String role, String city, double rate );


}
