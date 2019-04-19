package java_source_code_management_tool.controller;

import java.util.ArrayList;

import java_source_code_management_tool.model.Description;
import java_source_code_management_tool.model.Version;
import java_source_code_management_tool.service.JavaSourceFileService;
import java_source_code_management_tool.service.UserService;
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
	
	public void manageVersionActionPerformed()
	{
		String selectedPathFs;
		
		// Show file dialog
		selectedPathFs = mainFrame.showFileDialog();
		
		// If a file has been selected
		if(selectedPathFs != null)
		{
			// Open Java source file
			javaSourceFileService.loadJavaSourceFile(selectedPathFs);
			
			// Show Java source file version management view
			mainFrame.showCard("VERSIONMANAGEMENTPANEL");
		}
	}
	
	public void addVersionActionPerformed(String versionNumber, ArrayList<String> strDescriptions)
	{
		Version version;
		ArrayList<Description> descriptions = new ArrayList<Description>();
		
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
	}
}
