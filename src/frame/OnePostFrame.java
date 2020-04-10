package frame;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import client.ClientChat;
import db.PostDTO;

public class OnePostFrame {
	private ClientChat cc = null;
	
	OnePostFrame(ClientChat cc){
		this.cc = cc;
//		System.out.println(cc.getNowCcId());
	}

	public Panel viewPost(PostDTO p) {
		
		Panel viewPost = new Panel();
		viewPost.setBounds(10, 120, 465, 240);
		
		JTextPane postContent = new JTextPane();
		
		JButton postfavoriteBtn = new JButton("Favorite");
		
		postfavoriteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 관심글(좋아요) 등록
			}
		});
		
		JButton postModifyBtn = new JButton("Modify");
		
		postModifyBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// 자신이 쓴 글이라면 수정 버튼 활성화
			}
		});
		
		// 글 작성자 이름
		JLabel writerId = new JLabel(p.getId());
		
		// getList - 조건 : 글 작성자=대상 Id
		JLabel postFavoriteNum = new JLabel("Favorite num");
		
		JButton postDeleteBtn = new JButton("Delete");
		
		postDeleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// 자신이 쓴 글이라면 삭제 버튼 활성화
			}
		});
		
		GroupLayout gl_panel = new GroupLayout(viewPost);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(12)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(writerId)
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
					.addComponent(writerId)
					.addGap(10)
					.addComponent(postContent, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(postfavoriteBtn)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(4)
							.addComponent(postFavoriteNum))
						.addComponent(postDeleteBtn)
						.addComponent(postModifyBtn)))
		);
		
		viewPost.setLayout(gl_panel);
		
		return viewPost;
	}

}
