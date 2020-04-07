package client;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class HomeFrame extends JFrame {

	private JTabbedPane tabPane = new JTabbedPane();
	
	private JPanel tab_1 = new JPanel();
	private JPanel tab_2 = new JPanel();
	private JPanel tab_3 = new JPanel();
	private JPanel tab_4 = new JPanel();
	
	private static ClientChat nowCc = null;
	private static String nowId = null;
	private String getTName = null;
	
	private static HomeFrame HomeF = null;
	
	private HomeFrame(){
		super("SNS Program");
		setList("member");
	}

	public void Frame() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 500, 500);
		
		createTimeline();
		createMyPage();
		createDirectMessage();
		createSearch();
		
		createTabbledP();
		
		this.add(tabPane, "Center");
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
	}

	public static HomeFrame getInstance(String id, ClientChat cc) {
		nowId = id;
		nowCc = cc;
		if(HomeF==null) {
			HomeF = new HomeFrame();
		}
		return HomeF;
	}
	
	private void setList(String tName) {
		// TODO Auto-generated method stub
		getTName = tName;
		nowCc.send("setList:" + tName + "/" + nowId);
	}
	
	public String getTName() {
		return getTName;
	}
	
	public void getDBList(Object o) {
		
	}

	private void createTimeline() {
		// TODO Auto-generated method stub		
		tab_1.setLayout(new BoxLayout(tab_1, BoxLayout.Y_AXIS));
		
		JTextArea timeline_1 = new JTextArea();
		timeline_1.setEditable(false);
//		timeline_1.setText("First");
		tab_1.add(timeline_1);
		
		JTextArea timeline_2 = new JTextArea();
		timeline_2.setEditable(false);
		timeline_2.setText("Second");
		tab_1.add(timeline_2);
		
		JTextArea timeline_3 = new JTextArea();
		timeline_3.setEditable(false);
		timeline_3.setText("Third");
		tab_1.add(timeline_3);
		
		JTextArea timeline_4 = new JTextArea();
		timeline_4.setEditable(false);
		timeline_4.setText("Fourth");
		tab_1.add(timeline_4);
		
		JTextArea timeline_5 = new JTextArea();
		timeline_5.setEditable(false);
		timeline_5.setText("Fifth");
		tab_1.add(timeline_5);
	}

	private void createMyPage() {
		// TODO Auto-generated method stub
		
	}

	private void createDirectMessage() {
		// TODO Auto-generated method stub
		
	}

	private void createSearch() {
		// TODO Auto-generated method stub
		
	}

	private void createTabbledP() {
		// TODO Auto-generated method stub
		tabPane.add("Timeline", tab_1);
		tabPane.add("MyPage", tab_2);
		tabPane.add("DirectMessage", tab_3);
		tabPane.add("Search", tab_4);
	}

}
