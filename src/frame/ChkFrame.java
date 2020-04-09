package frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.ClientChat;

public class ChkFrame extends JFrame {
	private JPanel cP, sP;
	private JLabel textLabel;
	private JButton OkBtn;
	
	private ChkFrame now = null;
	private ClientChat cc = null;
	
	private String chk = null;
	
	public ChkFrame(String chk, ClientChat cc){
		super("Check");
		setResizable(false);
		this.cc = cc;
		this.chk = chk;
		now = this;
		Frame();
	}
	
	private void Frame() {
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 250, 150);
		
		this.setLocationRelativeTo(null);
		
		// Center panel
		cP = new JPanel();
		cP.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
		
		if(chk.contains("Login")) {
			if(chk.contains("true")) {
				textLabel = new JLabel("Welcome");
			} else if(chk.contains("false")) {
				textLabel = new JLabel("Inexistant ID or Wrong PWD");
			}
		} else if(chk.contains("Join")) {
			if(chk.contains("true")) {
				textLabel = new JLabel("Join");
			} else if(chk.contains("false")) {
				textLabel = new JLabel("Exist Empty Data");
			}
		} else if(chk.contains("MyPage")){
			if(chk.contains("Update")) {
				if(chk.contains("true")) {
					textLabel = new JLabel("Apply");
				} else if(chk.contains("false")) {
					textLabel = new JLabel("Apply false");
				}
			} else if(chk.contains("Delete")) {
				if(chk.contains("hope")) {
					textLabel = new JLabel("Sure?");
				} else if(chk.contains("true")) {
					textLabel = new JLabel("Withdrawl true");
				}
			}
		} else {
			textLabel = new JLabel(chk);
		}
		cP.add(textLabel);
		
		// South panel
		sP = new JPanel();
		sP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		OkBtn = new JButton("OK");
		sP.add(OkBtn);
		
		this.add(cP, "Center");
		this.add(sP, "South");
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		if(textLabel.getText().equals("Sure?")) {
			JButton cancleBtn = new JButton("Cancel");
			sP.add(cancleBtn);
			
			OkBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated mString id = msg.substring(msg.indexOf(":")+1, msg.length());ethod stub
					setVisible(false);
					cc.chkSet("deletemyPage:sure");
				}
			});
			
			cancleBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					dispose();
				}
			});
		} else {
			OkBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					setVisible(false);
					cc.Home(chk, cc);
				}
			});
		}
	}

}
