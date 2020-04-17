package frame;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import client.ClientChat;
import db.DirectMessageDTO;

public class DirectMessageFrame extends JFrame {
	private ClientChat nowCc = null;
	private String nowId = null;
	private String dmRName = null;
	
	DirectMessageFrame(ClientChat cc){
		super("DirectMessage");
		nowCc = cc;
		nowId = nowCc.getNowCcId();
	}

	public void OpenChattingRoom(String dmRName) {
		// TODO Auto-generated method stub
		this.dmRName = dmRName;
		
		setBounds(200, 100, 480, 285);
		setLocationRelativeTo(null);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(450, 600));

		JTextPane textPane = new JTextPane();
		textPane.setBounds(0, 0, 400, 200);
		// 두 번 클릭했었을 때 처음 뙇! 창이 뜰 때 디비에 있던 dm 관련 정보를 가져옴
		// =>
		// 이 방에 있는 사람들 목록을 불러오고 대화 목록에 추가하기
//		ArrayList<Object> dmRommList = (ArrayList<Object>) nowCc.getObject("getList:dmroom/" + nowId + "/" + roomname + "\t");
//		// 이 방에서 대화한 정보를 모두 불러옴		
//		ArrayList<Object> dmList = (ArrayList<Object>) nowCc.getObject("getList:dm/" + roomname);
//		String msg = "";
//		for (int i = 0; i < dmList.size(); i++) {
//			DirectMessageDTO dm = (DirectMessageDTO) dmList.get(i);
//			msg = msg + "[" + dm.getId() + "] " + dm.getMessage() + " " + dm.getDay() + "\n";
//		}
//		textPane.setText(msg);

		JButton btnSend = new JButton("Send");
		JTextField textField = new JTextField();
		textField.setColumns(10);

		btnSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (textField.getText().length() > 0) {
					String msg = textField.getText();
					textField.setText("");
//					nowCc.send("senddm:" + nowId + "/" + msg + "/" + roomname);

					Document document = textPane.getDocument();

					try {
						document.insertString(document.getLength(), msg, null /* or attrubute set */);

					} catch (BadLocationException ble) {
						System.err.println("Bad Location. Exception:" + ble);
					}
				}
			}
		});

		scrollPane.add(textPane);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE).addGap(1)
						.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))));
		contentPane.setLayout(gl_contentPane);

		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

}
