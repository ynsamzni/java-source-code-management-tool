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
 * This class consists of view methods related to the display of the Java source file viewer panel following the Model-View-Controller pattern.
 * 
 * @author Jordan and Yanis (Group 4 - Pair 10)
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
	private JCheckBoxMenuItem checkBoxMenuItemDeleteJavadoc;
	private JCheckBoxMenuItem checkBoxMenuItemIndent;
	
	/**
	 * Constructs a new Java source file viewer panel with the specified Java source file controller and navigation controller.
	 * 
	 * @param javaSourceFileService the Java source file service acting as a model.
	 * @param navigationController the navigation controller.
	 */
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
		
		checkBoxMenuItemDeleteJavadoc = new JCheckBoxMenuItem("Hide Javadoc");
		checkBoxMenuItemDeleteJavadoc.addActionListener(this);
		
		checkBoxMenuItemIndent = new JCheckBoxMenuItem("Indent code");
		checkBoxMenuItemIndent.addActionListener(this);

		// Attach components to components
		menuBar.add(menuFile);
		menuBar.add(menuDisplay);
		
		menuFile.add(menuItemOpen);
		menuFile.add(menuItemVersions);
		menuFile.add(menuItemHome);
		
		menuDisplay.add(checkBoxMenuItemDeleteComments);
		menuDisplay.add(checkBoxMenuItemDeleteJavadoc);
		menuDisplay.add(checkBoxMenuItemIndent);
		
		// Attach components to JPanel
		this.add(menuBar, BorderLayout.PAGE_START);
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * Tells the controllers when an action occurs on the view.
	 */
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
			else if(ae.getSource() == checkBoxMenuItemDeleteJavadoc)
			{
				navigationController.toggleJavadocDeletionActionPerformed(checkBoxMenuItemDeleteJavadoc.isSelected(), textPane.getText());
			}
			else if(ae.getSource() == checkBoxMenuItemIndent)
			{
				navigationController.toggleIndentationActionPerformed(checkBoxMenuItemIndent.isSelected(), textPane.getText());
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the view when a change has been notified by the model.
	 */
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
	
	/**
	 * Displays specified Java source file content.
	 * 
	 * @param content the Java source file content to display.
	 */
	public void setDisplayedContent(String content)
	{
		// Display new Java source file content
		textPane.setText(content);
		
		// Scroll to the top
		textPane.setCaretPosition(0);
	}
	
	/**
	 * Sets all menu toggles to their default state.
	 */
	public void resetMenuItemsCheckBoxes()
	{
		checkBoxMenuItemDeleteComments.setSelected(false);
		checkBoxMenuItemIndent.setSelected(false);
	}
	
	/**
	 * Returns the comment deletion toggle state.
	 * 
	 * @return the comment deletion toggle state.
	 */
	public boolean commentDeletionIsActive()
	{
		return checkBoxMenuItemDeleteComments.isSelected();
	}
	
	/**
	 * Returns the Javadoc deletion toggle state.
	 * 
	 * @return the Javadoc deletion toggle state.
	 */
	public boolean javadocDeletionIsActive()
	{
		return checkBoxMenuItemDeleteJavadoc.isSelected();
	}
	
	/**
	 * Returns the indentation toggle state.
	 * 
	 * @return the indentation toggle state.
	 */
	public boolean indentationIsActive()
	{
		return checkBoxMenuItemIndent.isSelected();
	}
}
