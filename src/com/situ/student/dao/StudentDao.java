package com.situ.student.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.situ.student.entity.Student;

public class StudentDao {
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

	/**
	 * 添加单个学生的信息到数据库中：传入新添加的学生的信息，添加到数据库中
	 * @param student：新添加的学生的信息
	 * @return 添加成功返回：1，添加失败返回：0
	 */
	public int insert(Student student) {
		int result = 0;
		try {
			getConnection();
			statement = connection.createStatement();
			String sql = "insert into student(name,sex,age) values ('"
					+ student.getName() + "','" + student.getSex() + "',"
					+ student.getAge() + ");";
			result = statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement);
		}
		return result;
	}
	
	/**
	 * 传入一个修改后的学生对象，修改数据库中对应的id的学生的数据
	 * @param student:修改后的学生对象
	 * @return 修改成功返回：1，修改失败返回：0
	 */
	public int update(Student student) {
		int result = 0;
		try {
			getConnection();
			String sql = "update student set name=?, sex=?, age=? where id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, student.getName());
			preparedStatement.setString(2, student.getSex());
			preparedStatement.setInt(3, student.getAge());
			preparedStatement.setInt(4, student.getId());
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement);
		}
		return result;
	}
	
//	/**
//	 * 删除单个学生的信息：传入要删除的学生的id，从数据库中删除对应的学生信息
//	 * @param id: 要删除的学生的id
//	 * @return 删除成功返回:1 删除失败返回：0
//	 */
//	public int delete(int id) {
//		int result = 0;
//		try {
//			getConnection();
//			statement = connection.createStatement();
//			String sql = "delete from student WHERE id =" + id;
//			result = statement.executeUpdate(sql);
//			
//			// 删除学生信息后重置id
//			String sqlDeleteId = "alter table student drop column id;"; // 删除数据库中的id列
//			statement.executeUpdate(sqlDeleteId);
//			String sqlId = "alter table student add COLUMN id INT PRIMARY KEY AUTO_INCREMENT"; 
//			statement.executeUpdate(sqlId); // 重新生成id列，使id从1开始递增
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			close(connection, statement);
//		}
//		return result;
//	}

	/**
	 * 删除所有被选中的学生的信息：传入需要删除的所有学生的id数组，根据id删除数据库中对应学生的信息
	 * @param idArr: 需要删除的所有学生的id数组
	 * @return 删除成功返回：idArr数组的长度值，删除失败返回：0
	 */
	public int delete(int[] idArr) {
		int result = 0;
		try {
			getConnection();
			statement = connection.createStatement();
			for (int i = 0; i < idArr.length; i++) {
				// 主面板中的第一行对应的rowIndex=0，故对应的学生的id=rowIndex+1
				String sql = "delete from student WHERE id =" + (idArr[i]+1);
				result = statement.executeUpdate(sql);
			}
			
			// 删除学生信息后重置id
			String sqlDeleteId = "alter table student drop column id;"; // 删除数据库中的id列
			statement.executeUpdate(sqlDeleteId);
			String sqlId = "alter table student add COLUMN id INT PRIMARY KEY AUTO_INCREMENT"; 
			statement.executeUpdate(sqlId); // 重新生成id列，使id从1开始递增
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement);
		}
		return result;
	}
	
	/**
	 * 从数据库中查询全部学生的信息
	 * @return 数据库中查询全部学生的信息
	 */
	public List<Student> findAll(String className) {
		List<Student> list = new ArrayList<Student>();
		try {
			getConnection();
			statement = connection.createStatement();
			String sql = "select * from student where class_id=(select id from class where name='" + className + "')";
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Student student = new Student();
				student.setId(resultSet.getInt("id"));
				student.setName(resultSet.getString("name"));
				student.setSex(resultSet.getString("sex"));
				student.setAge(resultSet.getInt("age"));
				list.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement, resultSet);
		}
		return list;
	}

	/**
	 * 传入需要修改的学生的id，返回对应id的学生的信息
	 * @param id 需要修改的学生的id
	 * @return 对应id的学生的信息
	 */
	public Student findById(int id) {
		Student student = new Student();
		try {
			getConnection();
			statement = connection.createStatement();
			String sql = "select * from student where id=" + id;
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				student.setId(resultSet.getInt("id"));
				student.setName(resultSet.getString("name"));
				student.setSex(resultSet.getString("sex"));
				student.setAge(resultSet.getInt("age"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement, resultSet);
		}
		return student;
	}
	
	/**
	 * 查询符合条件的所有学生的信息：传入一个student对象，从数据库中查询符合条件的学生的集合并返回这个集合
	 * @param conditionStudent: 对应条件的学生对象
	 * @return 数据库中所有符合条件的学生的集合
	 */
	public List<Student> findByConditionStudent(Student conditionStudent, String className) {
		List<Student> list = new ArrayList<Student>();
		String sql;
		try {
			getConnection();
			statement = connection.createStatement();
			// 当传入的student的对象的三个属性都为空字符串（age=-1）时，即三个文本框都为空时，查询全部学生的信息
			sql = "select * from student where 1=1 ";
			if (!conditionStudent.getName().equals("")) {
				sql += " and name ='" + conditionStudent.getName() + "'";
			}
			if (!conditionStudent.getSex().equals("")) {
				sql += " and sex ='" + conditionStudent.getSex() + "'";
			}
			if (conditionStudent.getAge() > 0) {
				sql += " and age ='" + conditionStudent.getAge() + "'";
			}
			sql += " and class_id=(SELECT id FROM class WHERE NAME='" + className + "');";
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Student student = new Student();
				student.setId(resultSet.getInt("id"));
				student.setName(resultSet.getString("name"));
				student.setSex(resultSet.getString("sex"));
				student.setAge(resultSet.getInt("age"));
				list.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, statement, resultSet);
		}
		return list;
	}

}
