package DAO;

import java.util.List;

public interface MessageDAO {

    public void sendMessage(Message message);
    public void setRead(Message message);
    public List<Message> getMessageByUser(User user);
    public List<User> getAllUsersWithMessage(int id_user);
    public List<Message> getMessageByTwoUser(User user,User me);

}
