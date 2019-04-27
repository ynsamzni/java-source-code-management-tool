package java_source_code_management_tool.controller;

import java_source_code_management_tool.model.service.UserService;
import java_source_code_management_tool.view.MainFrame;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class NavigationController
{
	private MainFrame mainFrame;
	private UserService userService;
	
	public NavigationController(UserService userService)
	{
		this.userService = userService;
	}
	
	public void setView(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
	}
	
	public void goHomeActionPerformed()
	{
		if(userService.getCurrentUser().getAccessLevel() == 0)
			mainFrame.showCard("HOMEPANEL");
		else
			mainFrame.showCard("HOMEPANEL");
	}
	
	public void goHomeFromVersionManagementActionPerformed()
	{
		goHomeActionPerformed();
		mainFrame.getVersionManagementPanel().clearNewVersionPanel();
	}
	
	public void addTextFieldDescriptionActionPerformed()
	{
		mainFrame.getVersionManagementPanel().addTextFieldDescription();
	}
}
