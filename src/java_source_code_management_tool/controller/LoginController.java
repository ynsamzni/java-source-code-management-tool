package java_source_code_management_tool.controller;

import java_source_code_management_tool.model.service.UserService;
import java_source_code_management_tool.view.MainFrame;

/**
 * This class consists of controller methods related to login actions that follow the Model-View-Controller pattern.
 * 
 * @author Jordan and Yanis (Group 4 - Pair 10)
 *
 */
public class LoginController
{
	private UserService userService;
	private MainFrame mainFrame;
	
	/**
	 * Constructs a new login controller with the specified user service.
	 * 
	 * @param userService the user service acting as a model.
	 */
	public LoginController(UserService userService)
	{
		this.userService = userService;
	}
	
	/**
	 * Sets the view to use.
	 * 
	 * @param mainFrame the view to use.
	 */
	public void setView(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
	}
	
	/**
	 * Tells the model to load the user which has the specified username and password from the database.
	 * If such a user exists, tells the view to display the home panel. Otherwise display an error.
	 * 
	 * @param username the username of the user to load from the database.
	 * @param password the password of the user to load from the database.
	 */
	public void loginActionPerformed(String username, char[] password)
	{
		if(userService.loadUser(username, new String(password)))
		{
			mainFrame.showCard("HOMEPANEL");
			mainFrame.getLoginPanel().clearUsernameField();
		}
		else
			mainFrame.showLoginError();
		
		// Clear password field
		mainFrame.getLoginPanel().clearPasswordField();
	}
	
	/**
	 * Tells the view to display the login panel, and the model to unload the user.
	 */
	public void logoutActionPerformed()
	{
		mainFrame.showCard("LOGINPANEL");
		userService.unloadUser();
	}
}
