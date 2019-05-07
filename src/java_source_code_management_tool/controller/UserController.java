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
		
		// Convert view user data for the model
		if(isAdmin)
			accessLevel = 1;
		else
			accessLevel = 0;
		
		// Save created data
		userService.addUser(new User(username, new String(password), accessLevel));
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
}
