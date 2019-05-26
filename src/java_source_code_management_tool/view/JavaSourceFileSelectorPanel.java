package java_source_code_management_tool.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java_source_code_management_tool.controller.JavaSourceFileController;
import java_source_code_management_tool.controller.NavigationController;
import java_source_code_management_tool.model.service.JavaSourceFileService;

/**
 * This class consists of view methods related to the display of the Java source file selector panel following the Model-View-Controller pattern.
 * 
 * @author Jordan and Yanis (Group 4 - Pair 10)
 *
 */
public class JavaSourceFileSelectorPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JButton buttonSelectFromDb;
	private JButton buttonSelectFromFs;
	private JButton buttonCancel;
	private JScrollPane scrollPane;
	private JList<String> listDbJavaSourceFiles;
	private DefaultListModel<String> listModel;
	private JPanel dbSelectionPanel;
	private JPanel fsSelectionPanel;
	private GridBagConstraints gbc;
	private JavaSourceFileController javaSourceFileController;
	private NavigationController navigationController;
	
	/**
	 * Constructs a new Java source file selector panel with the specified Java source file controller, navigation controller and Java source file service.
	 * 
	 * @param javaSourceFileController the Java source file controller.
	 * @param navigationController the navigation controller.
	 * @param javaSourceFileService the Java source file service acting as a model.
	 */
	public JavaSourceFileSelectorPanel(JavaSourceFileController javaSourceFileController, NavigationController navigationController, JavaSourceFileService javaSourceFileService)
	{
		// Set controller
		this.javaSourceFileController = javaSourceFileController;
		this.navigationController = navigationController;
		
		// Configure this JPanel
		this.setLayout(new GridBagLayout());
		
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(4, 4, 4, 4);
		
		// Configure JPanels
		fsSelectionPanel = new JPanel();
		fsSelectionPanel.setLayout(new BoxLayout(fsSelectionPanel, BoxLayout.PAGE_AXIS));
		fsSelectionPanel.setBorder(new CompoundBorder(new TitledBorder("This PC"), new EmptyBorder(8, 0, 0, 0)));
		
		dbSelectionPanel = new JPanel();
		dbSelectionPanel.setLayout(new BoxLayout(dbSelectionPanel, BoxLayout.PAGE_AXIS));
		dbSelectionPanel.setBorder(new CompoundBorder(new TitledBorder("Database server"), new EmptyBorder(8, 0, 0, 0)));
		
		// Create and configure components
		listModel = new DefaultListModel<String>();
		
		listDbJavaSourceFiles = new JList<String>(listModel);
		listDbJavaSourceFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane = new JScrollPane(listDbJavaSourceFiles);
		
		buttonSelectFromDb = new JButton("Select remote Java source file");
		buttonSelectFromDb.setAlignmentX(CENTER_ALIGNMENT);
		buttonSelectFromDb.addActionListener(this);
		
		buttonSelectFromFs = new JButton("Select local Java source file");
		buttonSelectFromFs.setAlignmentX(CENTER_ALIGNMENT);
		buttonSelectFromFs.addActionListener(this);
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(this);

		// Attach components to JPanels
		dbSelectionPanel.add(scrollPane);
		dbSelectionPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		dbSelectionPanel.add(buttonSelectFromDb);
		
		fsSelectionPanel.add(Box.createVerticalGlue());
		fsSelectionPanel.add(buttonSelectFromFs);
		fsSelectionPanel.add(Box.createVerticalGlue());
		
		// Attach JPanels to this JPanel
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weightx = 0.3;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(fsSelectionPanel, gbc);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weightx = 0.7;
		gbc.weighty = 1.0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(dbSelectionPanel, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_END;
		gbc.weighty = 0.0;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(buttonCancel, gbc);		
	}
	
	/**
	 * Tells the controllers when an action occurs on the view.
	 */
	public void actionPerformed(ActionEvent ae)
	{		
		try
		{
			if(ae.getSource() == buttonSelectFromDb)
			{
				javaSourceFileController.openDbJavaSourceFileAndDisplayContentActionPerformed(listDbJavaSourceFiles.getSelectedValue());
			}
			else if(ae.getSource() == buttonSelectFromFs)
			{
				javaSourceFileController.openFsJavaSourceFileAndDisplayContentActionPerformed();
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
	
	/**
	 * Displays the specified list of database Java source files.
	 * 
	 * @param javaSourceFilePathsFs the list of Java source files saved on the database.
	 */
	public void showListDbJavaSourceFiles(ArrayList<String> javaSourceFilePathsFs)
	{
		// Clear list
		listModel.removeAllElements();
		
		// Fill list
		for(int i=0; i<javaSourceFilePathsFs.size(); i++)
			listModel.addElement(javaSourceFilePathsFs.get(i));
	}
}
