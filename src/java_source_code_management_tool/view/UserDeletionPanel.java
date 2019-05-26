package java_source_code_management_tool.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
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

import java_source_code_management_tool.controller.NavigationController;
import java_source_code_management_tool.controller.UserController;
import java_source_code_management_tool.model.service.UserService;

/**
 * This class consists of view methods related to the display of the user deletion panel following the Model-View-Controller pattern.
 * 
 * @author Jordan and Yanis (Group 4 - Pair 10)
 *
 */
public class UserDeletionPanel extends JPanel implements ActionListener, PropertyChangeListener
{
	private static final long serialVersionUID = 1L;
	private JButton buttonDeleteUser;
	private JButton buttonCancel;
	private UserController userController;
	private NavigationController navigationController;
	private JPanel dbUserSelectionPanel;
	private DefaultListModel<String> listModel;
	private JList<String> listDbUsers;
	private JScrollPane scrollPane;

	/**
	 * Constructs a new user deletion panel with the specified navigation controller, user controller and user service.
	 * @param navigationController the navigation controller.
	 * @param userController the user controller.
	 * @param userService the user service acting as a model.
	 */
	public UserDeletionPanel(NavigationController navigationController, UserController userController, UserService userService)
	{
		// Set controllers
		this.userController = userController;
		this.navigationController = navigationController;
		
		// Add model listener
		userService.addPropertyChangeListener(this);
		
		// Configure this JPanel
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0,100,0,100));
		
		// Create and configure JPanels
		dbUserSelectionPanel = new JPanel();
		dbUserSelectionPanel.setLayout(new BoxLayout(dbUserSelectionPanel, BoxLayout.PAGE_AXIS));
		dbUserSelectionPanel.setBorder(new CompoundBorder(new TitledBorder("Existing users"), new EmptyBorder(40, 20, 40, 20)));
		
		// Create and configure components
		listModel = new DefaultListModel<String>();
		
		listDbUsers = new JList<String>(listModel);
		listDbUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane = new JScrollPane(listDbUsers);
		
		buttonDeleteUser = new JButton("Delete user");
		buttonDeleteUser.setAlignmentX(CENTER_ALIGNMENT);
		buttonDeleteUser.addActionListener(this);
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.setAlignmentX(CENTER_ALIGNMENT);
		buttonCancel.addActionListener(this);

		// Attach components to JPanels
		dbUserSelectionPanel.add(scrollPane);
		dbUserSelectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		dbUserSelectionPanel.add(buttonDeleteUser);
		
		// Attach components to this JPanel
		this.add(Box.createVerticalGlue());
		this.add(dbUserSelectionPanel);
		this.add(Box.createRigidArea(new Dimension(0, 30)));
		this.add(buttonCancel);
		this.add(Box.createVerticalGlue());
	}
	
	/**
	 * Tells the controllers when an action occurs on the view.
	 */
	public void actionPerformed(ActionEvent ae)
	{		
		try
		{
			if(ae.getSource() == buttonDeleteUser)
			{
				userController.deleteUserActionPerformed(listDbUsers.getSelectedValue());
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
	 * Updates the view when a change has been notified by the model.
	 */
	public void propertyChange(PropertyChangeEvent pce)
	{
		if(pce.getPropertyName().equals("DELETEDUSER"))
		{
			// Remove deleted user from list
			listModel.removeElement(pce.getOldValue());
		}
	}
	
	/**
	 * Displays the specified list of database user usernames.
	 * 
	 * @param userUsernames the list of user usernames saved on the database.
	 */
	public void showListDbUserUsernames(ArrayList<String> userUsernames)
	{
		// Clear list
		listModel.removeAllElements();
		
		// Fill list
		for(int i=0; i<userUsernames.size(); i++)
			listModel.addElement(userUsernames.get(i));
	}
}
