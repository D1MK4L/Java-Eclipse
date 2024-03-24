package gr.aueb.cf.usersApp;
/**
 * Guides to Users or to Usernames!
 * 
 * @author D1MK4L
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminMenu extends JFrame {
	private static final long serialVersionUID = 123456;
	private JPanel contentPane;

	
	public AdminMenu() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminMenu.class.getResource("/resources/eduv2.png")));
		setTitle("Admin Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 373);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getUsersMenu().setVisible(true);
				Main.getUsersMenu().setEnabled(true);
				Main.getAdminMenu().setEnabled(false);
				Main.getAdminMenu().setVisible(false);
			}
		});
		btnNewButton_1.setBounds(32, 56, 40, 40);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("Users");
		lblNewLabel_1.setForeground(new Color(128, 64, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(82, 62, 117, 29);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton_1_1 = new JButton("");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getUsernameMenu().setVisible(true);
				Main.getAdminMenu().setEnabled(false);
			}
		});
		btnNewButton_1_1.setBounds(32, 113, 40, 40);
		contentPane.add(btnNewButton_1_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Usernames");
		lblNewLabel_1_1.setForeground(new Color(128, 64, 0));
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(82, 119, 120, 29);
		contentPane.add(lblNewLabel_1_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(32, 217, 270, 1);
		contentPane.add(separator);
		
		JButton btnNewButton_2 = new JButton("Close");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getLoginForm().setVisible(true);
				Main.getLoginForm().setEnabled(true);				
				Main.getAdminMenu().setEnabled(false);
				Main.getAdminMenu().setVisible(false);
				//System.exit(0);
			}
		});
		btnNewButton_2.setForeground(new Color(0, 0, 255));
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_2.setBounds(204, 276, 103, 40);
		contentPane.add(btnNewButton_2);
	}
}