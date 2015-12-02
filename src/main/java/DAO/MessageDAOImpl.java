package DAO;

import Hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jns on 30/11/15.
 */
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
    public void setRead(Message message) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        Query query = session.createQuery("update Message set read=1 where id_message=:id_message");
        query.setParameter("id_message", message.getId_message());
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Message> getMessageByUser(User user) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        List<Message> messageList = session.createQuery( "FROM Message WHERE (id_sender="+user.getId_user() +" or id_receiver="+user.getId_user()+")").list();
        session.getTransaction().commit();
        session.close();
        return messageList;
    }
}
