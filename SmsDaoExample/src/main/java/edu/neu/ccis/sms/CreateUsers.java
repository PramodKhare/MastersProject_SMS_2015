package edu.neu.ccis.sms;

import edu.neu.ccis.sms.dao.users.UserDao;
import edu.neu.ccis.sms.dao.users.UserDaoImpl;
import edu.neu.ccis.sms.entity.users.User;

public class CreateUsers {
    /**
     * @param args
     */
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();
        User one = new User();
        one.setFirstname("Pramod");
        one.setLastname("Khare");
        one.setEmail("khare.pr11@husky.neu.edu");
        one.setUsername("khare.pr11");
        one.setPassword("pass1");
        one.addTopicsOfInterest("Hiking");
        one.addTopicsOfInterest("Biking");
        userDao.saveUser(one);

        User two = new User();
        two.setFirstname("Prasad");
        two.setLastname("Khare");
        two.setEmail("khare.pr12@husky.neu.edu");
        two.setUsername("khare.pr12");
        two.setPassword("pass1");
        two.addTopicsOfInterest("Hiking1");
        two.addTopicsOfInterest("Biking1");
        two.addMyConflictsOfInterestWithUsers(one);
        userDao.saveUser(two);

        // User one = userDao.getUser(4l);
        User three = new User();
        three.setFirstname("Prasad");
        three.setLastname("Khare");
        three.setEmail("khare.pr13@husky.neu.edu");
        three.setUsername("khare.pr13");
        three.setPassword("pass1");
        three.addTopicsOfInterest("Hiking1");
        three.addTopicsOfInterest("Biking1");
        three.addMyConflictsOfInterestWithUsers(one);
        userDao.saveUser(three);
    }

}
