package java_source_code_management_tool.controller;

import java.util.ArrayList;

import java_source_code_management_tool.model.service.JavaSourceFileService;
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
	private JavaSourceFileService javaSourceFileService;
	private ArrayList<String> navHistory;
	
	public NavigationController(UserService userService, JavaSourceFileService javaSourceFileService)
	{
		this.userService = userService;
		this.javaSourceFileService = javaSourceFileService;
		navHistory = new ArrayList<String>();
	}
	
	public void setView(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
	}
	
	public void navigateActionPerformed(String cardName)
	{
		// Update navigation history
		navHistory.add(cardName);
		
		// Perform card start actions
		if(cardName.equals("JAVASOURCEFILESELECTORPANEL"))
		{
			// Display list of Java source file available on database
			mainFrame.getJavaSourceFileSelectorPanel().showListDbJavaSourceFiles(javaSourceFileService.getListJavaSourceFilePathsFs());
		}
		else if(cardName.equals("USERDELETIONPANEL"))
		{
			// Display list of users available on database
			mainFrame.getUserDeletionPanel().showListDbUserUsernames(userService.getListUserUsernames());
		}
	}
	
	public void goJavaSourceFileSelectorActionPerformed()
	{
		mainFrame.showCard("JAVASOURCEFILESELECTORPANEL");
	}
	
	public void goUserManagementActionPerformed()
	{
		mainFrame.showCard("USERMANAGEMENTPANEL");
	}
	
	public void goUserCreationActionPerformed()
	{
		mainFrame.showCard("USERCREATIONPANEL");
	}
	
	public void goUserDeletionActionPerformed()
	{
		mainFrame.showCard("USERDELETIONPANEL");
	}
	
	public void goBackActionPerformed()
	{
		mainFrame.showCard(getPreviousVisibleCardName());
	}
	
	public void goHomeActionPerformed()
	{
		mainFrame.showCard("HOMEPANEL");
		
		// Clear previous card if required
		if(getPreviousVisibleCardName().equals("VERSIONMANAGEMENTPANEL"))
			mainFrame.getVersionManagementPanel().clearNewVersionPanel();
	}
	
	public void addTextFieldDescriptionActionPerformed()
	{
		mainFrame.getVersionManagementPanel().addTextFieldDescription();
	}
	
	public String getPreviousVisibleCardName()
	{
		return navHistory.get(navHistory.size() - 2);
	}
}
