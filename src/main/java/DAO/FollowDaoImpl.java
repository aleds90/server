package DAO;

import Hibernate.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by jns on 24/11/15.
 */
public class FollowDaoImpl implements FollowDAO {


    private SessionFactory sessionFactory;//strettamente collegato alla classe HibernateUtil che gestisce le sessioni con il db
    private Session session;
    // costruttore della classe
    public FollowDaoImpl(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.session= sessionFactory.openSession();
    }

    @Override
    public List<User> getFollowersByUser(int id_user) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();


        DetachedCriteria usertable = DetachedCriteria.forClass(User.class).setProjection(Property.forName("usertable"));
        DetachedCriteria followtable = DetachedCriteria.forClass(Follow.class).add(Restrictions.eq("target_id_user", id_user)).setProjection(Property.forName("followtable"));
        List<User> userList = session.createCriteria(User.class)
                .add(Restrictions.or(
                        Property.forName("id_user").in(usertable),
                        Property.forName("id_user").in(followtable)
                )).list();

        session.getTransaction().commit();
        session.close();
        return userList;
    }

    @Override
    public List<User> getFollowedByUser(int id_user) {
        return null;
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
