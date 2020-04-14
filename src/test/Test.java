package test;

import javax.swing.JFrame;

import db.DAOCenter;
import db.MemberDTO;

public class Test extends JFrame {
	private DAOCenter Dc = DAOCenter.getInstance();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Test();
		
	}
	
	Test() {
		MemberDTO a = (MemberDTO)Dc.select("member", "kkk");
		System.out.println(a.getPwd());
	}

}
