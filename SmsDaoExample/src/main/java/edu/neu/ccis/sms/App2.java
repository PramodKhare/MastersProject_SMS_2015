package edu.neu.ccis.sms;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import edu.neu.ccis.sms.dao.users.UserDao;
import edu.neu.ccis.sms.dao.users.UserDaoImpl;
import edu.neu.ccis.sms.entity.users.User;

public class App2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        UserDao userDao = new UserDaoImpl();
        User one = userDao.getUser(new Long(1));
        Set<User> oldCoiSet = one.getMyConflictsOfInterestWithUsers();

        // Check what is the submitType of request
        String submitType = "Add Conflicts";

        Set<User> newCoiSet = new HashSet<User>();
        String coiUserEmailId = "khare.pr2@husky.neu.edu";
        User coiUser = userDao.getUserByEmailId(coiUserEmailId);
        System.out.println("User Id - "+coiUser.getId());
        // Make sure that its a valid user and that user is not himself
        if (coiUser != null) {
            newCoiSet.add(coiUser);
        }

        //oldCoiSet.clear();
        //one.setMyConflictsOfInterestWithUsers(oldCoiSet);
        //userDao.updateUser(one);
        
        oldCoiSet.addAll(newCoiSet);
        one.setMyConflictsOfInterestWithUsers(oldCoiSet);
        userDao.updateUser(one);
        /*
        // Default action is Replace the old topics with new ones
        if (null == submitType || submitType.startsWith("Replace")) {
            oldCoiSet.clear();
            oldCoiSet.addAll(newCoiSet);
            one.setMyConflictsOfInterestWithUsers(oldCoiSet);
            userDao.updateUser(one);
        } else if (submitType.startsWith("Clear")) {
            // Remove the old conflicts of interest
            oldCoiSet.clear();
            one.setMyConflictsOfInterestWithUsers(oldCoiSet);
            userDao.updateUser(one);
        } else if (submitType.startsWith("Add")) {
            oldCoiSet.addAll(newCoiSet);
            one.setMyConflictsOfInterestWithUsers(oldCoiSet);
            userDao.updateUser(one);
        }
        */
        
        System.out.println("Successfully updated the Conflicts of interest!");
    }

}
