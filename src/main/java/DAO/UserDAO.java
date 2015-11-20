package DAO;

import java.util.List;

/**
 * UserDAO interfaccia che gestisce tutte le query che verrano utilizzate in base alla chiamata del client
 */

public interface UserDAO {

    //1: query per inserire un nuovo user
    public void addUser(User user);

    //2: query per selezionare tutti gli user presenti nel db
    public List<User>  getAllUsers();

    //3: query per cancellare uno user dal db utilizzando il suo id
    public void deleteUser(Integer id_user);

    //4: query per selezionare un singolo user data un email
    public User getUser(String email);

    //5: query per selezionare una lista di user dati uno o piu' parametri
    public List<User> getUserByAttributes(String nome, String surname, String city, double rate, String role);

    //6: query utilizzata per il login che restituisce un user se la coppia email-password e' corretta
    public User getUserIfExist(String email, String password);

    //7: query che resituisce vero o false se la coppia email-password e' corretta
    public boolean authentication(String email, String password);
}
