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
		String chkMsg = "DM:" + "[" + "id" + "]" + "DMmsg" + "/" + "DMRoomName";
				
		String msg = chkMsg.substring(chkMsg.indexOf(":")+1, chkMsg.lastIndexOf("/"));
		
		String sendId = msg.substring(msg.indexOf("[")+1, msg.indexOf("]"));
		String receiveMsg = msg.substring(msg.indexOf("]")+1, msg.length());
		String rn = chkMsg.substring(chkMsg.lastIndexOf("/")+1, chkMsg.length());
		
		System.out.println(msg);
		System.out.println(receiveMsg);
		System.out.println(sendId);
		System.out.println(rn);
	}

}
