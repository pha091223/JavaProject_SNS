package temp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

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
		
		JPanel myPost = new JPanel();
		myPost.setLayout(new BoxLayout(myPost, BoxLayout.Y_AXIS));
		
		scrollPane.setViewportView(myPost);
		
		for(int i=0; i<10; i++) {
			myPost.add(viewMyPost());
		}
		
		this.add(myFriendAll);
	}

	private Panel viewMyPost() {
		// TODO Auto-generated method stub
		Panel Mp = new Panel();
		
		JLabel lblNewLabel = new JLabel("FriendName");
		
		JButton btnNewButton = new JButton("Profile");
		
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(Mp);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
						.addGap(30)
						.addComponent(lblNewLabel)
						.addPreferredGap(ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
						.addComponent(btnNewButton)
						.addGap(30))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnNewButton)
							.addComponent(lblNewLabel))
						.addContainerGap(5, Short.MAX_VALUE))
			);
		
		Mp.setLayout(groupLayout);
		
		return Mp;
	}
}
