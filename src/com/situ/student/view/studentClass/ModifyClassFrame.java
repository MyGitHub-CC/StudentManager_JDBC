package com.situ.student.view.studentClass;

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

import com.situ.student.biz.StudentClassManager;
import com.situ.student.entity.StudentClass;
import com.situ.student.util.CallBack;

public class ModifyClassFrame {
	List<StudentClass> classList;
	JFrame frame;
	StudentClassManager studentClassManager = new StudentClassManager();
	JTextField modifyclassNameText;
	StudentClass modifyClass;
	
	CallBack callBack;
	public ModifyClassFrame(CallBack callBack, StudentClass studentClass) {
		this.callBack = callBack;
		this.modifyClass = studentClass;
	}
	
	public void modifyClassName() {
		// 从数据库中查询到所有班级的信息给classList集合
		classList = studentClassManager.findAll();
		// 根据被选中班级的id，从数据库中查询到对应的班级信息
		modifyClass = studentClassManager.findById(modifyClass.getId());
		frame = new JFrame();
		frame.setTitle("修改班级名称");
		frame.setSize(350, 220);
		frame.setLocationRelativeTo(null);
		JPanel mainPanel = (JPanel) frame.getContentPane();
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);
		
		JPanel panel1 = new JPanel();
		JLabel nameLabel = new JLabel();
		nameLabel.setText("修改班级名称为：");
		panel1.add(nameLabel);
		modifyclassNameText = new JTextField();
		modifyclassNameText.setText(modifyClass.getName());
		modifyclassNameText.setPreferredSize(new Dimension(120, 30));
		panel1.add(modifyclassNameText);
		
		JPanel panel2 = new JPanel();
		JButton saveButton = new JButton();
		saveButton.setText("保存");
		saveButton.setPreferredSize(new Dimension(60,30));
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!modifyclassNameText.getText().equals("")) {
					boolean isExistSame = false;
					for (int i = 0; i < classList.size(); i++) {
						if (modifyclassNameText.getText().equals(classList.get(i).getName())) {
							isExistSame = true;
							break;
						}
					}
					
					if (!isExistSame) {
						modifyClass.setName(modifyclassNameText.getText());
						boolean flag = studentClassManager.modifyClass(modifyClass);
						String message = "保存成功！";
						if (!flag) {
							message = "保存失败！";
						}
						JOptionPane.showMessageDialog(null, message);
						frame.dispose();
						callBack.callBack();
					} else {
						JOptionPane.showMessageDialog(null, "保存失败！该班级名称已存在");
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "班级内容不能为空");
				}
			}
		});
		panel2.add(saveButton);
		
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		
		frame.setVisible(true);
	}
}
