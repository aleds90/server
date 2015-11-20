package Token;

import java.sql.Timestamp;

/**
 * AccessToken viene utilizzato per dare accesso alle chiamate ad un determinato utente. In particolare in fare si login
 * viene fornito un access token che sara' attivo per un determinato arco di tempo all'utente il quale sara' cosi' abilitato
 * a poter effettuare chiamate al server. Nel caso l'access token sia scaduto l'utente dovra' quindi rieffettuare un login
 * o tramite refresh token o tramite password ed nomeutente
 */
public class AccessToken implements Token {
    private int token_id;// primary key del token
    private int id_client;//foreign key con la tabella client
    private int id_user;//foreign key con la tabella user
    private Timestamp expair_app;// tempo di attivita' del token
    private String token;// nome del token

    public AccessToken(int id_client, int id_user, Timestamp expair_app, String token) {
        this.id_client = id_client;
        this.id_user = id_user;
        this.expair_app = expair_app;
        this.token = token;
    }

    public AccessToken() {
    }

    public int getToken_id() {
        return token_id;
    }

    public void setToken_id(int token_id) {
        this.token_id = token_id;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
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
