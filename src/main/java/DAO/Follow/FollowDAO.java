package DAO.Follow;

import DAO.User.User;

import java.util.List;

/**
 * Created by jns on 24/11/15.
 */
public interface FollowDAO {

    public List<User> getFollowersByUser(int target_id_user);
    public List<User> getFollowedByUser(int id_user);
    public void createFollowing(Follow follow);
    public void removeFollowing(Follow follow);
    public void removeAllFollowersByUser(int id_user);
    public Follow getFollow (User user,User target);

}
