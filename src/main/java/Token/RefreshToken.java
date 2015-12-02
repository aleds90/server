package Token;

import DAO.User;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * RefreshToken e' la classe che gestisce i token di refresh che vengono utilizzati per effettuare il login di refresh
 * all'interno dell'applicazione. In particolare questi login vengono chiamati quando un utente riapre l'applicazione oppure
 * quando questo cerca di effettuare delle ricerche con un access token scaduto. Il login di refresh andra a fornire due nuovi
 * token attivi all'utente nel caso il token di refresh sia attivo. Nel caso questo sia scaduto l'utente verra indirizzato
 * nella schermata di login con nome utente e password.
 *
 */
@Entity
@Table(name = "refresh_token")
public class RefreshToken implements Token {
    private int token_id;// primary key del token
    private Client id_client;//chiave esterna client
    private User id_user;//chiave esterna user
    private Timestamp expair_app;// tempo di attivita' del token
    private String token;// nome del token

    public RefreshToken(Client id_client, User id_user, Timestamp expair_app, String token) {
        this.id_client = id_client;
        this.id_user = id_user;
        this.expair_app = expair_app;
        this.token = token;
    }

    public RefreshToken() {
    }

    @Id
    @GeneratedValue
    public int getToken_id() {
        return token_id;
    }

    public void setToken_id(int token_id) {
        this.token_id = token_id;
    }

    @ManyToOne
    @JoinColumn(name = "id_client")
    public Client getId_client() {
        return id_client;
    }

    public void setId_client(Client id_client) {
        this.id_client = id_client;
    }

    @ManyToOne
    @JoinColumn(name = "id_user")
    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public Timestamp getExpair_app() {
        return expair_app;
    }

    public void setExpair_app(Timestamp expair_app) {
        this.expair_app = expair_app;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
