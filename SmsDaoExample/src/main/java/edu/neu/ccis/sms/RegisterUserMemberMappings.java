package edu.neu.ccis.sms;

import edu.neu.ccis.sms.dao.categories.MemberDao;
import edu.neu.ccis.sms.dao.categories.MemberDaoImpl;
import edu.neu.ccis.sms.dao.categories.UserToMemberMappingDao;
import edu.neu.ccis.sms.dao.categories.UserToMemberMappingDaoImpl;
import edu.neu.ccis.sms.dao.users.UserDao;
import edu.neu.ccis.sms.dao.users.UserDaoImpl;
import edu.neu.ccis.sms.entity.categories.Member;
import edu.neu.ccis.sms.entity.categories.UserToMemberMapping;
import edu.neu.ccis.sms.entity.users.RoleType;
import edu.neu.ccis.sms.entity.users.User;

public class RegisterUserMemberMappings {

    /**
     * @param args
     */
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();
        User u1 = userDao.getUser(new Long(7));
        //User u2 = userDao.getUser(new Long(2));

        MemberDao memDao = new MemberDaoImpl();
        Member m1 = memDao.getMember(new Long(2));

        UserToMemberMapping mapping = new UserToMemberMapping();
        mapping.setMember(m1);
        mapping.setUser(u1);
        mapping.setActive(true);
        mapping.setRole(RoleType.EVALUATOR);

        /*UserToMemberMapping mapping2 = new UserToMemberMapping();
        mapping2.setMember(m1);
        mapping2.setUser(u2);
        mapping2.setActive(true);
        mapping2.setRole(RoleType.EVALUATOR);*/

        UserToMemberMappingDao mappingDao = new UserToMemberMappingDaoImpl();
        mappingDao.saveUserToMemberMapping(mapping);
        //mappingDao.saveUserToMemberMapping(mapping2);
    }

    public static void main2(String[] args) {
        UserDao userDao = new UserDaoImpl();
        User u3 = userDao.getUser(new Long(8));
        User u4 = userDao.getUser(new Long(9));
        User u5 = userDao.getUser(new Long(10));
        User u6 = userDao.getUser(new Long(11));

        MemberDao memDao = new MemberDaoImpl();
        Member m1 = memDao.getMember(new Long(2));

        UserToMemberMapping mapping = new UserToMemberMapping();
        mapping.setMember(m1);
        mapping.setUser(u3);
        mapping.setActive(true);
        mapping.setRole(RoleType.SUBMITTER);

        UserToMemberMapping mapping2 = new UserToMemberMapping();
        mapping2.setMember(m1);
        mapping2.setUser(u4);
        mapping2.setActive(true);
        mapping2.setRole(RoleType.SUBMITTER);

        UserToMemberMapping mapping3 = new UserToMemberMapping();
        mapping3.setMember(m1);
        mapping3.setUser(u5);
        mapping3.setActive(true);
        mapping3.setRole(RoleType.SUBMITTER);

        UserToMemberMapping mapping4 = new UserToMemberMapping();
        mapping4.setMember(m1);
        mapping4.setUser(u6);
        mapping4.setActive(true);
        mapping4.setRole(RoleType.SUBMITTER);

        UserToMemberMappingDao mappingDao = new UserToMemberMappingDaoImpl();
        mappingDao.saveUserToMemberMapping(mapping);
        mappingDao.saveUserToMemberMapping(mapping2);
        mappingDao.saveUserToMemberMapping(mapping3);
        mappingDao.saveUserToMemberMapping(mapping4);
    }

}
