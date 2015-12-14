package DAO.Follow;

import DAO.User.User;
import Hibernate.HibernateUtil;
import org.hibernate.Query;
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
        List<User> userList= session.createQuery("from User where id_user in(select id_user from Follow where target_id_user="+target_id_user+")").list();
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
        List<User> userList= session.createQuery(" from " +
                "User where id_user in(select target_id_user from Follow where id_user="+id_user+")").list();
        session.getTransaction().commit();
        session.close();
        return userList;

    }

    @Override
    public void createFollowing(Follow follow) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        session.save(follow);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeFollowing(Follow follow) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        session.delete(follow);
        session.getTransaction().commit();
        session.close();
    }



    @Override
    public void removeAllFollowersByUser(int id_user) {

    }

    @Override
    public Follow getFollow(User user, User target) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        Query query = session.createQuery("from Follow where id_user=:id_user and target_id_user=:target_id_user");
        query.setParameter("id_user", user);
        query.setParameter("target_id_user", target);
        Follow follow = (Follow)query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        if(null != follow){
            return follow;
        } else{ return null;}
    }


}
