package java_source_code_management_tool.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import java_source_code_management_tool.controller.NavigationController;
import java_source_code_management_tool.model.dto.JavaSourceFile;
import java_source_code_management_tool.model.service.JavaSourceFileService;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class JavaSourceFileViewerPanel extends JPanel implements ActionListener, PropertyChangeListener
{
	private static final long serialVersionUID = 1L;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	private NavigationController navigationController;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem menuItemHome;
	private JMenuItem menuItemOpen;
	private JMenuItem menuItemVersions;
	private JMenu menuDisplay;
	private JCheckBoxMenuItem checkBoxMenuItemDeleteComments;
	private JCheckBoxMenuItem checkBoxMenuItemIndent;
	
	public JavaSourceFileViewerPanel(JavaSourceFileService javaSourceFileService, NavigationController navigationController)
	{
		// Set controller
		this.navigationController = navigationController;
		
		// Add model listener
		javaSourceFileService.addPropertyChangeListener(this);
		
		// Configure JPanel
		this.setLayout(new BorderLayout());
		
		// Create and configure components
		textPane = new JTextPane()
		{
			private static final long serialVersionUID = 1L;
			
			// Disable text wrap
			@Override
			public boolean getScrollableTracksViewportWidth()
			{
				return getUI().getPreferredSize(this).width 
						<= getParent().getSize().width;
			}
		};
		textPane.setEditable(false);
		
		scrollPane = new JScrollPane(textPane);
		
		menuBar = new JMenuBar();
		
		menuFile = new JMenu("File");
		menuDisplay = new JMenu("Display");
		
		menuItemOpen = new JMenuItem("Open...");
		menuItemOpen.addActionListener(this);
		
		menuItemVersions = new JMenuItem("Manage versions");
		menuItemVersions.addActionListener(this);
		
		menuItemHome = new JMenuItem("Return to home page");
		menuItemHome.addActionListener(this);
		
		checkBoxMenuItemDeleteComments = new JCheckBoxMenuItem("Hide comments");
		checkBoxMenuItemDeleteComments.addActionListener(this);
		
		checkBoxMenuItemIndent = new JCheckBoxMenuItem("Indent code");
		checkBoxMenuItemIndent.addActionListener(this);

		// Attach components to components
		menuBar.add(menuFile);
		menuBar.add(menuDisplay);
		
		menuFile.add(menuItemOpen);
		menuFile.add(menuItemVersions);
		menuFile.add(menuItemHome);
		
		menuDisplay.add(checkBoxMenuItemDeleteComments);
		menuDisplay.add(checkBoxMenuItemIndent);
		
		// Attach components to JPanel
		this.add(menuBar, BorderLayout.PAGE_START);
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent ae)
	{		
		try
		{
			if(ae.getSource() == menuItemOpen)
			{
				navigationController.goJavaSourceFileSelectorActionPerformed();
			}
			else if(ae.getSource() == menuItemVersions)
			{
				navigationController.goVersionManagementActionPerformed();
			}
			else if(ae.getSource() == menuItemHome)
			{
				navigationController.goHomeActionPerformed();
			}
			else if(ae.getSource() == checkBoxMenuItemDeleteComments)
			{
				navigationController.toggleCommentDeletionActionPerformed(checkBoxMenuItemDeleteComments.isSelected(), textPane.getText());
			}
			else if(ae.getSource() == checkBoxMenuItemIndent)
			{
				navigationController.toggleIndentActionPerformed(checkBoxMenuItemIndent.isSelected(), textPane.getText());
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void propertyChange(PropertyChangeEvent pce)
	{
		if(pce.getPropertyName().equals("NEWJAVASOURCEFILE"))
		{			
			// Display new Java source file content
			setDisplayedContent(((JavaSourceFile) pce.getNewValue()).getContent());
			
			// Reset user selected options
			resetMenuItemsCheckBoxes();
		}
	}
	
	public void setDisplayedContent(String content)
	{
		// Display new Java source file content
		textPane.setText(content);
		
		// Scroll to the top
		textPane.setCaretPosition(0);
	}
	
	public void resetMenuItemsCheckBoxes()
	{
		checkBoxMenuItemDeleteComments.setSelected(false);
		checkBoxMenuItemIndent.setSelected(false);
	}
	
	public boolean commentDeletionIsActive()
	{
		return checkBoxMenuItemDeleteComments.isSelected();
	}
	
	public boolean indentationIsActive()
	{
		return checkBoxMenuItemIndent.isSelected();
	}
}
