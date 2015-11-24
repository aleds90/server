package DAO;

import java.util.Date;

/**
 * Created by jns on 24/11/15.
 */
public class Follow {

    private int id_follow;
    private int id_user;
    private int target_id_user;
    private Date start_follow_date;

    public Follow(int id_user, int target_id_user, Date start_follow_date) {
        this.id_user = id_user;
        this.target_id_user = target_id_user;
        this.start_follow_date = start_follow_date;
    }

    public Follow() {
    }

    public Date getStart_follow_date() {
        return start_follow_date;
    }

    public void setStart_follow_date(Date start_follow_date) {
        this.start_follow_date = start_follow_date;
    }


    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }


    public int getId_follow() {
        return id_follow;
    }

    public void setId_follow(int id_follow) {
        this.id_follow = id_follow;
    }

    public int getTarget_id_user() {
        return target_id_user;
    }

    public void setTarget_id_user(int target_id_user) {
        this.target_id_user = target_id_user;
    }
}
