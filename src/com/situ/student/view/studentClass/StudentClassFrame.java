package com.situ.student.view.studentClass;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.situ.student.biz.StudentClassManager;
import com.situ.student.entity.StudentClass;
import com.situ.student.util.CallBack;
import com.situ.student.view.StudentFrame;

public class StudentClassFrame {
	List<StudentClass> classList;// 全部班级的集合
	StudentClassManager studentClassManager = new StudentClassManager();// 班级管理控制层
	JTextField nameTextField;
	JTable table;
	JComboBox comboBox;
	
	StudentClass studentClass = new StudentClass();// 建立一个班级对象
	Map<String, Integer> map;// 用于存放studentClass的name和id属性
	String className;// 班级名称
	public void init() {
		// 新建学生管理系统主窗口及主面板
		JFrame frame = new JFrame();
		frame.setTitle("欢迎进入学生信息管理系统");
		frame.setSize(600, 230);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = (JPanel) frame.getContentPane();
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);
		
		// 新建2个子面板，并添加到主面板中去
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER,15,15));
		JLabel classLabel=new JLabel("班级名称:"); 
		panel1.add(classLabel);
		// 从数据库中取得所有班级的信息，放入classList集合中，防止新添加班级后，出现空指针异常
		classList = studentClassManager.findAll();
		comboBox = new JComboBox(); 
		comboBox.addItem("请选择");
		for (int i = 0; i < classList.size(); i++) {
			comboBox.addItem(classList.get(i).getName());
		}
        comboBox.setPreferredSize(new Dimension(180, 30));
        panel1.add(comboBox); 
        mainPanel.add(panel1); 
        
        // 建立map集合将class的name和id属性对应起来
        map = new HashMap<String, Integer>();
		for (int i = 0; i < classList.size(); i++) {
			map.put(classList.get(i).getName(), classList.get(i).getId());
		}
        
		JButton searchButton = new JButton();
		searchButton.setText("查找");
		searchButton.setPreferredSize(new Dimension(60, 30));
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 重新从数据库中获取所有班级集合，放入map集合中
				classList = studentClassManager.findAll();
				for (int i = 0; i < classList.size(); i++) {
					map.put(classList.get(i).getName(), classList.get(i).getId());
				}
				
				// 获取下拉列表的内容
				className = (String) comboBox.getSelectedItem();
				if (!className.equals("请选择")) {
					studentClass.setId(map.get(className));
					studentClass.setName(className);
					StudentFrame studentFrame = new StudentFrame(studentClass);
					studentFrame.init();
				}
			}
		});
		panel1.add(searchButton);
		mainPanel.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
		JButton addButton = new JButton();
		addButton.setText("添加新班级");
		addButton.setPreferredSize(new Dimension(120, 30));
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddClassFrame addClassFrame = new AddClassFrame(new CallBack() {
					@Override
					public void callBack() {
						refreshFrame();
					}
				});
				addClassFrame.addClassName();
			}
		});
		panel2.add(addButton);

		JButton modifyButton = new JButton();
		modifyButton.setText("修改班级名称");
		modifyButton.setPreferredSize(new Dimension(120, 30));
		modifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 重新从数据库中获取所有班级集合，放入map集合中，防止新添加班级后，出现空指针异常
				classList = studentClassManager.findAll();
				for (int i = 0; i < classList.size(); i++) {
					map.put(classList.get(i).getName(), classList.get(i).getId());
				}
				
				className = (String) comboBox.getSelectedItem();
				if (!className.equals("请选择")) {
					studentClass.setId(map.get(className));
					studentClass.setName(className);
					ModifyClassFrame modifyClassFrame = new ModifyClassFrame(new CallBack() {
						@Override
						public void callBack() {
							refreshFrame();
						}
					}, studentClass);
					modifyClassFrame.modifyClassName();
				} else {
					JOptionPane.showMessageDialog(null, "请选中要修改的班级！");
				}
			}
		});
		panel2.add(modifyButton);

		JButton deleteButton = new JButton();
		deleteButton.setText("删除该班级名称");
		deleteButton.setPreferredSize(new Dimension(120, 30));
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				className = (String) comboBox.getSelectedItem();
				if (!className.equals("请选择")) {
					int result = JOptionPane.showConfirmDialog(null, "是否要删除记录?", "标题YES_NO_OPTION", 0);
						if (result == 0) {
						int result2 = JOptionPane.showConfirmDialog(null,
								"删除班级会删除该班级所有学生的信息，是否确认删除？", "标题YES_NO_OPTION",
								0);
						if (result2 == 0) {
							boolean flag = studentClassManager.delete(className);
							String message = "删除成功！";
							if (!flag) {
								message = "删除失败";
							}
							JOptionPane.showMessageDialog(null, message);
							refreshFrame();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "请选中要删除的班级！");
				}
			}
		});
		panel2.add(deleteButton);
		mainPanel.add(panel2);
	
		frame.setVisible(true);
	}

	/**
	 * 增删改完成后，从数据库中重新加载全部班级的数据，并刷新下拉列表中的数据
	 */
	public void refreshFrame() {
		classList = studentClassManager.findAll();
		comboBox.removeAllItems();
		comboBox.addItem("请选择");
		for (int i = 0; i < classList.size(); i++) {
			comboBox.addItem(classList.get(i).getName());
		}
//		comboBox.updateUI();
//		int index = classList.size() - 1;
//		comboBox.addItem(classList.get(index).getName());
	}
	
	public static void main(String[] args) {
		StudentClassFrame studentClassFrame = new StudentClassFrame();
		studentClassFrame.init();

	}
}
