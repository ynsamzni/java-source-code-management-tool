package java_source_code_management_tool.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java_source_code_management_tool.controller.JavaSourceFileController;
import java_source_code_management_tool.controller.NavigationController;
import java_source_code_management_tool.model.dto.Description;
import java_source_code_management_tool.model.dto.JavaSourceFile;
import java_source_code_management_tool.model.dto.Version;
import java_source_code_management_tool.model.service.JavaSourceFileService;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class VersionManagementPanel extends JPanel implements ActionListener, PropertyChangeListener
{
	private static final long serialVersionUID = 1L;
	private JavaSourceFileController javaSourceFileController;
	private NavigationController navigationController;
	private ArrayList<JTextField> textFieldsDescription = new ArrayList<JTextField>();
	private JButton buttonAddTextFieldDescription;
	private JButton buttonAddVersion;
	private JButton buttonCancel;
	private JLabel labelDescription;
	private JLabel labelVersionNumber;
	private JPanel newVersionPanel;
	private JPanel newVersionDescriptionPanel;
	private JPanel versionHistoryPanel;
	private JTextField textFieldVersionNumber;
	private JTextArea textAreaVersions;
	private JScrollPane scrollPaneVersions;
	private GridBagConstraints gbc;
	
	public VersionManagementPanel(JavaSourceFileService javaSourceFileService, JavaSourceFileController javaSourceFileController, NavigationController navigationController)
	{
		// Set controllers
		this.javaSourceFileController = javaSourceFileController;
		this.navigationController = navigationController;
		
		// Add model listener
		javaSourceFileService.addPropertyChangeListener(this);
		
		// Configure this JPanel
		this.setLayout(new GridBagLayout());
		
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(4, 4, 4, 4);
		
		// Attach "New version" panel
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(createNewVersionPanel(), gbc);
		
		// Attach "Version history" panel
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(createVersionHistoryPanel(), gbc);
		
		// Attach home button
		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(this);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_END;
		gbc.weighty = 0.0;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(buttonCancel, gbc);
	}
	
	public JPanel createNewVersionPanel()
	{
		// Create and configure JPanels
		newVersionPanel = new JPanel();
		newVersionPanel.setLayout(new BoxLayout(newVersionPanel, BoxLayout.PAGE_AXIS));
		newVersionPanel.setBorder(new CompoundBorder(new TitledBorder("New version"), new EmptyBorder(8, 0, 0, 0)));

		newVersionDescriptionPanel = new JPanel();
		newVersionDescriptionPanel.setLayout(new BoxLayout(newVersionDescriptionPanel, BoxLayout.PAGE_AXIS));
		
		// Create components
		labelVersionNumber = new JLabel("Version number:");
		
		labelDescription = new JLabel("Descriptions:");

		textFieldVersionNumber = new JTextField(20);
		textFieldVersionNumber.setMaximumSize(textFieldVersionNumber.getPreferredSize());
		textFieldVersionNumber.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		buttonAddTextFieldDescription = new JButton("+");
		buttonAddTextFieldDescription.addActionListener(this);
		
		buttonAddVersion = new JButton("Add version");
		buttonAddVersion.addActionListener(this);

		// Attach components to JPanels
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
		
		return newVersionPanel;
	}
	
	public JPanel createVersionHistoryPanel()
	{
		// Create and configure JPanel
		versionHistoryPanel = new JPanel();
		versionHistoryPanel.setLayout(new BorderLayout());
		versionHistoryPanel.setBorder(new CompoundBorder(new TitledBorder("Version history"), new EmptyBorder(8, 0, 0, 0)));
		
		// Create components		
		textAreaVersions = new JTextArea(5, 20);
		textAreaVersions.setEditable(false);
		textAreaVersions.setOpaque(false);
		
		scrollPaneVersions = new JScrollPane(textAreaVersions);
		scrollPaneVersions.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneVersions.setOpaque(false);

		// Attach components to JPanel	
		versionHistoryPanel.add(scrollPaneVersions);
		
		return versionHistoryPanel;
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
			}
			else if(ae.getSource() == buttonAddTextFieldDescription)
			{
				navigationController.addTextFieldDescriptionActionPerformed();
			}
			else if(ae.getSource() == buttonCancel)
			{
				navigationController.goBackActionPerformed();
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
			clearVersionHistoryPanel();
			
			// Display new Java source file version history
			showVersionHistory(((JavaSourceFile) pce.getNewValue()).getListVersions());
		}
	}
	
	public void showVersionHistory(Version version)
	{
		ArrayList<Description> descriptions = version.getListDescriptions();
		
		// Show version
		textAreaVersions.append("Version " + version.getVersion() + ":");
		textAreaVersions.append("\n");
		
		// Show version descriptions
		for(int i=0; i<descriptions.size(); i++)
		{
			textAreaVersions.append("- " + descriptions.get(i).getDescription());
			textAreaVersions.append("\n");
		}
		
		textAreaVersions.append("\n");
	}
	
	public void showVersionHistory(ArrayList<Version> versions)
	{
		for(int i=0; i<versions.size(); i++)
		{
			showVersionHistory(versions.get(i));
		}
	}
	
	public void clearNewVersionPanel()
	{
		// Clear text fields
		textFieldVersionNumber.setText("");
		textFieldsDescription = new ArrayList<JTextField>();
		
		// Clear components added by the user
		newVersionDescriptionPanel.removeAll();
		
		// Refresh panel
		newVersionPanel.revalidate();
		newVersionPanel.repaint();
	}
	
	public void clearVersionHistoryPanel()
	{
		// Clear text area
		textAreaVersions.setText("");
	}
	
	public void addTextFieldDescription()
	{
		JTextField textFieldDescription = new JTextField(20);
		textFieldDescription.setMaximumSize(textFieldDescription.getPreferredSize());
		textFieldDescription.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		// Add new description text field
		newVersionDescriptionPanel.add(textFieldDescription);
		textFieldsDescription.add(textFieldDescription);
		
		// Refresh panel
		newVersionPanel.revalidate();
		newVersionPanel.repaint();
	}
}

