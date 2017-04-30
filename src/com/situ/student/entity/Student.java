package com.situ.student.entity;

import java.io.Serializable;
/**
 * 创建学生实体类，属性和数据库中的列名一一对应
 * @author Administrator
 */
@SuppressWarnings("serial")
public class Student implements Serializable {
	private int id;
	private String name;
	private String sex;
	private int age;

	public Student() {
		super();
	}

	public Student(String name, String sex, int age) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
	}

	public Student(int id, String name, String sex, int age) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", sex=" + sex
				+ ", age=" + age + "]";
	}

}
