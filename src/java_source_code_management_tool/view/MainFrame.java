package java_source_code_management_tool.view;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java_source_code_management_tool.controller.JavaSourceFileController;
import java_source_code_management_tool.controller.LoginController;
import java_source_code_management_tool.controller.NavigationController;
import java_source_code_management_tool.controller.UserController;
import java_source_code_management_tool.model.service.JavaSourceFileService;
import java_source_code_management_tool.model.service.UserService;

/**
 * This class consists of view methods related to the display of all panels following the Model-View-Controller pattern.
 * 
 * @author Jordan and Yanis (Group 4 - Pair 10)
 *
 */
public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private Container container;
	private LoginPanel loginPanel;
	private HomePanel homePanel;
	private VersionManagementPanel versionManagementPanel;
	private JavaSourceFileViewerPanel javaSourceFileViewerPanel;
	private JavaSourceFileSelectorPanel javaSourceFileSelectorPanel;
	private UserManagementPanel userManagementPanel;
	private UserCreationPanel userCreationPanel;
	private UserDeletionPanel userDeletionPanel;
	private UserHistoryPanel userHistoryPanel;
	private NavigationController navigationController;

	/**
	 * Constructs a new main frame with the specified Java source file service and controller, login controller, navigation controller and user service and controller.
	 * 
	 * @param javaSourceFileService the Java source file service acting as a model.
	 * @param javaSourceFileController the Java source file controller.
	 * @param loginController the login controller.
	 * @param navigationController the navigation controller.
	 * @param userService the user service acting as a model.
	 * @param userController the user controller.
	 */
	public MainFrame(JavaSourceFileService javaSourceFileService, JavaSourceFileController javaSourceFileController, LoginController loginController, NavigationController navigationController, UserService userService, UserController userController)
	{	
		// Set controller
		this.navigationController = navigationController;
		
		// Configure frame
		this.setTitle("Java source code management tool");
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Configure container
		container = this.getContentPane();
		container.setLayout(new CardLayout());
		
		// Create JPanels
		loginPanel = new LoginPanel(loginController);
		homePanel = new HomePanel(javaSourceFileController, navigationController, loginController, userService);
		versionManagementPanel = new VersionManagementPanel(javaSourceFileService, javaSourceFileController, navigationController);
		javaSourceFileViewerPanel = new JavaSourceFileViewerPanel(javaSourceFileService, navigationController);
		javaSourceFileSelectorPanel = new JavaSourceFileSelectorPanel(javaSourceFileController, navigationController, javaSourceFileService);
		userManagementPanel = new UserManagementPanel(navigationController);
		userCreationPanel = new UserCreationPanel(navigationController, userController);
		userDeletionPanel = new UserDeletionPanel(navigationController, userController, userService);
		userHistoryPanel = new UserHistoryPanel(navigationController, userController);
		
		// Attach JPanels to the container
		container.add(loginPanel, "LOGINPANEL");
		container.add(homePanel, "HOMEPANEL");
		container.add(javaSourceFileSelectorPanel, "JAVASOURCEFILESELECTORPANEL");
		container.add(versionManagementPanel, "VERSIONMANAGEMENTPANEL");
		container.add(javaSourceFileViewerPanel, "JAVASOURCEFILEVIEWERPANEL");
		container.add(userManagementPanel, "USERMANAGEMENTPANEL");
		container.add(userCreationPanel, "USERCREATIONPANEL");
		container.add(userDeletionPanel, "USERDELETIONPANEL");
		container.add(userHistoryPanel, "USERHISTORYPANEL");
		
		// Show frame
		this.setVisible(true);
	}
	
	/**
	 * Displays the specified card.
	 * 
	 * @param card the name of the card to display.
	 */
	public void showCard(String card)
	{
		// Show card
		CardLayout cardLayout = (CardLayout) container.getLayout();
		cardLayout.show(container, card);
		
		// Notify controller
		navigationController.navigateActionPerformed(card);
	}
	
	/**
	 * Returns the login panel.
	 * 
	 * @return the login panel.
	 */
	public LoginPanel getLoginPanel()
	{
		return loginPanel;
	}
	
	/**
	 * Returns the home panel.
	 * 
	 * @return the home panel.
	 */
	public HomePanel getHomePanel()
	{
		return homePanel;
	}
	
	/**
	 * Returns the versions management panel.
	 * 
	 * @return the version management panel.
	 */
	public VersionManagementPanel getVersionManagementPanel()
	{
		return versionManagementPanel;
	}
	
	/**
	 * Returns the Java source file selector panel.
	 * 
	 * @return the Java source file selector panel.
	 */
	public JavaSourceFileSelectorPanel getJavaSourceFileSelectorPanel()
	{
		return javaSourceFileSelectorPanel;
	}
	
	/**
	 * Returns the Java source file viewer panel.
	 * 
	 * @return the Java source file viewer panel.
	 */
	public JavaSourceFileViewerPanel getJavaSourceFileViewerPanel()
	{
		return javaSourceFileViewerPanel;
	}
	
	/**
	 * Returns the user deletion panel.
	 * 
	 * @return the user deletion panel.
	 */
	public UserDeletionPanel getUserDeletionPanel()
	{
		return userDeletionPanel;
	}
	
	/**
	 * Returns the user creation panel.
	 * 
	 * @return the user creation panel.
	 */
	public UserCreationPanel getUserCreationPanel()
	{
		return userCreationPanel;
	}
	
	/**
	 * Returns the user history panel.
	 * 
	 * @return the user history panel.
	 */
	public UserHistoryPanel getUserHistoryPanel()
	{
		return userHistoryPanel;
	}
	
	/**
	 * Displays a login error.
	 */
	public void showLoginError()
	{
		JOptionPane.showMessageDialog(this, "Incorrect username or password. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Displays a incorrect version number error.
	 */
	public void showIncorrectVersionNumberError()
	{
		JOptionPane.showMessageDialog(this, "Incorrect version number. Try again.\nMaximum 4 digits, each being between 0 and 9999, and separated by a dot are allowed  (e.g. 1 or 1.0 or 1.0.1 or 1.0.0.1).", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Displays a duplicate version number error.
	 */
	public void showDuplicateVersionNumberError()
	{
		JOptionPane.showMessageDialog(this, "Duplicate version number. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Displays a incorrect description error.
	 */
	public void showIncorrectDescriptionError()
	{
		JOptionPane.showMessageDialog(this, "Incorrect description. Try again.\nMaximum 300 characters are allowed.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Displays a incorrect username error.
	 */
	public void showIncorrectUsernameError()
	{
		JOptionPane.showMessageDialog(this, "Incorrect username. Try again.\nBetween 1-20 characters are allowed.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Displays a duplicate username error.
	 */
	public void showDuplicateUsernameError()
	{
		JOptionPane.showMessageDialog(this, "Duplicate username. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Displays a incorrect password error.
	 */
	public void showIncorrectPasswordError()
	{
		JOptionPane.showMessageDialog(this, "Incorrect password. Try again.\nBetween 1-100 characters are allowed.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Displays a current user deletion warning.
	 * 
	 * @return the user choice of deleting itself.
	 */
	public boolean showCurrentUserDeletionWarning()
	{
		boolean userChoice = false;
		Object[] options = {"Delete and logout", "Cancel"};

		if(JOptionPane.showOptionDialog(this, "Are you sure you want to delete the user you are currently being logged in?\nIf you choose to, the application will logout after the deletion.", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]) == JOptionPane.YES_OPTION)
			userChoice = true;
		
		return userChoice;
	}
	
	/**
	 * Displays a user creation success information.
	 */
	public void showUserCreationSuccessInformation()
	{
		JOptionPane.showMessageDialog(this, "User successfully created.");
	}
	
	/**
	 * Displays a file dialog from which the user can select a file on the file system.
	 * 
	 * @return the selected file path on the file system.
	 */
	public String showFileDialog()
	{
		String selectedFilePathFs = null;
		FileDialog fileDialog = new FileDialog(this, "Choose a file");
		
		// Show file dialog
		fileDialog.setVisible(true);
		
		// Get selected file		
		if(fileDialog.getDirectory() != null && fileDialog.getFile() != null)
			selectedFilePathFs = fileDialog.getDirectory() + fileDialog.getFile();
		
		return selectedFilePathFs;
	}
}
