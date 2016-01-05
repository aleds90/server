package DAO.Notice;

/**
 * Created by jns on 1/4/16.
 */

import DAO.User.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="notice")
public class Notice {

    public int id_notice;
    public User id_user;
    public String notice_text;
    public Date notice_time;

    public Notice(User id_user, String notice_text, Date notice_time) {
        this.id_user = id_user;
        this.notice_text = notice_text;
        this.notice_time = notice_time;
    }

    public Notice() {
    }

    @Id
    @GeneratedValue
    public int getId_notice() {
        return id_notice;
    }

    public void setId_notice(int id_notice) {
        this.id_notice = id_notice;
    }

    @ManyToOne
    @JoinColumn(name="id_user")
    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public String getNotice_text() {
        return notice_text;
    }

    public void setNotice_text(String notice_text) {
        this.notice_text = notice_text;
    }


    public Date getNotice_time() {
        return notice_time;
    }

    public void setNotice_time(Date notice_time) {
        this.notice_time = notice_time;
    }
}
