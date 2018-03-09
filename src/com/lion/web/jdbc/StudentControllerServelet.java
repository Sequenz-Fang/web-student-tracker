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
@WebServlet("/StudentControllerServelet")
public class StudentControllerServelet extends HttpServlet {
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
			// list the student in mvc fashion
			listStudents(request, response);		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
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
