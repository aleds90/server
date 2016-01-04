package DAO.Notice;

import DAO.User.User;

import java.util.List;

/**
 * Created by jns on 1/4/16.
 */
public interface NoticeDAO {

    public void insert_notice(Notice notice);
    public List<Notice> get_user_notice(User user);
}
