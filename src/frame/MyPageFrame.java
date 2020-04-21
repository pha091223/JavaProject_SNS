package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import client.ClientChat;
import db.MemberDTO;

public class MyPageFrame extends JFrame {
	private JLabel idLabel, pwdLabel, phLabel;
	private String[] labelText = {"　ID", " PWD", "PHONE"};
	private JLabel[] label = {idLabel, pwdLabel, phLabel};
	private JTextField idField, pwdField, phField_1, phField_2, phField_3;
	private JTextField[] textField = {idField, pwdField, phField_1, phField_2, phField_3};
	private JButton applyBtn, endBtn;
	
	private ClientChat nowCc = null;
	
	private String nowId = null;
	
	MyPageFrame(ClientChat cc, String id){
		this.nowCc = cc;
		this.nowId = id;
		
		MemberDTO my = (MemberDTO)nowCc.getObject("myPage:" + id);
		
		Frame(my);
	}

	private void Frame(MemberDTO my) {
		// TODO Auto-generated method stub
		this.setLayout(null);
		this.setBounds(0, 0, 300, 300);
		
		this.setLocationRelativeTo(null);
		
		JLabel main = new JLabel("My Page");
		main.setBounds(105, 15, 100, 25);
		main.setFont(new Font("Dialog", Font.BOLD, 20));
		this.add(main);
		
		int count = 60;
		
		for(int i=0; i<label.length; i++) {
			label[i] = new JLabel(labelText[i]);				
			label[i].setBounds(30, count, 50, 20);
			this.add(label[i]);
			count = count + 40;
		}
		
		count = 60;
		int count_X = 80;
		
		for(int i=0; i<textField.length; i++) {
			if(i>=2) {
				textField[i] = new JTextField();
				textField[i].setBounds(count_X, count, 40, 20);
				this.add(textField[i]);
				count_X = count_X + 55;
			} else {
				textField[i] = new JTextField();
				textField[i].setBounds(80, count, 150, 20);
				this.add(textField[i]);
				count = count + 40;
			}
		}
		
		JLabel hyphen_1 = new JLabel("-");
		hyphen_1.setBounds(125, count, 5, 15);
		JLabel hyphen_2 = new JLabel("-");
		hyphen_2.setBounds(180, count, 5, 15);
		this.add(hyphen_1);
		this.add(hyphen_2);
		
		JButton delBtn = new JButton("Withdrawal");
		delBtn.setBounds(90, count+35, 120, 20);
		delBtn.setBorderPainted(false);
		delBtn.setContentAreaFilled(false);
		delBtn.setFont(new Font("Dialog", Font.ITALIC, 12));
		delBtn.setForeground(Color.red);
		
		Font font = delBtn.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		delBtn.setFont(font.deriveFont(attributes));
		
		this.add(delBtn);
		
		delBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				nowCc.send("deletemyPage:" + nowId);
			}
		});
		
		applyBtn = new JButton("Apply");
		applyBtn.setBounds(75, 210, 70, 30);
		this.add(applyBtn);
		endBtn = new JButton("End");
		endBtn.setBounds(160, 210, 70, 30);
		this.add(endBtn);
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		textField[0].setText(my.getId());
		textField[1].setText(my.getPwd());
		textField[2].setText(my.getPhone().substring(0, 3));
		textField[3].setText(my.getPhone().substring(3, 7));
		textField[4].setText(my.getPhone().substring(7, my.getPhone().length()));
		
		textField[0].setEditable(false);
		
		applyBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] content = new String[5];
				String msg = "updatemyPage:";
				for(int i=0; i<content.length; i++) {
					if(i>=2) {
						msg = msg + textField[i].getText();
					} else {
						content[i] = textField[i].getText();
						msg = msg + content[i] + "/";
					}
				}
				nowCc.send(msg);
				
				if(nowCc.getReceiveMessage().indexOf("true")!=-1){
					dispose();
				}
			}
		});
		
		endBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
	}

}
