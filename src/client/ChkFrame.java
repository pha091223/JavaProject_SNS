package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChkFrame extends JFrame {
	private JPanel cP, sP;
	private JLabel textLabel;
	private JButton OkBtn;
	
	private ChkFrame now = null;
	private ClientChat cc = null;
	
	private String chk = null;
	
	ChkFrame(String chk, ClientChat cc){
		super("Check");
		setResizable(false);
		this.cc = cc;
		this.chk = chk;
		now = this;
		Frame();
	}
	
	private void Frame() {
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 250, 150);
		
		this.setLocationRelativeTo(null);
		
		// Center panel
		cP = new JPanel();
		cP.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
		
		if(chk.equals("login true")) {
			textLabel = new JLabel("Welcome.");
			cP.add(textLabel);
		} else if(chk.equals("login false")) {
			textLabel = new JLabel("Inexistant ID or Wrong PWD");
			cP.add(textLabel);
		} else if(chk.equals("join true")) {
			textLabel = new JLabel("Join.");
			cP.add(textLabel);
		} else if(chk.equals("join false")) {
			textLabel = new JLabel("Do not join.");
			cP.add(textLabel);
		} else {
			textLabel = new JLabel(chk);
			cP.add(textLabel);
		}
		
		// South panel
		sP = new JPanel();
		sP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		OkBtn = new JButton("OK");
		sP.add(OkBtn);
		
		this.add(cP, "Center");
		this.add(sP, "South");
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		OkBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				now.setVisible(false);
				cc.Home(chk, cc);
			}
		});
	}

}
