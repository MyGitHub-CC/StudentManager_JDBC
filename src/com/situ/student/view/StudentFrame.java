package com.situ.student.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.situ.student.biz.StudentClassManager;
import com.situ.student.biz.StudentManager;
import com.situ.student.entity.Student;
import com.situ.student.entity.StudentClass;
import com.situ.student.model.StudentTableModel;
import com.situ.student.util.CallBack;

public class StudentFrame {
	List<Student> list;
	List<StudentClass> classList;
	StudentTableModel studentTableModel;
	StudentManager studentManager = new StudentManager();
	StudentClassManager studentClassManager = new StudentClassManager();
	JTextField nameTextField;
	JTextField sexTextField;
	JTextField ageTextField;
	JTable table;
	JComboBox comboBox;
//	Map<String, Integer> map;// 用于存放班级名称与student的class_id属性
	
	public StudentFrame() {
		super();
	}
	
	StudentClass searchClass;// 要查询的班级
	public StudentFrame(StudentClass searchClass) {
		this.searchClass = searchClass;
	}
	
	public void init() {
		// 新建学生管理系统主窗口及主面板
		JFrame frame = new JFrame();
		frame.setTitle("欢迎进入学生信息管理系统----" + searchClass.getName() + "班");
		frame.setSize(650, 550);
		frame.setLocationRelativeTo(null);
		JPanel mainPanel = (JPanel) frame.getContentPane();
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);
		
		// 新建3+1个子面板，并添加到主面板中去
//		// 添加班级下拉列表
//		JPanel classPanel = new JPanel();
//		classPanel.setLayout(new FlowLayout(FlowLayout.CENTER,15,15));
//		JLabel classLabel=new JLabel("班级名称"); 
//		classPanel.add(classLabel);
//		classList = studentClassManager.findAll();
//		comboBox =new JComboBox(); 
//		comboBox.addItem("请选择");
//		for (int i = 0; i < classList.size(); i++) {
//			comboBox.addItem(classList.get(i).getName());
//		}
//		comboBox.addItem("添加新班级");
//        comboBox.setPreferredSize(new Dimension(180, 30));
//        comboBox.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				String text = (String) comboBox.getSelectedItem();
//				if (text.equals("添加新班级")) {
//					AddClassFrame addClassFrame = new AddClassFrame(new CallBack() {
//						@Override
//						public void callBack() {
//							refreshFrame();
//						}
//					});
//					addClassFrame.addClass();
//				}
//			}
//		});
//        classPanel.add(comboBox); 
//        mainPanel.add(classPanel); 
        

		// 新建3个标签（姓名，性别，年龄）及1个查找按钮，添加到第一个子面板中
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER,15,15));
		JLabel nameLabel = new JLabel();
		nameLabel.setText("姓名");
		panel1.add(nameLabel);
		nameTextField = new JTextField();
		nameTextField.setPreferredSize(new Dimension(90, 30));
		panel1.add(nameTextField);
		JLabel sexLabel = new JLabel();
		sexLabel.setText("性别");
		panel1.add(sexLabel);
		sexTextField = new JTextField();
		sexTextField.setPreferredSize(new Dimension(90, 30));
		panel1.add(sexTextField);
		JLabel ageLabel = new JLabel();
		ageLabel.setText("年龄");
		panel1.add(ageLabel);
		ageTextField = new JTextField();
		ageTextField.setPreferredSize(new Dimension(90, 30));
		panel1.add(ageTextField);
		
//		// 用一个map集合将班级名称与student中的class_id对应起来，便于将className转换为class_id
//		map = new HashMap<String, Integer>();
//		classList = studentClassManager.findAll();
//		for (int i = 0; i < classList.size(); i++) {
//			map.put(classList.get(i).getName(), classList.get(i).getId());
//		}
		
		JButton searchButton = new JButton();
		searchButton.setText("查找");
		searchButton.setPreferredSize(new Dimension(60, 30));
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
				String sex = sexTextField.getText();
				int age = -1;
				if (!ageTextField.getText().equals("")) {
					age = Integer.parseInt(ageTextField.getText());
				}
				// 接收用户从文本框中输入的内容，并赋值给student对象，作为查询学生的条件
				Student student = new Student(name, sex, age, searchClass.getId());
				list = studentManager.findByConditionStudent(student); // 接收查询符合条件的学生集合
				refreshFrame(list); // 按条件查找后得到结果，刷新主面板
			}
		});
		panel1.add(searchButton);
		mainPanel.add(panel1);

		JPanel panel2 = new JPanel();
		// 初始化时，调用控制层方法，传一个班级的名称，查询在该班级的所有学生信息并显示
		list = studentManager.findAll(searchClass.getName());
		studentTableModel = new StudentTableModel(list);
		table = new JTable(studentTableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 300));
		panel2.add(scrollPane);
		mainPanel.add(panel2);

		JPanel panel3 = new JPanel();
		panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 80, 20));
		JButton addButton = new JButton();
		addButton.setText("新增");
		addButton.setPreferredSize(new Dimension(60, 30));
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// new AddFrame时，传两个参数callBack和当前班级的id，将该id默认赋值给新添加的学生
				AddFrame addFrame = new AddFrame(new CallBack() {
					@Override
					public void callBack() {
						refreshFrame();
					}
				}, searchClass.getId()); 
				addFrame.add();
			}
		});
		panel3.add(addButton);

		JButton modifyButton = new JButton();
		modifyButton.setText("修改");
		modifyButton.setPreferredSize(new Dimension(60, 30));
		modifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 获取选中的学生的行号，如果未选择，则rowIndex默认是-1
				int rowIndex = table.getSelectedRow();
//				int class_id = map.get(className);
				if (rowIndex > -1) {
//					className = map.get(list.get(rowIndex).getClass_id());
					// *重要*：当前主面板的内容，即是当前的list集合，被选中的行号刚好等于list集合中的学生的下标
					// 通过下标取得对应的学生对象，再通过学生对象去得该学生的id
					int studentId = list.get(rowIndex).getId();
					// 通过被选中修改对象的行号，获得对应的学生的对象，再通过该学生对象的class_id获取学生所在的班级名称
					ModifyFrame modifyFrame = new ModifyFrame(new CallBack() {
						@Override
						public void callBack() {
							refreshFrame();
						}
					}, studentId, searchClass.getName());
					modifyFrame.modify();
				} else {
					JOptionPane.showMessageDialog(null, "请选中要修改的学生！");
				}
			}
		});
		panel3.add(modifyButton);

		JButton deleteButton = new JButton();
		deleteButton.setText("删除");
		deleteButton.setPreferredSize(new Dimension(60, 30));
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 获取所有选中的学生的行号数组，如果未选择，则rowsIndex数组的长度默认是0
				int[] rowsIndex = table.getSelectedRows();
				if (rowsIndex.length > 0) {
					int result = JOptionPane.showConfirmDialog(null, "是否要删除记录?", "标题YES_NO_OPTION", 0);
					if (result == 0) {
						// 和修改类似，通过所有被选中的行号，取得list集合中对应下标的学生对象
						// 再通过学生对象的获取对应学生的id属性，即数据库中对应学生的id序号
						int[] studentId = new int[rowsIndex.length];
						for (int i = 0; i < rowsIndex.length; i++) {
							studentId[i] = list.get(rowsIndex[i]).getId();
						}
						// 调用控制层delete的重载方法，删除所有选中的学生信息
						boolean flag = studentManager.delete(studentId);
						String message = "删除成功！";
						if (!flag) {
							message = "删除失败";
						}
						JOptionPane.showMessageDialog(null, message);
						refreshFrame();
					}
				} else {
					JOptionPane.showMessageDialog(null, "请选中要删除的学生！");
				}
				
				
				// 删除单个学生：获取选中的学生的行号，如果未选择，则rowIndex默认是-1，从0开始
//				int rowIndex = table.getSelectedRow();	
//				if (rowsIndex > -1) {
//					int result = JOptionPane.showConfirmDialog(null,
//							"是否要删除记录?", "标题YES_NO_OPTION", 0);
//					if (result == 0) {
//						// 主面板中的第一行对应的rowIndex=0，故对应的学生的id=rowIndex+1
//						boolean flag = studentManager.delete(rowIndex + 1);
//						String message = "删除成功！";
//						if (!flag) {
//							message = "删除失败";
//						}
//						JOptionPane.showMessageDialog(null, message);
//						refreshFrame();
//					}
//				} else {
//					JOptionPane.showMessageDialog(null, "请选中要删除的学生！");
//				}
			}
		});
		panel3.add(deleteButton);
		mainPanel.add(panel3);

		frame.setVisible(true);
	}

	/**
	 * 增删改完成后，从数据库中重新加载全部学生的数据，并刷新主面板中的数据
	 */
	public void refreshFrame() {
		list = studentManager.findAll(searchClass.getName());
		studentTableModel.setData(list);
	}

	/**
	 * 查询符合条件的所有学生的集合，放到主面板上并刷新
	 * @param conditionList
	 */
	public void refreshFrame(List<Student> conditionList) {
		studentTableModel.setData(conditionList);
	}

//	public static void main(String[] args) {
//		StudentFrame studentFrame = new StudentFrame();
//		studentFrame.init();
//
//	}
}
