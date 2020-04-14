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
	private HomeFrame HomeF = null;
	private ClientChat nowCc = null;
	
	private ArrayList<Object> list = null;
	
	UserListFrame(HomeFrame h, ClientChat cc, Object o){
		super("List");
		HomeF = h;
		this.nowCc = cc;
		
		list = (ArrayList<Object>)o;
	}
	
	public void viewListFrame(String keyword) {
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 300, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
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
	
	public Panel viewMyFList(String userId, String keyword) {
		// TODO Auto-generated method stub
		Panel FList = new Panel();
		
		JLabel FName = new JLabel(userId);
		
		if(keyword.equals("DMfriend")) {
			JButton FDMBtn = new JButton("D.M");
			
			FDMBtn.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					System.out.println("DM////");
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
					new ProfileFrame(userId, nowCc, HomeF);
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

}
