package DAO;

import java.util.List;

/**
 * Created by jns on 24/11/15.
 */
public interface FollowDAO {

    public List<User> getFollowersByUser(int target_id_user);
    public List<User> getFollowedByUser(int id_user);
    public void createFollowing(int id_user, int target_id_user);
    public void removeFollowing(int id_user, int target_id_user);
    public void removeAllFollowersByUser(int id_user);

}
