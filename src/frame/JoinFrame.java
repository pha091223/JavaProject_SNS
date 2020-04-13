package frame;

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

public class JoinFrame extends JFrame {
	private JLabel idLabel, pwdLabel, phLabel;
	private String[] labelText = {"　ID", " PWD", "PHONE"};
	private JLabel[] label = {idLabel, pwdLabel, phLabel};
	private JTextField idField, pwdField, phField_1, phField_2, phField_3;
	private JTextField[] textField = {idField, pwdField, phField_1, phField_2, phField_3};
	private JButton joinBtn, endBtn;
	
	private ClientChat nowCc = null;
	
	public JoinFrame(ClientChat cc){
		super("Join");
		setResizable(false);
		this.nowCc = cc;
		Frame();
	}

	private void Frame() {
		// TODO Auto-generated method stub
		this.setLayout(null);
		this.setBounds(0, 0, 300, 300);
		
		this.setLocationRelativeTo(null);
		
		JLabel main = new JLabel("Sign in");
		main.setBounds(115, 15, 100, 25);
		main.setFont(new Font("Dialog", Font.BOLD, 20));
		this.add(main);
		
		int count = 60;
		
		label[0] = new JLabel(labelText[0]);				
		label[0].setBounds(30, count, 50, 20);
		this.add(label[0]);
		count = count + 60;
		
		for(int i=1; i<label.length; i++) {
			label[i] = new JLabel(labelText[i]);				
			label[i].setBounds(30, count, 50, 20);
			this.add(label[i]);
			count = count + 40;
		}
		
		JButton sameChkBtn = new JButton("ID Check");
		sameChkBtn.setBounds(105, 85, 90, 20);
		sameChkBtn.setBorderPainted(false);
		sameChkBtn.setContentAreaFilled(false);
		
		// JButton 글자에 Underline
		Font font = sameChkBtn.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		sameChkBtn.setFont(font.deriveFont(attributes));
		
		this.add(sameChkBtn);
		
		sameChkBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String msg = "idCheck:" + textField[0].getText();
				nowCc.chkSet(msg);
			}
		});
		
		count = 60;
		int count_X = 80;
		
		textField[0] = new JTextField();
		textField[0].setBounds(80, count, 150, 20);
		this.add(textField[0]);
		count = count + 60;
		
		for(int i=1; i<textField.length; i++) {
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
		
		joinBtn = new JButton("Join");
		joinBtn.setBounds(85, 210, 60, 30);
		this.add(joinBtn);
		endBtn = new JButton("End");
		endBtn.setBounds(150, 210, 60, 30);
		this.add(endBtn);
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		joinBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] content = new String[5];
				String msg = "join:";
				for(int i=0; i<content.length; i++) {
					if(i>=2) {
						msg = msg + textField[i].getText();
					} else {
						content[i] = textField[i].getText();
						msg = msg + content[i] + "/";
					}
				}
				nowCc.chkSet(msg);
				
				if(nowCc.getChkMessage().indexOf("true")!=-1){
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
