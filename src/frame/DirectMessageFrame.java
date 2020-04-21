package frame;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;

import client.ClientChat;
import db.DirectMessageDTO;

public class DirectMessageFrame extends JFrame {
	private ClientChat nowCc = null;
	private String nowId = null;
	
	private JPanel DMPanel = new JPanel();
	private JTextPane DM = new JTextPane();
	private JTextField textField = null;
	
	private String allMsg = "";
	
	DirectMessageFrame(){
		super("DirectMessage");
	}

	public void OpenChattingRoom(ClientChat cc, String dmRoomName) {
		// TODO Auto-generated method stub		
		nowCc = cc;
		nowId = nowCc.getNowCcId();
		
		setBounds(200, 100, 480, 450);
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		DMPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(DMPanel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(450, 600));
		
		DM.setBounds(0, 0, 400, 350);
		DM.setEditable(false);
		
		// 전체 DM 내용
		ArrayList<Object> DMList = 
				(ArrayList<Object>)nowCc.getObject("getList:" + "directmessage" + "/" + dmRoomName);
		
		if(DMList!=null) {
			for(int i=0; i<DMList.size(); i++) {
				DirectMessageDTO dm = (DirectMessageDTO)DMList.get(i);
				allMsg = allMsg + "[" + dm.getId() + "] " + dm.getMessage() + " " + dm.getDay() + "\n";
			}
		}
		DM.setText(allMsg);

		JButton sendBtn = new JButton("Send");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(textField.getText().length()>0) {
					sendMessage(dmRoomName);
				}
			}
		});

		sendBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(textField.getText().length()>0) {
					sendMessage(dmRoomName);
				}
			}
		});
		scrollPane.add(DM);
		
		GroupLayout gl_contentPane = new GroupLayout(DMPanel);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE).addGap(1)
						.addComponent(sendBtn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(sendBtn, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))));
		DMPanel.setLayout(gl_contentPane);
	}
	
	private void sendMessage(String dmRoomName) {
		String msg = textField.getText();
		textField.setText("");
		
		nowCc.send("sendDM:" + nowId + "/" + msg + "/" + dmRoomName);
	}
	
	// TextPane에 Message를 Append 하기 위한 메소드(Document 이용)
	public void setMessage(String msg) {
		try {
			System.out.println(msg + "//");
			
			Document document = DM.getDocument();
			
			Date time = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String day = format.format(time);
			
			String m = msg + " " + day + "\n";
			
			document.insertString(document.getLength(), m, null);
		} catch (Exception ble) {
			System.err.println("Bad Location. Exception:" + ble);
		}
	}

}
