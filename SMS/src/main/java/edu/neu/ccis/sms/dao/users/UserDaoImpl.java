package edu.neu.ccis.sms.dao.users;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import edu.neu.ccis.sms.dao.categories.MemberDao;
import edu.neu.ccis.sms.dao.categories.MemberDaoImpl;
import edu.neu.ccis.sms.dao.categories.UserToMemberMappingDao;
import edu.neu.ccis.sms.dao.categories.UserToMemberMappingDaoImpl;
import edu.neu.ccis.sms.entity.categories.Member;
import edu.neu.ccis.sms.entity.categories.UserToMemberMapping;
import edu.neu.ccis.sms.entity.submissions.Document;
import edu.neu.ccis.sms.entity.users.RoleType;
import edu.neu.ccis.sms.entity.users.User;
import edu.neu.ccis.sms.util.HibernateUtil;

/**
 * DAO implementation class for User Entity bean
 * 
 * @author Pramod R. Khare
 * @date 9-May-2015
 * @modifiedBy Swapnil Gupta
 * @lastUpdate 10-June-2015
 */
public class UserDaoImpl implements UserDao {
    private Session currentSession;
    private Transaction currentTransaction;

    public UserDaoImpl() {
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

    public void setCurrentSession(final Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(final Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    @Override
    public void saveUser(final User newUser) {
        openCurrentSessionwithTransaction();
        getCurrentSession().save(newUser);
        closeCurrentSessionwithTransaction();
        ;
    }

    @Override
    public void updateUser(final User modifiedUser) {
        openCurrentSessionwithTransaction();
        getCurrentSession().update(modifiedUser);
        closeCurrentSessionwithTransaction();
        ;
    }

    public User findByUserId(final Long id) {
        openCurrentSessionwithTransaction();
        User user = (User) getCurrentSession().get(User.class, id);
        closeCurrentSessionwithTransaction();
        return user;
    }

    @Override
    public void deleteUser(final User user) {
        openCurrentSessionwithTransaction();
        getCurrentSession().delete(user);
        closeCurrentSessionwithTransaction();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUser() {
        openCurrentSessionwithTransaction();
        List<User> users = getCurrentSession().createQuery("from User").list();
        closeCurrentSessionwithTransaction();
        return users;
    }

    public void deleteAll() {
        List<User> usersList = getAllUser();
        for (User user : usersList) {
            deleteUser(user);
        }
    }

    @Override
    public User getUser(final Long id) {
        return findByUserId(id);
    }

    @Override
    public User findUserByUsername(final String username) {
        openCurrentSessionwithTransaction();
        Query query = getCurrentSession().createQuery("from User WHERE username = :username");
        query.setParameter("username", username);
        List<User> users = query.list();
        closeCurrentSessionwithTransaction();
        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public User findUserByUsernameAndPassword(final String username, final String password) {
        openCurrentSessionwithTransaction();
        Query query = getCurrentSession().createQuery("from User WHERE username = :username AND password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);

        List<User> users = query.list();
        closeCurrentSessionwithTransaction();
        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public UserToMemberMapping registerUserForMember(final User user, final Member member, final RoleType role) {
        UserToMemberMapping mapping = new UserToMemberMapping();
        mapping.setMember(member);
        mapping.setUser(user);
        mapping.setActive(true);
        mapping.setRole(role);
        UserToMemberMappingDao mappingDao = new UserToMemberMappingDaoImpl();
        mappingDao.saveUserToMemberMapping(mapping);

        return mapping;
    }

    @Override
    public UserToMemberMapping registerUserForMember(final Long userId, final Long memeberId, final RoleType role) {
        User u1 = getUser(userId);

        MemberDao memDao = new MemberDaoImpl();
        Member m1 = memDao.getMember(memeberId);

        UserToMemberMapping mapping = new UserToMemberMapping();
        mapping.setMember(m1);
        mapping.setUser(u1);
        mapping.setActive(true);
        mapping.setRole(role);

        UserToMemberMappingDao mappingDao = new UserToMemberMappingDaoImpl();
        mappingDao.saveUserToMemberMapping(mapping);

        return mapping;
    }

    @Override
    public User getUserByEmailId(final String userEmailId) {
        openCurrentSessionwithTransaction();
        Query query = getCurrentSession().createQuery("from User WHERE email = :email");
        query.setParameter("email", userEmailId);
        List<User> users = query.list();
        closeCurrentSessionwithTransaction();
        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public User getUserByIdWithSubmissions(final Long userId) {
        openCurrentSessionwithTransaction();
        Query query = getCurrentSession().createQuery(
                "select u from User u left join fetch u.submissions where u.id = :id");
        query.setParameter("id", userId);
        List<User> users = query.list();
        closeCurrentSessionwithTransaction();
        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    /**
     * Get the submitted document for given memberid, if there is any, otherwise
     * return null
     * 
     * @param memberId
     * @return
     */
    @Override
    public Document getSubmissionDocumentForMemberIdByUserId(final Long userId, final Long memberIdToUploadFor) {
        User user = getUserByIdWithSubmissions(userId);
        if (user == null) {
            return null;
        }
        for (Document submission : user.getSubmissions()) {
            if (memberIdToUploadFor.equals(submission.getSubmittedForMember().getId())) {
                return submission;
            }
        }
        return null;
    }

    @Override
    public User getUserByIdWithDocumentsForEvaluation(final Long userId) {
        openCurrentSessionwithTransaction();
        Query query = getCurrentSession().createQuery(
                "select u from User u left join fetch u.documentsForEvaluation where u.id = :id");
        query.setParameter("id", userId);
        List<User> users = query.list();
        closeCurrentSessionwithTransaction();
        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public User getUserByIdWithAllocatedEvaluatorsMappings(final Long userId) {
        openCurrentSessionwithTransaction();
        Query query = getCurrentSession().createQuery(
                "select u from User u left join fetch u.allocatedEvaluators where u.id = :id");
        query.setParameter("id", userId);
        List<User> users = query.list();
        closeCurrentSessionwithTransaction();
        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public User getUserByIdWithSubmittersToEvaluateMappings(final Long userId) {
        openCurrentSessionwithTransaction();
        Query query = getCurrentSession().createQuery(
                "select u from User u left join fetch u.submittersToEvaluate where u.id = :id");
        query.setParameter("id", userId);
        List<User> users = query.list();
        closeCurrentSessionwithTransaction();
        if (users == null || users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }
}