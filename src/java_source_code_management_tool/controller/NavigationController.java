package java_source_code_management_tool.controller;

import java.util.ArrayList;

import java_source_code_management_tool.model.service.JavaSourceFileService;
import java_source_code_management_tool.model.service.UserService;
import java_source_code_management_tool.util.JavaFormatter;
import java_source_code_management_tool.view.MainFrame;

/**
 * This class consists of controller methods related to navigation actions that follow the Model-View-Controller pattern.
 * 
 * @author Jordan and Yanis (Group 4 - Pair 10)
 *
 */
public class NavigationController
{
	private MainFrame mainFrame;
	private UserService userService;
	private JavaSourceFileService javaSourceFileService;
	private ArrayList<String> navHistory;
	
	/**
	 * Constructs a new navigation controller with the specified user service and Java source file service.
	 * 
	 * @param userService the user service acting as a model.
	 * @param javaSourceFileService the Java source file service acting as a model.
	 */
	public NavigationController(UserService userService, JavaSourceFileService javaSourceFileService)
	{
		this.userService = userService;
		this.javaSourceFileService = javaSourceFileService;
		navHistory = new ArrayList<String>();
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
	 * Keeps a history of navigation done between panels.
	 * 
	 * @param cardName the newly user accessed panel name.
	 */
	public void navigateActionPerformed(String cardName)
	{
		// Update navigation history
		navHistory.add(cardName);
	}
	
	/**
	 * Tells the view to display the Java source file selector panel.
	 */
	public void goJavaSourceFileSelectorActionPerformed()
	{
		mainFrame.showCard("JAVASOURCEFILESELECTORPANEL");
		
		// Display list of Java source file available on database
		mainFrame.getJavaSourceFileSelectorPanel().showListDbJavaSourceFiles(javaSourceFileService.getListJavaSourceFilePathsFs());
	}
	
	/**
	 * Tells the view to display the version management panel.
	 */
	public void goVersionManagementActionPerformed()
	{
		mainFrame.showCard("VERSIONMANAGEMENTPANEL");
	}
	
	/**
	 * Tells the view to display the user management panel.
	 */
	public void goUserManagementActionPerformed()
	{
		mainFrame.showCard("USERMANAGEMENTPANEL");
	}
	
	/**
	 * Tells the view to display the user creation panel.
	 */
	public void goUserCreationActionPerformed()
	{
		mainFrame.showCard("USERCREATIONPANEL");
	}
	
	/**
	 * Tells the view to display the user deletion panel.
	 */
	public void goUserDeletionActionPerformed()
	{
		mainFrame.showCard("USERDELETIONPANEL");
		
		// Display list of users available on database
		mainFrame.getUserDeletionPanel().showListDbUserUsernames(userService.getListUserUsernames());
	}
	
	/**
	 * Tells the view to display the user history panel.
	 */
	public void goUserHistoryActionPerformed()
	{
		mainFrame.showCard("USERHISTORYPANEL");
		
		// Display list of users available on database
		mainFrame.getUserHistoryPanel().showListDbUserUsernames(userService.getListUserUsernames());
	}
	
	/**
	 * Tells the view to display the previous user accessed panel.
	 */
	public void goBackActionPerformed()
	{
		mainFrame.showCard(getPreviousVisibleCardName());
		
		// Clear previous card if required
		if(getPreviousVisibleCardName().equals("VERSIONMANAGEMENTPANEL"))
			mainFrame.getVersionManagementPanel().clearNewVersionPanel();
		
		else if(getPreviousVisibleCardName().equals("USERCREATIONPANEL"))
			mainFrame.getUserCreationPanel().clear();
	}
	
	/**
	 * Tells the view to display the home panel.
	 */
	public void goHomeActionPerformed()
	{
		mainFrame.showCard("HOMEPANEL");
	}
	
	/**
	 * Tells the view to display a new description text field in the version management panel.
	 */
	public void addTextFieldDescriptionActionPerformed()
	{
		mainFrame.getVersionManagementPanel().addTextFieldDescription();
	}
	
	/**
	 * Tells the view to update the displayed content of the opened Java source file by applying the comment deletion toggle state in the Java source file viewer panel.
	 * 
	 * @param commentDeletionIsActive the comment deletion toggle state.
	 * @param currentContent the currently being displayed Java source file content in the Java source file viewer panel.
	 */
	public void toggleCommentDeletionActionPerformed(boolean commentDeletionIsActive, String currentContent)
	{
		if(commentDeletionIsActive)
			mainFrame.getJavaSourceFileViewerPanel().setDisplayedContent(JavaFormatter.deleteCommentsOrJavadoc(currentContent, 0));
		else
			reloadJavaSourceFileViewerContent();
	}
	
	/**
	 * Tells the view to update the displayed content of the opened Java source file by applying the Javadoc deletion toggle state in the Java source file viewer panel.
	 * 
	 * @param javadocDeletionIsActive the Javadoc deletion toggle state.
	 * @param currentContent the currently being displayed Java source file content in the Java source file viewer panel.
	 */
	public void toggleJavadocDeletionActionPerformed(boolean javadocDeletionIsActive, String currentContent)
	{
		if(javadocDeletionIsActive)
			mainFrame.getJavaSourceFileViewerPanel().setDisplayedContent(JavaFormatter.deleteCommentsOrJavadoc(currentContent, 1));
		else
			reloadJavaSourceFileViewerContent();
	}

	/**
	 * Tells the view to update the displayed content of the opened Java source file by applying the indentation toggle state in the Java source file viewer panel.
	 * 
	 * @param indentationIsActive the indentation toggle state.
	 * @param currentContent the currently being displayed Java source file content in the Java source file viewer panel.
	 */
	public void toggleIndentationActionPerformed(boolean indentationIsActive, String currentContent)
	{
		if(indentationIsActive)
			mainFrame.getJavaSourceFileViewerPanel().setDisplayedContent(JavaFormatter.indent(currentContent));
		else
			reloadJavaSourceFileViewerContent();
	}
	
	/**
	 * Tells the view to reload the displayed content of the opened Java source file and re-apply all active display toggle in the Java source file viewer panel.
	 */
	public void reloadJavaSourceFileViewerContent()
	{
		String reloadedContent;
		
		// Get original content
		reloadedContent = javaSourceFileService.getCurrentJavaSourceFile().getContent();
		
		// Re-apply toggled display options
		if(mainFrame.getJavaSourceFileViewerPanel().commentDeletionIsActive())
			reloadedContent = JavaFormatter.deleteCommentsOrJavadoc(reloadedContent, 0);
		
		if(mainFrame.getJavaSourceFileViewerPanel().javadocDeletionIsActive())
			reloadedContent = JavaFormatter.deleteCommentsOrJavadoc(reloadedContent, 1);
		
		if(mainFrame.getJavaSourceFileViewerPanel().indentationIsActive())
			reloadedContent = JavaFormatter.indent(reloadedContent);
		
		// Set reloaded content
		mainFrame.getJavaSourceFileViewerPanel().setDisplayedContent(reloadedContent);
	}
	
	/**
	 * Returns the previously user accessed panel name.
	 * 
	 * @return the previously user accessed panel name.
	 */
	public String getPreviousVisibleCardName()
	{
		return navHistory.get(navHistory.size() - 2);
	}
}
