package DAO.Message;

import DAO.User.User;
import Hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class MessageDAOImpl implements MessageDAO {

    // attributi necessari per creare le query tramite hibernate
    private SessionFactory sessionFactory;//strettamente collegato alla classe HibernateUtil che gestisce le sessioni con il db
    private Session session;
    // costruttore della classe
    public MessageDAOImpl(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.session= sessionFactory.openSession();
    }

    /**
     * Inserisce nuovi messaggi nel DB
     * @param message nuovo messaggio creato
     */
    @Override
    public void sendMessage(Message message) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        session.save(message);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void setRead(User receiver, User sender) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        session.createQuery("update Message set read=1 where (id_receiver="
                +receiver.getId_user()+ " and id_sender="+sender.getId_user()+")").executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Message> getMessageByUser(User user) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        List<Message> messageList = session.createQuery( "FROM Message WHERE (id_sender="+user.getId_user() +"" +
                " or id_receiver="+user.getId_user()+")").list();
        session.getTransaction().commit();
        session.close();
        return messageList;
    }

    @Override
    public List<Message> getMessageByTwoUser(User user,User me) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        List<Message> messageList = session.createQuery( "FROM Message WHERE ((id_sender="+user.getId_user()+" AND id_receiver="+ me.getId_user()
                +")or (id_receiver="+user.getId_user()+" AND id_sender="+me.getId_user()+"))ORDER BY sendetAt ASC").list();
        session.getTransaction().commit();
        session.close();
        return messageList;
    }

    @Override
    public int number_messages_not_read(User user) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();

        Query query = session.createQuery("select count(distinct id_sender) " +
                "from Message" +
                " where (id_receiver=:user) and (read=false)");
        query.setParameter("user",user);


        int count = (int)(long)query.uniqueResult();


        session.getTransaction().commit();
        session.close();
        return count;
    }

    @Override
    public List<User> getAllUsersWithMessage(int id_user) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        List<User> userList = session.createQuery(" FROM User WHERE id_user in (" +
                "SELECT id_sender FROM Message WHERE id_receiver="+id_user+")" +
                " or " +
                "id_user in(SELECT id_receiver FROM Message WHERE id_sender="+id_user+")").list();
        session.getTransaction().commit();
        session.close();
        return userList;
    }



}
