package DAO.Notice;

import DAO.Message.Message;
import DAO.User.User;
import Hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by jns on 1/4/16.
 */
public class NoticeDAOImpl implements NoticeDAO{

    // attributi necessari per creare le query tramite hibernate
    private SessionFactory sessionFactory;//strettamente collegato alla classe HibernateUtil che gestisce le sessioni con il db
    private Session session;

    public NoticeDAOImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.session= sessionFactory.openSession();
    }

    @Override
    public void insert_notice(Notice notice) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        session.save(notice);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Notice> get_user_notice(User user) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        List<Notice> noticeList = session.createQuery( "FROM Notice WHERE (id_user="+user.getId_user()+") order by notice_time desc ").list();
        session.getTransaction().commit();
        session.close();
        return noticeList;
    }
}
