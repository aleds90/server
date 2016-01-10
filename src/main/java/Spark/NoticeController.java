package Spark;

import DAO.Notice.Notice;
import DAO.Notice.NoticeDAOImpl;
import DAO.User.User;
import DAO.UserManager.UserManager;

import java.util.Date;
import java.util.List;

import static Json.JsonUtil.json;
import static spark.Spark.post;

/**
 * Created by jns on 1/10/16.
 */
public class NoticeController {

    public NoticeController(UserManager userManager, NoticeDAOImpl noticeDAO){

        post("/addNotice", (request, response) -> {
            String email = request.queryParams("email");
            User user = userManager.getUser(email);
            String text = request.queryParams("notice_text");
            Date notice_date = new Date();
            Notice notice = new Notice(user, text, notice_date);
            noticeDAO.insert_notice(notice);
            return "OK";
        });

        post("getNotice", (request, response) -> {
            String email = request.queryParams("email");
            User user = userManager.getUser(email);
            List<Notice> noticeList = noticeDAO.get_user_notice(user);
            if(noticeList.isEmpty()){
                return new Notice(user, "", new Date());
            }
            return noticeList.get(0);
        },json());

    }
}
