package com.situ.student.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.situ.student.entity.Student;


/**
 * 
 * JTable、JTree 是界面所有空间中最为复杂的，需要用一个model来控制展示的数据，此处模仿即可。
 */
@SuppressWarnings("serial")
public class StudentTableModel extends AbstractTableModel {
	String[] columnNames = { "序号", "姓名", "性别", "年龄" ,"班级"}; // 自己加班级一列

	private List<Student> data;

	public StudentTableModel(List<Student> data) {
		this.data = data;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		// 根据实际情况返回列数
		return columnNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		// 根据实际情况返回列名
		return columnNames[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// 拿到list中的数据
		Student stu = data.get(rowIndex);
		if (columnIndex == 0) {
			return rowIndex + 1;
		} else if (columnIndex == 1) {
			return stu.getName();
		} else if (columnIndex == 2) {
			return stu.getSex();
		} else if (columnIndex == 3) {
			return stu.getAge();
		} else if (columnIndex == 4) {
			Map<Integer, String> map = new HashMap<Integer, String>();
			map.put(1, "Java1701");
			map.put(2, "Java1703");
			map.put(3, "HTML1701");
			map.put(4, "UI1701");
			String className = map.get(stu.getClass_id());
			return className; // 自己加的通过class_id取得对应的班级名称
		}
		return null;

	}
	
	
	// 自己写的一个方法，用于取得被选中要修改的学生的所在班级
	// 通过被选中修改对象的行号，获得对应的学生的对象，再通过该学生对象的class_id获取学生所在的班级名称
	public String getCellName(int rowIndex) {
		// 取得被选中学生的id
		int stuId = data.get(rowIndex).getClass_id();
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "Java1701");
		map.put(2, "Java1703");
		map.put(3, "HTML1701");
		map.put(4, "UI1701");
		String className = map.get(stuId);
		
		return className;
	}

	public void setData(List<Student> data) {
		if (data == null) {
			throw new IllegalArgumentException("参数data不能为null。");
		}
		this.data = data;

		fireTableDataChanged();
	}
}