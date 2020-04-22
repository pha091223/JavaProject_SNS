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
		} else if(chkMsg.substring(0, chkMsg.indexOf(":")+1).equals("DM:")) {
			DirectMessageFrame OpenDmF = nowCc.getOpenDmFrame();
			
			String msg = chkMsg.substring(chkMsg.indexOf(":")+1, chkMsg.lastIndexOf("/"));
			
			System.out.println(msg + "/");
			
			String sendId = msg.substring(msg.indexOf("[")+1, msg.indexOf("]"));
			String rn = chkMsg.substring(chkMsg.lastIndexOf("/")+1, chkMsg.length());
			
			// 기존 DM방을 불러옴
			OneDMFrame OneDmF = nowCc.getHomeF().getOneDMFrame(rn);
				
			if(OpenDmF!=null) {
				// 임시 방 이름이였을 시 할당받은 방 번호를 DirectMessageFrame에 전달
				if(OpenDmF.getRoomName().contains("temp")) {
					OpenDmF.setRoomName(rn);
				}
				// 기존에 만들어진 DM방이 존재하는 경우
				if(OneDmF!=null) {
					String color = null;
					// 열려있는 DM창과 만들어진 OneDMFrame이 같은 것인가 판별
					// 같다면 메세지를 DM창에 띄우며 DM창 팝업 여부에 따라 OneDMFrame의 Panel 색 변경
					if(OpenDmF.getYourId().equals(OneDmF.getYourId())) {
						OpenDmF.setMessage(msg);
						
						if(OpenDmF.isVisible()) {
							color = "noColor";
						} else if(OpenDmF.isVisible()==false) {
							color = "Color";
						}
					}
					// DM창을 열고 있는 사용자와 메세지를 보내는 사용자가 같다면 Tab 색깔 바꾸기 X
					// 자신이 보낸 것이기에 굳이 새로 메세지가 왔다는 알람을 줄 필요가 없음 > OneDMFrame의 Label만 변경
					if(!OpenDmF.getNowId().equals(sendId)) {
						nowCc.getHomeF().setTabColor(2, color);
						OneDmF.setLabel(rn, msg, color);
					} else {
						OneDmF.setLabel(rn, msg, color);
					}
				} else if(OneDmF==null) {
					OpenDmF.setMessage(msg);
					// Tab 새로고침으로 해당되는 OneDMFrame 생성
					nowCc.getHomeF().tabRefresh("2");
					OneDMFrame createOneDmF = nowCc.getHomeF().getOneDMFrame(rn);
					if(createOneDmF.getYourId().equals(sendId)) {
						createOneDmF.setLabel(rn, msg, "Color");						
					} else {
						createOneDmF.setLabel(rn, msg, "noColor");
					}
				}
			} else if(OpenDmF==null) {
				// DM창이 열려있지 않은 상태
				nowCc.getHomeF().setTabColor(2, "Color");
				
				// 기존 DM방의 존재 여부
				if(OneDmF==null) {
					nowCc.getHomeF().tabRefresh("2");
					OneDMFrame createOneDmF = nowCc.getHomeF().getOneDMFrame(rn);
					createOneDmF.setLabel(rn, msg, "Color");
				} else {
					OneDmF.setLabel(rn, msg, "Color");
				}
			}
		} else {
			Frame();
		}
	}
	
	private void setLocationFrame() {
		HomeFrame homeF = nowCc.getHomeF();
		
		if(homeF==null) {
			this.setLocationRelativeTo(null);
		} else {
			int xx = 0;
			int yy = 0;
			
			if(this.getWidth()<homeF.getWidth()) {
				int x1 = (homeF.getWidth()-this.getWidth())/2;
				int x = homeF.getX();
				xx = x + x1;
			} else if(this.getWidth()>homeF.getWidth()) {
				int x1 = (this.getWidth()-homeF.getWidth())/2;
				int x = homeF.getX();
				xx = x - x1;
			} else if(this.getWidth()==homeF.getWidth()) {
				int x = homeF.getX();
				xx = x;
			}
			
			if(this.getHeight()<homeF.getHeight()) {
				int y1 = (homeF.getHeight()-this.getHeight())/2;
				int y = homeF.getY();
				yy = y + y1;
			} else if(this.getHeight()>homeF.getHeight()) {
				int y1 = (this.getHeight()-homeF.getHeight())/2;
				int y = homeF.getY();
				yy = y - y1;
			} else if(this.getHeight()==homeF.getHeight()) {
				int y = homeF.getY();
				yy = y;
			}
			
			this.setLocation(xx, yy);
		}
	}
	
	private void Frame() {
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 250, 150);
		
		setLocationFrame();
		
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
					
					nowCc.getHomeF().tabRefresh("0");
					nowCc.getHomeF().tabRefresh("1");
				}
			}
		} else if(chkMsg.contains("MyDM")){
			if(chkMsg.contains("Delete")) {
				if(chkMsg.contains("hope")) {
					textLabel = new JLabel("Sure?");
				} else if(chkMsg.contains("true")) {
//					String rn = chkMsg.substring(chkMsg.indexOf(":")+1, chkMsg.length());
//					OneDMFrame delOneDmF = nowCc.getHomeF().getOneDMFrame(rn);
//					nowCc.getHomeF().delOneDMFrame(delOneDmF);
					
					textLabel = new JLabel("DM Exit");
					
					nowCc.getHomeF().tabRefresh("2");
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
					} else if(chkMsg.contains("MyDM")) {
						String keyword = chkMsg.substring(chkMsg.indexOf(":")+1, chkMsg.length());
						nowCc.send("deleteDM:sure" + "/" + keyword);
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
		} else if(textLabel.getText().contains("Logout?")) {
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
