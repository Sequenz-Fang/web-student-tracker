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
				</tr>
				<c:forEach var="s" items="${STUDENT_LIST}" >
					<tr>
						<td>${s.getFirstName()}</td>
						<td>${s.getLastName()}</td>
						<td>${s.getEmail()}</td> 
					</tr>	
				</c:forEach>	
				
		
			</table>
		</div>
	</div>
</body>

</html>