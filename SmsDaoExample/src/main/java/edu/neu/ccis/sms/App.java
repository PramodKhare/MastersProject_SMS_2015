package edu.neu.ccis.sms;

/**
 * Main application class
 */
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.neu.ccis.sms.dao.categories.CategoryDao;
import edu.neu.ccis.sms.dao.categories.CategoryDaoImpl;
import edu.neu.ccis.sms.dao.categories.MemberDao;
import edu.neu.ccis.sms.dao.categories.MemberDaoImpl;
import edu.neu.ccis.sms.dao.categories.UserToMemberMappingDao;
import edu.neu.ccis.sms.dao.categories.UserToMemberMappingDaoImpl;
import edu.neu.ccis.sms.dao.submissions.DocumentDao;
import edu.neu.ccis.sms.dao.submissions.DocumentDaoImpl;
import edu.neu.ccis.sms.dao.submissions.EvaluationDao;
import edu.neu.ccis.sms.dao.submissions.EvaluationDaoImpl;
import edu.neu.ccis.sms.dao.users.TeamDao;
import edu.neu.ccis.sms.dao.users.TeamDaoImpl;
import edu.neu.ccis.sms.dao.users.UserDao;
import edu.neu.ccis.sms.dao.users.UserDaoImpl;
import edu.neu.ccis.sms.entity.categories.Category;
import edu.neu.ccis.sms.entity.categories.CategoryAttribute;
import edu.neu.ccis.sms.entity.categories.Member;
import edu.neu.ccis.sms.entity.categories.MemberAttribute;
import edu.neu.ccis.sms.entity.categories.UserToMemberMapping;
import edu.neu.ccis.sms.entity.submissions.Document;
import edu.neu.ccis.sms.entity.submissions.Evaluation;
import edu.neu.ccis.sms.entity.users.RoleType;
import edu.neu.ccis.sms.entity.users.Team;
import edu.neu.ccis.sms.entity.users.User;
import edu.neu.ccis.sms.util.HibernateUtil;

public class App {
    public static void main(String[] args) {

//        UserDao userDao = new UserDaoImpl();
//        User one = new User();
//        one.setFirstname("Pramod");
//        one.setLastname("Khare");
//        one.setEmail("khare.pr@husky.neu.edu");
//        one.setUsername("khare.pr");
//        one.setPassword("pass1");
//        one.addTopicsOfInterest("Hiking");
//        one.addTopicsOfInterest("Biking");
//        userDao.saveUser(one);
//
//        User two = new User();
//        two.setFirstname("Prasad");
//        two.setLastname("Khare");
//        two.setEmail("khare.pr2@husky.neu.edu");
//        two.setUsername("khare.pr2");
//        two.setPassword("pass2");
//        two.addTopicsOfInterest("Hiking1");
//        two.addTopicsOfInterest("Biking1");
//        two.addMyConflictsOfInterestWithUsers(one);
//        userDao.saveUser(two);
//
//        // Update the Conflict for one user
//        one.addMyConflictsOfInterestWithUsers(two);
//        userDao.updateUser(one);
//        System.out.println("Two users added!!");
//
//        one = userDao.getUser(new Long(1));
//
//        Set<String> toiSet = one.getTopicsOfInterest();
//        // Remove the old topics of preferences
//        toiSet.clear();
//        userDao.updateUser(one);

        // System.out.println("Starting the DaoImpl");
        // TeamDao teamDao = new TeamDaoImpl();
        // Team team = new Team();
        // team.setName("Team1");
        // teamDao.saveTeam(team);
        //
        // UserDao userDao = new UserDaoImpl();
        // User one = new User();
        // one.setFirstname("Pramod");
        // one.setLastname("Khare");
        // one.setEmail("khare.pr@husky.neu.edu");
        // one.setUsername("khare.pr");
        // one.setPassword("pass1");
        // userDao.saveUser(one);
        //
        // User two = new User();
        // two.setFirstname("Prasad");
        // two.setLastname("Khare");
        // two.setEmail("khare.pr2@husky.neu.edu");
        // two.setUsername("khare.pr2");
        // two.setPassword("pass2");
        // userDao.saveUser(two);
        // System.out.println("Two users added!!");
        //
        // // Search user by username
        // User ppkk = userDao.findUserByUsername("khare.pr");
        // if (ppkk == null) {
        // System.out.println("No user found!");
        // } else {
        // System.out.println("user found - " + ppkk.getPassword());
        // }
        //
        // ppkk = userDao.findUserByUsernameAndPassword("khare.pr", "pass1");
        // if (ppkk == null) {
        // System.out.println("No user found!");
        // } else {
        // System.out.println("user - n - pass - found - "
        // + ppkk.getPassword());
        // }

        // // one.setActivation("false");
        // // two.setActivation("true");
        // // one.setTeam(team);
        // // two.setTeam(team);
        // // userDao.updateUser(one);
        // // userDao.updateUser(two);
        // // System.out.println("Updated both the users!");
        //
        // // userDao.deleteUser(one);
        // // userDao.deleteUser(two);
        // // System.out.println("Deleted both users");
        // //
        // // teamDao.deleteTeam(team);
        // // System.out.println("Team deleted!");
        // //
        //
        // // System.out.println("Hibernate one to many (Annotation)");
        // // Session session = HibernateUtil.getSessionFactory().openSession();
        // // session.beginTransaction();
        // //
        // // Team team = new Team();
        // // team.setName("Team1");
        // // session.save(team);
        // //
        // // User one = new User();
        // // one.setFirstname("Pramod");
        // // one.setLastname("Khare");
        // // one.setEmail("khare.pr@husky.neu.edu");
        // // one.setRole(RoleType.SUBMITTER);
        // // one.setUsername("khare.pr");
        // // one.setTeam(team);
        // //
        // // User two = new User();
        // // two.setFirstname("Prasad");
        // // two.setLastname("Khare");
        // // two.setEmail("khare.pr2@husky.neu.edu");
        // // two.setRole(RoleType.SUBMITTER);
        // // two.setUsername("khare.pr2");
        // // two.setTeam(team);
        // //
        // // session.save(one);
        // // session.save(two);
        //
        // // // Update the objects team.getTeamMembers().add(one);
        // // team.getTeamMembers().add(two);
        // //
        // // session.update(one);
        // // session.update(two);
        //
        // // session.getTransaction().commit();
        // // System.out.println("Done");
        //
        // /**
        // * Query all Categories
        // */
        // // Query q = session.createQuery("From Employee ");
        // // List<Employee> resultList = q.list();
        // // System.out.println("num of employess:" + resultList.size());
        // // for (Employee next : resultList) {
        // // System.out.println("next employee: " + next);
        // // }
        //
        // // Save the Category and Member
         CategoryDao catDao = new CategoryDaoImpl();
        
         Category cat = new Category();
         cat.setName("Institute");
         cat.setRegisterable(false);
         cat.setSubmittable(false);
        
         CategoryAttribute catat1 = new CategoryAttribute();
         catat1.setName("Institute Name");
         catat1.setType("String");
         catat1.setCategory(cat);
         cat.addAttributes(catat1);
        
         CategoryAttribute catat2 = new CategoryAttribute();
         catat2.setName("Institute Address");
         catat2.setType("String");
         catat2.setCategory(cat);
         cat.addAttributes(catat2);
        
         catDao.saveCategory(cat);
        
         // Course Category
         Category course = new Category();
         course.setName("Course");
         course.setRegisterable(true);
         course.setSubmittable(false);
         course.setParentCategory(cat);
        
         CategoryAttribute courseat1 = new CategoryAttribute();
         courseat1.setName("Course Name");
         courseat1.setType("String");
         courseat1.setCategory(course);
         course.addAttributes(courseat1);
        
         CategoryAttribute courseat2 = new CategoryAttribute();
         courseat2.setName("Course Info");
         courseat2.setType("String");
         courseat2.setCategory(course);
         course.addAttributes(courseat2);
        
         catDao.saveCategory(course);
        
         // Assignment Category
         Category assign = new Category();
         assign.setName("Assignment");
         assign.setRegisterable(false);
         assign.setSubmittable(true);
         assign.setParentCategory(course);
        
         CategoryAttribute assat1 = new CategoryAttribute();
         assat1.setName("Course Name");
         assat1.setType("String");
         assat1.setCategory(assign);
         assign.addAttributes(assat1);
        
         CategoryAttribute assat2 = new CategoryAttribute();
         assat2.setName("Course Info");
         assat2.setType("String");
         assat2.setCategory(assign);
         assign.addAttributes(assat2);
        
         catDao.saveCategory(assign);
        
         UserDao userDao = new UserDaoImpl();
         // Get the Conductor of the Member
         User one = userDao.getUser(new Long(1));
         
         // Create a member out of category
         Member m = new Member();
         m.setCategory(cat);
         m.setName("Northeastern University");
         
         MemberAttribute memat1 = new MemberAttribute();
         memat1.setName("Institute Name");
         memat1.setValue("Northeastern University");
         memat1.setMember(m);
         m.addAttributes(memat1);
        
         MemberAttribute memat2 = new MemberAttribute();
         memat2.setName("Institute Description");
         memat2.setValue("Northeastern Uni is Boston based university");
         memat2.setMember(m);
         m.addAttributes(memat2);
        
         MemberDao memDao = new MemberDaoImpl();
         memDao.saveMember(m, one);
        
         // Course Member
         Member c = new Member();
         c.setCategory(course);
         c.setName("CS5500");
         c.setParentMember(m);
        
         MemberAttribute cat1 = new MemberAttribute();
         cat1.setName("Course Name");
         cat1.setValue("CS5500 MSD");
         cat1.setMember(c);
         c.addAttributes(cat1);
        
         MemberAttribute cat2 = new MemberAttribute();
         cat2.setName("Course Description");
         cat2.setValue("MSD Description");
         cat2.setMember(c);
         c.addAttributes(cat2);
        
         memDao.saveMember(c, one);
        
         // Assignment Member
         Member assm = new Member();
         assm.setCategory(assign);
         assm.setName("Assignment 1");
         assm.setParentMember(c);
        
         MemberAttribute assmt1 = new MemberAttribute();
         assmt1.setName("Assignement Name");
         assmt1.setValue("CS5500 MSD Assignement 1 Class Diagram");
         assmt1.setMember(assm);
         assm.addAttributes(assmt1);
        
         MemberAttribute assmt2 = new MemberAttribute();
         assmt2.setName("Assignment Description");
         assmt2.setValue("Draw Class Diagram");
         assmt2.setMember(assm);
         assm.addAttributes(assmt2);
        
         memDao.saveMember(assm, one);
        
         Member assm2 = new Member();
         assm2.setCategory(assign);
         assm2.setName("Assignment 2");
         assm2.setParentMember(c);
        
         MemberAttribute assm2t1 = new MemberAttribute();
         assm2t1.setName("Assignement Name");
         assm2t1.setValue("CS5500 MSD Assignement 2 State Chart Diagram");
         assm2t1.setMember(assm2);
         assm2.addAttributes(assm2t1);
        
         MemberAttribute assm2t2 = new MemberAttribute();
         assm2t2.setName("Assignment Description");
         assm2t2.setValue("Draw State Chart Diagram");
         assm2t2.setMember(assm2);
         assm2.addAttributes(assm2t2);
        
         memDao.saveMember(assm2, one);

        // Get the submittable children for parentMemberId
        /*MemberDao memDao = new MemberDaoImpl();
        Set<Member> submittable = memDao.findAllSubmittableMembersByParentMemberId(new Long(4));
        if (submittable != null || !submittable.isEmpty()) {
            for (Member m : submittable) {
                System.out.println("Mmeber Name - " + m.getName());
            }
        } else {
            System.out.println("No Submittable Memebers found");
        }
        */

        // UserDao userDao = new UserDaoImpl();
        // User u1 = userDao.getUser(new Long(1));
        //
        // MemberDao memDao = new MemberDaoImpl();
        // Member m1 = memDao.getMember(new Long(1));
        //
        // UserToMemberMapping mapping = new UserToMemberMapping();
        // mapping.setMember(m1);
        // mapping.setUser(u1);
        // mapping.setActive(true);
        // mapping.setRole(RoleType.CONDUCTOR);
        //
        // UserToMemberMappingDao mappingDao = new UserToMemberMappingDaoImpl();
        // mappingDao.saveUserToMemberMapping(mapping);

        // User two = new User();
        // two.setFirstname("Prasad4");
        // two.setLastname("Khare4");
        // two.setEmail("khare.pr4@husky.neu.edu");
        // two.setUsername("khare.pr4");
        // two.setPassword("pass4");
        //
        // Session currentSession = HibernateUtil.getSessionFactory()
        // .openSession();
        // Transaction currentTransaction = currentSession.beginTransaction();
        //
        // Long userId = (Long) currentSession.save(two);
        // System.out.println("User ID - "+userId);
        //
        // currentTransaction.commit();
        // currentSession.close();
        //
        // System.out.println("Two users added!!");

        // Document doc = new Document();
        // doc.setCmsDocId("1");
        // doc.setCmsDocumentPath("/cs5500/assignment1");
        // doc.setContentType("pdf");
        // doc.setFilename("assign1.pdf");
        // doc.setSubmittedForMember(m);
        // doc.setSubmittedOnTimestamp(System.currentTimeMillis());
        // doc.setSubmittedFromRemoteAddress("192.168.255.225");
        //
        // Evaluation ev1 = new Evaluation();
        // ev1.setEvaluatedBy(one);
        // ev1.setEvaluatedOnTimestamp(System.currentTimeMillis());
        // ev1.setEvaluationFor(doc);
        // ev1.setOutOfTotal(100f);
        // ev1.setResult(87f);
        //
        // Evaluation ev2 = new Evaluation();
        // ev2.setEvaluatedBy(two);
        // ev2.setEvaluatedOnTimestamp(System.currentTimeMillis());
        // ev2.setEvaluationFor(doc);
        // ev2.setOutOfTotal(100f);
        // ev2.setResult(93f);
        //
        // Set<Evaluation> evals = new HashSet<Evaluation>();
        // evals.add(ev1);
        // evals.add(ev2);
        // doc.setEvaluations(evals);
        //
        // DocumentDao docDao = new DocumentDaoImpl();
        // docDao.saveDocument(doc);
        //
        // EvaluationDao evDao = new EvaluationDaoImpl();
        // evDao.saveEvaluation(ev1);
        // evDao.saveEvaluation(ev2);
    }
}
