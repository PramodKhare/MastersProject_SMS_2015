package edu.neu.ccis.sms.dao.categories;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import edu.neu.ccis.sms.entity.categories.Member;
import edu.neu.ccis.sms.entity.categories.UserToMemberMapping;
import edu.neu.ccis.sms.entity.users.User;
import edu.neu.ccis.sms.util.HibernateUtil;

public class UserToMemberMappingDaoImpl implements UserToMemberMappingDao {
    private Session currentSession;
    private Transaction currentTransaction;

    public UserToMemberMappingDaoImpl() {
    }

    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        return HibernateUtil.getSessionFactory();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    @Override
    public void saveUserToMemberMapping(
            UserToMemberMapping newUserToMemberMapping) {
        openCurrentSessionwithTransaction();
        getCurrentSession().save(newUserToMemberMapping);
        closeCurrentSessionwithTransaction();
    }

    @Override
    public void updateUserToMemberMapping(
            UserToMemberMapping modifiedUserToMemberMapping) {
        openCurrentSessionwithTransaction();
        getCurrentSession().update(modifiedUserToMemberMapping);
        closeCurrentSessionwithTransaction();
    }

    @Override
    public void deleteUserToMemberMapping(
            UserToMemberMapping userToMemberMapping) {
        openCurrentSessionwithTransaction();
        getCurrentSession().delete(userToMemberMapping);
        closeCurrentSessionwithTransaction();
    }

    @Override
    public List<UserToMemberMapping> getAllUserToMemberMappings() {
        openCurrentSessionwithTransaction();
        List<UserToMemberMapping> mappings = (List<UserToMemberMapping>) getCurrentSession()
                .createQuery("from UserToMemberMapping").list();
        closeCurrentSessionwithTransaction();
        return mappings;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<UserToMemberMapping> getAllMembersForUser(Long userId) {
        openCurrentSessionwithTransaction();
        Query query = getCurrentSession().createQuery("from UserToMemberMapping WHERE user_id = :userId ORDER BY role");
        query.setParameter("userId", userId);
        
        List<UserToMemberMapping> mappings = (List<UserToMemberMapping>) query.list();
        closeCurrentSessionwithTransaction();
        return mappings;
    }

    public UserToMemberMapping findByUserToMemberMappingId(Long id) {
        openCurrentSessionwithTransaction();
        UserToMemberMapping mapping = (UserToMemberMapping) getCurrentSession()
                .get(UserToMemberMapping.class, id);
        closeCurrentSessionwithTransaction();
        return mapping;
    }

    public void deleteAll() {
        List<UserToMemberMapping> mappings = getAllUserToMemberMappings();
        for (UserToMemberMapping mapping : mappings) {
            deleteUserToMemberMapping(mapping);
        }
    }

    @Override
    public UserToMemberMapping getUserToMemberMapping(Long id) {
        return findByUserToMemberMappingId(id);
    }
}
