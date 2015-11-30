package DAO;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * User identifica tutti gli attributi che un inserzionista che si registra per la prima volta all'applicazione dovra' compilare.
 * Per quanto riguarda coloro che si inscrivono solamente come visitatori sara' necessario registrarsi inserendo solamente alcuni degli attributi della classe.
 */
@Entity
@Table(name="user")
@SecondaryTable(name="userdetails")
public class User {



    private int id_user;// id della classe
    private String name;// nome dello user
    private String surname;// cognome dello user
    private String email;// email dello user(serve ad effettuare il login)
    private String password;// password di login(serve ad effettuare il login)
    private Date bday;// data di nascita
    private String role;// ruolo che identifica il lavoro dell'utente
    private String city;// citta' in cui lavora
    private double rate;//costo orario medio del servizio che lo user offre

    /**
     *
     * Costruttore con tutti gli attributi della classe.
     */
    public User(String name, String surname, String email, String password, Date bday, String role,String city, double rate) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.bday = bday;
        this.role = role;
        this.city = city;
        this.rate = rate;
    }

    public User(){}

    @Id
    @GeneratedValue
    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(table = "userdetails")
    @Type(type = "date")
    public Date getBday() {
        return bday;
    }

    public void setBday(Date bday) {
        this.bday = bday;
    }

    @Column(table = "userdetails")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(table = "userdetails")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(table = "userdetails")
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

}
