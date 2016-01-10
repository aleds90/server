package Spark;

import DAO.Feedback.Feedback;
import DAO.Feedback.FeedbackDAOImpl;
import DAO.Follow.FollowDaoImpl;
import DAO.User.User;
import DAO.UserManager.UserManagerImpl;

import java.util.ArrayList;
import java.util.Date;

import static Json.JsonUtil.json;
import static spark.Spark.*;
/**
 * Created by jns on 1/10/16.
 */
public class FeedbackController {


    public FeedbackController(final UserManagerImpl userManager, final FeedbackDAOImpl feedbackDAO,
                              final  FollowDaoImpl followDao){


        post("checkFeedback", (request, response) -> {
            String user_email = request.queryParams("user_email");
            String target_email = request.queryParams("target_email");
            User user = userManager.getUser(user_email);
            User target = userManager.getUser(target_email);

            boolean result = feedbackDAO.check_feedack(user, target);
            return  Boolean.toString(result);
        });


        post("countFeedback", (request, response) -> {
            String user_email = request.queryParams("user_email");
            User user = userManager.getUser(user_email);
            int count_f = feedbackDAO.get_count_feedback(user);
            int count_followers = followDao.getFollowersByUser(user).size();
            int count_followed = followDao.getFollowedByUser(user).size();
            ArrayList<Integer> count = new ArrayList<Integer>();
            count.add(count_f);
            count.add(count_followers);
            count.add(count_followed);
            return count;
        },json());


        post("removeFeedback", (request, response) -> {
            String user_email = request.queryParams("user_email");
            String target_email = request.queryParams("target_email");
            User user = userManager.getUser(user_email);
            User target = userManager.getUser(target_email);
            new FeedbackDAOImpl().remove_feedback(user, target);
            return "OK";
        });


        post("addFeedback", (request, response) -> {
            String user_email = request.queryParams("user_email");
            String target_email = request.queryParams("target_email");
            Date date = new Date();
            User user = userManager.getUser(user_email);
            User target = userManager.getUser(target_email);
            Feedback f = new Feedback(user, target, date);
            new FeedbackDAOImpl().add_feedback(f);
            return "OK";
        });

    }


}
