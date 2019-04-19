package java_source_code_management_tool.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java_source_code_management_tool.controller.JavaSourceFileController;
import java_source_code_management_tool.controller.NavigationController;
import java_source_code_management_tool.model.Description;
import java_source_code_management_tool.model.JavaSourceFile;
import java_source_code_management_tool.model.Version;
import java_source_code_management_tool.service.JavaSourceFileService;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class VersionManagementPanel extends JPanel implements ActionListener, PropertyChangeListener
{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<JTextField> textFieldsDescription = new ArrayList<JTextField>();
	private JButton buttonAddTextFieldDescription;
	private JButton buttonAddVersion;
	private JButton buttonCancel;
	private JLabel labelAddVersionInstruction;
	private JLabel labelDescription;
	private JLabel labelVersionNumber;
	private JPanel newVersionPanel;
	private JPanel newVersionDescriptionPanel;
	private JPanel versionHistoryPanel;
	private JPanel versionManagementPanel;
	private JTextField textFieldDescription;
	private JTextField textFieldVersionNumber;
	private JavaSourceFileController javaSourceFileController;
	private NavigationController viewController;
	private JLabel labelVersionHistory;
	private JTextArea textAreaVersions;
	private JScrollPane scrollPaneVersions;
	
	public VersionManagementPanel(JavaSourceFileService javaSourceFileService, JavaSourceFileController javaSourceFileController, NavigationController viewController)
	{
		// Set controllers
		this.javaSourceFileController = javaSourceFileController;
		this.viewController = viewController;
		
		// Set model
		javaSourceFileService.addPropertyChangeListener(this);
		
		// Configure JPanel
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Create and configure sub JPanels
		versionManagementPanel = new JPanel();
		versionManagementPanel.setLayout(new BoxLayout(versionManagementPanel, BoxLayout.LINE_AXIS));
		
		versionHistoryPanel = new JPanel();
		versionHistoryPanel.setLayout(new BoxLayout(versionHistoryPanel, BoxLayout.PAGE_AXIS));
		
		newVersionPanel = new JPanel();
		newVersionPanel.setLayout(new BoxLayout(newVersionPanel, BoxLayout.PAGE_AXIS));
		
		newVersionDescriptionPanel = new JPanel();
		newVersionDescriptionPanel.setLayout(new BoxLayout(newVersionDescriptionPanel, BoxLayout.PAGE_AXIS));
		
		// Create labels
		labelAddVersionInstruction = new JLabel("Add a new version");
		labelVersionNumber = new JLabel("Version number:");
		labelDescription = new JLabel("Descriptions:");
		labelVersionHistory = new JLabel("Version history");
		
		// Create text areas
		textAreaVersions = new JTextArea(5,20);
		scrollPaneVersions = new JScrollPane(textAreaVersions);
		textAreaVersions.setEditable(false);

		// Create text fields
		textFieldVersionNumber = new JTextField();
		
		// Create buttons
		buttonAddTextFieldDescription = new JButton("+");
		buttonAddTextFieldDescription.addActionListener(this);
		
		buttonAddVersion = new JButton("Add");
		buttonAddVersion.addActionListener(this);
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(this);

		// Attach components to sub JPanels
		newVersionPanel.add(labelAddVersionInstruction);
		newVersionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		newVersionPanel.add(labelVersionNumber);
		newVersionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		newVersionPanel.add(textFieldVersionNumber);
		newVersionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		newVersionPanel.add(labelDescription);
		newVersionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		newVersionPanel.add(newVersionDescriptionPanel);
		newVersionPanel.add(buttonAddTextFieldDescription);
		newVersionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		newVersionPanel.add(buttonAddVersion);
		
		versionHistoryPanel.add(labelVersionHistory);
		versionHistoryPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		versionHistoryPanel.add(scrollPaneVersions);
		
		versionManagementPanel.add(newVersionPanel);	
		versionManagementPanel.add(versionHistoryPanel);
		
		this.add(versionManagementPanel);
		this.add(buttonCancel);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			if(ae.getSource() == buttonAddVersion)
			{
				// Prepare input data for controller
		    	ArrayList<String> descriptions = new ArrayList<String>();
		    	
		    	for(int i=0; i<textFieldsDescription.size(); i++)
		        	descriptions.add(textFieldsDescription.get(i).getText());
		    	
		    	// Notify controller
		    	javaSourceFileController.addVersionActionPerformed(textFieldVersionNumber.getText(), descriptions);
		    	
		    	// Clear corresponding text fields
		    	textFieldVersionNumber.setText("");
		    	newVersionDescriptionPanel.removeAll();
		    	
		    	// Refresh panel
                newVersionPanel.revalidate();
                newVersionPanel.repaint();
			}
			else if(ae.getSource() == buttonAddTextFieldDescription)
			{
                textFieldDescription = new JTextField();
                
                // Add new description text field
                newVersionDescriptionPanel.add(textFieldDescription);
                textFieldsDescription.add(textFieldDescription);
                
                // Refresh panel
                newVersionPanel.revalidate();
                newVersionPanel.repaint();
			}
			else if(ae.getSource() == buttonCancel)
			{
				viewController.showHomeMenu();
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void propertyChange(PropertyChangeEvent pce)
	{
		if(pce.getPropertyName().equals("NEWVERSION"))
		{
			// Display new version in history area
			showVersionHistory((Version) pce.getNewValue());
		}
		else if(pce.getPropertyName().equals("NEWJAVASOURCEFILE"))
		{
			// Clear history area
			textAreaVersions.setText("");
			
			// Display version history
			ArrayList<Version> versions = ((JavaSourceFile) pce.getNewValue()).getListVersions();
			for(int i=0; i<versions.size(); i++)
			{
				showVersionHistory(versions.get(i));
			}
		}
	}
	
	public void showVersionHistory(Version version)
	{
		ArrayList<Description> descriptions = version.getListDescriptions();
		
		// Show version
		textAreaVersions.append(version.toString());
		textAreaVersions.append("\n");
		
		// Show version descriptions
		for(int i=0; i<descriptions.size(); i++)
		{
			textAreaVersions.append(descriptions.get(i).toString());
			textAreaVersions.append("\n");
		}
	}
}

