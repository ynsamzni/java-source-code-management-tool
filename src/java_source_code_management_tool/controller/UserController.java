package java_source_code_management_tool.controller;

import java_source_code_management_tool.model.dto.User;
import java_source_code_management_tool.model.service.UserService;
import java_source_code_management_tool.view.MainFrame;

/**
 * This class consists of controller methods related to actions on users that follow the Model-View-Controller pattern.
 * @author Jordan and Yanis (Group 4 - Pair 10)
 *
 */
public class UserController
{
	private UserService userService;
	private MainFrame mainFrame;
	
	/**
	 * Constructs a new user controller with the specified user service.
	 * 
	 * @param userService the user service acting as a model.
	 */
	public UserController(UserService userService)
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
	 * Tells the model to create a new user.
	 * The specified username and password are first checked before being saved.
	 * 
	 * @param username the username of the user to create.
	 * @param password the password of the user to create.
	 * @param isAdmin the access level of the user to create.
	 */
	public void createUserActionPerformed(String username, char[] password, boolean isAdmin)
	{
		int accessLevel;
		boolean validUsername, validPassword, uniqueUsername;
		
		// Check if username is valid (length)
		validUsername = false;
		if(username.length() > 0 && username.length() < 21)
			validUsername = true;
		
		// Check if username is unique
		uniqueUsername = true;
		for(int i=0; i<userService.getListUserUsernames().size(); i++)
		{
			if(username.equals(userService.getListUserUsernames().get(i)))
				uniqueUsername = false;
		}
		
		// Check if password is valid (length)
		validPassword = false;
		if(password.length > 0 && password.length < 101)
			validPassword = true;
		
		// If all checks passed
		if(validUsername && validPassword && uniqueUsername)
		{
			// Convert view user input for the model
			if(isAdmin)
				accessLevel = 1;
			else
				accessLevel = 0;
					
			// Save created data
			userService.addUser(new User(username, new String(password), accessLevel));
			
			// Show success message
			mainFrame.showUserCreationSuccessInformation();
			
			// Clear view from user input
			mainFrame.getUserCreationPanel().clear();
		}
		else if(!validUsername)
		{
			mainFrame.showIncorrectUsernameError();
		}
		else if(!uniqueUsername)
		{
			mainFrame.showDuplicateUsernameError();
		}
		else if(!validPassword)
		{
			mainFrame.showIncorrectPasswordError();
		}
	}
	
	/**
	 * Tells the model to delete the user which has the specified username.
	 * 
	 * @param username the username of the user to delete.
	 */
	public void deleteUserActionPerformed(String username)
	{
		// If the current user is the one being deleted
		if(username.equals(userService.getCurrentUser().getUsername()))
		{
			// Warn the user
			if(mainFrame.showCurrentUserDeletionWarning())
			{
				// Delete user
				userService.deleteUser(username);
				
				// Logout
				mainFrame.showCard("LOGINPANEL");
				userService.unloadUser();
			}
		}
		else
		{
			// Delete user
			userService.deleteUser(username);
		}
	}
	
	/**
	 * Tells the view to display the specified user history in the user history panel.
	 * 
	 * @param username the username of the user to select.
	 */
	public void selectUserForHistoryActionPerformed(String username)
	{
		// Clear currently displayed user history
		mainFrame.getUserHistoryPanel().clearUserHistoryPanel();
		
		// Display newly selected user history
		mainFrame.getUserHistoryPanel().showUserHistory(userService.getUserHistory(username));
	}
}
