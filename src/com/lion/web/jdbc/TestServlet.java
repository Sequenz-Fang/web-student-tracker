package com.lion.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	// define data source/connection pool for resource injection
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// step1: set up printwriter
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		// step2: get connection to database
		
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		
		try {
			conn = dataSource.getConnection();
			// step3: create sql statements
			
			String sql = "select * from student";
			st = conn.createStatement();
			
			// step4: execute sql query			
			res = st.executeQuery(sql);
			
			// step5: process the result set			
			while (res.next()) {
				String email = res.getString("email");
				out.println(email);
			}
		} catch (Exception e){
			e.printStackTrace();
			
		}
		
	}

	

}









