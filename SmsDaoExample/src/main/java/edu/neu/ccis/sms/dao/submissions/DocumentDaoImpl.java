package edu.neu.ccis.sms.dao.submissions;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import edu.neu.ccis.sms.entity.submissions.Document;
import edu.neu.ccis.sms.entity.users.User;
import edu.neu.ccis.sms.util.HibernateUtil;

/**
 * DAO implementation class for Document Entity bean
 * 
 * @author Pramod R. Khare
 * @date 9-May-2015
 * @lastUpdate 10-June-2015
 */
public class DocumentDaoImpl implements DocumentDao {
    private Session currentSession;
    private Transaction currentTransaction;

    public DocumentDaoImpl() {
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
    public void saveDocument(Document newDocument) {
        openCurrentSessionwithTransaction();
        getCurrentSession().save(newDocument);
        closeCurrentSessionwithTransaction();
    }

    @Override
    public void updateDocument(Document modifiedDocument) {
        openCurrentSessionwithTransaction();
        getCurrentSession().update(modifiedDocument);
        closeCurrentSessionwithTransaction();
    }

    public Document findByDocumentId(Long id) {
        openCurrentSessionwithTransaction();
        Document Document = (Document) getCurrentSession().get(Document.class, id);
        closeCurrentSessionwithTransaction();
        return Document;
    }

    @Override
    public void deleteDocument(Document Document) {
        openCurrentSessionwithTransaction();
        getCurrentSession().delete(Document);
        closeCurrentSessionwithTransaction();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Document> getAllDocuments() {
        openCurrentSessionwithTransaction();
        List<Document> categories = (List<Document>) getCurrentSession().createQuery("from Document").list();
        closeCurrentSessionwithTransaction();
        return categories;
    }

    public void deleteAllCategories() {
        List<Document> DocumentList = getAllDocuments();
        for (Document document : DocumentList) {
            deleteDocument(document);
        }
    }

    @Override
    public Document getDocument(Long id) {
        return findByDocumentId(id);
    }

    @Override
    public Document getDocumentByIdWithEvaluations(final Long documentId) {
        openCurrentSessionwithTransaction();
        Query query = getCurrentSession().createQuery(
                "select d from Document d left join fetch d.evaluations where d.id = :id");
        query.setParameter("id", documentId);
        List<Document> docs = (List<Document>) query.list();
        closeCurrentSessionwithTransaction();
        if (docs == null || docs.isEmpty()) {
            return null;
        } else {
            return docs.get(0);
        }
    }
}
