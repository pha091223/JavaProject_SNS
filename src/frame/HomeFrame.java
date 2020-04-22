package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import client.ClientChat;
import db.DMRoomDTO;
import db.MemberDTO;
import db.PostDTO;

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

	private static HomeFrame homeF = null;

	private ArrayList<OneDMFrame> OneDMFrameList = new ArrayList<>();

	private HomeFrame() {
		super("SNS Program" + "_" + nowId);
	}

	public static HomeFrame getInstance(String id, ClientChat cc){
		nowId=id;
		nowCc=cc;
		if(homeF==null) {
			homeF = new HomeFrame();
		}
		return homeF;
	}

	public void Frame() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 500, 500);

		createTimeline();
//		createProfile(tab_2, nowId, nowCc);
		createDirectMessage();
//		createSearch();

		createTabbledP();

		this.add(tabPane, "Center");

		this.setLocationRelativeTo(null);
		this.setVisible(true);

		this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);

		// 윈도우 동작을 읽는 리스너 : HomeFrame 창을 X 버튼을 눌러 닫을시 로그아웃 처리
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				nowCc.send("logout:" + nowCc.getNowCcId());
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void createTimeline() {
		// TODO Auto-generated method stub
		tab_1.setLayout(null);
		tab_1.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel timeline = new JPanel();
		timeline.setLayout(new BorderLayout());
		timeline.setBounds(0, 0, 480, 435);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 480, 435);
		scrollPane.setPreferredSize(new Dimension(450, 1000));
		timeline.add(scrollPane);

		JPanel myPost = new JPanel();
		myPost.setLayout(new BoxLayout(myPost, BoxLayout.Y_AXIS));

		scrollPane.setViewportView(myPost);

		Object o = nowCc.getObject("getList:" + "post" + "/" + nowId + "/t");
		ArrayList<Object> pList = (ArrayList<Object>) o;

		settingPostView(pList, myPost, nowId);

		JPanel Btn = new JPanel();
		Btn.setLayout(new FlowLayout(FlowLayout.RIGHT, 8, 3));

		JButton RefreshBtn = new JButton("Refresh");
		RefreshBtn.setPreferredSize(new Dimension(97, 23));

		RefreshBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("/RefreshBtn Click:Timeline");

				myPost.removeAll();

				Object o = nowCc.getObject("getList:" + "post" + "/" + nowId + "/t");
				ArrayList<Object> pList = (ArrayList<Object>)o;

				settingPostView(pList, myPost, nowId);

				// 레이아웃 변경을 적용하고 다시 그림
				revalidate();
				repaint();
			}
		});

		JButton PostWriteBtn = new JButton("Write");
		PostWriteBtn.setPreferredSize(new Dimension(97, 23));

		PostWriteBtnListner(PostWriteBtn, nowCc);

		Btn.add(PostWriteBtn);
		Btn.add(RefreshBtn);

		timeline.add(scrollPane, "Center");
		timeline.add(Btn, "South");

		tab_1.add(timeline);
	}

	// PostList, PostList를 띄우는 그룹화 된 Panel이 들어갈 JPanel, 글을 보고 있는 사용자 Id
	private void settingPostView(ArrayList<Object> pList, JPanel postPanel, String id) {
		OnePostFrame pF = new OnePostFrame(homeF, nowCc);

		if(pList.size()>0) {
			for (int i=0; i<pList.size(); i++) {
				PostDTO p = (PostDTO)pList.get(i);
				postPanel.add(pF.viewPost(p, ""));
			}
		} else if(pList.size()==0) {
			JPanel temp = new JPanel();
			temp.setLayout(new BorderLayout());
			JLabel empty = new JLabel("Empty Post");
			empty.setHorizontalAlignment(JLabel.CENTER);
			temp.add(empty, "Center");
			postPanel.add(temp);
		}
	}

	public void createProfile(JPanel tab_2, String id, ClientChat nowCc) {
		// TODO Auto-generated method stub
		Object receiveObject = nowCc.getObject("profile:" + id);
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

		FrListBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object receiveObject = null;

				if(nowId.equals(id)) {
					receiveObject = nowCc.getObject("getList:" + "friend" + "/" + nowId);
				} else if(!nowId.equals(id)) {
					receiveObject = nowCc.getObject("getList:" + "friend" + "/" + id);
				}

				UserListFrame userListF = new UserListFrame(homeF, nowCc, receiveObject);
				userListF.viewListFrame("friend");
			}
		});

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

		FListBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object receiveObject = null;

				if(nowId.equals(id)) {
					receiveObject = nowCc.getObject("getList:" + "favorite" + "/" + nowId);
				} else if(!nowId.equals(id)) {
					receiveObject = nowCc.getObject("getList:" + "favorite" + "/" + id);
				}

				new FavoriteFrame(homeF, nowCc, receiveObject);
			}
		});

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

		// 자신이 쓴 글 List 받아오기
		Object receiveObject_myPost = nowCc.getObject("getList:" + "post" + "/" + id);
		ArrayList<Object> myPList = (ArrayList<Object>) receiveObject_myPost;

		settingPostView(myPList, myPost, id);

		tab_2.add(scrollPane);

		// 사용자와 Profile 대상자가 같은 사람일 시 : MyPage, PostWrite
		if(this.nowId.equals(id)) {
			JButton MyPageBtn = new JButton("MyPage");
			MyPageBtn.setBounds(12, 410, 97, 23);
			tab_2.add(MyPageBtn);

			MyPageBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new MyPageFrame(nowCc, id, homeF);
				}
			});

			JButton PostWriteBtn = new JButton("Write");
			PostWriteBtn.setBounds(270, 410, 97, 23);
			tab_2.add(PostWriteBtn);

			PostWriteBtnListner(PostWriteBtn, nowCc);
		} else {
			// 사용자와 Profile 대상자가 다른 사람일 시 : Follow, D.M
			JButton FollowBtn = new JButton();
			FollowBtn.setBounds(12, 410, 97, 23);

			nowCc.send("chkFollow:" + nowId + "/" + id);
			nowCc.sleep();

			if(nowCc.getReceiveMessage().indexOf("true")!=-1) {
				FollowBtn.setText("Unfollow");
			} else if(nowCc.getReceiveMessage().indexOf("false")!=-1) {
				FollowBtn.setText("Follow");
			}
			tab_2.add(FollowBtn);

			FollowBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(FollowBtn.getText().equals("Follow")) {
						nowCc.send("addFollow:" + nowId + "/" + id);
						nowCc.sleep();

						if(nowCc.getReceiveMessage().indexOf("true")!=-1) {
							FollowBtn.setText("Unfollow");
						}
					} else if(FollowBtn.getText().equals("Unfollow")) {
						nowCc.send("delFollow:" + nowId + "/" + id);
						nowCc.sleep();

						if(nowCc.getReceiveMessage().indexOf("true")!=-1) {
							FollowBtn.setText("Follow");
						}
					}
				}
			});

			JButton DMBtn = new JButton("D.M");
			DMBtn.setBounds(118, 410, 70, 23);
			tab_2.add(DMBtn);

			DMBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Object o = nowCc.getObject("chkDM:" + nowCc.getNowCcId() + "/" + id);
					String roomName = (String)o;

					System.out.println("/chkRoom:" + roomName);

					DirectMessageFrame dmF = new DirectMessageFrame(homeF, id);
					nowCc.setOpenDmFrame(dmF);

					if(roomName!=null) {
						dmF.OpenChattingRoom(nowCc, roomName);
					} else if(roomName==null) {
						dmF.OpenChattingRoom(nowCc, "temp" + "+" + id);
					}
				}
			});
		}

		JButton RefreshBtn = new JButton("Refresh");
		RefreshBtn.setBounds(375, 410, 97, 23);
		tab_2.add(RefreshBtn);

		RefreshBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("/RefreshBtn Click:Profile");

				myPost.removeAll();

				Object receiveObject_Post = nowCc.getObject("getList:" + "post" + "/" + id);
				ArrayList<Object> pList = (ArrayList<Object>)receiveObject_Post;

				settingPostView(pList, myPost, id);

				myPost.revalidate();
				myPost.repaint();
			}
		});
	}

	private void PostWriteBtnListner(JButton PostWriteBtn, ClientChat nowCc) {
		PostWriteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				WritePostFrame writePost = new WritePostFrame(nowCc, homeF);
			}
		});
	}

	private void createDirectMessage() {
		// TODO Auto-generated method stub
		tab_3.setLayout(null);
		tab_3.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel DM = new JPanel();
		DM.setLayout(new BorderLayout());
		DM.setBounds(0, 0, 480, 435);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 480, 435);
		scrollPane.setPreferredSize(new Dimension(450, 1000));
		DM.add(scrollPane);

		JPanel myDMList = new JPanel();
		myDMList.setLayout(new BoxLayout(myDMList, BoxLayout.Y_AXIS));

		scrollPane.setViewportView(myDMList);

		ArrayList<Object> DMRoomList = 
				(ArrayList<Object>) nowCc.getObject("getList:" + "dmroom" + "/" + nowId);

		settingDMView(DMRoomList, myDMList);

		JPanel Btn = new JPanel();
		Btn.setLayout(new FlowLayout(FlowLayout.RIGHT, 8, 3));

		JButton frListBtn = new JButton("Follow List");
		frListBtn.setPreferredSize(new Dimension(97, 23));

		frListBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object receiveObject = nowCc.getObject("getList:" + "friend" + "/" + nowId);

				UserListFrame userListF = new UserListFrame(homeF, nowCc, receiveObject);
				userListF.viewListFrame("DMfriend");
			}
		});

		JButton RefreshBtn = new JButton("Refresh");
		RefreshBtn.setPreferredSize(new Dimension(97, 23));

		RefreshBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("/RefreshBtn Click:DirectMessage");

				myDMList.removeAll();

				ArrayList<Object> DMRoomList = 
						(ArrayList<Object>) nowCc.getObject("getList:" + "dmroom" + "/" + nowId);

				settingDMView(DMRoomList, myDMList);

				revalidate();
				repaint();
			}
		});

		Btn.add(frListBtn);
		Btn.add(RefreshBtn);

		DM.add(scrollPane, "Center");
		DM.add(Btn, "South");

		tab_3.add(DM);
	}

	private void settingDMView(ArrayList<Object> DMRoomList, JPanel myDMList) {
		if(DMRoomList.size()>0) {
			for(int i=0; i<DMRoomList.size(); i++) {
				DMRoomDTO dmR = (DMRoomDTO) DMRoomList.get(i);
				String dmRoomName = dmR.getRoomname();
				String dmYourId = dmR.getId();

				OneDMFrame dmF = new OneDMFrame(nowCc, nowId, homeF);
//				nowCc.setOneDMFrame(dmF);
				myDMList.add(dmF.oneDM(dmRoomName, dmYourId));
				OneDMFrameList.add(dmF);
			}
		} else if(DMRoomList.size()==0) {
			JPanel temp = new JPanel();
			temp.setLayout(new BorderLayout());
			JLabel empty = new JLabel("Empty Message");
			empty.setHorizontalAlignment(JLabel.CENTER);
			temp.add(empty, "Center");
			myDMList.add(temp);
		}
	}
	
//	public void delOneDMFrame(OneDMFrame OneDMF) {
//		OneDMFrameList.remove(OneDMF);
//	}

	public OneDMFrame getOneDMFrame(String dmRoomName) {
		if(OneDMFrameList.size()>0) {
			for(OneDMFrame i : OneDMFrameList) {
				if(i.getDmRoomName().equals(dmRoomName)) {
					return i;
				}
			}
		}
		return null;
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
					new ProfileFrame(searchM.getText(), nowCc, homeF);
				}
			}
		});
	}

	private void createSearchData(JTextField txtInput) {
		Object receiveObject = nowCc.getObject("setList:" + "member" + "/" + nowId);
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
								MemberDTO m = (MemberDTO) items.get(i);
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
						MemberDTO m = (MemberDTO) items.get(i);
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

	// 변화가 있을 때마다 탭을 지웠다가 새로 씀으로써 바로바로 반영
	// 탭 번호 > 0 : timeline
	// 1 : profile
	// 2 : directmessage
	// 4 : search
	public void tabRefresh(String number) {
		switch (number) {
		case "0":
			tab_1.removeAll();
			createTimeline();
			break;
		case "1":
			tab_2.removeAll();
			createProfile(tab_2, nowId, nowCc);
			break;
		case "2":
			tab_3.removeAll();
			createDirectMessage();
		}
	}

	public void setTabColor(int tabNum, String keyword) {
		if(keyword.equals("Color")) {
			tabPane.setBackgroundAt(tabNum, Color.cyan);
		} else if(keyword.equals("noColor")) {
			tabPane.setBackgroundAt(tabNum, null);
		}
	}

	private void createTabbledP() {
		// TODO Auto-generated method stub
		tabPane.add("Timeline", tab_1);
		tabPane.add("Profile", tab_2);
		tabPane.add("DirectMessage", tab_3);
		tabPane.add("Search", tab_4);

		// 탭 변화를 감지하여 각 탭을 누르면 자동 새로고침하여 가장 최근 정보를 띄워줌
		tabPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if(tabPane.getSelectedIndex()==0) {
					tabRefresh("0");
					System.out.println("/Tab0:Timeline");
				} else if(tabPane.getSelectedIndex()==1) {
					tabRefresh("1");
					System.out.println("/Tab1:myProilfe");
				} else if(tabPane.getSelectedIndex()==2) {
					System.out.println("/Tab2:myDirectMessage");
				} else if(tabPane.getSelectedIndex()==3) {
					createSearch();
					System.out.println("/Tab3:Search");
				}
			}
		});
	}

}
