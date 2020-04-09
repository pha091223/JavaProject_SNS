package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import client.ClientChat;
import db.FriendDTO;
import db.MemberDTO;

public class HomeFrame extends JFrame {
	private JTabbedPane tabPane = new JTabbedPane();
	
	private JPanel tab_1 = new JPanel();
	private JPanel tab_2 = new JPanel();
	private JPanel tab_3 = new JPanel();
	private JPanel tab_4 = new JPanel();
	
	private JPanel searchP = new JPanel();
	
	private JButton searchM = new JButton();
	
	private static ClientChat nowCc = null;
	private static String nowId = null;
	
	private static HomeFrame HomeF = null;
	
	private HomeFrame(){
		super("SNS Program");
	}

	public void Frame(Object o) {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 500, 500);
		
		createTimeline();
		createProfile(tab_2, nowId, nowCc);
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

	private void createTimeline() {
		// TODO Auto-generated method stub		
		
	}

	public void createProfile(JPanel tab_2, String id, ClientChat nowCc) {
		// TODO Auto-generated method stub
		Object receiveObject = nowCc.getDBObject("profile:" + id);
		MemberDTO my = (MemberDTO)receiveObject;
		
		tab_2.setLayout(null);
		tab_2.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// 현재 사용자 Id
		JLabel IdLabel = new JLabel(my.getId());
		IdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		IdLabel.setBounds(30, 55, 120, 15);
		IdLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		tab_2.add(IdLabel);
		
		// 친구 목록 Button
		JButton FrListBtn = new JButton("Follow List");
		FrListBtn.setBounds(190, 55, 95, 20);
		FrListBtn.setBorderPainted(false);
		FrListBtn.setContentAreaFilled(false);
		Font font = FrListBtn.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		FrListBtn.setFont(font.deriveFont(attributes));
		tab_2.add(FrListBtn);
		
		// 관심글 목록 Button
		JButton FListBtn = new JButton("Favorite");
		FListBtn.setBounds(345, 55, 95, 20);
		FListBtn.setBorderPainted(false);
		FListBtn.setContentAreaFilled(false);
		font = FListBtn.getFont();
		attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		FListBtn.setFont(font.deriveFont(attributes));
		tab_2.add(FListBtn);
		
		// 내가 쓴 글 (스크롤 기능)
		JPanel myPostAll = new JPanel();
		myPostAll.setLayout(null);
		myPostAll.setBounds(0, 120, 480, 285);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 120, 480, 285);
		scrollPane.setPreferredSize(new Dimension(450, 1000));
		myPostAll.add(scrollPane);

		JPanel myPost = new JPanel();
		myPost.setLayout(new BoxLayout(myPost, BoxLayout.Y_AXIS));
		
		scrollPane.setViewportView(myPost);
		
		for(int i=0; i<10; i++) {
			myPost.add(viewMyPost());
		}
		
		tab_2.add(scrollPane);
		
		if(this.nowId.equals(id)) {
			JButton MyPageBtn = new JButton("MyPage");
			MyPageBtn.setBounds(12, 410, 97, 23);
			tab_2.add(MyPageBtn);
			
			MyPageBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new MyPageFrame(nowCc, id);
				}
			});
			
			JButton PostWriteBtn = new JButton("Write");
			PostWriteBtn.setBounds(270, 410, 97, 23);
			tab_2.add(PostWriteBtn);
			
			PostWriteBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		} else {
			receiveObject = nowCc.getDBObject("setList:" + "friend" + "/" + nowId);
			ArrayList<Object> fList = (ArrayList<Object>)receiveObject;
			
			JButton FollowBtn = new JButton();
			FollowBtn.setBounds(12, 410, 97, 23);
			
			boolean a = false;
			for(int i=0; i<fList.size(); i++) {
				FriendDTO f = (FriendDTO)fList.get(i);
				if(f.getMyId().equals(nowId) && f.getyourId().equals(id)) {
					FollowBtn.setText("Unfollow");
					a = true;
					break;
				} else {
					a = false;
				}
				if(a==false) {
					FollowBtn.setText("Follow");
				}
				tab_2.add(FollowBtn);					
				
				FollowBtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(FollowBtn.getText().equals("Follow")) {
							nowCc.chkSet("addfollow:" + nowId + "/" + id);
							
							if(nowCc.getChkMessage().indexOf("true")!=-1){
								FollowBtn.setText("Unfollow");
							}
						} else if(FollowBtn.getText().equals("Unfollow")) {
							nowCc.chkSet("delfollow" + nowId + "/" + id);
						}
					}
				});
			}
		}
		
		JButton RefreshBtn = new JButton("Refresh");
		RefreshBtn.setBounds(375, 410, 97, 23);
		tab_2.add(RefreshBtn);
		
		RefreshBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	private Panel viewMyPost() {
		Panel Mp = new Panel();
		Mp.setBounds(10, 120, 465, 240);
		
		JTextPane myPostContent = new JTextPane();
		
		JButton myPostfavoriteBtn = new JButton("Favorite");
		
		myPostfavoriteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton myPostModifyBtn = new JButton("Modify");
		
		myPostModifyBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JLabel writerId = new JLabel(nowId);
		
		JLabel myPostFavoriteNum = new JLabel("Favorite num");
		
		JButton myPostDeleteBtn = new JButton("Delete");
		
		GroupLayout gl_panel = new GroupLayout(Mp);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(12)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(writerId)
						.addComponent(myPostContent, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(myPostfavoriteBtn)
							.addGap(8)
							.addComponent(myPostFavoriteNum)
							.addGap(98)
							.addComponent(myPostDeleteBtn)
							.addGap(1)
							.addComponent(myPostModifyBtn))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(19)
					.addComponent(writerId)
					.addGap(10)
					.addComponent(myPostContent, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(myPostfavoriteBtn)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(4)
							.addComponent(myPostFavoriteNum))
						.addComponent(myPostDeleteBtn)
						.addComponent(myPostModifyBtn)))
		);
		
		Mp.setLayout(gl_panel);
		
		return Mp;
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
		
		JPanel searchR = new JPanel();
		searchR.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		searchM.setPreferredSize(new Dimension(80, 30));
		searchR.add(searchM);
		tab_4.add(searchR, "Center");
		
		searchM.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(searchM.getText().length()>0) {
					new ProfileFrame(searchM.getText(), nowCc, HomeF);
				}
			}
		});
	}
	
	private void createSearchData(JTextField txtInput) {
		Object receiveObject = nowCc.getDBObject("setList:" + "member" + "/" + nowId);
		setupAutoComplete(txtInput, (ArrayList<Object>)receiveObject);
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
						if(cbInput.getSelectedItem()!=null && cbInput.getSelectedItem().toString()!=null) {
							txtInput.setText(cbInput.getSelectedItem().toString());
							for(int i=0; i<items.size(); i++) {
								MemberDTO m = (MemberDTO)items.get(i);
								if(m.getId().equals(txtInput.getText())) {
									searchM.setText(txtInput.getText());
									txtInput.setText("");
									break;
								}
							}
						}
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
		tabPane.add("Profile", tab_2);
		tabPane.add("DirectMessage", tab_3);
		tabPane.add("Search", tab_4);
	}

}
