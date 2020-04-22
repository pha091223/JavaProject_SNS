package frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import client.ClientChat;
import db.DirectMessageDTO;

public class OneDMFrame extends JFrame {
	private ClientChat nowCc = null;
	private String nowId = null;
	
	private JPanel oneUser = new JPanel();
	
	private String yourId = null;
	
	private DirectMessageDTO recentlyDM = null;
	
	private String recentlyDay = null;
	private String recentlyMsg = null;
	
	private JLabel userDMId = new JLabel();
	private JLabel userDMDay = new JLabel();
	private JLabel userDMMsg = new JLabel();
	
	private String dmRoomName = null;
	
	private HomeFrame homeF = null;
	private OneDMFrame nowOneDMF = null;
	
	public OneDMFrame(ClientChat cc, String id, HomeFrame homeF) {
		this.nowCc = cc;
		this.nowId = id;
		this.homeF = homeF;
		this.nowOneDMF = this;
	}
	
	public String getDmRoomName() {
		return dmRoomName;
	}
	
	public String getYourId() {
		return yourId;
	}
	
	public void setLabel(String dmRoomName, String msg, String keyword) {
		// msg가 null일 경우 : 초기 라벨 세팅
		// msg가 null이 아닐 경우 : receive가 발생되어 받은 msg로 라벨 새로 세팅
		System.out.println("/msg:" + msg);
		
		if(msg==null) {
			recentlyDM = (DirectMessageDTO)nowCc.getObject("myDM:" + dmRoomName);
			if(recentlyDM!=null) {
				recentlyDay = recentlyDM.getDay();
				recentlyMsg = "[" + recentlyDM.getId() + "]" + recentlyDM.getMessage();				
			}
		} else if(msg!=null) {
			String sendId = msg.substring(msg.indexOf("[")+1, msg.indexOf("]"));
			String receiveMsg = msg.substring(msg.indexOf("]")+1, msg.length());
			
			Date time = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String day = format.format(time);
			
			recentlyDay = day;
			recentlyMsg = "[" + sendId + "]" + receiveMsg;
		}
		
		userDMId.setText(yourId);
		userDMDay.setText(recentlyDay);
		
		if(recentlyMsg!=null) {
			if(recentlyMsg.length()<=50) {
				userDMMsg.setText(recentlyMsg);
			} else {
				userDMMsg.setText(recentlyMsg.substring(0,47) + "...");
			}
		} else if(recentlyMsg==null) {
			System.out.println("Empty Message");
		}
		
		if(keyword.equals("Color")) {
			oneUser.setBackground(Color.gray);
		} else if(keyword.equals("noColor")){
			oneUser.setBackground(null);
		}
	}

	public JPanel oneDM(String dmRoomName, String yourId) {
		oneUser.setBounds(12, 35, 410, 64);
		
		oneUser.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==1) {
					System.out.println("/Message Click");
					oneUser.setBackground(Color.LIGHT_GRAY);
					oneUser.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY)));
					
					if(e.getClickCount()==2) {
						DirectMessageFrame dmF = new DirectMessageFrame(homeF, yourId);
						nowCc.setOpenDmFrame(dmF);
						
						dmF.OpenChattingRoom(nowCc, dmRoomName);
						oneUser.setBackground(null);
						oneUser.setBorder(null);
						
						// New Message로 인해 바뀌었던 tab 색깔 원래대로
						nowCc.getHomeF().setTabColor(2, "noColor");
					}
				}
				
				if(e.getButton()==3) {
					oneUser.setBackground(Color.LIGHT_GRAY);
					oneUser.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY)));
					
					JPopupMenu DmPopup = new JPopupMenu();
					JMenuItem dmDel = new JMenuItem("Exit");
					
					dmDel.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							oneUser.setBackground(null);
							oneUser.setBorder(null);
							
							nowCc.send("deleteDM:" + dmRoomName + "/" + nowId);
						}
					});
					
					DmPopup.add(dmDel);
					oneUser.add(DmPopup);
					
					DmPopup.show(oneUser, e.getX(), e.getY());
				}
			}
		});
		
		this.dmRoomName = dmRoomName;
		this.yourId = yourId;
		
		setLabel(dmRoomName, null, "");

		GroupLayout gl_oneUser = new GroupLayout(oneUser);
		gl_oneUser.setHorizontalGroup(
			gl_oneUser.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_oneUser.createSequentialGroup()
					.addGap(50)
					.addGroup(gl_oneUser.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_oneUser.createSequentialGroup()
							.addComponent(userDMId)
							.addGap(150)
							.addComponent(userDMDay, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))
						.addComponent(userDMMsg, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE)))
		);
		gl_oneUser.setVerticalGroup(
			gl_oneUser.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_oneUser.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_oneUser.createParallelGroup(Alignment.LEADING)
						.addComponent(userDMId)
						.addComponent(userDMDay))
					.addGap(14)
					.addComponent(userDMMsg))
				.addGap(70)
		);
		oneUser.setLayout(gl_oneUser);

		return oneUser;
	}

}
