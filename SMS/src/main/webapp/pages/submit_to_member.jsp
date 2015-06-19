<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="edu.neu.ccis.sms.constants.SessionKeys,javax.servlet.http.HttpSession,
    java.util.*,javax.servlet.http.HttpServletRequest,edu.neu.ccis.sms.dao.categories.MemberDao,
    edu.neu.ccis.sms.dao.categories.MemberDaoImpl,edu.neu.ccis.sms.entity.categories.Member"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="member/templates/header.jsp" />
<%
    // Get the current user id
    Long userId = (Long) session.getAttribute(SessionKeys.keyUserId);
    System.out.println("Session userId - " + userId);

    // Load all the submittable Member Details
    Long activeMemberId = (Long) session.getAttribute(SessionKeys.activeMemberId);
    System.out.println("Session activeMemberId - " + activeMemberId);

    Long submittableMemberId = (Long) session.getAttribute(SessionKeys.activeSubmittableMemberId);
    System.out.println("Session activeSubmittableMemberId - " + activeMemberId);

    // Get the MemberDaoImple instance
    MemberDao memberDao = new MemberDaoImpl();
    Member activeMember = memberDao.getMember(activeMemberId);
    Set<Member> members = memberDao.findAllSubmittableMembersByParentMemberId(activeMemberId);
    List<Member> submittableMembers = new ArrayList<Member>(members);
    Collections.sort(submittableMembers);
    System.out.println("Total Number of submittables - "+submittableMembers.size());
    String activeMemberName = activeMember.getName();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Upload Submission</title>
        <style>
            body {
              font-family: 'Roboto', sans-serif;
            }
            .form_header {
              font-weight: 100;
              text-align: left;
              font-size: 1.8em;
            }
            td.doc { color: navy; font-size: 150%; }
            td.label { font-size: 150%; }
            th.label { font-size: 150%; }
            div { color: black; font-size: 150%; }
            div.error { color: red; font-size: 200%; }
        </style>
    </head>
    <body>
        <div class="form_header">Document Submission for <%=activeMemberName%></div>
        <hr/>
        <form action="<%=request.getContextPath()%>/UploadForMember"
            method="POST" enctype="multipart/form-data">
            <table cellpadding="3" border="0">
                <tr>
                    <td class='label'>Submit Document for: </td>
                <td>
                <select id="member" name="memberId" style="width: 250px;">
                    <% 
                        for (Member member : submittableMembers) {
                            out.println("<option value=" + member.getId() + ">"
                                    + member.getName() + "</option>");
                        }
                    %>
                </select></td>
            </tr>
                <tr>
                    <td class='label'>File to Upload:</td>
                    <td><input type='file' style="width:400px;display:inline-block;"name='uploadedfile' /></td>
                </tr>
                <tr>
                    <td align=center colspan=2>
                        <input type='submit' style="width:150px;display:inline-block;" id="uploadFile" value='Upload file' />
                    </td>
                </tr>
            </table>
        </form>
        <hr/>
    </body>
</html>