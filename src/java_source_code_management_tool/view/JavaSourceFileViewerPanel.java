package java_source_code_management_tool.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
		
		menuItemOpen = new JMenuItem("Open...");
		menuItemOpen.addActionListener(this);
		
		menuItemHome = new JMenuItem("Return to home page");
		menuItemHome.addActionListener(this);

		// Attach components to components
		menuBar.add(menuFile);
		
		menuFile.add(menuItemOpen);
		menuFile.add(menuItemHome);
		
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
			else if(ae.getSource() == menuItemHome)
			{
				navigationController.goHomeActionPerformed();
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
			textPane.setText(((JavaSourceFile) pce.getNewValue()).getContent());
			
			// Scroll to the top
			textPane.setCaretPosition(0);
		}
	}
}
