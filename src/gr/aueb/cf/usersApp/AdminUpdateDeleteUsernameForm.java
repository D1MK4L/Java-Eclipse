package gr.aueb.cf.usersApp;

/**
 * It Updates the Username of a User
 * and Deletes the old one!
 * 
 * @author D1MK4L
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;

import gr.aueb.cf.usersApp.security.SecUtil;
import gr.aueb.cf.usersApp.util.DBUtil;
import gr.aueb.cf.usersApp.util.DateUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
//import java.awt.event.FocusAdapter;
//import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminUpdateDeleteUsernameForm extends JFrame {

	private static final long serialVersionUID = 123L;
	private JPanel contentPane;		
	private JTextField birthDateTxt;
	private JTextField cityTextField;
	private JTextField UserTextField;
	private JTextField idTxt;
	
	private JComboBox<String> cityComboBox = new JComboBox<>();
	private JComboBox<String> specialityComboBox = new JComboBox<>();
	private Map<String, Integer> cities;
	private Map<String, Integer> usernames;
	private Map<String, Integer> specialities;
	private Map<Integer, String> userCity;
	private Map<Integer, String> userSpeciality;
	private Map<Integer, String> usersName;
	private DefaultComboBoxModel<String> citiesModel;
	private DefaultComboBoxModel<String> usernamesModel;
	private DefaultComboBoxModel<String> specialitiesModel;
	private ButtonGroup buttonGroup = new ButtonGroup();	
	private PreparedStatement psStudents = null;
	private PreparedStatement psTeachers = null;
	private JRadioButton maleRdBtn;
	private JRadioButton femaleRdBtn;
	private JButton firstBtn;
	private JButton prevBtn;
	private JButton nextBtn;
	private JButton lastBtn;
	Connection conn = null;
	private JTextField firstnameTxt;
	private JTextField lastnameTxt;		
	private JTextField NameTeacherText;
	private JTextField SurnameTeacherText;
	private JTextField idTeacherText;
	private JTextField ssnText;
	private JTextField SpecialityTextField;
	private JTextField TeacherUserTextField;
	private ResultSet rsStudents = null;
	private ResultSet rsTeachers = null;
	private JTextField newUsernameText;
	private JTextField newPasswordText;
	private JTextField confirmPasswordtext;
	private JTextField NewUserStudentText;
	private JTextField NewStudentPassText;
	private JTextField ConfirmStudentPassText;
	private ResultSet rs = null;
	private int userId;
	private int userIdOld;

	public AdminUpdateDeleteUsernameForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				String sql1 = "SELECT * FROM STUDENTS WHERE LASTNAME LIKE ?";				
				// Connection conn = Login.getConnection();
				//Connection conn = null;
				try {
					conn = DBUtil.getConnection();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					//DBUtil.getConnection();
					//System.out.println("Search name" + Main.getStudentsMenu().getLastname().trim())
					psStudents = conn.prepareStatement(sql1, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);					
					psStudents.setString(1, Main.getUsersMenu().getLastname() + "%");
					rsStudents = psStudents.executeQuery();
					
					if (!rsStudents.next()) {
						JOptionPane.showMessageDialog(null, "Empty", "Result", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 					
				
				PreparedStatement psCities;
				ResultSet rsCities;
			    try {
			    	String sqlCities = "SELECT * FROM CITIES";
			    	psCities = conn.prepareStatement(sqlCities);
		    		rsCities = psCities.executeQuery();
			    	cities = new HashMap<>();
			    	userCity = new HashMap<>();
			    	citiesModel = new DefaultComboBoxModel<>();
			    	
			    	while (rsCities.next()) {
			    		String city = rsCities.getString("CITY");
			    		int id = rsCities.getInt("ID");
			    		cities.put(city, id);
			    		userCity.put(id, city);
			    		citiesModel.addElement(city);
			    	}
			    	cityComboBox.setModel(citiesModel);
			    	cityComboBox.setMaximumRowCount(5);			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}		    
			    
			  
			    PreparedStatement psUser;
			    ResultSet rsUser;
			    try {
			    	    String sqlUsers = "SELECT ID, USERNAME FROM USERS";			    	    
					    psUser = conn.prepareStatement(sqlUsers);
			    		rsUser = psUser.executeQuery();			    		
			    	usernames = new HashMap<>();
			    	usersName = new HashMap<>();
			    	usernamesModel = new DefaultComboBoxModel<>();
			    	
			    	while (rsUser.next()) {
			    		String username = rsUser.getString("USERNAME");
			    		int id = rsUser.getInt("ID");
			    		usernames.put(username, id);
			    		usersName.put(id, username);
			    		usernamesModel.addElement(username);
			    	}			    	
//			    	userComboBox.setModel(usernamesModel);
//			    	userComboBox.setMaximumRowCount(5);			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			    
			    try {
					idTxt.setText(Integer.toString(rsStudents.getInt("ID")));
					firstnameTxt.setText(rsStudents.getString("FIRSTNAME"));
					lastnameTxt.setText(rsStudents.getString("LASTNAME"));
					if (rsStudents.getString("GENDER").equals("M")) {
						maleRdBtn.setSelected(true);
					} else {
						femaleRdBtn.setSelected(true);
					}
					birthDateTxt.setText(DateUtil.toSQLDateString(rsStudents.getDate("BIRTH_DATE")));
					cityComboBox.setSelectedItem(userCity.get(rsStudents.getInt("CITY_ID")));					
					UserTextField.setText(usersName.get(rsStudents.getInt("USER_ID")));
					cityTextField.setText(userCity.get(rsStudents.getInt("CITY_ID")));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			    
			    
			    
			    String sql2 = "SELECT * FROM TEACHERS WHERE LASTNAME LIKE ?";				
				// Connection conn = Login.getConnection();
				//Connection conn = null;
				try {
					conn = DBUtil.getConnection();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					//DBUtil.getConnection();
					//System.out.println("Search name" + Main.getStudentsMenu().getLastname().trim())
					psTeachers = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);					
					psTeachers.setString(1, Main.getUsersMenu().getLastname() + "%");
					rsTeachers = psTeachers.executeQuery();
					
					if (!rsTeachers.next()) {
						JOptionPane.showMessageDialog(null, "Empty", "Result", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
				PreparedStatement psSpecialities;
				ResultSet rsSpecialities;
			    try {
			    	String sqlSpecialities = "SELECT * FROM SPECIALITIES";
			    	psSpecialities = conn.prepareStatement(sqlSpecialities);
		    		rsSpecialities = psSpecialities.executeQuery();
		    		specialities = new HashMap<>();
			    	userSpeciality = new HashMap<>();
			    	specialitiesModel = new DefaultComboBoxModel<>();
			    	
			    	while (rsSpecialities.next()) {
			    		String speciality = rsSpecialities.getString("SPECIALITY");
			    		int id = rsSpecialities.getInt("ID");
			    		specialities.put(speciality, id);
			    		userSpeciality.put(id, speciality);
			    		specialitiesModel.addElement(speciality);
			    	}
			    	specialityComboBox.setModel(specialitiesModel);
			    	specialityComboBox.setMaximumRowCount(5);			    	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			    
			    try {
			    	idTeacherText.setText(Integer.toString(rsTeachers.getInt("ID")));
			    	NameTeacherText.setText(rsTeachers.getString("FIRSTNAME"));
			    	SurnameTeacherText.setText(rsTeachers.getString("LASTNAME"));
			    	ssnText.setText(rsTeachers.getString("SSN"));
			    	specialityComboBox.setSelectedItem(userSpeciality.get(rsTeachers.getInt("SPECIALITY_ID")));					
			    	TeacherUserTextField.setText(usersName.get(rsTeachers.getInt("USER_ID")));
					SpecialityTextField.setText(userSpeciality.get(rsTeachers.getInt("SPECIALITY_ID")));
			    } catch (SQLException e1) {
			    	e1.printStackTrace();
			    } 
			    
			}
		});	
		
		setTitle("Modify Users");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 528);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 434, 491);
		contentPane.add(tabbedPane);
		
		JPanel StudentPanel = new JPanel();		
		tabbedPane.addTab("Students", null, StudentPanel, null);
		StudentPanel.setLayout(null);
		
		JLabel firstnameLbl = new JLabel("Name");
		firstnameLbl.setForeground(new Color(128, 0, 0));
		firstnameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		firstnameLbl.setBounds(108, 44, 56, 17);
		StudentPanel.add(firstnameLbl);
		
		firstnameTxt = new JTextField();
		firstnameTxt.setBackground(new Color(252, 252, 205));
		firstnameTxt.setEditable(false);
		firstnameTxt.setColumns(10);
		firstnameTxt.setBounds(169, 42, 207, 20);
		StudentPanel.add(firstnameTxt);
		
		JLabel lastnameLbl = new JLabel("Surname");
		lastnameLbl.setForeground(new Color(128, 0, 0));
		lastnameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		lastnameLbl.setBounds(92, 76, 72, 17);
		StudentPanel.add(lastnameLbl);
		
		lastnameTxt = new JTextField();
		lastnameTxt.setBackground(new Color(252, 252, 205));
		lastnameTxt.setEditable(false);
		lastnameTxt.setColumns(10);
		lastnameTxt.setBounds(169, 74, 207, 20);
		StudentPanel.add(lastnameTxt);
		
		JLabel genderLbl = new JLabel("Gender");
		genderLbl.setForeground(new Color(128, 0, 0));
		genderLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		genderLbl.setBounds(102, 107, 62, 17);
		StudentPanel.add(genderLbl);
		
		maleRdBtn = new JRadioButton("Male");
		maleRdBtn.setEnabled(false);
		maleRdBtn.setActionCommand("M");
		maleRdBtn.setBounds(169, 104, 81, 23);
		StudentPanel.add(maleRdBtn);
		
		femaleRdBtn = new JRadioButton("Female");
		femaleRdBtn.setEnabled(false);
		femaleRdBtn.setActionCommand("F");
		femaleRdBtn.setBounds(252, 104, 95, 23);
		StudentPanel.add(femaleRdBtn);
		
		buttonGroup.add(maleRdBtn);
		buttonGroup.add(femaleRdBtn);
		
		JLabel birthDateLbl = new JLabel("Date of Birth");
		birthDateLbl.setForeground(new Color(128, 0, 0));
		birthDateLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		birthDateLbl.setBounds(65, 138, 99, 17);
		StudentPanel.add(birthDateLbl);
		
		birthDateTxt = new JTextField();
		birthDateTxt.setBackground(new Color(252, 252, 205));
		birthDateTxt.setEditable(false);
		birthDateTxt.setColumns(10);
		birthDateTxt.setBounds(169, 136, 130, 20);
		StudentPanel.add(birthDateTxt);
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setForeground(new Color(128, 0, 0));
		usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl.setBounds(92, 197, 72, 17);
		StudentPanel.add(usernameLbl);
		
		JButton btnUpdate = new JButton("Update / Delete");
		btnUpdate.addActionListener(new ActionListener() {			
				public void actionPerformed(ActionEvent e) {
					
					String usernameStudent = NewUserStudentText.getText().trim();
					
					//char[] password1chars = passwordRegTxt.getPassword();
					String password1 = String.valueOf(NewStudentPassText.getText()).trim();
					
					//char[] password2chars = passwordReg2Txt.getPassword();
					String password2 = String.valueOf(ConfirmStudentPassText.getText()).trim();					
					
					if (usernameStudent == "" || password1 == "" || password2 == "") {
						JOptionPane.showMessageDialog(null, "Please fill username / password", "Basic info", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
//					if (username.length() <= 2 || password2.length() <= 8) {
//						JOptionPane.showMessageDialog(null, "Please fill username / password", "Basic info", JOptionPane.ERROR_MESSAGE);
//						return;
//					}
					
					if (!password1.equals(password2)) {
						JOptionPane.showMessageDialog(null, "Confirmation password not the same", "Basic info", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					String sqlOldUsername = "SELECT ID FROM USERS WHERE USERNAME LIKE ?";
					try (Connection connection = DBUtil.getConnection();
							PreparedStatement ps = connection.prepareStatement(sqlOldUsername)) {		 
							ps.setString(1, UserTextField.getText());
							rs = ps.executeQuery();
							if (rs.next()) {
								userIdOld = rs.getInt("ID");
							}							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					
					String sql = "INSERT INTO USERS (USERNAME, PASSWORD, ROLE) VALUES(?, ?, ? )";					
					try (Connection connection = DBUtil.getConnection();
						PreparedStatement ps = connection.prepareStatement(sql)) {		 
						ps.setString(1, usernameStudent);
						ps.setString(2, SecUtil.hashPassword(password1));
						ps.setString(3, "Student");
						ps.executeUpdate();												
						JOptionPane.showMessageDialog(null, "One row affected", "Username Inserted Successfully", JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					String sql2 = "SELECT ID FROM USERS WHERE USERNAME LIKE ?"; 					
					try (Connection connection = DBUtil.getConnection();
							PreparedStatement ps = connection.prepareStatement(sql2)) {		 
							ps.setString(1, usernameStudent);
							rs = ps.executeQuery();
							if (rs.next()) {
								userId = rs.getInt("ID");
							}							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					
					String sql3 = "UPDATE STUDENTS SET USER_ID = ? WHERE ID = ?";
					
					String idString = idTxt.getText();
					int currentId = Integer.parseInt(idString);
					try (Connection connection = DBUtil.getConnection();
							PreparedStatement ps = connection.prepareStatement(sql3)) {		 
							ps.setInt(1, userId);
							ps.setInt(2, currentId);							
							ps.executeUpdate();												
							JOptionPane.showMessageDialog(null, "One row affected", "Username Updated Successfully", JOptionPane.INFORMATION_MESSAGE);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					
					String sqlDeleteUsername = "DELETE FROM USERS WHERE ID = ?";
					
					try (Connection conn = DBUtil.getConnection();){
						
						PreparedStatement ps = conn.prepareStatement(sqlDeleteUsername);
						ps.setInt(1, userIdOld);						
						
						int response = JOptionPane.showConfirmDialog(null, "Are you sure to delete the Username?", "Warning", JOptionPane.YES_NO_OPTION);
						if (response == JOptionPane.YES_OPTION) {
							int n = ps.executeUpdate();
							JOptionPane.showMessageDialog(null, n + " row affected. Username Deleted! ", "Delete", JOptionPane.INFORMATION_MESSAGE);
						} else {
							return;
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					NewUserStudentText.setText("");
					NewStudentPassText.setText("");
					ConfirmStudentPassText.setText("");
				}		
			});
		btnUpdate.setForeground(Color.BLUE);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdate.setBounds(56, 397, 150, 58);
		StudentPanel.add(btnUpdate);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getUsernameMenu().setEnabled(true);
				Main.getUsernameMenu().setVisible(true);
				Main.getAdminUpdateDeleteUsernameForm().setEnabled(false);
				Main.getAdminUpdateDeleteUsernameForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(268, 397, 108, 58);
		StudentPanel.add(closeBtn);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setForeground(new Color(128, 0, 0));
		lblCity.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCity.setBounds(120, 168, 44, 17);
		StudentPanel.add(lblCity);
		
		cityTextField = new JTextField();
		cityTextField.setEditable(false);
		cityTextField.setColumns(10);
		cityTextField.setBackground(new Color(252, 252, 205));
		cityTextField.setBounds(169, 166, 207, 20);
		StudentPanel.add(cityTextField);
		
		UserTextField = new JTextField();
		UserTextField.setColumns(10);
		UserTextField.setBackground(new Color(252, 252, 205));
		UserTextField.setBounds(169, 197, 207, 20);
		StudentPanel.add(UserTextField);
		
		firstBtn = new JButton("");
		firstBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rsStudents.first()) {
						idTxt.setText(Integer.toString(rsStudents.getInt("ID")));
						firstnameTxt.setText(rsStudents.getString("FIRSTNAME"));
						lastnameTxt.setText(rsStudents.getString("LASTNAME"));
						if (rsStudents.getString("GENDER").equals("M")) {
							maleRdBtn.setSelected(true);
						} else {
							femaleRdBtn.setSelected(true);
						}
						birthDateTxt.setText(DateUtil.toSQLDateString(rsStudents.getDate("BIRTH_DATE")));
						cityComboBox.setSelectedItem(userCity.get(rsStudents.getInt("CITY_ID")));
						UserTextField.setText(usersName.get(rsStudents.getInt("USER_ID")));
						cityTextField.setText(userCity.get(rsStudents.getInt("CITY_ID")));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		firstBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/First record.png")));
		firstBtn.setBounds(120, 351, 49, 23);
		StudentPanel.add(firstBtn);
		
		prevBtn = new JButton("");
		prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rsStudents.previous()) {
						idTxt.setText(Integer.toString(rsStudents.getInt("ID")));
						firstnameTxt.setText(rsStudents.getString("FIRSTNAME"));
						lastnameTxt.setText(rsStudents.getString("LASTNAME"));
						if (rsStudents.getString("GENDER").equals("M")) {
							maleRdBtn.setSelected(true);
						} else {
							femaleRdBtn.setSelected(true);
						}
						birthDateTxt.setText(DateUtil.toSQLDateString(rsStudents.getDate("BIRTH_DATE")));
						cityComboBox.setSelectedItem(userCity.get(rsStudents.getInt("CITY_ID")));
						UserTextField.setText(usersName.get(rsStudents.getInt("USER_ID")));
						cityTextField.setText(userCity.get(rsStudents.getInt("CITY_ID")));
					} else {
						rsStudents.first();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		prevBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/Previous_record.png")));
		prevBtn.setBounds(167, 351, 49, 23);
		StudentPanel.add(prevBtn);
		
		nextBtn = new JButton("");
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rsStudents.next()) {
						idTxt.setText(Integer.toString(rsStudents.getInt("ID")));
						firstnameTxt.setText(rsStudents.getString("FIRSTNAME"));
						lastnameTxt.setText(rsStudents.getString("LASTNAME"));
						if (rsStudents.getString("GENDER").equals("M")) {
							maleRdBtn.setSelected(true);
						} else {
							femaleRdBtn.setSelected(true);
						}
						birthDateTxt.setText(DateUtil.toSQLDateString(rsStudents.getDate("BIRTH_DATE")));
						cityComboBox.setSelectedItem(userCity.get(rsStudents.getInt("CITY_ID")));
						UserTextField.setText(usersName.get(rsStudents.getInt("USER_ID")));
						cityTextField.setText(userCity.get(rsStudents.getInt("CITY_ID")));
					} else {
						rsStudents.last();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		nextBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/Next_track.png")));
		nextBtn.setBounds(212, 351, 49, 23);
		StudentPanel.add(nextBtn);
		
		lastBtn = new JButton("");
		lastBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					if (rsStudents.last()) {
						idTxt.setText(Integer.toString(rsStudents.getInt("ID")));
						firstnameTxt.setText(rsStudents.getString("FIRSTNAME"));
						lastnameTxt.setText(rsStudents.getString("LASTNAME"));
						if (rsStudents.getString("GENDER").equals("M")) {
							maleRdBtn.setSelected(true);
						} else {
							femaleRdBtn.setSelected(true);
						}
						birthDateTxt.setText(DateUtil.toSQLDateString(rsStudents.getDate("BIRTH_DATE")));
						cityComboBox.setSelectedItem(userCity.get(rsStudents.getInt("CITY_ID")));
						UserTextField.setText(usersName.get(rsStudents.getInt("USER_ID")));
						cityTextField.setText(userCity.get(rsStudents.getInt("CITY_ID")));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		lastBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/Last_Record.png")));
		lastBtn.setBounds(260, 351, 49, 23);
		StudentPanel.add(lastBtn);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(34, 328, 370, 2);
		StudentPanel.add(separator);
		
		idTxt = new JTextField();
		idTxt.setEditable(false);
		idTxt.setColumns(10);
		idTxt.setBackground(new Color(252, 252, 205));
		idTxt.setBounds(169, 11, 59, 20);
		StudentPanel.add(idTxt);
		
		JLabel idLbl = new JLabel("ID");
		idLbl.setForeground(new Color(128, 0, 0));
		idLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		idLbl.setBounds(131, 11, 17, 17);
		StudentPanel.add(idLbl);
		
		JLabel usernameLbl_1_2_1 = new JLabel("New Username");
		usernameLbl_1_2_1.setForeground(new Color(128, 0, 0));
		usernameLbl_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl_1_2_1.setBounds(57, 225, 110, 17);
		StudentPanel.add(usernameLbl_1_2_1);
		
		NewUserStudentText = new JTextField();
		NewUserStudentText.setColumns(10);
		NewUserStudentText.setBackground(Color.WHITE);
		NewUserStudentText.setBounds(169, 225, 207, 20);
		StudentPanel.add(NewUserStudentText);
		
		JLabel usernameLbl_1_3_1 = new JLabel("New Password");
		usernameLbl_1_3_1.setForeground(new Color(128, 0, 0));
		usernameLbl_1_3_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl_1_3_1.setBounds(59, 253, 108, 17);
		StudentPanel.add(usernameLbl_1_3_1);
		
		NewStudentPassText = new JTextField();
		NewStudentPassText.setColumns(10);
		NewStudentPassText.setBackground(Color.WHITE);
		NewStudentPassText.setBounds(169, 253, 207, 20);
		StudentPanel.add(NewStudentPassText);
		
		JLabel usernameLbl_1_4_1 = new JLabel("Confirn New Password");
		usernameLbl_1_4_1.setForeground(new Color(128, 0, 0));
		usernameLbl_1_4_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl_1_4_1.setBounds(6, 281, 160, 17);
		StudentPanel.add(usernameLbl_1_4_1);
		
		ConfirmStudentPassText = new JTextField();
		ConfirmStudentPassText.setColumns(10);
		ConfirmStudentPassText.setBackground(new Color(255, 255, 255));
		ConfirmStudentPassText.setBounds(169, 281, 207, 20);
		StudentPanel.add(ConfirmStudentPassText);
		
		JPanel TeacherPanel = new JPanel();
		tabbedPane.addTab("Teachers", null, TeacherPanel, null);
		TeacherPanel.setLayout(null);
		
		JButton btnUpdateTeacher = new JButton("Update / Delete");
		btnUpdateTeacher.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String usernameTeacher = newUsernameText.getText().trim();
					
					//char[] password1chars = passwordRegTxt.getPassword();
					String password1 = String.valueOf(newPasswordText.getText()).trim();
					
					//char[] password2chars = passwordReg2Txt.getPassword();
					String password2 = String.valueOf(confirmPasswordtext.getText()).trim();					
					
					if (usernameTeacher == "" || password1 == "" || password2 == "") {
						JOptionPane.showMessageDialog(null, "Please fill username / password", "Basic info", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
//					if (username.length() <= 2 || password2.length() <= 8) {
//						JOptionPane.showMessageDialog(null, "Please fill username / password", "Basic info", JOptionPane.ERROR_MESSAGE);
//						return;
//					}
					
					if (!password1.equals(password2)) {
						JOptionPane.showMessageDialog(null, "Confirmation password not the same", "Basic info", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					String sqlOldUsername = "SELECT ID FROM USERS WHERE USERNAME LIKE ?";
					try (Connection connection = DBUtil.getConnection();
							PreparedStatement ps = connection.prepareStatement(sqlOldUsername)) {		 
							ps.setString(1, TeacherUserTextField.getText());
							rs = ps.executeQuery();
							if (rs.next()) {
								userIdOld = rs.getInt("ID");
							}							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					
					
					String sql = "INSERT INTO USERS (USERNAME, PASSWORD, ROLE) VALUES(?, ?, ? )";					
					try (Connection connection = DBUtil.getConnection();
						PreparedStatement ps = connection.prepareStatement(sql)) {		 
						ps.setString(1, usernameTeacher);
						ps.setString(2, SecUtil.hashPassword(password1));
						ps.setString(3, "Teacher");
						ps.executeUpdate();												
						JOptionPane.showMessageDialog(null, "One row affected", "Username Inserted Successfully", JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					String sql2 = "SELECT ID FROM USERS WHERE USERNAME LIKE ?"; 					
					try (Connection connection = DBUtil.getConnection();
							PreparedStatement ps = connection.prepareStatement(sql2)) {		 
							ps.setString(1, usernameTeacher);
							rs = ps.executeQuery();
							if (rs.next()) {
								userId = rs.getInt("ID");
							}							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					
					String sql3 = "UPDATE TEACHERS SET USER_ID = ? WHERE ID = ?";
					
					String idString = idTeacherText.getText();
					int currentId = Integer.parseInt(idString);
					try (Connection connection = DBUtil.getConnection();
							PreparedStatement ps = connection.prepareStatement(sql3)) {		 
							ps.setInt(1, userId);
							ps.setInt(2, currentId);							
							ps.executeUpdate();												
							JOptionPane.showMessageDialog(null, "One row affected", "Username Updated Successfully", JOptionPane.INFORMATION_MESSAGE);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					
					String sqlDeleteUsername = "DELETE FROM USERS WHERE ID = ?";
					
					try (Connection conn = DBUtil.getConnection();){
						
						PreparedStatement ps = conn.prepareStatement(sqlDeleteUsername);
						ps.setInt(1, userIdOld);						
						
						int response = JOptionPane.showConfirmDialog(null, "Are you sure to delete the Username?", "Warning", JOptionPane.YES_NO_OPTION);
						if (response == JOptionPane.YES_OPTION) {
							int n = ps.executeUpdate();
							JOptionPane.showMessageDialog(null, n + " row affected. Username Deleted! ", "Delete", JOptionPane.INFORMATION_MESSAGE);
						} else {
							return;
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					newUsernameText.setText("");
					newPasswordText.setText("");
					confirmPasswordtext.setText("");
				}		
			});
		btnUpdateTeacher.setForeground(Color.BLUE);
		btnUpdateTeacher.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdateTeacher.setBounds(50, 394, 150, 58);
		TeacherPanel.add(btnUpdateTeacher);
		
		JButton closeBtn_1 = new JButton("Close");
		closeBtn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getUsernameMenu().setEnabled(true);
				Main.getUsernameMenu().setVisible(true);
				Main.getAdminUpdateDeleteUsernameForm().setEnabled(false);
				Main.getAdminUpdateDeleteUsernameForm().setVisible(false);
			}
		});
		closeBtn_1.setForeground(Color.BLUE);
		closeBtn_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn_1.setBounds(275, 394, 108, 58);
		TeacherPanel.add(closeBtn_1);
		
		JButton TeacherFirstBtn = new JButton("");
		TeacherFirstBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rsTeachers.first()) {
						idTeacherText.setText(Integer.toString(rsTeachers.getInt("ID")));
				    	NameTeacherText.setText(rsTeachers.getString("FIRSTNAME"));
				    	SurnameTeacherText.setText(rsTeachers.getString("LASTNAME"));
				    	ssnText.setText(rsTeachers.getString("SSN"));
				    	specialityComboBox.setSelectedItem(userSpeciality.get(rsTeachers.getInt("SPECIALITY_ID")));					
				    	TeacherUserTextField.setText(usersName.get(rsTeachers.getInt("USER_ID")));
						SpecialityTextField.setText(userSpeciality.get(rsTeachers.getInt("SPECIALITY_ID")));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		TeacherFirstBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/First record.png")));
		TeacherFirstBtn.setBounds(121, 332, 49, 23);
		TeacherPanel.add(TeacherFirstBtn);
		
		JButton TeacherPrevBtn = new JButton("");
		TeacherPrevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rsTeachers.previous()) {
						idTeacherText.setText(Integer.toString(rsTeachers.getInt("ID")));
				    	NameTeacherText.setText(rsTeachers.getString("FIRSTNAME"));
				    	SurnameTeacherText.setText(rsTeachers.getString("LASTNAME"));
				    	ssnText.setText(rsTeachers.getString("SSN"));
				    	specialityComboBox.setSelectedItem(userSpeciality.get(rsTeachers.getInt("SPECIALITY_ID")));					
				    	TeacherUserTextField.setText(usersName.get(rsTeachers.getInt("USER_ID")));
						SpecialityTextField.setText(userSpeciality.get(rsTeachers.getInt("SPECIALITY_ID")));
					} else {
						rsTeachers.first();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		TeacherPrevBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/Previous_record.png")));
		TeacherPrevBtn.setBounds(168, 332, 49, 23);
		TeacherPanel.add(TeacherPrevBtn);
		
		JButton TeacherNextBtn = new JButton("");
		TeacherNextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rsTeachers.next()) {
						idTeacherText.setText(Integer.toString(rsTeachers.getInt("ID")));
				    	NameTeacherText.setText(rsTeachers.getString("FIRSTNAME"));
				    	SurnameTeacherText.setText(rsTeachers.getString("LASTNAME"));
				    	ssnText.setText(rsTeachers.getString("SSN"));
				    	specialityComboBox.setSelectedItem(userSpeciality.get(rsTeachers.getInt("SPECIALITY_ID")));					
				    	TeacherUserTextField.setText(usersName.get(rsTeachers.getInt("USER_ID")));
						SpecialityTextField.setText(userSpeciality.get(rsTeachers.getInt("SPECIALITY_ID")));
					} else {
						rsTeachers.last();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		TeacherNextBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/Next_track.png")));
		TeacherNextBtn.setBounds(213, 332, 49, 23);
		TeacherPanel.add(TeacherNextBtn);
		
		JButton TeacherLastBtn = new JButton("");
		TeacherLastBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				try {
					if (rsTeachers.last()) {
						idTeacherText.setText(Integer.toString(rsTeachers.getInt("ID")));
				    	NameTeacherText.setText(rsTeachers.getString("FIRSTNAME"));
				    	SurnameTeacherText.setText(rsTeachers.getString("LASTNAME"));
				    	ssnText.setText(rsTeachers.getString("SSN"));
				    	specialityComboBox.setSelectedItem(userSpeciality.get(rsTeachers.getInt("SPECIALITY_ID")));					
				    	TeacherUserTextField.setText(usersName.get(rsTeachers.getInt("USER_ID")));
						SpecialityTextField.setText(userSpeciality.get(rsTeachers.getInt("SPECIALITY_ID")));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		TeacherLastBtn.setIcon(new ImageIcon(AdminUpdateDeleteUsersForm.class.getResource("/resources/Last_Record.png")));
		TeacherLastBtn.setBounds(261, 332, 49, 23);
		TeacherPanel.add(TeacherLastBtn);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(27, 301, 370, 2);
		TeacherPanel.add(separator_1);
		
		NameTeacherText = new JTextField();
		NameTeacherText.setBackground(new Color(252, 252, 205));
		NameTeacherText.setEditable(false);
		NameTeacherText.setColumns(10);
		NameTeacherText.setBounds(176, 42, 207, 20);
		TeacherPanel.add(NameTeacherText);
		
		JLabel firstnameLbl_1 = new JLabel("Name");
		firstnameLbl_1.setForeground(new Color(128, 0, 0));
		firstnameLbl_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		firstnameLbl_1.setBounds(121, 44, 56, 17);
		TeacherPanel.add(firstnameLbl_1);
		
		JLabel lastnameLbl_1 = new JLabel("Surname");
		lastnameLbl_1.setForeground(new Color(128, 0, 0));
		lastnameLbl_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lastnameLbl_1.setBounds(99, 76, 72, 17);
		TeacherPanel.add(lastnameLbl_1);
		
		SurnameTeacherText = new JTextField();
		SurnameTeacherText.setBackground(new Color(252, 252, 205));
		SurnameTeacherText.setEditable(false);
		SurnameTeacherText.setColumns(10);
		SurnameTeacherText.setBounds(176, 74, 207, 20);
		TeacherPanel.add(SurnameTeacherText);
		
		JLabel idLbl_1 = new JLabel("ID");
		idLbl_1.setForeground(new Color(128, 0, 0));
		idLbl_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		idLbl_1.setBounds(138, 11, 17, 17);
		TeacherPanel.add(idLbl_1);
		
		idTeacherText = new JTextField();
		idTeacherText.setEditable(false);
		idTeacherText.setColumns(10);
		idTeacherText.setBackground(new Color(252, 252, 205));
		idTeacherText.setBounds(176, 11, 59, 20);
		TeacherPanel.add(idTeacherText);
		
		JLabel ssnLbl = new JLabel("SSN");
		ssnLbl.setForeground(new Color(128, 0, 0));
		ssnLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		ssnLbl.setBounds(132, 106, 29, 17);
		TeacherPanel.add(ssnLbl);
		
		ssnText = new JTextField();
		ssnText.setColumns(10);
		ssnText.setBackground(new Color(252, 252, 205));
		ssnText.setBounds(176, 104, 59, 20);
		TeacherPanel.add(ssnText);
		
		JLabel specialityLbl = new JLabel("Speciality");
		specialityLbl.setForeground(new Color(128, 0, 0));
		specialityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		specialityLbl.setBounds(99, 136, 72, 17);
		TeacherPanel.add(specialityLbl);
		
		SpecialityTextField = new JTextField();
		SpecialityTextField.setBackground(new Color(252, 252, 205));
		SpecialityTextField.setColumns(10);
		SpecialityTextField.setBounds(176, 134, 207, 20);
		TeacherPanel.add(SpecialityTextField);
		
		JLabel usernameLbl_1 = new JLabel("Username");
		usernameLbl_1.setForeground(new Color(128, 0, 0));
		usernameLbl_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl_1.setBounds(99, 165, 72, 17);
		TeacherPanel.add(usernameLbl_1);
		
		TeacherUserTextField = new JTextField();
		TeacherUserTextField.setEditable(false);
		TeacherUserTextField.setColumns(10);
		TeacherUserTextField.setBackground(new Color(252, 252, 205));
		TeacherUserTextField.setBounds(176, 165, 207, 20);
		TeacherPanel.add(TeacherUserTextField);
		
		JLabel usernameLbl_1_2 = new JLabel("New Username");
		usernameLbl_1_2.setForeground(new Color(128, 0, 0));
		usernameLbl_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl_1_2.setBounds(64, 196, 110, 17);
		TeacherPanel.add(usernameLbl_1_2);
		
		newUsernameText = new JTextField();
		newUsernameText.setColumns(10);
		newUsernameText.setBackground(new Color(255, 255, 255));
		newUsernameText.setBounds(176, 196, 207, 20);
		TeacherPanel.add(newUsernameText);
		
		JLabel usernameLbl_1_3 = new JLabel("New Password");
		usernameLbl_1_3.setForeground(new Color(128, 0, 0));
		usernameLbl_1_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl_1_3.setBounds(66, 224, 108, 17);
		TeacherPanel.add(usernameLbl_1_3);
		
		newPasswordText = new JTextField();
		newPasswordText.setColumns(10);
		newPasswordText.setBackground(new Color(255, 255, 255));
		newPasswordText.setBounds(176, 224, 207, 20);
		TeacherPanel.add(newPasswordText);
		
		JLabel usernameLbl_1_4 = new JLabel("Confirn New Password");
		usernameLbl_1_4.setForeground(new Color(128, 0, 0));
		usernameLbl_1_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl_1_4.setBounds(13, 252, 160, 17);
		TeacherPanel.add(usernameLbl_1_4);
		
		confirmPasswordtext = new JTextField();
		confirmPasswordtext.setColumns(10);
		confirmPasswordtext.setBackground(new Color(255, 255, 255));
		confirmPasswordtext.setBounds(176, 252, 207, 20);
		TeacherPanel.add(confirmPasswordtext);
	}
}
