package java_source_code_management_tool.controller;

import java_source_code_management_tool.model.dto.User;
import java_source_code_management_tool.model.service.UserService;
import java_source_code_management_tool.view.MainFrame;

public class UserController
{
	private UserService userService;
	private MainFrame mainFrame;
	
	public UserController(UserService userService)
	{
		this.userService = userService;
	}
	
	public void setView(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
	}
	
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
				
				// Exit app
				mainFrame.exit();
			}
		}
		else
		{
			// Delete user
			userService.deleteUser(username);
		}
	}
	
	public void selectUserForHistoryActionPerformed(String username)
	{
		// Clear currently displayed user history
		mainFrame.getUserHistoryPanel().clearUserHistoryPanel();
		
		// Display newly selected user history
		mainFrame.getUserHistoryPanel().showUserHistory(userService.getUserHistory(username));
	}
}
