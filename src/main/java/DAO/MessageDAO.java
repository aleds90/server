package DAO;

import java.util.List;

/**
 * Created by jns on 30/11/15.
 */
public interface MessageDAO {

    public void sendMessage(Message message);
    public void setRead(Message message);
    public List<Message> getMessageByUser(User user);

}
