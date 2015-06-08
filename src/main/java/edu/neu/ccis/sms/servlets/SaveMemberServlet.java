package edu.neu.ccis.sms.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
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
import edu.neu.ccis.sms.entity.categories.MemberAttribute;
import edu.neu.ccis.sms.entity.categories.Member;

/**
 * Servlet implementation class SaveMemberServlet
 */
@WebServlet("/SaveMember")
public class SaveMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Fields required for any member, correspond to hidden fields in "Create new member form"
	private static final String CATEGORY_NAME = "categoryName";
	private static final String PARENT_MEMEBER_ID = "parentMemberId";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		printRequestParameters(request);
        ArrayList<String> paramNames = Collections.list(request.getParameterNames());
		
		MemberDao memberDao = new MemberDaoImpl();
		Member newMember = new Member();
		
		String memberName = paramNames.get(0);
		newMember.setName(request.getParameter(memberName));
		
		CategoryDao categoryDao = new CategoryDaoImpl();
		Category category = categoryDao.getCategoryByName(request.getParameter(CATEGORY_NAME));
		newMember.setCategory(category);
		paramNames.remove(CATEGORY_NAME);
		
		String parentMemberId = request.getParameter(PARENT_MEMEBER_ID);
		if(StringUtils.isEmpty(parentMemberId)) {
			newMember.setParentMember(null);
		} else {
			MemberDao parentMemberDao = new MemberDaoImpl();
			Member parentMember = parentMemberDao.getMember(Long.parseLong(parentMemberId));
			newMember.setParentMember(parentMember);
		}
		paramNames.remove(PARENT_MEMEBER_ID);
		
		Set<MemberAttribute> memberAttributes = new HashSet<MemberAttribute>();
		for (String paramName : paramNames) {
            String paramValue = request.getParameter(paramName);;
            memberAttributes.add(new MemberAttribute(paramName, paramValue, newMember));
        }
		newMember.setAttributes(memberAttributes);
		
		memberDao.saveMember(newMember);
		
		StringBuffer content = new StringBuffer();
		content.append("Data updated into database");
		response.setContentType("text/html");
		response.getWriter().write(content.toString());
	}
	
	private void printRequestParameters(HttpServletRequest request) {
		Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            System.out.println(paramName + ":" + paramValue);   
        }
	}
}
