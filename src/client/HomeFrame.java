package client;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class HomeFrame extends JFrame {
	private JTabbedPane tabPane = new JTabbedPane();
	
	private JPanel tab_1 = new JPanel();
	private JPanel tab_2 = new JPanel();
	private JPanel tab_3 = new JPanel();
	private JPanel tab_4 = new JPanel();
	
	private ClientChat nowSc = null;
	private String nowId = null;
	
	HomeFrame(String id, ClientChat cc){
		super("SNS Program");
		nowId = id;
		nowSc = cc;
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 500, 500);
		
		setList();
		
		createTimeline();
		createMyPage();
		createDirectMessage();
		CreateSearch();
		
		createTabbledP();
		
		this.add(tabPane, "Center");
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
	}

	private void setList() {
		// TODO Auto-generated method stub
		nowSc.send("setList:" + nowId);
	}

	private void createTimeline() {
		// TODO Auto-generated method stub		
		tab_1.setLayout(new BoxLayout(tab_1, BoxLayout.Y_AXIS));
		
		JTextArea timeline_1 = new JTextArea();
		timeline_1.setEditable(false);
		timeline_1.setText("First");
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

	private void CreateSearch() {
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
