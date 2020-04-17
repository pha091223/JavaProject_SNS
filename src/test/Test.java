package test;

import javax.swing.JFrame;

import db.DAOCenter;

public class Test extends JFrame {	
	private DAOCenter Dc = DAOCenter.getInstance();
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		new Test();
		
	}
	
	Test() throws Exception {
		String msg = "createDM:" + "aaa" + "/" + "bbb" + "/" + "message";
		String sendId = msg.substring(msg.indexOf(":")+1, msg.indexOf("/"));
		String receiveId = msg.substring(msg.indexOf("/")+1, msg.lastIndexOf("/"));
		String message = msg.substring(msg.lastIndexOf("/")+1, msg.length());
		
		System.out.println(sendId);
		System.out.println(receiveId);
		System.out.println(message);
	}

}
