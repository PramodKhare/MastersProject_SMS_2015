package edu.neu.ccis.sms;

import edu.neu.ccis.sms.dao.categories.MemberDao;
import edu.neu.ccis.sms.dao.categories.MemberDaoImpl;
import edu.neu.ccis.sms.entity.categories.Member;
import edu.neu.ccis.sms.util.CMISConnector;

public class UpdateCMSMemberDetails {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        CMISConnector.getCMISSession();
        
        MemberDao memberDao = new MemberDaoImpl();
//        Member university = memberDao.getMember(new Long(1));
//        
//        university.setCmsFolderId("2d7d52c1-62b0-44f0-8b8b-18264fea829b");
//        university.setCmsFolderPath("/Northeastern University");
//        
//        memberDao.updateMember(university);

        Member course = memberDao.getMember(new Long(2));
        course.setCmsFolderId("f2c35fc4-7e03-47b9-86cd-85986f904ee3");
        course.setCmsFolderPath("/Northeastern University/CS5500");
        memberDao.updateMember(course);
        
        Member ass1 = memberDao.getMember(new Long(3));
        ass1.setCmsFolderId("b3237ae1-cb0e-4771-bb06-334fc9b55bc8");
        ass1.setCmsFolderPath("/Northeastern University/CS5500/Assignment 1");
        memberDao.updateMember(ass1);
        
        Member ass2 = memberDao.getMember(new Long(4));
        ass2.setCmsFolderId("8492204f-3715-453c-b09d-b2391535aa87");
        ass2.setCmsFolderPath("/Northeastern University/CS5500/Assignment 2");
        memberDao.updateMember(ass2);
    }

}
