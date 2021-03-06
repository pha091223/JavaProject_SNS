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
	private HomeFrame homeF = null;

	WritePostFrame(ClientChat cc, HomeFrame homeF) {
		super("WritePost");
		nowCc = cc;
		this.homeF = homeF;
		
		createWritePostFrame();
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

	private void createWritePostFrame() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 530, 400);
		
		setLocationFrame();
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		JTextPane textPane = new JTextPane();
		textPane.setBounds(12, 10, 491, 274);
		
		contentPane.add(textPane);

//		JButton btnAddPicture = new JButton("addPic");
//		
//		btnAddPicture.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// 그림 넣기
//			}
//		});
//		
//		btnAddPicture.setBounds(12, 290, 97, 23);
//		contentPane.add(btnAddPicture);

		JButton btnShare = new JButton("Share");
		
		btnShare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nowCc.send("sharePost:" + nowCc.getNowCcId() + "/" + textPane.getText());
				nowCc.sleep();
				
				if(nowCc.getReceiveMessage().contains("true")) {
					setClose();
				}
				
				homeF.tabRefresh("0");
				homeF.tabRefresh("1");
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
