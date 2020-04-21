package test;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

import db.DAOCenter;
import frame.DirectMessageFrame;

public class Test extends JFrame {	
	private DAOCenter Dc = DAOCenter.getInstance();
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		new Test();
		
	}
	
	Test() throws Exception {
		String chkMsg = "DM:" + "[" + "i.getNowScId()" + "]" + "DMmsg";
		String a = chkMsg.substring(0, chkMsg.indexOf(":")+1);
		
		System.out.println(a);
	}

}
