package java_source_code_management_tool.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import java_source_code_management_tool.controller.JavaSourceFileController;
import java_source_code_management_tool.controller.NavigationController;
import java_source_code_management_tool.model.dto.User;
import java_source_code_management_tool.model.service.UserService;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class HomePanel extends JPanel implements ActionListener, PropertyChangeListener
{
	private static final long serialVersionUID = 1L;
	private JButton buttonDisplayJavaSourceFile;
	private JButton buttonManageVersions;
	private JButton buttonManageUsers;
	private JavaSourceFileController javaSourceFileController;
	private NavigationController navigationController;
	
	public HomePanel(JavaSourceFileController javaSourceFileController, NavigationController navigationController, UserService userService)
	{
		// Set controllers
		this.javaSourceFileController = javaSourceFileController;
		this.navigationController = navigationController;
		
		// Add model listener
		userService.addPropertyChangeListener(this);
		
		// Configure JPanel
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Create and configure components
		buttonDisplayJavaSourceFile = new JButton("Display a Java source file");
		buttonDisplayJavaSourceFile.setAlignmentX(CENTER_ALIGNMENT);
		buttonDisplayJavaSourceFile.addActionListener(this);
		
		buttonManageVersions = new JButton("Manage Java source file versions");
		buttonManageVersions.setAlignmentX(CENTER_ALIGNMENT);
		buttonManageVersions.addActionListener(this);
		
		buttonManageUsers = new JButton("Manage users");
		buttonManageUsers.setAlignmentX(CENTER_ALIGNMENT);
		buttonManageUsers.addActionListener(this);

		// Attach components to JPanel
		this.add(Box.createVerticalGlue());
		this.add(buttonDisplayJavaSourceFile);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(buttonManageVersions);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(buttonManageUsers);
		this.add(Box.createVerticalGlue());			
	}
	
	public void actionPerformed(ActionEvent ae)
	{		
		try
		{
			if(ae.getSource() == buttonManageVersions)
			{
				javaSourceFileController.manageVersionsActionPerformed();
			}
			else if(ae.getSource() == buttonDisplayJavaSourceFile)
			{
				navigationController.goJavaSourceFileSelectorActionPerformed();
			}
			else if(ae.getSource() == buttonManageUsers)
			{
				navigationController.goUserManagementActionPerformed();
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void propertyChange(PropertyChangeEvent pce)
	{
		if(pce.getPropertyName().equals("NEWLOGGEDINUSER"))
		{
			// Display user accessible areas
			if(((User) pce.getNewValue()).getAccessLevel() == 0)
				hideAdminArea();
			else
				showAdminArea();
		}
	}
	
	public void showAdminArea()
	{
		buttonManageUsers.setVisible(true);
	}
	
	public void hideAdminArea()
	{
		buttonManageUsers.setVisible(false);
	}
}
