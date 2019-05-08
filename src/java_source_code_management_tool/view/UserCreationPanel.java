package java_source_code_management_tool.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java_source_code_management_tool.controller.NavigationController;
import java_source_code_management_tool.controller.UserController;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class UserCreationPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JLabel labelUsername;
	private JTextField textFieldUsername;
	private JLabel labelPassword;
	private JPasswordField passwordField;
	private JButton buttonCreateUser;
	private JCheckBox checkBoxIsAdmin;
	private JButton buttonCancel;
	private UserController userController;
	private NavigationController navigationController;
	private JPanel newUserPanel;

	public UserCreationPanel(NavigationController navigationController, UserController userController)
	{
		// Set controllers
		this.userController = userController;
		this.navigationController = navigationController;
		
		// Configure this JPanel
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Create and configure JPanels
		newUserPanel = new JPanel();
		newUserPanel.setLayout(new BoxLayout(newUserPanel, BoxLayout.PAGE_AXIS));
		newUserPanel.setBorder(new CompoundBorder(new TitledBorder("New user"), new EmptyBorder(40, 40, 40, 40)));
		
		// Create and configure components
		labelUsername = new JLabel("Username:");
		labelUsername.setAlignmentX(CENTER_ALIGNMENT);
		
		textFieldUsername = new JTextField(10);
		textFieldUsername.setAlignmentX(CENTER_ALIGNMENT);
		textFieldUsername.setMaximumSize(textFieldUsername.getPreferredSize());
		
		labelPassword = new JLabel("Password:");
		labelPassword.setAlignmentX(CENTER_ALIGNMENT);
		
		passwordField = new JPasswordField(10);
		passwordField.setAlignmentX(CENTER_ALIGNMENT);
		passwordField.setMaximumSize(passwordField.getPreferredSize());
		
		checkBoxIsAdmin = new JCheckBox("Administrator");
		checkBoxIsAdmin.setAlignmentX(CENTER_ALIGNMENT);
		
		buttonCreateUser = new JButton("Create user");
		buttonCreateUser.setAlignmentX(CENTER_ALIGNMENT);
		buttonCreateUser.addActionListener(this);
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.setAlignmentX(CENTER_ALIGNMENT);
		buttonCancel.addActionListener(this);

		// Attach components to JPanels
		newUserPanel.add(labelUsername);
		newUserPanel.add(textFieldUsername);
		newUserPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		newUserPanel.add(labelPassword);
		newUserPanel.add(passwordField);
		newUserPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		newUserPanel.add(checkBoxIsAdmin);
		newUserPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		newUserPanel.add(buttonCreateUser);
		
		// Attach components to this JPanel
		this.add(Box.createVerticalGlue());
		this.add(newUserPanel);
		this.add(Box.createRigidArea(new Dimension(0, 30)));
		this.add(buttonCancel);
		this.add(Box.createVerticalGlue());
	}
	
	public void actionPerformed(ActionEvent ae)
	{		
		try
		{
			if(ae.getSource() == buttonCreateUser)
			{
				userController.createUserActionPerformed(textFieldUsername.getText(), passwordField.getPassword(), checkBoxIsAdmin.isSelected());
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
	
	public void clear()
	{
		// Clear text fields
		textFieldUsername.setText("");
		clearPasswordField();
		
		// Set checkbox default state
		checkBoxIsAdmin.setSelected(false);
	}
	
	public void clearPasswordField()
	{
		passwordField.setText("");
	}
}
