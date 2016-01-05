package DAO.Feedback;

import DAO.User.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jns on 1/4/16.
 */
@Entity
@Table(name = "feedback")
public class Feedback {

    private int id_feedback;
    private User id_user;
    private User id_target;
    private Date date_feedback;


    public Feedback(User id_user, User id_target, Date date_feedback) {
        this.id_user = id_user;
        this.id_target = id_target;
        this.date_feedback = date_feedback;
    }


    @Id
    @GeneratedValue
    public int getId_feedback() {
        return id_feedback;
    }

    public void setId_feedback(int id_feedback) {
        this.id_feedback = id_feedback;
    }

    @ManyToOne
    @JoinColumn(name = "id_user")
    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    @ManyToOne
    @JoinColumn(name = "id_target")
    public User getId_target() {
        return id_target;
    }

    public void setId_target(User id_target) {
        this.id_target = id_target;
    }

    public Date getDate_feedback() {
        return date_feedback;
    }

    public void setDate_feedback(Date date_feedback) {
        this.date_feedback = date_feedback;
    }
}
