package edu.neu.ccis.sms;

import edu.neu.ccis.sms.dao.submissions.DocumentDao;
import edu.neu.ccis.sms.dao.submissions.DocumentDaoImpl;
import edu.neu.ccis.sms.entity.submissions.Document;

public class DocumentDaoTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        DocumentDao dao = new DocumentDaoImpl();
        Document doc = dao.getDocument(new Long(1));
        
    }

}
