package test;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class Test extends JFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Test();

	}
	
	Test() {
		/* component text에 underline 긋기
		JLabel main = new JLabel("Sign in");
		main.setBounds(125, 15, 50, 20);
		main.setFont(new Font("Dialog", Font.BOLD, 20));
		System.out.println(main.getFont());
		this.add(main);
		*/
		
		/* panel에 scroll 기능 추가
		component가 위치할 panel 생성 후, panel 위에 scrollpane을 올림
		전체 panel(tab_2)에는 component를 갖고 있는 panel은 add 안해도 무관 > scrollpane만 add
		scrollpane의 대상 panel의 크기에 따라 scroll 여부가 결정됨 */
		
		/* component 추가에 따른 panel 크기 변경 (테스트 필요)
		JTextArea temp_1 = new JTextArea();
		temp_1.setBounds(0, 0, 465, 200);
		temp_1.setEditable(false);
		JTextArea temp_2 = new JTextArea();
		temp_2.setBounds(0, 0, 465, 200);
		temp_2.setEditable(false);
		JTextArea[] temp = {temp_1, temp_2};
		
		int count_y = 0;
		int panelYSize = 0;
		int textAreaSize = 200;
		for(int i=0; i<temp.length; i++) {
			temp[i].setBounds(0, count_y, 465, textAreaSize);
			temp[i].setEditable(false);
			count_y = count_y + 100;
			panelYSize = panelYSize + textAreaSize + count_y;
			myPost.setPreferredSize(new Dimension(465, panelYSize));
			myPost.add(temp[i]);
		}
		*/
	}

}
