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
		navHistory.add(cardName);
	}
	
	public void goJavaSourceFileSelectorActionPerformed()
	{
		// Refresh list of Java source file available on database
		mainFrame.getJavaSourceFileSelectionPanel().showListDbJavaSourceFiles(javaSourceFileService.getListJavaSourceFilePathsFs());
		
		// Display card
		mainFrame.showCard("JAVASOURCEFILESELECTORPANEL");
	}
	
	public void goBackActionPerformed()
	{
		mainFrame.showCard(getPreviousVisibleCardName());
	}
	
	public void goHomeActionPerformed()
	{
		if(userService.getCurrentUser().getAccessLevel() == 0)
			mainFrame.showCard("HOMEPANEL");
		else
			mainFrame.showCard("HOMEPANEL");
		
		// Clear if required
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
