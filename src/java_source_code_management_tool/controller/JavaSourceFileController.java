package java_source_code_management_tool.controller;

import java.util.ArrayList;

import java_source_code_management_tool.model.dto.Description;
import java_source_code_management_tool.model.dto.Version;
import java_source_code_management_tool.model.service.JavaSourceFileService;
import java_source_code_management_tool.model.service.UserService;
import java_source_code_management_tool.view.MainFrame;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class JavaSourceFileController
{
	private JavaSourceFileService javaSourceFileService;
	private UserService userService;
	private MainFrame mainFrame;
	
	public JavaSourceFileController(JavaSourceFileService javaSourceFileService, UserService userService)
	{
		this.javaSourceFileService = javaSourceFileService;
		this.userService = userService;
	}
	
	public void setView(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
	}
	
	public void manageVersionsActionPerformed()
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
	
	public void selectFsJavaSourceFileActionPerformed()
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
			mainFrame.showCard("JAVASOURCEFILEVIEWERPANEL");
		}
	}
	
	public void selectDbJavaSourceFileActionPerformed(String selectedPathFs)
	{
		if(selectedPathFs != null)
		{
			// Open Java source file
			javaSourceFileService.loadJavaSourceFileFromDb(selectedPathFs);
			
			// Show Java source file version management view
			mainFrame.showCard("JAVASOURCEFILEVIEWERPANEL");
		}
	}
	
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
