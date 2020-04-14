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
import java.awt.TextArea;

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
		getContentPane().setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		getContentPane().add(panel, "Center");
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(32, 5, 150, 500);
		textPane.setText("AAAAAA" + "\n");
		panel.add(textPane);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		this.setBounds(200, 100, 300, 500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		scrollPane.setViewportView(textPane);
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
	}
}
