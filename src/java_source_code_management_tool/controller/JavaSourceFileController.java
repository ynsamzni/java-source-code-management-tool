package java_source_code_management_tool.controller;

import java.util.ArrayList;

import java_source_code_management_tool.model.dto.Description;
import java_source_code_management_tool.model.dto.Version;
import java_source_code_management_tool.model.service.JavaSourceFileService;
import java_source_code_management_tool.model.service.UserService;
import java_source_code_management_tool.view.MainFrame;

/**
 * This class consists of controller methods related to actions on Java source files that follow the Model-View-Controller pattern.
 * 
 * @author Jordan and Yanis (Group 4 - Pair 10)
 *
 */
public class JavaSourceFileController
{
	private JavaSourceFileService javaSourceFileService;
	private UserService userService;
	private MainFrame mainFrame;
	
	/**
	 * Constructs a new Java source file controller with the specified Java source file service and user service.
	 * 
	 * @param javaSourceFileService the Java source file service acting as a model.
	 * @param userService the user service acting as a model.
	 */
	public JavaSourceFileController(JavaSourceFileService javaSourceFileService, UserService userService)
	{
		this.javaSourceFileService = javaSourceFileService;
		this.userService = userService;
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
	 * Tells the view to show a file dialog from which the user can select a file on its file system.
	 * If a file is selected, tells the model to load it, and the view to display its version related data in the file version management panel.
	 */
	public void openFsJavaSourceFileAndManageVersionsActionPerformed()
	{
		String selectedPathFs;
		
		// Show file dialog
		selectedPathFs = mainFrame.showFileDialog();
		
		// If a file has been selected
		if(selectedPathFs != null)
		{
			// Open Java source file
			javaSourceFileService.loadJavaSourceFileFromFs(selectedPathFs);
			
			// Show Java source file version management view
			mainFrame.showCard("VERSIONMANAGEMENTPANEL");
		}
	}
	
	/**
	 * Tells the view to show a file dialog from which the user can select a file on its file system.
	 * If a file is selected, tells the model to load it, and the view to display its content in the Java source file viewer panel.
	 */
	public void openFsJavaSourceFileAndDisplayContentActionPerformed()
	{
		String selectedPathFs;
		
		// Show file dialog
		selectedPathFs = mainFrame.showFileDialog();
		
		// If a file has been selected
		if(selectedPathFs != null)
		{
			// Open Java source file
			javaSourceFileService.loadJavaSourceFileFromFs(selectedPathFs);
			
			// Show Java source file viewer
			mainFrame.showCard("JAVASOURCEFILEVIEWERPANEL");
		}
	}
	
	/**
	 * Tells the model to load from the database the Java source file that has the specified file system path.
	 * When the file has been loaded, tells the view to display its content in the Java source file viewer panel.
	 * 
	 * @param selectedPathFs the file system path of the Java source file to load from the database.
	 */
	public void openDbJavaSourceFileAndDisplayContentActionPerformed(String selectedPathFs)
	{
		if(selectedPathFs != null)
		{
			// Open Java source file
			javaSourceFileService.loadJavaSourceFileFromDb(selectedPathFs);
			
			// Show Java source file viewer
			mainFrame.showCard("JAVASOURCEFILEVIEWERPANEL");
		}
	}
	
	/**
	 * Tells the model to add a new version to the currently opened Java source file.
	 * The specified version number and its list of related descriptions are first checked before being saved.
	 * 
	 * @param versionNumber the version number to add.
	 * @param strDescriptions the list of descriptions of the new version to add.
	 */
	public void addVersionActionPerformed(String versionNumber, ArrayList<String> strDescriptions)
	{
		Version version;
		ArrayList<Description> descriptions = new ArrayList<Description>();
		boolean validVersionNumber, validDescriptions, uniqueVersionNumber;
		
		// Check if version number is valid (length and format)
		validVersionNumber = false;
		if(versionNumber.matches("^([0-9]{1,4}\\.)?([0-9]{1,4}\\.)?([0-9]{1,4}\\.)?([0-9]{1,4})$"))
			validVersionNumber = true;
		
		// Check if version number is unique
		uniqueVersionNumber = true;
		for(int i=0; i<javaSourceFileService.getCurrentJavaSourceFile().getListVersions().size(); i++)
		{
			if(versionNumber.equals(javaSourceFileService.getCurrentJavaSourceFile().getVersion(i).getVersion()))
				uniqueVersionNumber = false;
		}
		
		// Check if descriptions are valid (length)
		validDescriptions = true;
		for(int i=0; i<strDescriptions.size(); i++)
		{
			if(strDescriptions.get(i).length() > 300)
				validDescriptions = false;
		}
		
		if(validVersionNumber && uniqueVersionNumber && validDescriptions)
		{
			// Convert view descriptions for the model
			for(int i=0; i<strDescriptions.size(); i++)
			{
				// Ignore empty descriptions
				if(!strDescriptions.get(i).trim().isEmpty())
					descriptions.add(new Description(strDescriptions.get(i)));
			}
			
			// Convert view version for the model
			version = new Version(versionNumber, userService.getCurrentUser().getUsername());
			version.setListDescriptions(descriptions);
					
			// Save created data
			javaSourceFileService.addVersion(version);
			
			mainFrame.getVersionManagementPanel().clearNewVersionPanel();
		}
		else if(!validVersionNumber)
		{
			mainFrame.showIncorrectVersionNumberError();
		}
		else if(!uniqueVersionNumber)
		{
			mainFrame.showDuplicateVersionNumberError();
		}
		else if(!validDescriptions)
		{
			mainFrame.showIncorrectDescriptionError();
		}
	}
}
