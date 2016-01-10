package Spark;

import DAO.Follow.Follow;
import DAO.Follow.FollowDaoImpl;
import DAO.User.User;
import DAO.UserManager.UserManagerImpl;

import java.util.Date;
import java.util.List;

import static Json.JsonUtil.json;
import static spark.Spark.post;

/**
 * Created by jns on 1/10/16.
 */
public class FollowController {

    public FollowController(UserManagerImpl userManager, FollowDaoImpl followDao){


        /**
         * /getFollowed url che dato un id_utente andra' ad individuare tutti gli users che l'utente segue
         */
        post("/getFollowed", ((request, response) -> {
            User user = userManager.getUser(request.queryParams("email"));
            List<User> userList = followDao.getFollowedByUser(user);
            return userList;
        }), json());

        /**
         * /addFollow url che viene chiama quando una persona inizia a seguirne un altra.
         */
        post("/addFollow", (request, response) -> {
            User user = userManager.getUser(request.queryParams("emailUser"));
            User target = userManager.getUser(request.queryParams("emailTarget"));
            boolean isFollowed = followDao.isFollowed(user, target);
            if(isFollowed){
                followDao.removeFollowing(user, target);
                return "FOLLOW";
            }
            else{
                Follow follow = new Follow(user, target, new Date());
                followDao.createFollowing(follow);
                return "Unfollow";
            }
        });

        post("/checkFollow", (request, response) -> {
            User user = userManager.getUser(request.queryParams("emailUser"));
            User target = userManager.getUser(request.queryParams("emailTarget"));
            return new FollowDaoImpl().isFollowed(user, target);
        });

    }
}
