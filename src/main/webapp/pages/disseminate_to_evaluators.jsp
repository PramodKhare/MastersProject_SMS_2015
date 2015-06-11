<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="edu.neu.ccis.sms.constants.SessionKeys,javax.servlet.http.HttpSession,java.util.*,javax.servlet.http.HttpServletRequest,edu.neu.ccis.sms.dao.categories.MemberDao,edu.neu.ccis.sms.dao.categories.MemberDaoImpl,edu.neu.ccis.sms.entity.categories.Member"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    /* Load all the submittable Member Details */
    Long activeMemberId = (Long) session
            .getAttribute(SessionKeys.activeMemberId);
    System.out.println("Session activeMemberId - "+activeMemberId);

    //TODO Remove once fully tested
    activeMemberId = new Long(5);

    // Get the MemberDaoImple instance
    MemberDao memberDao = new MemberDaoImpl();
    Set<Member> SubmittableMembers = memberDao
            .findAllSubmittableMembersByParentMemberId(activeMemberId);
    
    System.out.println("Total Number of submittables - "+SubmittableMembers.size());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Update User's Topics of Preferences</title>
        <style>
            body {
              font-family: 'Roboto', sans-serif;
            }
            .form_header {
              font-weight: 100;
              text-align: left;
              font-size: 1.8em;
            }
        </style>

        <script type="text/javascript">
            var i = 1;
            function addField(tableid)
            {
               var row = document.createElement("tr");
               var col1 = document.createElement("td");
               var col2 = document.createElement("td");
               var input = document.createElement("input");

               input.setAttribute("type","text");
               input.setAttribute("name","toifield" + i++ );
               input.setAttribute("value","");
               input.setAttribute("size","50");
               input.setAttribute("maxlength","254");
               input.setAttribute("placeholder","Enter topic of preference");

               var deleteButton = document.createElement("input");
               deleteButton.setAttribute("type","button");
               deleteButton.setAttribute("style","width:150px;");
               deleteButton.setAttribute("value","(-) Delete Field");
               deleteButton.setAttribute("title","Delete this field");
               deleteButton.setAttribute("onclick","removeRow(this)");
               
               col1.appendChild(input);
               col2.appendChild(deleteButton);
               row.appendChild(col1);
               row.appendChild(col2);
               var table = document.getElementById(tableid);
               table.appendChild(row);
            }
            
            function removeRow(x)
            {
                var table = x.parentNode.parentNode.parentNode;
                var rowIndex = x.parentNode.parentNode.rowIndex;
                table.deleteRow(rowIndex);
            }
            
            function resetForm(tableid)
            {
               var table = document.getElementById(tableid);
               var rowsLength = table.rows.length;
               for(var i = rowsLength-1; i>0; i--){
                   table.deleteRow(i);
               }
            }
        </script>
    </head>
    <body>
        <div class="form_header">Update Topics of Preferences</div>
        <hr/>
        <% 
            StringBuffer tois = new StringBuffer();
            for (String toi : toiSet) {
                tois.append(toi).append(", ");
            }
            out.println("<textarea rows='4' placeholder='No topics of preference found.' cols='100' disabled readonly>" + 
                        tois + "</textarea>");
        %>
        <br/>
        <br/>
        <form action="<%=request.getContextPath()%>/UpdateTOIForUser" method="POST">
            <label style="width:400px;display:inline-block;">Enter Topics of preferences</label><br/>
            <table id="fieldTable">
                <tr>
                    <td><input type="text" placeholder="Enter topic of preference" name="toifield0" value="" size=50 maxlength=254/></td>
                </tr>
            </table>
            <br/>
            <table>
                <tr>
                    <td>
                    <input style="width:150px;" title="Add topics field" type="button" value="(+) Add Field" id="button"  onclick="addField('fieldTable')"/>
                    </td>
                    <td><input style="width:150px;" title="Reset form" type="button" value="Reset Form" id="button"  onclick="resetForm('fieldTable')" /></td>
                </tr>
            </table>
            <table>
                <tr>
                    <td><input style="width:150px;" title="Add new topics to previous ones" type="submit" name="submitType" value="Add Topics"/></td>
                    <td><input style="width:150px;" title="Replace old topics with new ones" type="submit" name="submitType" value="Replace Topics"/></td>
                    <td><input style="width:150px;" title="Removes all topics of preference" type="submit" name="submitType" value="Clear All Topics"/></td>
                </tr>
            </table>
        </form>
        <br/>
        <hr/>
    </body>
</html>
