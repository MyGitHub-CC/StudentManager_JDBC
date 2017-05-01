package com.situ.student.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.situ.student.entity.StudentClass;

public class StudentClassDao {
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	
	/**
	 * 加载驱动，获取连接
	 */
	public void getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/student02?characterEncoding=utf-8", "root", "root");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 方法重载：增删改时关闭数据库连接
	 * @param connection
	 * @param statement
	 */
	public void close(Connection connection, Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * 方法重载：查询时关闭数据库连接
	 * @param connection
	 * @param statement
	 * @param resultSet
	 */
	public void close(Connection connection, Statement statement, ResultSet resultSet) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<StudentClass> findAll() {
		List<StudentClass> classList = new ArrayList<StudentClass>();
		try {
			getConnection();
			statement = connection.createStatement();
			String sql = "select * from class";
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				StudentClass studentClass = new StudentClass();
				studentClass.setId(resultSet.getInt("id"));
				studentClass.setName(resultSet.getString("name"));
				classList.add(studentClass);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement, resultSet);
		}
		return classList;
	}
	
	public StudentClass findById(int classId) {
		StudentClass studentClass = new StudentClass();
		try {
			getConnection();
			statement = connection.createStatement();
			String sql = "select * from class where id=" + classId;
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				studentClass.setId(resultSet.getInt("id"));
				studentClass.setName(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement, resultSet);
		}
		return studentClass;
	}
	
	public int insert(StudentClass studentClass) {
		int result = 0;
		try {
			getConnection();
			statement = connection.createStatement();
			String sql = "insert into class(id,name) values ("
					+ studentClass.getId() + ",'" + studentClass.getName()
					+ "');";
			result = statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement);
		}
		return result;
	}
	
	public int update(StudentClass studentClass) {
		int result = 0;
		try {
			getConnection();
			String sql = "update class set name=? where id=" + studentClass.getId();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, studentClass.getName());
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement);
		}
		return result;
	}
	
	public int delete(String className) {
		int result = 0;
		try {
			getConnection();
			statement = connection.createStatement();
			
			String studentSql = "delete from student WHERE class_id=(select id from class where name='" + className + "');";
			result = statement.executeUpdate(studentSql);
			
			String classSql = "delete from class WHERE name='" + className + "';";
			result = statement.executeUpdate(classSql);
			
//			// 删除班级信息后重置id,class表中的id是student表中的外键，必须先删除student表中的class_id列，所以无法实现
//			String sqlDeleteId = "alter table class drop column id;"; // 删除数据库中的id列
//			statement.executeUpdate(sqlDeleteId);
//			String sqlId = "alter table class add COLUMN id INT PRIMARY KEY AUTO_INCREMENT"; 
//			statement.executeUpdate(sqlId); // 重新生成id列，使id从1开始递增
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement);
		}
		return result;
	}
	
}
