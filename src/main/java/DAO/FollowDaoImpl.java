package DAO;

import Hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;


public class FollowDaoImpl implements FollowDAO {


    private SessionFactory sessionFactory;//strettamente collegato alla classe HibernateUtil che gestisce le sessioni con il db
    private Session session;
    // costruttore della classe
    public FollowDaoImpl(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.session= sessionFactory.openSession();
    }

    @Override
    public List<User> getFollowersByUser(int target_id_user) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        List<User> userList= session.createQuery("select id_user,name,surname,email,password,bday,role,city,rate from User where id_user in(select id_user from Follow where target_id_user="+target_id_user+")").list();
        session.getTransaction().commit();
        session.close();
        return userList;
    }

    @Override
    public List<User> getFollowedByUser(int id_user) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        List<User> userList= session.createQuery("select id_user,name,surname,email,password,bday,role,city,rate from User where id_user in(select target_id_user from Follow where id_user="+id_user+")").list();
        session.getTransaction().commit();
        session.close();
        return userList;

    }

    @Override
    public void createFollowing(int id_user, int target_id_user) {

    }

    @Override
    public void removeFollowing(int id_user, int target_id_user) {

    }

    @Override
    public void removeAllFollowersByUser(int id_user) {

    }
}
