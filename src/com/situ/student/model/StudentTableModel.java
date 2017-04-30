package com.situ.student.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.situ.student.entity.Student;


/**
 * 
 * JTable、JTree 是界面所有空间中最为复杂的，需要用一个model来控制展示的数据，此处模仿即可。
 */
@SuppressWarnings("serial")
public class StudentTableModel extends AbstractTableModel {
	String[] columnNames = { "序号", "姓名", "性别", "年龄" };

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
		}
		return null;

	}

	public void setData(List<Student> data) {
		if (data == null) {
			throw new IllegalArgumentException("参数data不能为null。");
		}
		this.data = data;

		fireTableDataChanged();
	}
}