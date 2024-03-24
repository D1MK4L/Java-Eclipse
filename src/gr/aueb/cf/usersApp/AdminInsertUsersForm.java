package gr.aueb.cf.usersApp;
/**
 * Creates a User and links the User with
 * an existing Username!
 * 
 * @author D1MK4L
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.usersApp.util.DBUtil;
import gr.aueb.cf.usersApp.util.DateUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTabbedPane;

public class AdminInsertUsersForm extends JFrame {
	private static final long serialVersionUID = 123456;
	private JPanel contentPane;
	private Map<String, Integer> cities;
	private Map<String, Integer> usernames;
	private DefaultComboBoxModel<String> citiesModel;
	private DefaultComboBoxModel<String> usernamesModel;
	private JComboBox<String> cityComboBox = new JComboBox<>();
	private JComboBox<String> usernameComboBox = new JComboBox<>();
	private JComboBox<String> TeacherSpecComboBox = new JComboBox<String>();
	private JComboBox<String> TeacherUserComboBox = new JComboBox<String>();
	//private DefaultComboBoxModel<String> TeacherUserModel;
	private DefaultComboBoxModel<String> TeacherSpecsModel;
	private Map<String, Integer> specialities;	
	private ButtonGroup buttonGroup = new ButtonGroup(); 
	private JTextField firstnameTxt;
	private JTextField lastnameTxt;
	private JTextField birthDateTxt;
	private JTextField TeacherNameTextField;
	private JTextField TeacherSurenameTextField;
	private JTextField TeacherSsnTextField;
	private JRadioButton maleRdBtn;
	private JRadioButton femaleRdBtn;

	public AdminInsertUsersForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				
 				firstnameTxt.setText("");
				lastnameTxt.setText("");
				maleRdBtn.setSelected(true);
				birthDateTxt.setText("");
				cityComboBox.setSelectedItem(null);
				usernameComboBox.setSelectedItem(null);	
			}
		});
		setTitle("Insert User");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 522, 385);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 506, 335);
		contentPane.add(tabbedPane);
		
		JPanel StudentPanel = new JPanel();
		tabbedPane.addTab("Student", null, StudentPanel, null);
		StudentPanel.setLayout(null);
		
		JLabel firstnameLbl = new JLabel("Name");
		firstnameLbl.setForeground(new Color(128, 0, 0));
		firstnameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		firstnameLbl.setBounds(74, 34, 56, 17);
		StudentPanel.add(firstnameLbl);
		
		firstnameTxt = new JTextField();
		firstnameTxt.setColumns(10);
		firstnameTxt.setBounds(135, 32, 207, 20);
		StudentPanel.add(firstnameTxt);
		
		JLabel lastnameLbl = new JLabel("Surname");
		lastnameLbl.setForeground(new Color(128, 0, 0));
		lastnameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		lastnameLbl.setBounds(58, 66, 72, 17);
		StudentPanel.add(lastnameLbl);
		
		lastnameTxt = new JTextField();
		lastnameTxt.setColumns(10);
		lastnameTxt.setBounds(135, 64, 207, 20);
		StudentPanel.add(lastnameTxt);
		
		JLabel genderLbl = new JLabel("Gender");
		genderLbl.setForeground(new Color(128, 0, 0));
		genderLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		genderLbl.setBounds(86, 97, 44, 17);
		StudentPanel.add(genderLbl);
		
		maleRdBtn = new JRadioButton("Male");
		maleRdBtn.setActionCommand("M");
		maleRdBtn.setBounds(135, 94, 81, 23);
		StudentPanel.add(maleRdBtn);
		
		femaleRdBtn = new JRadioButton("Female");
		femaleRdBtn.setActionCommand("F");
		femaleRdBtn.setBounds(218, 94, 95, 23);
		StudentPanel.add(femaleRdBtn);
		
		maleRdBtn.setActionCommand("M");
        femaleRdBtn.setActionCommand("F");
		
		buttonGroup.add(maleRdBtn);
		buttonGroup.add(femaleRdBtn);
		
		JLabel specialityLbl = new JLabel("City");
		specialityLbl.setForeground(new Color(128, 0, 0));
		specialityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		specialityLbl.setBounds(86, 159, 44, 17);
		StudentPanel.add(specialityLbl);
		
		cityComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				String sql = "SELECT * FROM CITIES";
				//connection = Login.getConnection();
				
			    try (Connection connection = DBUtil.getConnection();
			    		PreparedStatement ps = connection.prepareStatement(sql);
			    		ResultSet rs = ps.executeQuery()) {
			    	cities = new HashMap<>();
			    	citiesModel = new DefaultComboBoxModel<>();
			    	
			    	while (rs.next()) {
			    		String city = rs.getString("CITY");
			    		int id = rs.getInt("ID");
			    		cities.put(city, id);
			    		citiesModel.addElement(city);
			    	}
			    	cityComboBox.setModel(citiesModel);
			    	cityComboBox.setMaximumRowCount(5);
			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		cityComboBox.setBounds(141, 167, 207, 20);
		cityComboBox.setBounds(135, 157, 207, 20);
		StudentPanel.add(cityComboBox);
		
		JLabel birthDateLbl = new JLabel("Date of Birth");
		birthDateLbl.setForeground(new Color(128, 0, 0));
		birthDateLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		birthDateLbl.setBounds(31, 128, 99, 17);
		StudentPanel.add(birthDateLbl);
		
		birthDateTxt = new JTextField();
		birthDateTxt.setColumns(10);
		birthDateTxt.setBounds(135, 126, 207, 20);
		StudentPanel.add(birthDateTxt);
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setForeground(new Color(128, 0, 0));
		usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl.setBounds(58, 188, 72, 17);
		StudentPanel.add(usernameLbl);
		
		
		usernameComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				String sql = "SELECT ID, USERNAME FROM USERS";
				//connection = Login.getConnection();
				
			    try (Connection connection = DBUtil.getConnection();
			    		PreparedStatement ps = connection.prepareStatement(sql);
			    		ResultSet rs = ps.executeQuery(sql)) {
			    	usernames = new HashMap<>();
			    	usernamesModel = new DefaultComboBoxModel<>();
			    	
			    	while (rs.next()) {
			    		String username = rs.getString("USERNAME");
			    		int id = rs.getInt("ID");
			    		usernames.put(username, id);
			    		usernamesModel.addElement(username);
			    	}
			    	usernameComboBox.setModel(usernamesModel);
			    	usernameComboBox.setMaximumRowCount(5);
			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		usernameComboBox.setBounds(135, 188, 207, 20);
		StudentPanel.add(usernameComboBox);
		
		JButton insertBtn = new JButton("Insert");
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (buttonGroup.getSelection() == null || cityComboBox.getSelectedItem() == null
						|| usernameComboBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "Please select gender / city / username", "Gender", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String firstname = firstnameTxt.getText().trim();
				String lastname = lastnameTxt.getText().trim();
				String gender = buttonGroup.getSelection().getActionCommand();
				String city = (String) cityComboBox.getSelectedItem();
				String username = (String) usernameComboBox.getSelectedItem();
				int cityId = cities.get(city);
				int usernameId = usernames.get(username);
				
				java.sql.Date sqlBirthDate;
				try {
					sqlBirthDate = DateUtil.toSQLDate(DateUtil.toDate(birthDateTxt.getText()));
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "Please insert a valid date (dd-MM-yyyy)", "Date", JOptionPane.ERROR_MESSAGE);
					//e1.printStackTrace();
					return;
				}
				
				if (firstname == "" || lastname == "") {
					JOptionPane.showMessageDialog(null, "Please fill firstname / lastname", "Basic info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				PreparedStatement ps = null;
				try (Connection conn = DBUtil.getConnection();) {
					String sql = "INSERT INTO STUDENTS (FIRSTNAME, LASTNAME, GENDER, BIRTH_DATE, CITY_ID, USER_ID) " +
								"VALUES(?, ?, ?, ?, ?, ?)";
					
					ps = conn.prepareStatement(sql);
					ps.setString(1, firstname);
					ps.setString(2,  lastname);
					ps.setString(3, gender);
					ps.setDate(4, sqlBirthDate);
					ps.setInt(5, cityId);
					ps.setInt(6, usernameId);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successful Register", "Insert", JOptionPane.INFORMATION_MESSAGE);			
				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					try {
						if (ps != null) ps.close();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		insertBtn.setForeground(Color.BLUE);
		insertBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		insertBtn.setBounds(234, 238, 108, 58);
		StudentPanel.add(insertBtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getUsersMenu().setEnabled(true);
				Main.getAdminInsertUsersForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(371, 238, 108, 58);
		StudentPanel.add(closeBtn);
		
		JPanel TeacherPanel = new JPanel();
		tabbedPane.addTab("Teacher", null, TeacherPanel, null);
		TeacherPanel.setLayout(null);
		
		JLabel firstnameLbl_1 = new JLabel("Name");
		firstnameLbl_1.setForeground(new Color(128, 0, 0));
		firstnameLbl_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		firstnameLbl_1.setBounds(98, 48, 56, 17);
		TeacherPanel.add(firstnameLbl_1);
		
		TeacherNameTextField = new JTextField();
		TeacherNameTextField.setColumns(10);
		TeacherNameTextField.setBounds(159, 46, 207, 20);
		TeacherPanel.add(TeacherNameTextField);
		
		JLabel lastnameLbl_1 = new JLabel("Surname");
		lastnameLbl_1.setForeground(new Color(128, 0, 0));
		lastnameLbl_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lastnameLbl_1.setBounds(82, 108, 72, 17);
		TeacherPanel.add(lastnameLbl_1);
		
		TeacherSurenameTextField = new JTextField();
		TeacherSurenameTextField.setColumns(10);
		TeacherSurenameTextField.setBounds(159, 106, 207, 20);
		TeacherPanel.add(TeacherSurenameTextField);
		
		JLabel ssnLbl = new JLabel("SSN");
		ssnLbl.setForeground(new Color(128, 0, 0));
		ssnLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		ssnLbl.setBounds(109, 77, 36, 17);
		TeacherPanel.add(ssnLbl);
		
		TeacherSsnTextField = new JTextField();
		TeacherSsnTextField.setColumns(10);
		TeacherSsnTextField.setBounds(159, 76, 207, 20);
		TeacherPanel.add(TeacherSsnTextField);
		
		JLabel specialityLbl_1 = new JLabel("Username");
		specialityLbl_1.setForeground(new Color(128, 0, 0));
		specialityLbl_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		specialityLbl_1.setBounds(67, 168, 87, 17);
		TeacherPanel.add(specialityLbl_1);
		
		//TeacherUserComboBox = new JComboBox<String>();
		TeacherUserComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				String sql = "SELECT ID, USERNAME FROM USERS";
				//connection = Login.getConnection();
				
			    try (Connection connection = DBUtil.getConnection();
			    		PreparedStatement ps = connection.prepareStatement(sql);
			    		ResultSet rs = ps.executeQuery(sql)) {
			    	usernames = new HashMap<>();
			    	usernamesModel = new DefaultComboBoxModel<>();
			    	
			    	while (rs.next()) {
			    		String username = rs.getString("USERNAME");
			    		int id = rs.getInt("ID");
			    		usernames.put(username, id);
			    		usernamesModel.addElement(username);
			    	}
			    	TeacherUserComboBox.setModel(usernamesModel);
			    	TeacherUserComboBox.setMaximumRowCount(5);
			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		TeacherUserComboBox.setBounds(159, 167, 207, 20);
		TeacherPanel.add(TeacherUserComboBox);
		
		JButton TeacherInsertBtn = new JButton("Insert");
		TeacherInsertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (TeacherSpecComboBox.getSelectedItem() == null
						|| TeacherUserComboBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "Please select Speciality / username", "Speciality", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String firstname = TeacherNameTextField.getText().trim();
				String lastname = TeacherSurenameTextField.getText().trim();
				int ssn = Integer.parseInt(TeacherSsnTextField.getText().trim());
				String speciality = (String) TeacherSpecComboBox.getSelectedItem();
				String username = (String) TeacherUserComboBox.getSelectedItem();
				int specialityId = specialities.get(speciality);
				int usernameId = usernames.get(username);			
				
				if (firstname == "" || lastname == "") {
					JOptionPane.showMessageDialog(null, "Please fill firstname / lastname", "Basic info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				PreparedStatement ps = null;
				try (Connection conn = DBUtil.getConnection();) {
					String sql = "INSERT INTO TEACHERS (SSN, FIRSTNAME, LASTNAME, SPECIALITY_ID, USER_ID) " +
								"VALUES(?, ?, ?, ?, ?)";
					
					ps = conn.prepareStatement(sql);
					ps.setInt(1, ssn);
					ps.setString(2,  firstname);
					ps.setString(3, lastname);
					ps.setInt(4, specialityId);					
					ps.setInt(5, usernameId);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successful Register", "Insert", JOptionPane.INFORMATION_MESSAGE);			
				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					try {
						if (ps != null) ps.close();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				}
			}
		});	
		TeacherInsertBtn.setForeground(Color.BLUE);
		TeacherInsertBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		TeacherInsertBtn.setBounds(232, 238, 108, 58);
		TeacherPanel.add(TeacherInsertBtn);
		
		JButton TeacherCloseBtn = new JButton("Close");
		TeacherCloseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getUsersMenu().setEnabled(true);
				Main.getAdminInsertUsersForm().setVisible(false);
			}
		});
		TeacherCloseBtn.setForeground(Color.BLUE);
		TeacherCloseBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		TeacherCloseBtn.setBounds(369, 238, 108, 58);
		TeacherPanel.add(TeacherCloseBtn);
		
		JLabel SpecialityLbl = new JLabel("Speciality ID");
		SpecialityLbl.setForeground(new Color(128, 0, 0));
		SpecialityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		SpecialityLbl.setBounds(57, 139, 97, 17);
		TeacherPanel.add(SpecialityLbl);
		
		//TeacherSpecComboBox = new JComboBox<String>();
		TeacherSpecComboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				String sql = "SELECT * FROM SPECIALITIES";
				//connection = Login.getConnection();
				
			    try (Connection connection = DBUtil.getConnection();
			    		PreparedStatement ps = connection.prepareStatement(sql);
			    		ResultSet rs = ps.executeQuery()) {
			    	specialities = new HashMap<>();
			    	TeacherSpecsModel = new DefaultComboBoxModel<>();
			    	
			    	while (rs.next()) {
			    		String speciality = rs.getString("SPECIALITY");
			    		int id = rs.getInt("ID");
			    		specialities.put(speciality, id);
			    		TeacherSpecsModel.addElement(speciality);
			    	}
			    	TeacherSpecComboBox.setModel(TeacherSpecsModel);
			    	TeacherSpecComboBox.setMaximumRowCount(5);
			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		TeacherSpecComboBox.setBounds(159, 137, 207, 20);
		TeacherPanel.add(TeacherSpecComboBox);
		
	}
}