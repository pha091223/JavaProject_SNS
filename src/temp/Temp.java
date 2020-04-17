package temp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import frame.DirectMessageFrame;

public class Temp extends JFrame {
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Temp frame = new Temp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Temp() {
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 500, 500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setLocationRelativeTo(null);
		
		JPanel temp = new JPanel();
		temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
		
		JPanel oneUser = new JPanel();
		oneUser.setBounds(12, 35, 410, 65);
		oneUser.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY)));
		
		JLabel userDMsendId = new JLabel("recentlyId");
		
		// 마지막 Msg 날짜
		JLabel userDMDay = new JLabel("0000-00-00 --:--:--");
		
		// 가장 최근에 받은 혹은 보낸 Message
		JLabel userDMMsg = new JLabel("recentlyId");
		
//		JButton userDMMsg = new JButton("recentlyMsg");
//		userDMMsg.setBorderPainted(false);
//		userDMMsg.setContentAreaFilled(false);
//		Font font = userDMMsg.getFont();
//		Map attributes = font.getAttributes();
//		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
//		userDMMsg.setFont(font.deriveFont(attributes));
//		
//		userDMMsg.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				temp.removeAll();
//				
//				JButton tempL = new JButton("Clear");
//				temp.add(tempL);
//				
//				temp.revalidate();
//				temp.repaint();
//				
//				tempL.addActionListener(new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						// TODO Auto-generated method stub
//						temp.removeAll();
//						temp.add(oneUser);
//						temp.revalidate();
//						temp.repaint();
//					}
//				});
//			}
//		});

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
						.addComponent(userDMMsg, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)))
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
		
		oneUser.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("/Panel ClickOff");
				oneUser.setBackground(null);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("/Panel ClickOn");
				oneUser.setBackground(Color.LIGHT_GRAY);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		temp.add(oneUser);
		
		this.add(temp, "Center");
	}
}
