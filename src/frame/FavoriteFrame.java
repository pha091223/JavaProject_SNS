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
import db.FavoriteDTO;
import db.PostDTO;

public class FavoriteFrame extends JFrame {
	private HomeFrame HomeF = null;
	private ClientChat nowCc = null;
	
	private ArrayList<Object> fList = null;
	
	FavoriteFrame(HomeFrame h, ClientChat cc, Object o){
		super("Favorite");
		HomeF = h;
		this.nowCc = cc;
		
		fList = (ArrayList<Object>)o;
		
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 300, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		
		JPanel myFavoriteAll = new JPanel();
		myFavoriteAll.setLayout(null);
		myFavoriteAll.setBounds(0, 0, 300, 400);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(5, 5, 275, 355);
		scrollPane.setPreferredSize(new Dimension(250, 1000));
		myFavoriteAll.add(scrollPane);
		
		JPanel myFList = new JPanel();
		myFList.setLayout(new BoxLayout(myFList, BoxLayout.Y_AXIS));
		
		scrollPane.setViewportView(myFList);
		
		for(int i=0; i<fList.size(); i++) {
			FavoriteDTO f = (FavoriteDTO)(fList.get(i));
			String PostNum = f.getPostNum();
			myFList.add(viewMyFList(PostNum));
		}
		
		this.add(myFavoriteAll);
	}

	private Panel viewMyFList(String PostNum) {
		// TODO Auto-generated method stub
		Object o = nowCc.getObject("selectPost:" + PostNum);
		PostDTO FavoritePost = (PostDTO)o;
				
		Panel FList = new Panel();
		
		JLabel FNumAndWriter = new JLabel(PostNum + " /" + FavoritePost.getId());
		
		JButton FPostBtn = new JButton("Post");
		
		FPostBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				OnePostFrame pF = new OnePostFrame(HomeF, nowCc);
				pF.viewLayOut(FavoritePost);
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(FList);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
						.addGap(30)
						.addComponent(FNumAndWriter)
						.addPreferredGap(ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
						.addComponent(FPostBtn)
						.addGap(30))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(FPostBtn)
							.addComponent(FNumAndWriter))
						.addContainerGap(5, Short.MAX_VALUE))
			);
		
		FList.setLayout(groupLayout);
		
		return FList;
	}
	
}
