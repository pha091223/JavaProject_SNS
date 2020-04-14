package test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

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
		this.setBounds(100, 100, 530, 400);
		
		JPanel temp = new JPanel();
		
		JButton postFavoriteNum = new JButton("Favorite num");
		postFavoriteNum.setPreferredSize(new Dimension(50, 20));
//		postFavoriteNum.setBorderPainted(false);
		postFavoriteNum.setContentAreaFilled(false);
		Font font = postFavoriteNum.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		postFavoriteNum.setFont(font.deriveFont(attributes));
		
		temp.add(postFavoriteNum);
		this.add(temp, "Center");
		this.setVisible(true);
	}

}
