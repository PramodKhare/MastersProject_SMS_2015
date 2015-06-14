package edu.neu.ccis.sms;

import edu.neu.ccis.sms.dao.categories.MemberDao;
import edu.neu.ccis.sms.dao.categories.MemberDaoImpl;
import edu.neu.ccis.sms.entity.categories.Member;

public class MemberDaoTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        MemberDao memberDao = new MemberDaoImpl();
        // Member m = memberDao.getMemberByIdWithUserMappingsAndSubmissions(new
        // Long(1));
        // Member m = memberDao.getMember(new Long(2));
        Member m = memberDao.getMemberByIdWithUserMappings(new Long(2));
        if (m != null) {
            System.out.println("Successfully retrieved - " + m.getUserToMemberMappings().size());
        } else {
            System.out.println("No such member found!");
        }
    }

}
