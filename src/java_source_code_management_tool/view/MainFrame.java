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

	public MainFrame(JavaSourceFileService javaSourceFileService, JavaSourceFileController javaSourceFileController, LoginController loginController, NavigationController navigationController)
	{	
		// Configure frame
		this.setTitle("Java source code management tool");
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Configure container
		container = this.getContentPane();
		container.setLayout(new CardLayout());
				
		// Attach JPanels to the container
		container.add(new LoginPanel(loginController), "LOGINPANEL");
		container.add(new HomePanel(javaSourceFileController), "HOMEPANEL");
		container.add(new VersionManagementPanel(javaSourceFileService, javaSourceFileController, navigationController), "VERSIONMANAGEMENTPANEL");
		
		// Show frame
		this.setVisible(true);
	}
	
	public void showCard(String card)
	{
		CardLayout cardLayout = (CardLayout) container.getLayout();
		cardLayout.show(container, card);
	}
	
	public void showLoginError()
	{
		JOptionPane.showMessageDialog(this, "Incorrect username or password. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void showIncorrectVersionNumberError()
	{
		JOptionPane.showMessageDialog(this, "Incorrect version number. Try again.\nMaximum 4 digits, each separated by a dot are allowed (e.g. 1 or 1.0 or 1.0.1 or 1.0.0.1).", "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void showDuplicateVersionNumberError()
	{
		JOptionPane.showMessageDialog(this, "Duplicate version number. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
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
