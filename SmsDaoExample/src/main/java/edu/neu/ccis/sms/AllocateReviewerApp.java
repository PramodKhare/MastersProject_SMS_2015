package edu.neu.ccis.sms;

import edu.neu.ccis.sms.dao.categories.MemberDao;
import edu.neu.ccis.sms.dao.categories.MemberDaoImpl;
import edu.neu.ccis.sms.dao.users.UserDao;
import edu.neu.ccis.sms.dao.users.UserDaoImpl;
import edu.neu.ccis.sms.dao.users.UserToReviewerMappingDao;
import edu.neu.ccis.sms.dao.users.UserToReviewerMappingDaoImpl;
import edu.neu.ccis.sms.entity.categories.Member;
import edu.neu.ccis.sms.entity.users.User;
import edu.neu.ccis.sms.entity.users.UserToReviewerMapping;

public class AllocateReviewerApp {
    public static void main(String[] args) {
        UserToReviewerMappingDao userToRevDao = new UserToReviewerMappingDaoImpl();
        UserDao userDao = new UserDaoImpl();
        MemberDao memberDao = new MemberDaoImpl();

        /* Create new users */
        // User one = new User();
        // one.setFirstname("Pramod");
        // one.setLastname("Khare");
        // one.setEmail("khare.pr@husky.neu.edu");
        // one.setUsername("khare.pr");
        // one.setPassword("pass1");
        // one.addTopicsOfInterest("Hiking");
        // one.addTopicsOfInterest("Biking");
        // userDao.saveUser(one);
        //
        // User two = new User();
        // two.setFirstname("Prasad");
        // two.setLastname("Khare");
        // two.setEmail("khare.pr2@husky.neu.edu");
        // two.setUsername("khare.pr2");
        // two.setPassword("pass2");
        // two.addTopicsOfInterest("Hiking1");
        // two.addTopicsOfInterest("Biking1");
        // two.addMyConflictsOfInterestWithUsers(one);
        // userDao.saveUser(two);
        //
        // User three = new User();
        // three.setFirstname("Prasad");
        // three.setLastname("Khare");
        // three.setEmail("khare.pr3@husky.neu.edu");
        // three.setUsername("khare.pr3");
        // three.setPassword("pass1");
        // three.addTopicsOfInterest("Hiking1");
        // three.addTopicsOfInterest("Biking1");
        // three.addMyConflictsOfInterestWithUsers(two);
        // userDao.saveUser(three);

        /* Save User to review mappings */
        // User one = userDao.getUser(new Long(1));
        // User two = userDao.getUser(new Long(2));
        // User three = userDao.getUser(new Long(3));
        //
        // Member assignement1 = memberDao.getMember(new Long(3));
        //
        // // Allocate the Reviewers to the submitters for assignment no 1
        // UserToReviewerMapping map1 = new UserToReviewerMapping();
        // map1.setEvaluator(one);
        // map1.setSubmitter(three);
        // map1.setEvaluationForMemberId(assignement1.getId());
        //
        // UserToReviewerMapping map2 = new UserToReviewerMapping();
        // map2.setEvaluator(two);
        // map2.setSubmitter(three);
        // map2.setEvaluationForMemberId(assignement1.getId());
        //
        // userToRevDao.saveUserToReviewerMapping(map1);
        // userToRevDao.saveUserToReviewerMapping(map2);

        /* Update user to reviewer mapping */
        // Member ass2 = memberDao.getMember(new Long(4));
        //
        // User three = userDao.getUserByIdWithAllocatedEvaluatorsMappings(new
        // Long(3));
        // for (UserToReviewerMapping m : three.getAllocatedEvaluators()) {
        // System.out.println("mapping - " + m.getEvaluator().getId() + " - " +
        // m.getSubmitter().getId());
        // // userToRevDao.deleteUserToReviewerMapping(m);
        // }

        // Get Reviewers list and get submitters list
        User three = userDao.getUserByIdWithAllocatedEvaluatorsMappings(new Long(3));
        for (UserToReviewerMapping m : three.getAllocatedEvaluators()) {
            System.out.println("mapping - " + m.getEvaluator().getEmail() + " - " + m.getSubmitter().getEmail());
        }

        // Submitters to evaluate
        User one = userDao.getUserByIdWithSubmittersToEvaluateMappings(new Long(1));
        for (UserToReviewerMapping m : one.getSubmittersToEvaluate()) {
            System.out.println("mapping - " + m.getEvaluator().getEmail() + " - " + m.getSubmitter().getEmail());
        }
    }
}
