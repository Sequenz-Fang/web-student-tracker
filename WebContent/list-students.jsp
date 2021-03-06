<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Student tracker app</title>
	<link type = "text/css" rel = "stylesheet" href = "css/style.css">
</head>

<body>
	<div id = "container">
		<div id = "content">
		
			<!-- Add a new student button -->
			<input type="button" value="Add Student" onclick="window.location.href='add-student-form.jsp'; return false;"
					class='add-student-botton'/>
					
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				<c:forEach var="s" items="${STUDENT_LIST}" >
					<!-- set up new student links -->
					<c:url var="templink" value="StudentControllerServlet">
						<c:param name="command" value="LOAD"/>
						<c:param name="studentId" value= "${s.getId()}"/>
					</c:url>
					<c:url var="deletelink" value="StudentControllerServlet">
						<c:param name="command" value="DELETE"/>
						<c:param name="studentId" value="${s.getId()}"/>
					</c:url>
					<tr>
						<td>${s.getFirstName()}</td>
						<td>${s.getLastName()}</td>
						<td>${s.getEmail()}</td>
						<td>
							<a href="${templink}">Update</a>
							| 
							<a href="${deletelink}"
							   onclick="if(!(confirm('Are you sure delete the student?'))) return false">
							   Delete</a>
						</td>
					</tr>	
				</c:forEach>	
				
		
			</table>
		</div>
	</div>
</body>

</html>