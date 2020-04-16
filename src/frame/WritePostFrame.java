package frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import client.ClientChat;

public class WritePostFrame extends JFrame {
	private ClientChat nowCc = null;

	WritePostFrame(ClientChat cc) {
		super("WritePost");
		nowCc = cc;
		createWritePostFrame();
	}

	private void createWritePostFrame() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout());
		this.setBounds(100, 100, 530, 400);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		JTextPane textPane = new JTextPane();
		textPane.setBounds(12, 10, 491, 274);
		
		contentPane.add(textPane);

		JButton btnAddPicture = new JButton("addPic");
		
		btnAddPicture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 그림 넣기
			}
		});
		
		btnAddPicture.setBounds(12, 290, 97, 23);
		contentPane.add(btnAddPicture);

		JButton btnShare = new JButton("Share");
		
		btnShare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nowCc.send("sharePost:" + nowCc.getNowCcId() + "/" + textPane.getText());
				
				if(nowCc.getReceiveMessage().contains("true")) {
					setClose();
				}
			}
		});
		
		btnShare.setBounds(306, 290, 97, 23);
		contentPane.add(btnShare);
		
		JButton btnCancel = new JButton("Cancel");
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setClose();
			}
		});

		btnCancel.setBounds(406, 290, 97, 23);
		contentPane.add(btnCancel);

		this.add(contentPane);

		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);

	}

	public void setClose() {
		this.setVisible(false);
	}

}
