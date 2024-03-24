package gr.aueb.cf.usersApp;
/**
 * A Username Menu that guides to search 
 * a username with the linked User or 
 * insert a new username.
 * 
 * @author D1MK4L
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UsernameMenu extends JFrame {
	private static final long serialVersionUID = 1234567;
	private JPanel contentPane;
	private String lastname = "";

	public UsernameMenu() {
		setTitle("Usernames Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 481);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		searchPanel.setBounds(32, 23, 355, 170);
		contentPane.add(searchPanel);
		searchPanel.setLayout(null);
		
		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getAdminUpdateDeleteUsernameForm().setVisible(true);
				Main.getAdminUpdateDeleteUsernameForm().setEnabled(true);
				Main.getUsersMenu().setEnabled(true);				
				//lastname = lastnameTxt.getText();
			}
		});
		searchBtn.setForeground(new Color(0, 0, 255));
		searchBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		searchBtn.setBounds(113, 49, 122, 47);
		searchPanel.add(searchBtn);
		
		JPanel insertPanel = new JPanel();
		insertPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		insertPanel.setBounds(32, 204, 355, 145);
		contentPane.add(insertPanel);
		insertPanel.setLayout(null);
		
		JButton insertbtn = new JButton("Insert");
		insertbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login loginForm = Main.getLoginForm();
		        loginForm.setVisible(true);
		        loginForm.setEnabled(true);
		        loginForm.setActiveTab(1);
		        loginForm.showButton();
				Main.getUsernameMenu().setEnabled(false);
				Main.getUsernameMenu().setVisible(false);
			}
		});
		insertbtn.setForeground(new Color(0, 0, 255));
		insertbtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		insertbtn.setBounds(100, 54, 150, 47);
		insertPanel.add(insertbtn);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getAdminMenu().setEnabled(true);
				Main.getAdminMenu().setVisible(true);
				Main.getUsernameMenu().setVisible(false);
			}
		});
		closeBtn.setForeground(new Color(0, 0, 255));
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeBtn.setBounds(280, 384, 108, 49);
		contentPane.add(closeBtn);
	}

	public String getLastname() {
		return lastname;
	}

	
}