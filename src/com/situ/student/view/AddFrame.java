package com.situ.student.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.situ.student.biz.StudentManager;
import com.situ.student.entity.Student;
import com.situ.student.util.CallBack;

public class AddFrame {
	List<Student> list;
	StudentManager studentManager = new StudentManager();
	JFrame frame;
	JTextField nameTextField;
	JTextField sexTextField;
	JTextField ageTextField;
	JComboBox comboBox;
//	List<StudentClass> classList;// 从数据库中获取所有班级的名称
//	StudentClassManager studentClassManager = new StudentClassManager();
	
	Map<String, Integer> map; // 用于存放班级名称与student的class_id属性
	CallBack callBack;
	int class_id;
	public AddFrame(CallBack callBack, int class_id) {
		this.callBack = callBack;
		this.class_id = class_id;
	}

	public void add() {
		// 新建添加学生的窗口，并建立主面板
		frame = new JFrame();
		frame.setTitle("添加学生");
		frame.setSize(350, 260);
		frame.setLocationRelativeTo(null);
		JPanel mainPanel = (JPanel) frame.getContentPane();
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		// 新建5个子面板，并添加到添加学生的新窗口中的主面板中
		JPanel panel1 = new JPanel();
		JLabel nameLabel = new JLabel();
		nameLabel.setText("姓名");
		panel1.add(nameLabel);
		nameTextField = new JTextField();
		nameTextField.setPreferredSize(new Dimension(90, 30));
		panel1.add(nameTextField);
		JPanel panel2 = new JPanel();
		JLabel sexLabel = new JLabel();
		sexLabel.setText("性别");
		panel2.add(sexLabel);
		sexTextField = new JTextField();
		sexTextField.setPreferredSize(new Dimension(90, 30));
		panel2.add(sexTextField);
		JPanel panel3 = new JPanel();
		JLabel ageLabel = new JLabel();
		ageLabel.setText("年龄");
		panel3.add(ageLabel);
		ageTextField = new JTextField();
		ageTextField.setPreferredSize(new Dimension(90, 30));
		panel3.add(ageTextField);
		JPanel panel4 = new JPanel();
		
//		 添加班级下拉列表
//		JPanel classPanel = new JPanel();
//		JLabel classLabel=new JLabel("班级"); 
//		classPanel.add(classLabel);
//		comboBox =new JComboBox();  
//        comboBox.setPreferredSize(new Dimension(90, 30));
//        classPanel.add(comboBox);
        
//        // 建一个map集合，用于将班级名称与student中的class_id对应，便于对数据库进行操作
//    	map = new HashMap<String, Integer>();
//		map.put("Java1701", 1);
//		map.put("Java1703", 2);
//		map.put("HTML1701", 3);
//		map.put("UI1701", 4);

		JButton saveButton = new JButton();
		saveButton.setText("保存");
		saveButton.setPreferredSize(new Dimension(60, 30));
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(nameTextField.getText().equals("")
						|| sexTextField.getText().equals("") || ageTextField
						.getText().equals(""))) {
					Student student = new Student();
					student.setName(nameTextField.getText());
					student.setSex(sexTextField.getText());
					student.setAge(Integer.parseInt(ageTextField.getText()));
					student.setClass_id(class_id);
					boolean flag = studentManager.add(student);
					String message = "保存成功！";
					if (!flag) {
						message = "保存失败！";
					}
					JOptionPane.showMessageDialog(null, message);
					frame.dispose();
					callBack.callBack();
					
//					String className = (String) comboBox.getSelectedItem();
//					if (!className.equals("请选择")) {
//						student.setClass_id(map.get(className));
//						//调用控制层add方法，将新增的学生信息添加到数据库中
//						boolean flag = studentManager.add(student);
//						String message = "保存成功！";
//						if (!flag) {
//							message = "保存失败！";
//						}
//						JOptionPane.showMessageDialog(null, message);
//						frame.dispose();
//						callBack.callBack();
//					} else {
//						JOptionPane.showMessageDialog(null, "请选择班级");
//					}
				}
			}
		});
		panel4.add(saveButton);

		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
//		mainPanel.add(classPanel); 
		mainPanel.add(panel4);
		
		frame.setVisible(true);
	}
}
