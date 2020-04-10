package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import db.FriendDTO;

public class FriendFrame extends JFrame {
	private HomeFrame HomeF = null;
	private ClientChat cc = null;
	
	private ArrayList<Object> fList = null;
	
	FriendFrame(HomeFrame h, ClientChat cc, Object o){
		HomeF = h;
		this.cc = cc;
		
		fList = (ArrayList<Object>)o;
		
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 300, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		
		JPanel myFriendAll = new JPanel();
		myFriendAll.setLayout(null);
		myFriendAll.setBounds(0, 0, 300, 400);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(5, 5, 275, 355);
		scrollPane.setPreferredSize(new Dimension(250, 1000));
		myFriendAll.add(scrollPane);
		
		JPanel myFList = new JPanel();
		myFList.setLayout(new BoxLayout(myFList, BoxLayout.Y_AXIS));
		
		scrollPane.setViewportView(myFList);
		
		for(int i=0; i<fList.size(); i++) {
			FriendDTO f = (FriendDTO)(fList.get(i));
			String FriendId = f.getYourId();
			myFList.add(viewMyFList(FriendId));
		}
		
		this.add(myFriendAll);
	}

	private Panel viewMyFList(String FriendId) {
		// TODO Auto-generated method stub
		Panel FList = new Panel();
		
		JLabel FName = new JLabel(FriendId);
		
		JButton FProfileBtn = new JButton("Profile");
		
		FProfileBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new ProfileFrame(FriendId, cc, HomeF);
				dispose();
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
		
		return FList;
	}

}
