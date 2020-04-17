package frame;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import client.ClientChat;
import db.DirectMessageDTO;

public class OneDMFrame extends JFrame {
	private ClientChat nowCc = null;
	private String nowId = null;
	private String roomName = null;

	public OneDMFrame(ClientChat cc, String id) {
		this.nowCc = cc;
		this.nowId = id;
	}

	public JPanel oneDM(String dmRName) {
		roomName = dmRName;
		
		ArrayList<Object> DMList = 
				(ArrayList<Object>)nowCc.getObject("getList:" + "directmessage" + "/" + dmRName);
		
		System.out.println("/////////" + DMList);
		
		JPanel oneUser = new JPanel();
		oneUser.setBounds(12, 35, 410, 64);
		oneUser.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY)));
		
		DirectMessageDTO recently = (DirectMessageDTO)DMList.get(0);
		String recentlyId = recently.getId();
		String recentlyDay = recently.getDay();
		String recentlyMsg = recently.getMessage();
		
		// DM 상대 ID
		JLabel userDMsendId = new JLabel(recentlyId);
		
		// 마지막 Message 날짜
		JLabel userDMDay = new JLabel(recentlyDay);
		
		// 마지막으로 보낸 혹은 받은 Message
		JLabel userDMMsg = new JLabel(recentlyId);
		// 50자까지 미리 보기
		if(recentlyMsg.length()<=50) {
			userDMMsg.setText(recentlyMsg);
		} else {
			userDMMsg.setText(recentlyMsg.substring(0,47) + "...");
		}

		GroupLayout gl_oneUser = new GroupLayout(oneUser);
		gl_oneUser.setHorizontalGroup(
			gl_oneUser.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_oneUser.createSequentialGroup()
					.addGap(50)
					.addGroup(gl_oneUser.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_oneUser.createSequentialGroup()
							.addComponent(userDMsendId)
							.addGap(150)
							.addComponent(userDMDay, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))
						.addComponent(userDMMsg, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE)))
		);
		gl_oneUser.setVerticalGroup(
			gl_oneUser.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_oneUser.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_oneUser.createParallelGroup(Alignment.LEADING)
						.addComponent(userDMsendId)
						.addComponent(userDMDay))
					.addGap(14)
					.addComponent(userDMMsg))
				.addGap(70)
		);
		oneUser.setLayout(gl_oneUser);

		return oneUser;
	}

}
