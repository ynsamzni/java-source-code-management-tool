package java_source_code_management_tool.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import java_source_code_management_tool.controller.JavaSourceFileController;
import java_source_code_management_tool.controller.NavigationController;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class HomePanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JButton buttonDisplayJavaSourceFile;
	private JButton buttonManageVersions;
	private JButton buttonManageUsers;
	private JavaSourceFileController javaSourceFileController;
	private NavigationController navigationController;
	
	public HomePanel(JavaSourceFileController javaSourceFileController, NavigationController navigationController)
	{
		// Set controllers
		this.javaSourceFileController = javaSourceFileController;
		this.navigationController = navigationController;
		
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
	
	public void showAdminArea()
	{
		buttonManageUsers.setVisible(true);
	}
	
	public void hideAdminArea()
	{
		buttonManageUsers.setVisible(false);
	}
}
