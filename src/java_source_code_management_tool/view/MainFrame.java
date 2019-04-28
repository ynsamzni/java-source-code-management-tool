package java_source_code_management_tool.view;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java_source_code_management_tool.controller.JavaSourceFileController;
import java_source_code_management_tool.controller.LoginController;
import java_source_code_management_tool.controller.NavigationController;
import java_source_code_management_tool.model.service.JavaSourceFileService;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
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
	private JavaSourceFileSelectorPanel javaSourceFileSelectionPanel;
	private NavigationController navigationController;

	public MainFrame(JavaSourceFileService javaSourceFileService, JavaSourceFileController javaSourceFileController, LoginController loginController, NavigationController navigationController)
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
		homePanel = new HomePanel(javaSourceFileController, navigationController);
		versionManagementPanel = new VersionManagementPanel(javaSourceFileService, javaSourceFileController, navigationController);
		javaSourceFileViewerPanel = new JavaSourceFileViewerPanel(javaSourceFileService, navigationController);
		javaSourceFileSelectionPanel = new JavaSourceFileSelectorPanel(javaSourceFileController, navigationController, javaSourceFileService);
		
		// Attach JPanels to the container
		container.add(loginPanel, "LOGINPANEL");
		container.add(homePanel, "HOMEPANEL");
		container.add(javaSourceFileSelectionPanel, "JAVASOURCEFILESELECTORPANEL");
		container.add(versionManagementPanel, "VERSIONMANAGEMENTPANEL");
		container.add(javaSourceFileViewerPanel, "JAVASOURCEFILEVIEWERPANEL");
		
		// Show frame
		this.setVisible(true);
	}
	
	public void showCard(String card)
	{
		// Show card
		CardLayout cardLayout = (CardLayout) container.getLayout();
		cardLayout.show(container, card);
		
		// Notify controller
		navigationController.navigateActionPerformed(card);
	}
	
	public LoginPanel getLoginPanel()
	{
		return loginPanel;
	}
	
	public HomePanel getHomePanel()
	{
		return homePanel;
	}
	
	public VersionManagementPanel getVersionManagementPanel()
	{
		return versionManagementPanel;
	}
	
	public JavaSourceFileSelectorPanel getJavaSourceFileSelectionPanel()
	{
		return javaSourceFileSelectionPanel;
	}
	
	public void showLoginError()
	{
		JOptionPane.showMessageDialog(this, "Incorrect username or password. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void showIncorrectVersionNumberError()
	{
		JOptionPane.showMessageDialog(this, "Incorrect version number. Try again.\nMaximum 4 digits, each being between 0 and 9999, and separated by a dot are allowed  (e.g. 1 or 1.0 or 1.0.1 or 1.0.0.1).", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void showDuplicateVersionNumberError()
	{
		JOptionPane.showMessageDialog(this, "Duplicate version number. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void showIncorrectDescriptionError()
	{
		JOptionPane.showMessageDialog(this, "Incorrect description. Try again.\nMaximum 300 characters are allowed.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
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
