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
		String chkMsg = "MyDM Delete hope:" + "dmRoomName" + "/" + "nowId";
		String keyword = chkMsg.substring(chkMsg.indexOf(":")+1, chkMsg.length());
		String msg = "deleteDM:sure" + "/" + keyword;
		String dmRoomName = msg.substring(msg.indexOf("/")+1, msg.lastIndexOf("/"));
		String id = msg.substring(msg.lastIndexOf("/")+1, msg.length());
		
		System.out.println(dmRoomName);
		System.out.println(id);
	}

}
