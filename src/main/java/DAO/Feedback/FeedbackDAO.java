package DAO.Feedback;

import DAO.User.User;

/**
 * Created by jns on 1/4/16.
 */
public interface FeedbackDAO {

    public void add_feedback(Feedback f);

    public void remove_feedback(User id_user, User id_target);

    public int get_count_feedback(User id_user);

    public boolean check_feedack(User id_user, User tardet_id);
}
