package com.lion.web.jdbc;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * Represent the data accessor object (DAO) in the db application.
 * @author Lion's laptop
 *
 */
public class StudentDataUtil {
	private DataSource dataSource;

	public StudentDataUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public List<Student> getStudents() throws Exception {
		List<Student> students = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		ResultSet res = null;
		try {
			// get a connection
			conn = dataSource.getConnection();
			
			// create sql statement
			String sql = "select * from students order by last_name";
			st = conn.createStatement();
			
			//execute query
			res = st.executeQuery(sql);
			
			// process result set
			while (res.next()) {
			
				// retrieve data from result set row
				int id = res.getInt("id");
				String firstName = res.getString("firstName");
				String lastName = res.getString("lastName");
				String email = res.getString("email");
				
				// create new student object
				Student tmpStudent = new Student(id, firstName, lastName, email);
				students.add(tmpStudent);
				
			}
			
			// close connection
			close(conn, st, res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		
		return students;
	}
	
	private void close(Connection conn, Statement st, ResultSet res) {
		try {
			if (res != null) {
				res.close();
			}
			if (st != null) {
				st.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
