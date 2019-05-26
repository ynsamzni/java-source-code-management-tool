package java_source_code_management_tool.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java_source_code_management_tool.controller.NavigationController;

/**
 * This class consists of view methods related to the display of the user management panel following the Model-View-Controller pattern.
 * 
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class UserManagementPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JButton buttonCreateUser;
	private JButton buttonDeleteUser;
	private JButton buttonUserHistory;
	private JButton buttonHome;
	private JLabel labelTitle;
	private NavigationController navigationController;
	
	/**
	 * Constructs a new user management panel with the specified navigation controller.
	 * 
	 * @param navigationController the navigation controller.
	 */
	public UserManagementPanel(NavigationController navigationController)
	{
		// Set controllers
		this.navigationController = navigationController;
		
		// Configure JPanel
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Create and configure components
		labelTitle = new JLabel("MANAGE USERS");
		labelTitle.setFont(labelTitle.getFont().deriveFont(32.0f));
		labelTitle.setAlignmentX(CENTER_ALIGNMENT);
		
		buttonCreateUser = new JButton("Create a new user");
		buttonCreateUser.setAlignmentX(CENTER_ALIGNMENT);
		buttonCreateUser.addActionListener(this);
		
		buttonDeleteUser = new JButton("Delete an existing user");
		buttonDeleteUser.setAlignmentX(CENTER_ALIGNMENT);
		buttonDeleteUser.addActionListener(this);
		
		buttonUserHistory = new JButton("Display an existing user history");
		buttonUserHistory.setAlignmentX(CENTER_ALIGNMENT);
		buttonUserHistory.addActionListener(this);
		
		buttonHome = new JButton("Return to home page");
		buttonHome.setAlignmentX(CENTER_ALIGNMENT);
		buttonHome.addActionListener(this);

		// Attach components to JPanel
		this.add(Box.createVerticalGlue());
		this.add(labelTitle);
		this.add(Box.createRigidArea(new Dimension(0, 60)));
		this.add(buttonCreateUser);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(buttonDeleteUser);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(buttonUserHistory);
		this.add(Box.createRigidArea(new Dimension(0, 70)));
		this.add(buttonHome);
		this.add(Box.createVerticalGlue());			
	}
	
	/**
	 * Tells the controllers when an action occurs on the view.
	 */
	public void actionPerformed(ActionEvent ae)
	{		
		try
		{
			if(ae.getSource() == buttonHome)
			{
				navigationController.goHomeActionPerformed();
			}
			else if(ae.getSource() == buttonCreateUser)
			{
				navigationController.goUserCreationActionPerformed();
			}
			else if(ae.getSource() == buttonDeleteUser)
			{
				navigationController.goUserDeletionActionPerformed();
			}
			else if(ae.getSource() == buttonUserHistory)
			{
				navigationController.goUserHistoryActionPerformed();
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
