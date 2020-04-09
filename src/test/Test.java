package test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Test();

	}
	
	Test() {
		this.setLayout(new BorderLayout());
		this.setBounds(0, 0, 300, 300);
		
		this.setLocationRelativeTo(null);
		
		JPanel temp = new JPanel();
		JButton btn = new JButton("OK");
		
		temp.add(btn);
		this.add(temp, "Center");
		
		this.setVisible(true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(btn.getText().equals("OK")) {
					System.out.println("****");
					btn.setText("NO");
				} else if(btn.getText().equals("NO")) {
					System.out.println("&&&&");
					btn.setText("OK");
				}
			}
		});
		
		/* component text에 underline 긋기
		JLabel main = new JLabel("Sign in");
		main.setBounds(125, 15, 50, 20);
		main.setFont(new Font("Dialog", Font.BOLD, 20));
		System.out.println(main.getFont());
		this.add(main);
		*/
		
	}

}
