package gr.aueb.cf.usersApp;

/**
 * You can Update or delete a User!
 * Not the username!
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
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminUpdateDeleteUsersForm extends JFrame {

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

	
	

	public AdminUpdateDeleteUsersForm() {
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
		firstnameLbl.setBounds(78, 61, 56, 17);
		StudentPanel.add(firstnameLbl);
		
		firstnameTxt = new JTextField();
		firstnameTxt.setColumns(10);
		firstnameTxt.setBounds(139, 59, 207, 20);
		StudentPanel.add(firstnameTxt);
		
		JLabel lastnameLbl = new JLabel("Surname");
		lastnameLbl.setForeground(new Color(128, 0, 0));
		lastnameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		lastnameLbl.setBounds(62, 93, 72, 17);
		StudentPanel.add(lastnameLbl);
		
		lastnameTxt = new JTextField();
		lastnameTxt.setColumns(10);
		lastnameTxt.setBounds(139, 91, 207, 20);
		StudentPanel.add(lastnameTxt);
		
		JLabel genderLbl = new JLabel("Gender");
		genderLbl.setForeground(new Color(128, 0, 0));
		genderLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		genderLbl.setBounds(72, 124, 62, 17);
		StudentPanel.add(genderLbl);
		
		maleRdBtn = new JRadioButton("Male");
		maleRdBtn.setActionCommand("M");
		maleRdBtn.setBounds(139, 121, 81, 23);
		StudentPanel.add(maleRdBtn);
		
		femaleRdBtn = new JRadioButton("Female");
		femaleRdBtn.setActionCommand("F");
		femaleRdBtn.setBounds(222, 121, 95, 23);
		StudentPanel.add(femaleRdBtn);
		
		buttonGroup.add(maleRdBtn);
		buttonGroup.add(femaleRdBtn);
		
		JLabel lblNewCity = new JLabel("New City");
		lblNewCity.setForeground(new Color(128, 0, 0));
		lblNewCity.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewCity.setBounds(62, 214, 72, 17);
		StudentPanel.add(lblNewCity);
		
		cityComboBox = new JComboBox<String>();
		cityComboBox.setBounds(139, 214, 207, 20);
		StudentPanel.add(cityComboBox);
		
		JLabel birthDateLbl = new JLabel("Date of Birth");
		birthDateLbl.setForeground(new Color(128, 0, 0));
		birthDateLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		birthDateLbl.setBounds(35, 155, 99, 17);
		StudentPanel.add(birthDateLbl);
		
		birthDateTxt = new JTextField();
		birthDateTxt.setColumns(10);
		birthDateTxt.setBounds(139, 153, 130, 20);
		StudentPanel.add(birthDateTxt);
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setForeground(new Color(128, 0, 0));
		usernameLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl.setBounds(62, 245, 72, 17);
		StudentPanel.add(usernameLbl);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (buttonGroup.getSelection() == null || cityComboBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "Please select city ", "Gender", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String idString = idTxt.getText();
				int currentId = Integer.parseInt(idString);				
				String firstname = firstnameTxt.getText().trim();
				String lastname = lastnameTxt.getText().trim();
				String gender = buttonGroup.getSelection().getActionCommand();
				String city = (String) cityComboBox.getSelectedItem();
				String username = (String) UserTextField.getText();
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

					String sql = "UPDATE STUDENTS SET FIRSTNAME = ?, LASTNAME = ?, GENDER = ?, BIRTH_DATE = ?, "
						+ "CITY_ID = ?, USER_ID = ? WHERE ID = ?";
					
					ps = conn.prepareStatement(sql);
					ps.setString(1, firstname);
					ps.setString(2,  lastname);
					ps.setString(3, gender);
					ps.setDate(4, sqlBirthDate);
					ps.setInt(5, cityId);
					ps.setInt(6, usernameId);
					ps.setInt(7, currentId);
					int n = ps.executeUpdate();
					
					if (n > 0) {
						JOptionPane.showMessageDialog(null, "Successful Update", "Update", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Update Error", "Update", JOptionPane.ERROR_MESSAGE);
					}					
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
		btnUpdate.setForeground(Color.BLUE);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdate.setBounds(159, 397, 108, 58);
		StudentPanel.add(btnUpdate);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getUsersMenu().setEnabled(true);
				Main.getUsersMenu().setVisible(true);
				Main.getAdminUpdateDeleteUsersForm().setEnabled(false);
				Main.getAdminUpdateDeleteUsersForm().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(296, 397, 108, 58);
		StudentPanel.add(closeBtn);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setForeground(new Color(128, 0, 0));
		lblCity.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCity.setBounds(90, 185, 44, 17);
		StudentPanel.add(lblCity);
		
		cityTextField = new JTextField();
		cityTextField.setEditable(false);
		cityTextField.setColumns(10);
		cityTextField.setBackground(new Color(252, 252, 205));
		cityTextField.setBounds(139, 183, 207, 20);
		StudentPanel.add(cityTextField);
		
		UserTextField = new JTextField();
		UserTextField.setEditable(false);
		UserTextField.setColumns(10);
		UserTextField.setBackground(new Color(252, 252, 205));
		UserTextField.setBounds(139, 245, 207, 20);
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
		firstBtn.setBounds(124, 328, 49, 23);
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
		prevBtn.setBounds(171, 328, 49, 23);
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
		nextBtn.setBounds(216, 328, 49, 23);
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
		lastBtn.setBounds(264, 328, 49, 23);
		StudentPanel.add(lastBtn);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(35, 293, 370, 2);
		StudentPanel.add(separator);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "DELETE FROM STUDENTS WHERE ID = ?";
				
				try (Connection conn = DBUtil.getConnection();){
					
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setInt(1, Integer.parseInt(idTxt.getText()));
					
					
					int response = JOptionPane.showConfirmDialog(null, "Are you sure to delete the user?", "Warning", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						int n = ps.executeUpdate();
						JOptionPane.showMessageDialog(null, n + " rows affected", "Delete", JOptionPane.INFORMATION_MESSAGE);
					} else {
						return;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}	
			}
		});
		btnDelete.setForeground(Color.BLUE);
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDelete.setBounds(26, 397, 108, 58);
		StudentPanel.add(btnDelete);
		
		idTxt = new JTextField();
		idTxt.setEditable(false);
		idTxt.setColumns(10);
		idTxt.setBackground(new Color(252, 252, 205));
		idTxt.setBounds(139, 28, 59, 20);
		StudentPanel.add(idTxt);
		
		JLabel idLbl = new JLabel("ID");
		idLbl.setForeground(new Color(128, 0, 0));
		idLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		idLbl.setBounds(101, 28, 17, 17);
		StudentPanel.add(idLbl);
		
		JPanel TeacherPanel = new JPanel();
		tabbedPane.addTab("Teachers", null, TeacherPanel, null);
		TeacherPanel.setLayout(null);
		
		JButton btnUpdateTeacher = new JButton("Update");
		btnUpdateTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (specialityComboBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "Please select Speciality ", "Speciality", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String idString = idTeacherText.getText();
				int currentId = Integer.parseInt(idString);	
				String idSsn = ssnText.getText();
				int ssn = Integer.parseInt(idSsn);
				String firstname = NameTeacherText.getText().trim();
				String lastname = SurnameTeacherText.getText().trim();				
				String speciality = (String) specialityComboBox.getSelectedItem();
				String username = (String) TeacherUserTextField.getText();
				int specialityId = specialities.get(speciality);
				int usernameId = usernames.get(username);
			
				if (firstname == "" || lastname == "") {
					JOptionPane.showMessageDialog(null, "Please fill firstname / lastname", "Basic info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				PreparedStatement ps = null;
				try (Connection conn = DBUtil.getConnection();) {

					String sql = "UPDATE TEACHERS SET SSN = ?, FIRSTNAME = ?, LASTNAME = ?"
						+ ", SPECIALITY_ID = ?, USER_ID = ? WHERE ID = ?";
					
					ps = conn.prepareStatement(sql);
					ps.setInt(1, ssn);
					ps.setString(2, firstname);
					ps.setString(3, lastname);
					ps.setInt(4, specialityId);
					ps.setInt(5, usernameId);
					ps.setInt(6, currentId);
					int n = ps.executeUpdate();
					
					if (n > 0) {
						JOptionPane.showMessageDialog(null, "Successful Update", "Update", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Update Error", "Update", JOptionPane.ERROR_MESSAGE);
					}					
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
		btnUpdateTeacher.setForeground(Color.BLUE);
		btnUpdateTeacher.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdateTeacher.setBounds(160, 394, 108, 58);
		TeacherPanel.add(btnUpdateTeacher);
		
		JButton closeBtn_1 = new JButton("Close");
		closeBtn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getUsersMenu().setEnabled(true);
				Main.getUsersMenu().setVisible(true);
				Main.getAdminUpdateDeleteUsersForm().setEnabled(false);
				Main.getAdminUpdateDeleteUsersForm().setVisible(false);
			}
		});
		closeBtn_1.setForeground(Color.BLUE);
		closeBtn_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn_1.setBounds(297, 394, 108, 58);
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
		TeacherFirstBtn.setBounds(125, 325, 49, 23);
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
		TeacherPrevBtn.setBounds(172, 325, 49, 23);
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
		TeacherNextBtn.setBounds(217, 325, 49, 23);
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
		TeacherLastBtn.setBounds(265, 325, 49, 23);
		TeacherPanel.add(TeacherLastBtn);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(36, 290, 370, 2);
		TeacherPanel.add(separator_1);
		
		JButton btnDeleteTeacher = new JButton("Delete");
		btnDeleteTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "DELETE FROM TEACHERS WHERE ID = ?";
				
				try (Connection conn = DBUtil.getConnection();){
					
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setInt(1, Integer.parseInt(idTeacherText.getText()));
					
					
					int response = JOptionPane.showConfirmDialog(null, "Are you sure to delete the user?", "Warning", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						int n = ps.executeUpdate();
						JOptionPane.showMessageDialog(null, n + " rows affected", "Delete", JOptionPane.INFORMATION_MESSAGE);
					} else {
						return;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}	
			}
		});
		btnDeleteTeacher.setForeground(Color.BLUE);
		btnDeleteTeacher.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDeleteTeacher.setBounds(27, 394, 108, 58);
		TeacherPanel.add(btnDeleteTeacher);
		
		NameTeacherText = new JTextField();
		NameTeacherText.setColumns(10);
		NameTeacherText.setBounds(148, 62, 207, 20);
		TeacherPanel.add(NameTeacherText);
		
		JLabel firstnameLbl_1 = new JLabel("Name");
		firstnameLbl_1.setForeground(new Color(128, 0, 0));
		firstnameLbl_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		firstnameLbl_1.setBounds(93, 64, 56, 17);
		TeacherPanel.add(firstnameLbl_1);
		
		JLabel lastnameLbl_1 = new JLabel("Surname");
		lastnameLbl_1.setForeground(new Color(128, 0, 0));
		lastnameLbl_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lastnameLbl_1.setBounds(71, 96, 72, 17);
		TeacherPanel.add(lastnameLbl_1);
		
		SurnameTeacherText = new JTextField();
		SurnameTeacherText.setColumns(10);
		SurnameTeacherText.setBounds(148, 94, 207, 20);
		TeacherPanel.add(SurnameTeacherText);
		
		JLabel idLbl_1 = new JLabel("ID");
		idLbl_1.setForeground(new Color(128, 0, 0));
		idLbl_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		idLbl_1.setBounds(110, 31, 17, 17);
		TeacherPanel.add(idLbl_1);
		
		idTeacherText = new JTextField();
		idTeacherText.setEditable(false);
		idTeacherText.setColumns(10);
		idTeacherText.setBackground(new Color(252, 252, 205));
		idTeacherText.setBounds(148, 31, 59, 20);
		TeacherPanel.add(idTeacherText);
		
		JLabel ssnLbl = new JLabel("SSN");
		ssnLbl.setForeground(new Color(128, 0, 0));
		ssnLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		ssnLbl.setBounds(104, 126, 29, 17);
		TeacherPanel.add(ssnLbl);
		
		ssnText = new JTextField();
		ssnText.setEditable(false);
		ssnText.setColumns(10);
		ssnText.setBackground(new Color(252, 252, 205));
		ssnText.setBounds(148, 124, 59, 20);
		TeacherPanel.add(ssnText);
		
		JLabel specialityLbl = new JLabel("Speciality");
		specialityLbl.setForeground(new Color(128, 0, 0));
		specialityLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		specialityLbl.setBounds(71, 156, 72, 17);
		TeacherPanel.add(specialityLbl);
		
		SpecialityTextField = new JTextField();
		SpecialityTextField.setBackground(new Color(252, 252, 205));
		SpecialityTextField.setEditable(false);
		SpecialityTextField.setColumns(10);
		SpecialityTextField.setBounds(148, 154, 207, 20);
		TeacherPanel.add(SpecialityTextField);
		
		JLabel lblNewSpeciality = new JLabel("New Speciality");
		lblNewSpeciality.setForeground(new Color(128, 0, 0));
		lblNewSpeciality.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewSpeciality.setBounds(36, 185, 103, 17);
		TeacherPanel.add(lblNewSpeciality);
		
		specialityComboBox = new JComboBox<String>();
		specialityComboBox.setBounds(148, 185, 207, 20);
		TeacherPanel.add(specialityComboBox);
		
		JLabel usernameLbl_1 = new JLabel("Username");
		usernameLbl_1.setForeground(new Color(128, 0, 0));
		usernameLbl_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		usernameLbl_1.setBounds(64, 216, 72, 17);
		TeacherPanel.add(usernameLbl_1);
		
		TeacherUserTextField = new JTextField();
		TeacherUserTextField.setEditable(false);
		TeacherUserTextField.setColumns(10);
		TeacherUserTextField.setBackground(new Color(252, 252, 205));
		TeacherUserTextField.setBounds(148, 216, 207, 20);
		TeacherPanel.add(TeacherUserTextField);
	}
}
