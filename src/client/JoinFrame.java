package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JoinFrame extends JFrame {
	private JLabel idLabel, pwdLabel, phLabel;
	private String[] labelText = {"　ID", " PWD", "PHONE"};
	private JLabel[] label = {idLabel, pwdLabel, phLabel};
	private JTextField idField, pwdField, phField;
	private JTextField[] textField = {idField, pwdField, phField};
	private JButton joinBtn, endBtn;
	
	private ClientChat cc = null;
	
	JoinFrame(ClientChat cc){
		super("Join");
		this.cc = cc;
		Frame();
	}

	private void Frame() {
		// TODO Auto-generated method stub
		this.setLayout(null);
		this.setBounds(0, 0, 300, 300);
		
		this.setLocationRelativeTo(null);
		
		JLabel main = new JLabel("Sign in");
		main.setBounds(125, 15, 50, 20);
		this.add(main);
		
		int count = 50;
		for(int i=0; i<label.length; i++) {
			label[i] = new JLabel(labelText[i]);				
			label[i].setBounds(30, count, 50, 20);
			this.add(label[i]);
			count = count + 40;
		}
		
		count = 50;
		for(int i=0; i<textField.length; i++) {
			textField[i] = new JTextField();
			textField[i].setBounds(80, count, 150, 20);
			this.add(textField[i]);
			count = count + 40;
		}
		
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
				String[] content = new String[3];
				String msg = "join:";
				for(int i=0; i<content.length; i++) {
					content[i] = textField[i].getText();
					msg = msg + content[i] + "/";
				}
				msg = msg.substring(0, msg.lastIndexOf("/"));
				cc.chkFrame(msg);
			}
		});
		
		endBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
	}

}
