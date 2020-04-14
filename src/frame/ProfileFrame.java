package frame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.ClientChat;

public class ProfileFrame extends JFrame {
	private ClientChat nowCc = null;
	private HomeFrame HomeF = null;
	
	private String id = null;
	
	ProfileFrame(String id, ClientChat cc, HomeFrame HomeF){		
		this.id = id;
		nowCc = cc;
		this.HomeF = HomeF;
		
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 500, 500);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
				
		HomeF.createProfile(panel, id, cc);
		
		this.add(panel, "Center");
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
	}

}
