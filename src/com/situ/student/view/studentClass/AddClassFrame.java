package com.situ.student.view.studentClass;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

public class AddClassFrame {
	List<StudentClass> classList = new ArrayList<StudentClass>();
	StudentClassManager studentClassManager = new StudentClassManager();
	JFrame frame;
	JTextField addClassNameText;
	
	CallBack callBack;
	public AddClassFrame(CallBack callBack) {
		this.callBack = callBack;
	}

	public void addClassName() {
		// 从数据库中查询到所有班级的信息给classList集合
		classList = studentClassManager.findAll();
		// 新建添加学生的窗口，并建立主面板
		frame = new JFrame();
		frame.setTitle("添加新班级");
		frame.setSize(350, 200);
		frame.setLocationRelativeTo(null);
		JPanel mainPanel = (JPanel) frame.getContentPane();
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		// 新建2个子面板，并添加到添加学生的新窗口中的主面板中
		JPanel panel1 = new JPanel();
		JLabel nameLabel = new JLabel();
		nameLabel.setText("请输入新班级的名称:");
		panel1.add(nameLabel);
		addClassNameText = new JTextField();
		addClassNameText.setPreferredSize(new Dimension(120, 30));
		panel1.add(addClassNameText);
		
		JPanel panel2 = new JPanel();
		JButton saveButton = new JButton();
		saveButton.setText("保存");
		saveButton.setPreferredSize(new Dimension(60, 30));
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!addClassNameText.getText().equals("")) {
					boolean isExistSame = false;
					for (int i = 0; i < classList.size(); i++) {
						if (addClassNameText.getText().equals(classList.get(i).getName())) {
							isExistSame = true;
							break;
						}
					}
					
					if (!isExistSame) {// 如果数据库中没有同名的班级，则向数据库中class表中新增该班级的信息
						StudentClass studentClass = new StudentClass();
						studentClass.setName(addClassNameText.getText());
						// 取classList集合中的最后一个元素（即最大的id，再+1）
						studentClass.setId(classList.get(classList.size() - 1).getId() + 1);// class的id为总班级数+1
						//调用控制层addClass方法，将新增的班级信息添加到数据库中的class表中
						boolean flag = studentClassManager.addClass(studentClass);
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
	
	public static void main(String[] args) {
		AddClassFrame addClassFrame = new AddClassFrame(null);
		addClassFrame.addClassName();
	}
}
