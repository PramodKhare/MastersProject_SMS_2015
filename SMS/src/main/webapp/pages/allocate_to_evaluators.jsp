<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="edu.neu.ccis.sms.constants.SessionKeys,
	edu.neu.ccis.sms.constants.ContextKeys,
	javax.servlet.http.HttpSession,java.util.*,
	javax.servlet.http.HttpServletRequest,
	edu.neu.ccis.sms.dao.categories.MemberDao,
	edu.neu.ccis.sms.dao.categories.MemberDaoImpl,
	edu.neu.ccis.sms.dao.categories.UserToMemberMappingDao,
    edu.neu.ccis.sms.dao.categories.UserToMemberMappingDaoImpl,
	edu.neu.ccis.sms.entity.categories.Member,
	edu.neu.ccis.sms.entity.users.User,
	edu.neu.ccis.sms.entity.users.RoleType"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="layout/header.jsp" />
<jsp:include page="layout/nav.jsp" />
<%
	// Check if there is any message to show in page
	String message = (String) request.getAttribute("message");

	// Get the current user id
	Long userId = (Long) session.getAttribute(SessionKeys.keyUserId);
	
	// Load all the submittable Member Details
	Long activeMemberId = (Long) session.getAttribute(SessionKeys.activeMemberId);
	
	Long submittableMemberId = (Long) session.getAttribute(SessionKeys.activeSubmittableMemberId);

    // Get the MemberDaoImple instance
    MemberDao memberDao = new MemberDaoImpl();
    UserToMemberMappingDao userToMemberMappingDao = new UserToMemberMappingDaoImpl();

    RoleType role = userToMemberMappingDao.getUsersRoleForMember(userId, activeMemberId);
    if(role == null || role == RoleType.SUBMITTER){
        response.sendRedirect("dashboard.jsp");
    }

    Member activeMember = memberDao.getMember(activeMemberId);
    Member submittableMember = memberDao.getMember(submittableMemberId);
    Set<Member> SubmittableMembers = memberDao.findAllSubmittableMembersByParentMemberId(activeMemberId);
    Set<User> evaluators = memberDao.getEvaluatorsForMemberId(activeMemberId);

    String submittableMemberName = submittableMember.getName();
    String activeMemberName = activeMember.getName();

    String evaluatorCustomTerm = "evaluator";
    Map<String, String> roleKeyToRoleMap = (HashMap<String, String>) request.getServletContext().getAttribute(ContextKeys.ROLE_KEY_TO_ROLE);
    if(roleKeyToRoleMap.get("evaluator") != null){
        evaluatorCustomTerm = roleKeyToRoleMap.get("evaluator");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css"/>
		<title>Allocate Submissions To <%=evaluatorCustomTerm%>s for <%=activeMemberName%> - <%=submittableMemberName%></title>
	</head>
	<body>
		<div>Allocate Submissions To <%=evaluatorCustomTerm%>s for <%=activeMemberName%> - <%=submittableMemberName%></div>
        <br/>
		<form action="<%=request.getContextPath()%>/AllocateToEvaluators" method="POST">
			<input type="hidden" id="memberId" name="memberId" value="<%=submittableMemberId%>">
			<table cellpadding="3" border="0">
				<tr>
	                <td>Select number of <%=evaluatorCustomTerm%>s per submission:</td>
	                <td><select id="numberOfEvaluatorsPerSub" name="numberOfEvaluatorsPerSub" style="width: 300px;">
	                        <%
	                            for (int i=1; i<=evaluators.size(); i++) {
	                                out.println("<option value=" + i + ">" + i + "</option>");
	                            }
	                        %>
	                </select></td>
	            </tr>
			</table>
	        <br/>
	        <table>
			    <tr>
	                <td align=center colspan=2>
	                    <input type='submit' value="Allocate <%=evaluatorCustomTerm%>s" style="width: 150px; display: inline-block;"/>
	                </td>
	            </tr>
	        </table>
		</form>
		<br/>
        <% 
            if(message != null) {
                out.println("<div>"+message+"</div><br/>");
            }
        %>
        <a href="<%=request.getContextPath()%>/ViewSubmittableMember?memberId=<%=submittableMemberId%>">Back to <%=submittableMemberName%></a>
        &nbsp;&nbsp;
        <a href="<%=request.getContextPath()%>/ViewRegistrableMember?memberId=<%=activeMemberId%>">Back to <%=activeMemberName%></a>
        <hr />
		<font size=2>
        NOTE : <br/>
            This page is to configure and perform automatic allocation of <%=evaluatorCustomTerm%>s for grading the submissions.
        </font>
        <jsp:include page="layout/footer.jsp" />
    </body>
</html>