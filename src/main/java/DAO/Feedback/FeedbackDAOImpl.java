package DAO.Feedback;

import DAO.User.User;
import Hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by jns on 1/4/16.
 */
public class FeedbackDAOImpl implements FeedbackDAO {

    private SessionFactory sessionFactory;//strettamente collegato alla classe HibernateUtil che gestisce le sessioni con il db
    private Session session;

    // costruttore della classe
    public FeedbackDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.session = sessionFactory.openSession();
    }

    @Override
    public void add_feedback(Feedback f) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        session.save(f);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void remove_feedback(User id_user, User id_target) {

        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        Query query = session.createQuery("delete Feedback WHERE id_user=:user and id_target=:target");
        query.setParameter("user", id_user);
        query.setParameter("target", id_target);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public int get_count_feedback(User id_user) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        Query query = session.createQuery("select count(id_feedback) from Feedback where id_target=:user");
        query.setParameter("user", id_user);
        int cout = ((Long) query.uniqueResult()).intValue();
        session.getTransaction().commit();
        session.close();
        return cout;
    }

    @Override
    public boolean check_feedack(User id_user, User tardet_id) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        boolean result = false;

        Query query = session.createQuery(" from Feedback" +
                " where id_target=:target and id_user=:user and Datediff(now(), date_feedback) = 0 ");

        query.setParameter("target", tardet_id);
        query.setParameter("user", id_user);

        result = query.list().isEmpty();

        return result;
    }
}
