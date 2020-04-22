package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import client.ClientChat;
import db.FavoriteDTO;
import db.FriendDTO;

public class UserListFrame extends JFrame {
	private HomeFrame homeF = null;
	private ClientChat nowCc = null;
	
	private ArrayList<Object> list = null;
	
	UserListFrame(HomeFrame h, ClientChat cc, Object o){
		super("List");
		homeF = h;
		this.nowCc = cc;
		
		list = (ArrayList<Object>)o;
	}
	
	public void viewListFrame(String keyword) {
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 300, 400);
		this.setVisible(true);
		
		setLocationFrame();
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		
		JPanel listAll = new JPanel();
		listAll.setLayout(null);
		listAll.setBounds(0, 0, 300, 400);
		
		JLabel listName = new JLabel(keyword + " list");
		listName.setBounds(15, 5, 100, 20);
		listName.setFont(new Font("Dialog", Font.BOLD, 15));
		listAll.add(listName);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(5, 30, 275, 325);
		scrollPane.setPreferredSize(new Dimension(250, 1000));
		listAll.add(scrollPane);
		
		JPanel myList = new JPanel();
		myList.setLayout(new BoxLayout(myList, BoxLayout.Y_AXIS));
		
		scrollPane.setViewportView(myList);
		
		if(keyword.contains("friend")) {
			for(int i=0; i<list.size(); i++) {
				FriendDTO fr = (FriendDTO)(list.get(i));
				String FriendId = fr.getYourId();
				myList.add(viewMyFList(FriendId, keyword));
			}
		} else if(keyword.equals("favorite")) {
			for(int i=0; i<list.size(); i++) {
				FavoriteDTO f = (FavoriteDTO)(list.get(i));
				String FavoriteId = f.getId();
				myList.add(viewMyFList(FavoriteId, keyword));
			}
		}
		
		this.add(listAll);
	}
	
	private void setLocationFrame() {
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
	
	public Panel viewMyFList(String userId, String keyword) {
		// TODO Auto-generated method stub
		Panel FList = new Panel();
		
		JLabel FName = new JLabel(userId);
		
		if(keyword.equals("DMfriend")) {
			JButton FDMBtn = new JButton("D.M");
			
			FDMBtn.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					// chkDM : DM 방이 존재하는지 아닌지 체크
					// 존재하면 그대로 roomName을 받아옴
					// 존재하지 않으면 임시 방 이름을 지정해 Server에 보내주며 DB에서 sequence로 방 이름 할당
					Object o = nowCc.getObject("chkDM:" + nowCc.getNowCcId() + "/" + userId);
					String roomName = (String)o;
					
					System.out.println("/chkRoom:" + roomName);
					
					DirectMessageFrame dmF = new DirectMessageFrame(homeF, userId);
					nowCc.setOpenDmFrame(dmF);
					
					if(roomName!=null) {
						dmF.OpenChattingRoom(nowCc, roomName);
					} else if(roomName==null) {
						dmF.OpenChattingRoom(nowCc, "temp" + "+" + userId);
					}
				}
			});
			
			GroupLayout groupLayout = new GroupLayout(FList);
			groupLayout.setHorizontalGroup(
					groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGap(30)
							.addComponent(FName)
							.addPreferredGap(ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
							.addComponent(FDMBtn)
							.addGap(30))
					);
			groupLayout.setVerticalGroup(
					groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(FDMBtn)
									.addComponent(FName))
							.addContainerGap(5, Short.MAX_VALUE))
					);
			FList.setLayout(groupLayout);
		} else {
			JButton FProfileBtn = new JButton("Profile");
			
			FProfileBtn.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					new ProfileFrame(userId, nowCc, homeF);
					setClose();
				}
			});
			
			GroupLayout groupLayout = new GroupLayout(FList);
			groupLayout.setHorizontalGroup(
					groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGap(30)
							.addComponent(FName)
							.addPreferredGap(ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
							.addComponent(FProfileBtn)
							.addGap(30))
					);
			groupLayout.setVerticalGroup(
					groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(FProfileBtn)
									.addComponent(FName))
							.addContainerGap(5, Short.MAX_VALUE))
					);
			FList.setLayout(groupLayout);
		}
		
		return FList;
	}
	
	private void setClose() {
		// TODO Auto-generated method stub
		this.setVisible(false);
	}

}
