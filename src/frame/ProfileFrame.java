package frame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.ClientChat;

public class ProfileFrame extends JFrame {
	private ClientChat nowCc = null;
	private HomeFrame homeF = null;
	
	private String id = null;
	
	ProfileFrame(String id, ClientChat cc, HomeFrame HomeF){		
		this.id = id;
		nowCc = cc;
		this.homeF = HomeF;
		
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 500, 500);
		
		setLocationFrame();
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
				
		HomeF.createProfile(panel, id, cc);
		
		this.add(panel, "Center");
		
		this.setVisible(true);
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
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

}
