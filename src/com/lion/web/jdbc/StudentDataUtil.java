package com.lion.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * 
 * Represent the data accessor object (DAO) in the db application.
 * @author Lion's laptop
 *
 */
public class StudentDataUtil {
	private static DataSource dataSource;

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
			String sql = "select * from student order by last_name";
			st = conn.createStatement();
			
			//execute query
			res = st.executeQuery(sql);
			
			// process result set
			while (res.next()) {
			
				// retrieve data from result set row
				int id = res.getInt("id");
				String firstName = res.getString("first_name");
				String lastName = res.getString("last_name");
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
	
	private static void close(Connection conn, Statement st, ResultSet res) {
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
	
	public static void addStudent(Student theStudent) throws Exception {
		
		Connection conn = null;
		PreparedStatement st = null;
		try {
			// get db connection
			conn = dataSource.getConnection();
			// create sql for insert
			String sql = "insert into student "+"(first_name, last_name, email) "+"values (?, ?, ?)";
			st = conn.prepareStatement(sql);
			
			// set the param values for the student
			st.setString(1, theStudent.getFirstName());
			st.setString(2, theStudent.getLastName());
			st.setString(3, theStudent.getEmail());
			
			// execute sql insert
			st.execute();
			
		} 
		finally {
			// cleanup JDBC objects
			close(conn, st, null);
		}
		
		
	}
	public Student getStudent(String requestId) throws Exception {
		Student student = null;
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet res = null;
		int studentId;
		try {
			// convert student id to int
			studentId = Integer.parseInt(requestId);
			
			// get connection to database
			conn = dataSource.getConnection();
			
			// create sql to get selected student
			String sql = "select * from student where id=?";
			
			// create prepared statement
			st = conn.prepareStatement(sql);
			// set params
			st.setInt(1, studentId);
			// execute query
			res = st.executeQuery();
			// retrieve data from result set
			if (res.next()) {
				String firstName = res.getString("first_name");
				String lastName = res.getString("last_name");
				String email = res.getString("email");
				
				student = new Student(studentId, firstName, lastName, email);
			} else {
				throw new Exception("Cannot find a student id: "+studentId);
			}
			
			return student;
			
		} finally {
			// close jdbc objects
			close(conn, st, res);
		}
		
	}
	public void updateStudent(Student newStudent) throws Exception {
		
		Connection conn = null;
		PreparedStatement st = null;
		try {
			// get db connection
			// create sql update statement
			conn = dataSource.getConnection();
			String sql = "update student " + "set first_name=?, last_name=?, email=? "
						+ "where id=?";
			st = conn.prepareStatement(sql);
			// set parameters in 1, 2, 3 column
			st.setString(1, newStudent.getFirstName());
			st.setString(2, newStudent.getLastName());
			st.setString(3, newStudent.getEmail());
			st.setInt(4, newStudent.getId());
			
			st.execute();
		} finally {
			close(conn, st, null);
		}
		
		
	}
	public void deleteStudent(String studentId) throws Exception {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			// convert student id to int
			int id = Integer.parseInt(studentId);
			
			// connect to db
			conn = dataSource.getConnection();
			// create sql
			String sql = "delete from student where id=?";
			// prepare statement
			st = conn.prepareStatement(sql);
			// set params
			st.setInt(1, id);
			
			// execute statement
			st.execute();
		} finally {
			close(conn, st, null);
		}
	}
}










