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
	
	private ClientChat nowCc = null;
	
	private String chkMsg = null;
	
	public ChkFrame(String chk, ClientChat cc){
		super("Check");
		setResizable(false);
		this.nowCc = cc;
		this.chkMsg = chk;
		
		if(chkMsg.indexOf("chk")!=-1) {
			System.out.println("Check");
		} else {
			Frame();
		}
	}
	
	private void Frame() {
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 250, 150);
		
		this.setLocationRelativeTo(null);
		
		// Center panel
		cP = new JPanel();
		cP.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
		
		if(chkMsg.contains("Login")) {
			if(chkMsg.contains("true")) {
				textLabel = new JLabel("Welcome");
			} else if(chkMsg.contains("false")) {
				textLabel = new JLabel("Wrong ID or PWD");
			}
		} else if(chkMsg.contains("Logout")) {
			if(chkMsg.contains("hope")) {
				textLabel = new JLabel("Logout?");
			} else if(chkMsg.contains("true")) {
				textLabel = new JLabel("GoodBye");
				chkMsg = chkMsg + ":GoodBye";
			}
		} else if(chkMsg.contains("Join")) {
			if(chkMsg.contains("true")) {
				textLabel = new JLabel("Join");
			} else if(chkMsg.contains("false")) {
				textLabel = new JLabel("Exist Empty Data");
			}
		} else if(chkMsg.contains("MyPage")){
			if(chkMsg.contains("Update")) {
				if(chkMsg.contains("true")) {
					textLabel = new JLabel("Apply");
				} else if(chkMsg.contains("false")) {
					textLabel = new JLabel("Apply false");
				}
			} else if(chkMsg.contains("Delete")) {
				if(chkMsg.contains("hope")) {
					textLabel = new JLabel("Sure?");
				} else if(chkMsg.contains("true")) {
					textLabel = new JLabel("Withdrawl true");
				}
			}
		} else if(chkMsg.contains("Post")){
			if(chkMsg.contains("Delete")) {
				if(chkMsg.contains("hope")) {
					textLabel = new JLabel("Sure?");
				} else if(chkMsg.contains("true")) {
					textLabel = new JLabel("Post delete");
				}
			}
		} else {
			textLabel = new JLabel(chkMsg);
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
					// TODO Auto-generated stub
					if(chkMsg.contains("MyPage")){
						nowCc.send("deletemyPage:sure");
					} else if(chkMsg.contains("Post")) {
						String postNum = chkMsg.substring(chkMsg.indexOf(":")+1, chkMsg.length());
						nowCc.send("deletePost:sure" + "/" + postNum);
					}
					setVisible(false);
				}
			});
			
			cancleBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					dispose();
				}
			});
		} else if(textLabel.getText().equals("Logout?")) {
			JButton cancleBtn = new JButton("Cancel");
			sP.add(cancleBtn);
			
			OkBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated stub
					setVisible(false);
					nowCc.send("logout:sure");
				}
			});
			
			cancleBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					dispose();
				}
			});
		} else if(chkMsg.equals("Logout true" + ":GoodBye")) {
			OkBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
		} else if(chkMsg.equals("Already access member")) {
			OkBtn.addActionListener(new ActionListener() {
				
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
					nowCc.Home(chkMsg, nowCc);
				}
			});
		}
	}

}
