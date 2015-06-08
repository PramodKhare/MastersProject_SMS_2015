<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Welcome user - SMS</title>
	</head>
	<body>
		<h1>Welcome to Submission Management System</h1>
		
		<h3>${entityId}s where you are: ${submitterId}</h3>
		<ul>
			<li>Course 1</li>
			<li>Course 2</li>
		</ul>
		
		<h3>${entityId}s where you are: ${evaluatorId}</h3>
		<ul>
			<li>Course 3</li>
			<li>Course 2</li>
		</ul>
		
		<h3>${entityId}s where you are: ${conductor.id}</h3>
		<ul>
			<li>Course 7</li>
			<li>Course 5</li>
		</ul>
		
		<h3>Register for a ${entityId}</h3>
		
		<select name="courseList" onchange="">
		    <!--<c:forEach items="${dd1options}" var="option">
		        <option value="${option.key}" ${param.dd1 == option.key ? 'selected' : ''}>${option.value}</option>
		    </c:forEach>
		    -->
		    <option value="Course 1">Course 1</option>
		    <option value="Course 1">Course 12</option>
		    <option value="Course 1">Course 10</option>
		</select>
		
		<br/><br/>
		<a href="${pageContext.request.contextPath}/CreateMember">Create New Member</a>
	</body>
</html>