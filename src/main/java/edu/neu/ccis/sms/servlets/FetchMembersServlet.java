package edu.neu.ccis.sms.servlets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import edu.neu.ccis.sms.dao.categories.CategoryDao;
import edu.neu.ccis.sms.dao.categories.CategoryDaoImpl;
import edu.neu.ccis.sms.dao.categories.MemberDao;
import edu.neu.ccis.sms.dao.categories.MemberDaoImpl;
import edu.neu.ccis.sms.entity.categories.Category;
import edu.neu.ccis.sms.entity.categories.Member;

/**
 * Servlet implementation class FetchMembers
 * @author Swapnil Gupta
 *
 */
@WebServlet("/FetchMembers")
public class FetchMembersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String CATEGORY_NAME = "category";
	private static final String PARENT_MEMBER_ID = "parentMember";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchMembersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuffer content = new StringBuffer();
		String parentMemberId = request.getParameter(PARENT_MEMBER_ID);
		String categoryName = request.getParameter(CATEGORY_NAME);
		
		CategoryDao categoryDao = new CategoryDaoImpl();
		Category category = categoryDao.getCategoryByName(categoryName);
		Set<Member> membersSet = new HashSet<Member>();
		
		// For root category we use category as member selector, i.e. get all members for a given category
		// For all other categories we use the category_id and parent_member_id to get its valid members
		if(StringUtils.isEmpty(parentMemberId)) {
			membersSet = category.getMembers();
		} else {
			MemberDao memberDao = new MemberDaoImpl();
			Long categoryId = category.getId();
			membersSet = memberDao.findAllMembersByCategoryAndParentMember(categoryId, Long.parseLong(parentMemberId));
		}
		
		content.append("<option value=' '></option>");
		for (Member member : membersSet) {
			content.append("<option value=" + member.getId() + ">" + member.getName() + "</option>");
		}
		
		response.setContentType("text/html");
		response.getWriter().write(content.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
