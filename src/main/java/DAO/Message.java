package DAO;

import javax.persistence.*;


@Entity
@Table(name = "message")
public class Message {

    private int id_message;
    private User send;
    private User recieve;
    public Message(int id_message, User send, User recieve){
        this.id_message=id_message;
        this.send=send;
        this.recieve=recieve;
    }
    @Id
    @GeneratedValue

    public int getId_message() {
        return id_message;
    }

    public void setId_message(int id_message) {
        this.id_message = id_message;
    }
    @ManyToOne
    @JoinColumn(name = "RECIEVE")
    public User getRecieve() {
        return recieve;
    }

    public void setRecieve(User recieve) {
        this.recieve = recieve;
    }
    @ManyToOne
    @JoinColumn(name = "SEND")
    public User getSend() {
        return send;
    }

    public void setSend(User send) {
        this.send = send;
    }
}
