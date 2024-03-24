package gr.aueb.cf.usersApp;
/**
 * A Full Stack API that handle Users into a Database
 * The Users can be either Students or Teachers
 * In order to access the CRUD commands you need to login
 * as an Admin!
 * 
 * @author D1MK4L
 */
import java.awt.EventQueue;

public class Main {
	
	private static Login loginForm;
	private static AdminMenu adminMenu;	
	private static AdminInsertUsersForm adminInsertUsersForm;	
	private static AdminUpdateDeleteUsersForm adminUpdateDeleteUsersForm;
	private static AdminUpdateDeleteUsernameForm adminUpdateDeleteUsernameForm;
	private static UsersMenu usersMenu;
	private static UsernameMenu usernameMenu;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginForm = new Login();
					loginForm.setVisible(true);
					
					adminMenu = new AdminMenu();
					adminMenu.setVisible(false);			
					
					usersMenu = new UsersMenu();
					usersMenu.setVisible(false);
					
					usernameMenu = new UsernameMenu();
					usernameMenu.setVisible(false);
					
					adminInsertUsersForm = new AdminInsertUsersForm();
					adminInsertUsersForm.setVisible(false);
					
					adminUpdateDeleteUsersForm = new AdminUpdateDeleteUsersForm();
					adminUpdateDeleteUsersForm.setVisible(false);
					
					adminUpdateDeleteUsernameForm = new AdminUpdateDeleteUsernameForm();
					adminUpdateDeleteUsernameForm.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static Login getLoginForm() {
		return loginForm;
	}

	public static AdminMenu getAdminMenu() {
		return adminMenu;
	}


	public static AdminInsertUsersForm getAdminInsertUsersForm() {
		return adminInsertUsersForm;
	}

	public static UsersMenu getUsersMenu() {
		return usersMenu;
	}
	
	public static UsernameMenu getUsernameMenu() {
		return usernameMenu;
	}

	public static AdminUpdateDeleteUsersForm getAdminUpdateDeleteUsersForm() {
		return adminUpdateDeleteUsersForm;
	}
	
	public static AdminUpdateDeleteUsernameForm getAdminUpdateDeleteUsernameForm() {
		return adminUpdateDeleteUsernameForm;
	}	
	
}