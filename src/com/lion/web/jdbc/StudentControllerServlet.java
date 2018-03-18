package com.lion.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServelet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StudentDataUtil studentDataUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	
       
	@Override
	public void init() throws ServletException {
		super.init();
		
		// create student db util and pass in connection pool/data source.
		try {
			studentDataUtil = new StudentDataUtil(dataSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// read the command parameter
			String theCommand = request.getParameter("command");
			// if the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LIST";
			}
			// route to the appropriate method
			switch(theCommand) {
			case "LIST":
				listStudents(request, response);
				break;
			
			case "ADD":
				addStudent(request, response);
				break;
				
			case "LOAD":
				loadStudent(request, response);
				break;
				
			case "UPDATE":
				updateStudent(request, response);
				break;
				
			case "DELETE":
				deleteStudent(request, response);
				break;
				
			default:
				listStudents(request, response);
			}	
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// read student id from form data
		// delete student from db
		// send users back to list-student page
		String studentId = request.getParameter("studentId");
		studentDataUtil.deleteStudent(studentId);
		listStudents(request, response);
		
	}



	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info from data 
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email = request.getParameter("email");
		
		// create a new Student object
		Student newStudent = new Student(id, firstName, lastName, email);
		
		// perform update on a database
		studentDataUtil.updateStudent(newStudent);
		// send them back to list student page
		listStudents(request, response);
		
		
	}



	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read the student from form data
		String requestId = request.getParameter("studentId");
		
		// get the student id from database
		Student student = studentDataUtil.getStudent(requestId);
		//place student in request attribute
		request.setAttribute("THE_STUDENT", student);
		
		//send to jsp page		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
		
	}



	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info from form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// create a new Student Object
		Student theStudent = new Student(firstName, lastName, email);
		// add the student to the database
		
		StudentDataUtil.addStudent(theStudent);
		
		// send back to main page (the student list)
		listStudents(request, response);
	}



	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get students from db util
		List<Student> students = studentDataUtil.getStudents();
		
		// add students to the request
		request.setAttribute("STUDENT_LIST", students);
		
		// send to JSP view!!
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		
		dispatcher.forward(request, response);
	}


}
