package java_source_code_management_tool.controller;

import java_source_code_management_tool.model.service.UserService;
import java_source_code_management_tool.view.MainFrame;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class LoginController
{
	private UserService userService;
	private MainFrame mainFrame;
	
	public LoginController(UserService userService)
	{
		this.userService = userService;
	}
	
	public void setView(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
	}
	
	public void loginActionPerformed(String username, char[] password)
	{
		if(userService.loadUser(username, new String(password)))
			mainFrame.showCard("HOMEPANEL");
		else
			mainFrame.showLoginError();
		
		mainFrame.getLoginPanel().clearPasswordField();
	}
}
