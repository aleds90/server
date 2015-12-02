package Token;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Client serve per individuare il client che sta facendo una chiamata. Nel nostro caso avremo solo un client quindi i dati
 * statici per tutte le chiamate effettuate verso il server. L'unica cosa che cambiera sara' il grant_types che gestira'
 * in particolar modo le chiamate di login che possono essere o di tipo refresh o di tipo password
 */
@Entity
@Table(name = "client")
public class Client {
    private int id;//primary key
    private String random_id;// uno dei due id che ci serve per identificare un client
    private String secret_id;// uno dei due id che ci serve per identificare un client
    private String grant_types;// stringa che individua il tipo di chiamata che sta facendo un client

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRandom_id() {
        return random_id;
    }

    public void setRandom_id(String random_id) {
        this.random_id = random_id;
    }

    public String getSecret_id() {
        return secret_id;
    }

    public void setSecret_id(String secret_id) {
        this.secret_id = secret_id;
    }

    public String getGrant_types() {
        return grant_types;
    }

    public void setGrant_types(String grant_types) {
        this.grant_types = grant_types;
    }
}
