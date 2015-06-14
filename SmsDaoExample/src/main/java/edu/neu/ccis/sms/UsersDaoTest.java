package edu.neu.ccis.sms;

import edu.neu.ccis.sms.dao.users.UserDao;
import edu.neu.ccis.sms.dao.users.UserDaoImpl;
import edu.neu.ccis.sms.entity.submissions.Document;
import edu.neu.ccis.sms.entity.users.User;

public class UsersDaoTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();
        User u1 = userDao.getUserByIdWithSubmissions(new Long(1));

        if (u1.getSubmissions().isEmpty()) {
            System.out.println("Submissions found!");
        } else {
            System.out.println("Zero Submissions found!");
        }
        // Document d = userDao.getSubmissionDocumentForMemberIdByUserId(new
        // Long(1), new Long(2));
    }

}
