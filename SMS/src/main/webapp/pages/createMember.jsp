<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<script src="https://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
		<script type="text/javascript">
		
			// Convert server side hashmap using jstl to javascript map
			// Was having a issue where javascript was interpreting hashmap as a flattened list
		 	var categoryToParentMap = { };
			<c:forEach var="category" items="${categoryToParent}">
				categoryToParentMap['${category.key}'] = '${category.value}';
			</c:forEach>
			
			// Convert server side hashmap using jstl to javascript map
			// Was having a issue where javascript was interpreting hashmap as a flattened list
			var labels = [];
			var categoryToHtmlLabels = {};
			<c:forEach var="category" items="${categoryToHtmlLabels}">
				var count = 0;
				<c:forEach var="label" items="${category.value}">
					labels[count++] = '${label}';
				</c:forEach>
				categoryToHtmlLabels['${category.key}'] = labels;
				labels = [];
			</c:forEach>
			
			function printMap(mapToPrint) {
				for (var i = 0, keys = Object.keys(mapToPrint), ii = keys.length; i < ii; i++) {
		 			console.log('key(' + keys[i] + ') : value(' + mapToPrint[keys[i]] + ')');
		 		}
			}
			
		</script>
		<script src="${pageContext.request.contextPath}/resources/js/createMember.js" type="text/javascript"></script>
	</head>
	<body>
		<div id="container">
			<div id="selectCategoryForNewMember">
				<strong><label for="categories">Select a member to create: </label></strong>
				<select id="categories">
				<option value="member">-select member-</option>
				    <c:forEach items="${categoryToParent}" var="category">
				        <option value="${category.key}">${category.key}</option>
				    </c:forEach>
				</select>
			</div>
			
			<div id="selectExistingMembersOfCategories">
				<!-- Dynamically generate id <category-name>List for drop down, 
				     listing all available members for current category (populated using ajax)-->
				<c:forEach items="${categoryToParent}" var="category">
					<br/>
			        <div id="${category.key}" style="display:none">
			        	<strong><label for="${category.key}List">Select a ${category.key}: </label></strong>
			        	<select id="${category.key}List"></select>
			        </div>
			    </c:forEach>
			</div>
			
			<form id="newMemberForm" action="${pageContext.request.contextPath}/SaveMember" method="GET">
				<fieldset id="newMemberFormFieldSet" style="display:none; width:400px">
					<legend id="newMemberLegend"></legend>  
				    <div id="newMemberFields" ></div>
				    <input type="submit" id="addMember" value="Submit">
				</fieldset>
			</form>
			<div id="result"></div>
		</div>
	</body>
</html>