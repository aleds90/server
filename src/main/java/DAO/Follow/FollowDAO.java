package DAO.Follow;

import DAO.User.User;

import java.util.List;

/**
 * Created by jns on 24/11/15.
 */
public interface FollowDAO {

    public List<User> getFollowersByUser(User target_id_user);
    public List<User> getFollowedByUser(User id_user);
    public void createFollowing(Follow follow);
    public void removeFollowing(User user, User target);
    public void removeAllFollowersByUser(int id_user);
    public Follow getFollow (User user,User target);
    public boolean isFollowed(User user, User target);

}
