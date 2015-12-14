package DAO.Response;

import DAO.User.User;

/**
 * Response si occupa della risposta che sara' inviata al server dopo una chiamata.
 */

public class Response {

    private User user;// identifica lo user di risposta
    private String access_Token;// token d'access
    private String refresh_Token;// token di refresh
    private String type;// il tipo di risposta tramite il quale il client gestisce i suoi comportamenti.

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccess_Token() {
        return access_Token;
    }

    public void setAccess_Token(String access_Token) {
        this.access_Token = access_Token;
    }

    public String getRefresh_Token() {
        return refresh_Token;
    }

    public void setRefresh_Token(String refresh_Token) {
        this.refresh_Token = refresh_Token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
