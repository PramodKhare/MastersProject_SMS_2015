package edu.neu.ccis.sms;

import java.util.Set;

import edu.neu.ccis.sms.dao.users.UserDao;
import edu.neu.ccis.sms.dao.users.UserDaoImpl;
import edu.neu.ccis.sms.entity.submissions.Document;

public class RetrieveDocForMemberForUser {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();
        Document doc = userDao.getSubmissionDocumentForMemberIdByUserId(new Long(3), new Long(3));
        System.out.println("Document submitted for this member - " + doc.getCmsDocumentPath());
    }
}
