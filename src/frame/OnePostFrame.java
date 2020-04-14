package frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import client.ClientChat;
import db.PostDTO;

public class OnePostFrame extends JFrame {
	private HomeFrame HomeF = null;
	private ClientChat nowCc = null;

	private String nowId = null;
	
	OnePostFrame(HomeFrame hF, ClientChat cc){
		super("Post");
		HomeF = hF;
		nowCc = cc;
		nowId = nowCc.getNowCcId();
	}
	
	public void viewPostLayOut(PostDTO p) {
		this.setLayout(new BorderLayout());
		this.setBounds(100, 100, 530, 400);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		contentPane.add(viewPost(p, "favorite"));
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		this.add(contentPane, "Center");
	}
	
	private void getListSize(PostDTO p, JButton postFavoriteNum) {
		String msg = "getList:" + "favorite" + "/" + p.getNo() + "/u";
		Object receiveObject = nowCc.getObject(msg);
		ArrayList<Object> list = (ArrayList<Object>)receiveObject;
		postFavoriteNum.setText("Likes : " + (String.valueOf(list.size())));
	}

	public Panel viewPost(PostDTO p, String keyword) {
		Panel viewPost = new Panel();
		
		if(keyword.equals("favorite")) {
			viewPost.setBounds(32, 20, 465, 240);	
		} else {
			viewPost.setBounds(10, 120, 465, 240);			
		}
		
		// 글 표시
		JTextPane postContent = new JTextPane();
		postContent.setEditable(false);
		postContent.setText(p.getText());
		
		JButton postfavoriteBtn = new JButton("Favorite");
		
		nowCc.send("chkFavorite:" + nowId + "/" + p.getNo());
		nowCc.receive();
		
		if(nowCc.getReceiveMessage().contains("true")) {
			postfavoriteBtn.setText("Unfavorite");
		} else {
			postfavoriteBtn.setText("Favorite");
		}
		
		// 관심글을 누른 사람 수
		JButton postFavoriteNum = new JButton();
		
		postfavoriteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(postfavoriteBtn.getText().equals("Favorite")) {
					nowCc.chkSet("addFavorite:" + nowId + "/" + p.getNo());
					
					if(nowCc.getChkMessage().indexOf("true")!=-1){
						postfavoriteBtn.setText("Unfavorite");
					}
					
					getListSize(p, postFavoriteNum);
				} else if(postfavoriteBtn.getText().equals("Unfavorite")) {
					nowCc.chkSet("delFavorite:" + nowId + "/" + p.getNo());
					
					if(nowCc.getChkMessage().indexOf("true")!=-1) {
						postfavoriteBtn.setText("Favorite");
					}
					
					getListSize(p, postFavoriteNum);
				}
			}
		});
		
		JButton postModifyBtn = new JButton("Modify");
		
		postModifyBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// 글 수정
			}
		});
		
		// 글 작성자 이름
		JLabel writerId = new JLabel(p.getId());
		
		// 글 작성 시간
		JLabel writeTime = new JLabel(p.getDay());
		
		// 관심글을 누른 사람 수
		getListSize(p, postFavoriteNum);
		postFavoriteNum.setBorderPainted(true);
		postFavoriteNum.setContentAreaFilled(false);
		Font font = postFavoriteNum.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		postFavoriteNum.setFont(font.deriveFont(attributes));
		
		postFavoriteNum.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String msg = "getList:" + "favorite" + "/" + p.getNo() + "/u";
				Object receiveObject = nowCc.getObject(msg);
				
				UserListFrame userListF = new UserListFrame(HomeF, nowCc, receiveObject);
				userListF.viewListFrame("favorite");
			}
		});
		
		// 글 삭제
		JButton postDeleteBtn = new JButton("Delete");
		
		postDeleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				nowCc.chkSet("deletePost:" + nowId + "/" + p.getNo());
			}
		});
		
		if(nowCc.getNowScId().equals(p.getId())) {
			GroupLayout gl_panel = new GroupLayout(viewPost);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(12)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(writerId)							
								.addGap(255)
								.addComponent(writeTime))
							.addComponent(postContent, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(postfavoriteBtn)
								.addGap(8)
								.addComponent(postFavoriteNum)
								.addGap(98)
								.addComponent(postDeleteBtn)
								.addGap(1)
								.addComponent(postModifyBtn))))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(19)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(writerId)
								.addComponent(writeTime))
						.addGap(10)
						.addComponent(postContent, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
						.addGap(10)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(postfavoriteBtn)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(postFavoriteNum))
							.addComponent(postDeleteBtn)
							.addComponent(postModifyBtn)))
			);
			viewPost.setLayout(gl_panel);
			
		} else if(!nowCc.getNowScId().equals(p.getId())) {
			GroupLayout gl_panel = new GroupLayout(viewPost);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(12)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(writerId)							
								.addGap(255)
								.addComponent(writeTime))
							.addComponent(postContent, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(postfavoriteBtn)
								.addGap(8)
								.addComponent(postFavoriteNum))))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(19)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(writerId)
								.addComponent(writeTime))
						.addGap(10)
						.addComponent(postContent, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
						.addGap(10)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(postfavoriteBtn)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(postFavoriteNum))))
			);
			viewPost.setLayout(gl_panel);
		}
		return viewPost;
	}

}
