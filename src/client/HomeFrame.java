package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import db.MemberDTO;

import db.MemberDTO;

public class HomeFrame extends JFrame {

	private JTabbedPane tabPane = new JTabbedPane();
	
	private JPanel tab_1 = new JPanel();
	private JPanel tab_2 = new JPanel();
	private JPanel tab_3 = new JPanel();
	private JPanel tab_4 = new JPanel();
	
	private JPanel searchP = new JPanel();
	
	private static ClientChat nowCc = null;
	private static String nowId = null;
	private String getTName = null;
	
	private static HomeFrame HomeF = null;
	
	private Object o = null;
	
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
<<<<<<< HEAD
=======
	
	public String getTName() {
		return getTName;
	}
	
	public void getDBList(Object o) {
		this.o = o;
	}
>>>>>>> refs/remotes/origin/master

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
		nowCc.send("myPage:" + nowId);
		MemberDTO my = (MemberDTO)nowCc.receiveObject();
		
		tab_2.setLayout(null);
		tab_2.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// 현재 사용자 Id
		JLabel IdLabel = new JLabel(my.getId());
		IdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		IdLabel.setBounds(50, 50, 55, 15);
		IdLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		tab_2.add(IdLabel);
		
		// 친구 목록 Button
		JButton FrListBtn = new JButton("Follow List");
		FrListBtn.setBounds(190, 50, 95, 20);
		FrListBtn.setBorderPainted(false);
		FrListBtn.setContentAreaFilled(false);
		Font font = FrListBtn.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		FrListBtn.setFont(font.deriveFont(attributes));
		tab_2.add(FrListBtn);
		
		// 관심글 목록 Button
		JButton FListBtn = new JButton("Favorite");
		FListBtn.setBounds(345, 50, 95, 20);
		FListBtn.setBorderPainted(false);
		FListBtn.setContentAreaFilled(false);
		font = FListBtn.getFont();
		attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		FListBtn.setFont(font.deriveFont(attributes));
		tab_2.add(FListBtn);
		
		// 내가 쓴 글 (스크롤 기능)
		JPanel myPost = new JPanel();
		myPost.setLayout(null);
		myPost.setPreferredSize(new Dimension(465, 500));
		myPost.setBounds(0, 120, 480, 285);
		
		JScrollPane scroll = new JScrollPane(myPost);
		scroll.setBounds(0, 120, 480, 285);
		// 가로 스크롤 사용하지 않음, 스크롤바가 필요할 경우에만 스크롤바 표시
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JTextArea temp = new JTextArea();
		temp.setBounds(0, 0, 480, 200);
		temp.setEditable(false);
		myPost.add(temp);
		tab_2.add(scroll);
	}

	private void createDirectMessage() {
		// TODO Auto-generated method stub
	}

	private void createSearch() {
		// TODO Auto-generated method stub
		tab_4.setLayout(new BorderLayout());
		
		JTextField txtInput = new JTextField();
		searchP.setLayout(new BorderLayout());
		searchP.add(txtInput, "North");
		
		tab_4.add(searchP, "North");
		
		createSearchData(txtInput);
	}
	
	private void createSearchData(JTextField txtInput) {
		setList("member");
		
		setupAutoComplete(txtInput, (ArrayList<Object>)nowCc.receiveObject());
		txtInput.setColumns(30);
	}

	private boolean isAdjusting(JComboBox cbInput) {
		if(cbInput.getClientProperty("is_adjusting") instanceof Boolean) {
			return (Boolean)cbInput.getClientProperty("is_adjusting");
		}
		return false;
	}

	private void setAdjusting(JComboBox cbInput, boolean adjusting) {
		cbInput.putClientProperty("is_adjusting", adjusting);
	}

	private void setupAutoComplete(final JTextField txtInput, final ArrayList<Object> items) {
		final DefaultComboBoxModel model = new DefaultComboBoxModel();
		final JComboBox cbInput = new JComboBox(model) {
			public Dimension getPreferredSize() {
				return new Dimension(super.getPreferredSize().width, 0);
			}
		};
		
		setAdjusting(cbInput, false);
		
		for(int i=0; i<items.size(); i++) {
			MemberDTO m = (MemberDTO)items.get(i);
			model.addElement(m.getId());
		}
		
		cbInput.setSelectedItem(null);
		
		cbInput.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isAdjusting(cbInput)) {
					if(cbInput.getSelectedItem()!=null) {
						txtInput.setText(cbInput.getSelectedItem().toString());
					}
				}
			}
		});

		txtInput.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				setAdjusting(cbInput, true);
				if(e.getKeyCode()==KeyEvent.VK_SPACE) {
					if(cbInput.isPopupVisible()) {
						e.setKeyCode(KeyEvent.VK_ENTER);
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_UP
						|| e.getKeyCode()==KeyEvent.VK_DOWN) {
					e.setSource(cbInput);
					cbInput.dispatchEvent(e);
					if(e.getKeyCode()==KeyEvent.VK_ENTER) {
						txtInput.setText(cbInput.getSelectedItem().toString());
						cbInput.setPopupVisible(false);
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					cbInput.setPopupVisible(false);
				}
				setAdjusting(cbInput, false);
			}
		});
		
		txtInput.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				updateList();
			}
			public void removeUpdate(DocumentEvent e) {
				updateList();
			}
			public void changedUpdate(DocumentEvent e) {
				updateList();
			}

			private void updateList() {
				setAdjusting(cbInput, true);
				model.removeAllElements();
				String input = txtInput.getText();
				if(!input.isEmpty()) {
					for(int i=0; i<items.size(); i++) {
						MemberDTO m = (MemberDTO)items.get(i);
						if(m.getId().toLowerCase().indexOf(input.toLowerCase())!=-1) {
							model.addElement(m.getId());
						}						
					}
				}
				cbInput.setPopupVisible(model.getSize()>0);
				setAdjusting(cbInput, false);
			}
		});
		
		txtInput.setLayout(new BorderLayout());
		txtInput.add(cbInput, BorderLayout.SOUTH);
	}

	private void createTabbledP() {
		// TODO Auto-generated method stub
		tabPane.add("Timeline", tab_1);
		tabPane.add("MyPage", tab_2);
		tabPane.add("DirectMessage", tab_3);
		tabPane.add("Search", tab_4);
	}

}
