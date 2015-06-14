package edu.neu.ccis.sms;

import edu.neu.ccis.sms.dao.categories.CategoryDao;
import edu.neu.ccis.sms.dao.categories.CategoryDaoImpl;
import edu.neu.ccis.sms.dao.categories.MemberDao;
import edu.neu.ccis.sms.dao.categories.MemberDaoImpl;
import edu.neu.ccis.sms.dao.users.UserDao;
import edu.neu.ccis.sms.dao.users.UserDaoImpl;
import edu.neu.ccis.sms.entity.categories.Category;
import edu.neu.ccis.sms.entity.categories.CategoryAttribute;
import edu.neu.ccis.sms.entity.categories.Member;
import edu.neu.ccis.sms.entity.categories.MemberAttribute;
import edu.neu.ccis.sms.entity.users.User;

public class SaveCatMemberApp {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // Save the Category and Member
        CategoryDao catDao = new CategoryDaoImpl();
//
//        Category cat = new Category();
//        cat.setName("Institute");
//        cat.setRegisterable(false);
//        cat.setSubmittable(false);
//
//        CategoryAttribute catat1 = new CategoryAttribute();
//        catat1.setName("Institute Name");
//        catat1.setType("String");
//        catat1.setCategory(cat);
//        cat.addAttributes(catat1);
//
//        CategoryAttribute catat2 = new CategoryAttribute();
//        catat2.setName("Institute Address");
//        catat2.setType("String");
//        catat2.setCategory(cat);
//        cat.addAttributes(catat2);
//
//        catDao.saveCategory(cat);
//
//        // Course Category
//        Category course = new Category();
//        course.setName("Course");
//        course.setRegisterable(true);
//        course.setSubmittable(false);
//        course.setParentCategory(cat);
//
//        CategoryAttribute courseat1 = new CategoryAttribute();
//        courseat1.setName("Course Name");
//        courseat1.setType("String");
//        courseat1.setCategory(course);
//        course.addAttributes(courseat1);
//
//        CategoryAttribute courseat2 = new CategoryAttribute();
//        courseat2.setName("Course Info");
//        courseat2.setType("String");
//        courseat2.setCategory(course);
//        course.addAttributes(courseat2);
//
//        catDao.saveCategory(course);
//
//        // Assignment Category
//        Category assign = new Category();
//        assign.setName("Assignment");
//        assign.setRegisterable(false);
//        assign.setSubmittable(true);
//        assign.setParentCategory(course);
//
//        CategoryAttribute assat1 = new CategoryAttribute();
//        assat1.setName("Course Name");
//        assat1.setType("String");
//        assat1.setCategory(assign);
//        assign.addAttributes(assat1);
//
//        CategoryAttribute assat2 = new CategoryAttribute();
//        assat2.setName("Course Info");
//        assat2.setType("String");
//        assat2.setCategory(assign);
//        assign.addAttributes(assat2);
//
//        catDao.saveCategory(assign);
//
        UserDao userDao = new UserDaoImpl();
        // Get the Conductor of the Member
        User one = userDao.getUser(new Long(1));
//
//        // Create a member out of category
//        Member m = new Member();
//        m.setCategory(cat);
//        m.setName("Northeastern University");
//
//        MemberAttribute memat1 = new MemberAttribute();
//        memat1.setName("Institute Name");
//        memat1.setValue("Northeastern University");
//        memat1.setMember(m);
//        m.addAttributes(memat1);
//
//        MemberAttribute memat2 = new MemberAttribute();
//        memat2.setName("Institute Description");
//        memat2.setValue("Northeastern Uni is Boston based university");
//        memat2.setMember(m);
//        m.addAttributes(memat2);
//
        MemberDao memDao = new MemberDaoImpl();
//        memDao.saveMember(m, one);
//
//        // Course Member
//        Member c = new Member();
//        c.setCategory(course);
//        c.setName("CS5500");
//        c.setParentMember(m);
//
//        MemberAttribute cat1 = new MemberAttribute();
//        cat1.setName("Course Name");
//        cat1.setValue("CS5500 MSD");
//        cat1.setMember(c);
//        c.addAttributes(cat1);
//
//        MemberAttribute cat2 = new MemberAttribute();
//        cat2.setName("Course Description");
//        cat2.setValue("MSD Description");
//        cat2.setMember(c);
//        c.addAttributes(cat2);
//
//        memDao.saveMember(c, one);
//
//        // Assignment Member
//        Member assm = new Member();
//        assm.setCategory(assign);
//        assm.setName("Assignment 1");
//        assm.setParentMember(c);
//
//        MemberAttribute assmt1 = new MemberAttribute();
//        assmt1.setName("Assignement Name");
//        assmt1.setValue("CS5500 MSD Assignement 1 Class Diagram");
//        assmt1.setMember(assm);
//        assm.addAttributes(assmt1);
//
//        MemberAttribute assmt2 = new MemberAttribute();
//        assmt2.setName("Assignment Description");
//        assmt2.setValue("Draw Class Diagram");
//        assmt2.setMember(assm);
//        assm.addAttributes(assmt2);
//
//        memDao.saveMember(assm, one);
//
//        Member assm2 = new Member();
//        assm2.setCategory(assign);
//        assm2.setName("Assignment 2");
//        assm2.setParentMember(c);
//
//        MemberAttribute assm2t1 = new MemberAttribute();
//        assm2t1.setName("Assignement Name");
//        assm2t1.setValue("CS5500 MSD Assignement 2 State Chart Diagram");
//        assm2t1.setMember(assm2);
//        assm2.addAttributes(assm2t1);
//
//        MemberAttribute assm2t2 = new MemberAttribute();
//        assm2t2.setName("Assignment Description");
//        assm2t2.setValue("Draw State Chart Diagram");
//        assm2t2.setMember(assm2);
//        assm2.addAttributes(assm2t2);
//
//        memDao.saveMember(assm2, one);

        Category assignCat = catDao.getCategory(new Long(3));
        Member parentMem = memDao.getMember(new Long(2));

        Member assm3 = new Member();
        assm3.setCategory(assignCat);
        assm3.setName("Assignment 4");
        assm3.setParentMember(parentMem);

        MemberAttribute assm3t1 = new MemberAttribute();
        assm3t1.setName("Assignement Name");
        assm3t1.setValue("CS5500 MSD Assignement 4 Sequence Diagram");
        assm3t1.setMember(assm3);
        assm3.addAttributes(assm3t1);

        MemberAttribute assm3t2 = new MemberAttribute();
        assm3t2.setName("Assignment Description");
        assm3t2.setValue("Draw Sequence4 Diagram");
        assm3t2.setMember(assm3);
        assm3.addAttributes(assm3t2);

        memDao.saveMember(assm3, one);
    }

}
