package com.situ.student.biz;

import java.util.ArrayList;
import java.util.List;

import com.situ.student.dao.StudentDao;
import com.situ.student.entity.Student;

public class StudentManager {
	List<Student> list = new ArrayList<Student>();
	StudentDao studentDao = new StudentDao();
	
	/**
	 * 传入新增的学生对象，调用DAO层insert方法存入数据库中
	 * @param student
	 * @return
	 */
	public boolean add(Student student) {
		int result = studentDao.insert(student);
		return (result > 0) ? true : false;
	}
	
	/**
	 * 传入修改后的一个学生对象，调用DAO层update方法，修改数据库中的数据
	 * @param student
	 * @return
	 */
	public boolean modify(Student student) {
		int result = studentDao.update(student);
		return (result > 0) ? true : false;
	}
	
	/**
	 * 传入一个需要删除的学生的id，调用DAO层delete方法，根据id删除对应的学生数据
	 * @param id
	 * @return
	 */
	public boolean delete(int id) {
		int result = studentDao.delete(id);
		return (result > 0) ? true : false;
	}

	/**
	 * 调用此方法，返回数据库中全部学生的信息
	 * @return
	 */
	public List<Student> findAll() {
		return studentDao.findAll();
	}
	
	/**
	 * 传入需要修改的学生的id，返回数据库中对应学生的信息
	 * @param id
	 * @return
	 */
	public Student findById(int id) {
		return studentDao.findById(id);
	}
	
	/**
	 * 传入需要查询的一个student对象，调用DAO层的按条件查找学生的方法，并返回查询结果
	 * @param student
	 * @return
	 */
	public List<Student> findByConditionStudent(Student student) {
		return studentDao.findByConditionStudent(student);
	}
}
