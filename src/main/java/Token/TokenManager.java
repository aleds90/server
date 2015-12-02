package Token;

import DAO.User;
import Hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * TokenManager e' la classe che si occupa di creare query inerenti alla tabella dei token.
 */
public class TokenManager {
    private SessionFactory sessionFactory;
    private Session session;

    public TokenManager() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.session = sessionFactory.openSession();
    }

    /**
     * fornisce l'id dello user prendendo in input il nome del token
     * @param token
     * @return
     */
    public User getUserIdByToken(String token) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        Query query = session.createQuery("select id_user FROM RefreshToken WHERE token =:token");
        query.setParameter("token", token);
        User user = (User) query.uniqueResult();
        session.close();
        return user;
    }

    /**
     * Aggiorna la data di scadenza del token di accesso
     * @param token
     */
    public void refreshTime(AccessToken token) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        long timeToAdd = 3600000 * 6; // 1 Ora per 6 = 6 Ore
        long future = new Date().getTime() + timeToAdd;
        token.setExpair_app(new Timestamp(future));
        session.save(token);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * query che controlla lo stato di attivita' del token di accesso prendendo in input il nome
     * @param token
     * @return
     */
    public boolean isATokenActive(String token) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        Query query = session.createQuery(" FROM AccessToken WHERE token =:token");
        query.setParameter("token", token);
        AccessToken accessToken = (AccessToken) query.uniqueResult();
        Date dateTime = accessToken.getExpair_app();
        session.close();
        return dateTime.after(new Date());
    }

    /**
     * query che controlla lo stato di attivita' del token di refresh prendendo in input il nome
     * @param token
     * @return
     */
    public boolean isRTokenActive(String token) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();

        Query query = session.createQuery(" FROM RefreshToken WHERE token =:token");
        query.setParameter("token", token);

        RefreshToken refreshToken = (RefreshToken) query.uniqueResult();
        Date dateTime = refreshToken.getExpair_app();
        session.close();
        return dateTime.after(new Date());

    }

    /**
     * query che inserisce un nuovo token di refresh con annesse foreign key nel db
     * @param id_user
     * @param id_client
     * @return
     */
    public RefreshToken createRefreshToken(User id_user, Client id_client) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        SecureRandom secureRandom = new SecureRandom();
        String randomToken = new BigInteger(130, secureRandom).toString(32);
        long future = Calendar.getInstance().getTimeInMillis() - 3600000 * 24*30; // 1 Mese
        RefreshToken token = new RefreshToken(id_client, id_user, new Timestamp(future), randomToken);
        session.save(token);
        session.getTransaction().commit();
        session.close();
        return token;
    }

    /**
     * query che inserisce un nuovo token di accesso con annesse foreign key nel db
     * @param id_user
     * @param id_client
     * @return
     */
    public AccessToken createAccessToken(User id_user, Client id_client) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        SecureRandom secureRandom = new SecureRandom();
        String randomToken = new BigInteger(130, secureRandom).toString(32);
        long future = new Date().getTime() + 3600000 * 6; // 6 Ore
        AccessToken token = new AccessToken(id_client, id_user, new Timestamp(future), randomToken);
        session.save(token);
        session.getTransaction().commit();
        session.close();
        return token;
    }
}
