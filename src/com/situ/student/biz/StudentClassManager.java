package com.situ.student.biz;

import java.util.ArrayList;
import java.util.List;

import com.situ.student.dao.StudentClassDao;
import com.situ.student.entity.StudentClass;


public class StudentClassManager {
	List<StudentClass> list = new ArrayList<StudentClass>();
	StudentClassDao studentClassDao = new StudentClassDao();
	
	public List<StudentClass> findAll() {
		return studentClassDao.findAll();
	}
	
	public StudentClass findById(int classId) {
		return studentClassDao.findById(classId);
	}
	
	public boolean addClass(StudentClass studentClass) {
		int result = studentClassDao.insert(studentClass);
		return (result > 0) ? true : false;
	}
	
	public boolean modifyClass(StudentClass studentClass) {
		int result = studentClassDao.update(studentClass);
		return (result > 0) ? true : false;
	}
	
	public boolean delete(String className) {
		int result = studentClassDao.delete(className);
		return (result > 0) ? true : false;
	}
}
