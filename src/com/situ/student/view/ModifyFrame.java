package com.situ.student.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
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

public class ModifyFrame {
	JFrame frame;
	StudentManager studentManager = new StudentManager();
	JTextField nameTextField;
	JTextField sexTextField;
	JTextField ageTextField;
	Student student;
	
	JComboBox comboBox;
	Map<String, Integer> map;
	String className;
	int studentId;// 要修改的学生的id
	CallBack callBack;
	// new ModifyFrame时传入callBack和需要修改学生的id
	public ModifyFrame(CallBack callBack, int studentId, String className) {
		this.callBack = callBack;
		this.studentId = studentId;
		this.className = className;
	}
	
	public void modify() {
		// 根据id从数据库中找到对应的学生对象，将选中的学生的属性放入3个文本框中
		student = studentManager.findById(studentId);
		frame = new JFrame();
		frame.setTitle("修改学生");
		frame.setSize(350, 260);
		frame.setLocationRelativeTo(null);
		JPanel mainPanel = (JPanel) frame.getContentPane();
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);
		
		JPanel panel1 = new JPanel();
		JLabel nameLabel = new JLabel();
		nameLabel.setText("姓名");
		panel1.add(nameLabel);
		nameTextField = new JTextField();
		nameTextField.setText(student.getName());// 将选中的学生的属性放入3个文本框中
		nameTextField.setPreferredSize(new Dimension(90, 30));
		panel1.add(nameTextField);
		JPanel panel2 = new JPanel();
		JLabel sexLabel = new JLabel();
		sexLabel.setText("性别");
		panel2.add(sexLabel);
		sexTextField = new JTextField();
		sexTextField.setText(student.getSex());// 将选中的学生的属性放入3个文本框中
		sexTextField.setPreferredSize(new Dimension(90, 30));
		panel2.add(sexTextField);
		JPanel panel3 = new JPanel();
		JLabel ageLabel = new JLabel();
		ageLabel.setText("年龄");
		panel3.add(ageLabel);
		ageTextField = new JTextField();
		ageTextField.setText(String.valueOf(student.getAge()));// 将选中的学生的属性放入3个文本框中
		ageTextField.setPreferredSize(new Dimension(90, 30));
		panel3.add(ageTextField);
		JPanel panel4 = new JPanel();
		
		// 添加班级下拉列表
		JPanel classPanel = new JPanel();
		JLabel classLabel=new JLabel("班级"); 
		classPanel.add(classLabel);
		String[] classArr = {"Java1701", "Java1703","HTML1701",  "UI1701"};
		comboBox =new JComboBox(classArr);
		comboBox.setSelectedItem(className);
        comboBox.setPreferredSize(new Dimension(90, 30));
        classPanel.add(comboBox);
        
    	map = new HashMap<String, Integer>();
		map.put("Java1701", 1);
		map.put("Java1703", 2);
		map.put("HTML1701", 3);
		map.put("UI1701", 4);
		
		JButton saveButton = new JButton();
		saveButton.setText("保存");
		saveButton.setPreferredSize(new Dimension(60,30));
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				student.setName(nameTextField.getText());
				student.setSex(sexTextField.getText());
				student.setAge(Integer.parseInt(ageTextField.getText()));
				student.setId(studentId);
				String className = (String) comboBox.getSelectedItem();
				int class_id = map.get(className);
				student.setClass_id(class_id);
				boolean flag = studentManager.modify(student);// 将修改后的学生的信息存入数据库中
				String message = "保存成功！";
				if (!flag) {
					message = "保存失败！";
				}
				JOptionPane.showMessageDialog(null, message);
				frame.dispose();
				callBack.callBack();
			}
		});
		panel4.add(saveButton);
		
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		mainPanel.add(classPanel);
		mainPanel.add(panel4);
		
		frame.setVisible(true);
	}
}
