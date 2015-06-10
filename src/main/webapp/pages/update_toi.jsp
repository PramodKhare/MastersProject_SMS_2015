<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8" import="edu.neu.ccis.sms.constants.SessionKeys,javax.servlet.http.HttpSession,java.util.*,javax.servlet.http.HttpServletRequest,edu.neu.ccis.sms.dao.users.UserDao,edu.neu.ccis.sms.dao.users.UserDaoImpl,edu.neu.ccis.sms.entity.users.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    /* Load all current Topics of interest */
    Long userId = (Long) session.getAttribute(SessionKeys.keyUserId);
    System.out.println("Session userId - "+userId);
    
    // Get the UserDaoImpl instance
    UserDao userDao = new UserDaoImpl();
    User one = userDao.getUser(userId);

    Set<String> toiSet = one.getTopicsOfInterest();

    System.out.println("Total Number of toiSet - "+toiSet.size());
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Update Topics of Interest</title>
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
               var input = document.createElement("input");

               input.setAttribute("type","text");
               input.setAttribute("name","toifield" + i++ );
               input.setAttribute("value","");
               input.setAttribute("size","50");
               input.setAttribute("maxlength","254");
               input.setAttribute("placeholder","Enter topics of interest");

               col1.appendChild(input);
               row.appendChild(col1);
               var table = document.getElementById(tableid);
               table.appendChild(row);
            }
            
            function resetForm(tableid)
            {
               var table = document.getElementById(tableid);
               for(; i>1; i--){
                    table.deleteRow(i-1);
               }
            }
        </script>
    </head>
    <body>
        <div class="form_header">Update Topics of Interest</div>
        <hr/>
        <form action="<%=request.getContextPath()%>/UpdateTOIForUser" method="POST">
            <label style="width:400px;display:inline-block;">Enter Topics of interest</label><br/>
            <font size="2">(Important Note: Replaces old topics of interest with new ones)</font><br/>
            <table id="fieldTable">
                <tr><td><input type="text" placeholder="Enter topics of interest" name="toifield0" value="" size=50 maxlength=254/></td></tr>
            </table>
            <br/>
            <table>
                <tr>
                    <td><input style="width:150px;" type="button" value="(+) Add" id="button"  onclick="addField('fieldTable')" /></td>
                </tr>
            </table>
            <table>
                <tr>
                    <td><input style="width:150px;" type="submit" /></td>
                    <td><input style="width:150px;" type="button" value="Reset" id="button"  onclick="resetForm('fieldTable')" /></td>
                </tr>
            </table>
        </form>
        <br/>
        <hr/>
    </body>
</html>
