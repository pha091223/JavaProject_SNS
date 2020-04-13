package frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.ClientChat;

public class LoginFrame extends JFrame implements ActionListener {
	private JPanel cP, sP;
	private JLabel idLabel, pwdLabel;
	private JTextField idField, pwdField;
	private JButton joinBtn, loginBtn, endBtn;
	
	private ClientChat nowCc = null;
	
	public LoginFrame(ClientChat cc){
		super("Login");
		setResizable(false);
		this.nowCc = cc;
		Frame();
	}
	
	private void Frame() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 300, 200);
		
		this.setLocationRelativeTo(null);
		
		// Center panel
		cP = new JPanel();
		cP.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		JPanel cnP = new JPanel();
		idLabel = new JLabel("I　D");
		idField = new JTextField(15);
		cnP.add(idLabel);
		cnP.add(idField);
		JPanel csP = new JPanel();
		pwdLabel = new JLabel("PWD");
		pwdField = new JTextField(15);
		csP.add(pwdLabel);
		csP.add(pwdField);
		cP.add(cnP, "North");
		cP.add(csP, "South");
		
		// South panel
		sP = new JPanel();
		sP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		joinBtn = new JButton("Join");
		loginBtn = new JButton("Login");
		endBtn = new JButton("End");
		sP.add(joinBtn);
		sP.add(loginBtn);
		sP.add(endBtn);
		
		loginBtn.addActionListener(this);
		endBtn.addActionListener(this);
		joinBtn.addActionListener(this);
		
		this.add(cP, "Center");
		this.add(sP, "South");
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object nowCom = e.getSource();
		String id = idField.getText();
		String pwd = pwdField.getText();
		
		if(nowCom.equals(loginBtn)) {
			nowCc.loginSet(id, pwd);
			idField.setText("");
			pwdField.setText("");
		} else if(nowCom.equals(endBtn)) {
			System.exit(0);
		} else if(nowCom.equals(joinBtn)) {
			nowCc.Join();
		}
	}

}
