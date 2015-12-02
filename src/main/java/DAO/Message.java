package DAO;

<<<<<<< HEAD
import javax.persistence.*;


=======
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jns on 30/11/15.
 */
>>>>>>> 2ebd1533d33db924b50e8d76054c16e83107e0eb
@Entity
@Table(name = "message")
public class Message {

    private int id_message;
<<<<<<< HEAD
    private User send;
    private User recieve;
    public Message(int id_message, User send, User recieve){
        this.id_message=id_message;
        this.send=send;
        this.recieve=recieve;
    }
    @Id
    @GeneratedValue

=======
    private String text;
    private Date sendetAt;
    private boolean read;
    private User id_sender;
    private User id_receiver;


    public Message(){}

    @Id
    @GeneratedValue
>>>>>>> 2ebd1533d33db924b50e8d76054c16e83107e0eb
    public int getId_message() {
        return id_message;
    }

    public void setId_message(int id_message) {
        this.id_message = id_message;
    }
<<<<<<< HEAD
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
=======


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSendetAt() {
        return sendetAt;
    }

    public void setSendetAt(Date sendetAt) {
        this.sendetAt = sendetAt;
    }

    @Column(name = "isRead", columnDefinition = "tinyint default false")
    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @ManyToOne
    @JoinColumn(name="id_sender")
    public User getId_sender() {
        return id_sender;
    }

    public void setId_sender(User id_sender) {
        this.id_sender = id_sender;
    }

    @ManyToOne
    @JoinColumn(name="id_receiver")
    public User getId_receiver() {
        return id_receiver;
    }

    public void setId_receiver(User id_receiver) {
        this.id_receiver = id_receiver;
    }

>>>>>>> 2ebd1533d33db924b50e8d76054c16e83107e0eb
}
