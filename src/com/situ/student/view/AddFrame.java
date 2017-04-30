package com.situ.student.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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

	CallBack callBack;
	public AddFrame(CallBack callBack) {
		this.callBack = callBack;
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

		// 新建4个子面板，并添加到添加学生的新窗口中的主面板中
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
					//调用控制层add方法，将新增的学生信息添加到数据库中
					boolean flag = studentManager.add(student);
					String message = "保存成功！";
					if (!flag) {
						message = "保存失败！";
					}
					JOptionPane.showMessageDialog(null, message);
					frame.dispose();
					callBack.callBack();
				}
			}
		});
		panel4.add(saveButton);

		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		mainPanel.add(panel4);

		frame.setVisible(true);
	}
}
