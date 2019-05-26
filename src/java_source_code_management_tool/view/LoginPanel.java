package java_source_code_management_tool.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java_source_code_management_tool.controller.LoginController;

/**
 * This class consists of view methods related to the display of the login panel following the Model-View-Controller pattern.
 * 
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class LoginPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JTextField textFieldUsername;
	private JButton buttonLogin;
	private JPasswordField passwordField;
	private JLabel labelUsername;
	private JLabel labelPassword;
	private LoginController loginController;
	
	/**
	 * Constructs a new login panel with the specified login controller.
	 * 
	 * @param loginController the login controller.
	 */
	public LoginPanel(LoginController loginController)
	{
		// Set controller
		this.loginController = loginController;
		
		// Configure JPanel
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
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
		
		buttonLogin = new JButton("Login");
		buttonLogin.setAlignmentX(CENTER_ALIGNMENT);
		buttonLogin.addActionListener(this);

		// Attach components to JPanel
		this.add(Box.createVerticalGlue());
		this.add(labelUsername);
		this.add(textFieldUsername);
		this.add(Box.createRigidArea(new Dimension(0, 15)));
		this.add(labelPassword);
		this.add(passwordField);
		this.add(Box.createRigidArea(new Dimension(0, 60)));
		this.add(buttonLogin);
		this.add(Box.createVerticalGlue());
	}
	
	/**
	 * Tells the controllers when an action occurs on the view.
	 */
	public void actionPerformed(ActionEvent ae)
	{	
		try
		{
			if(ae.getSource() == buttonLogin)
			{
				loginController.loginActionPerformed(textFieldUsername.getText(), passwordField.getPassword());
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Clears the username text field.
	 */
	public void clearUsernameField()
	{
		textFieldUsername.setText("");
	}
	
	/**
	 * Clears the password field.
	 */
	public void clearPasswordField()
	{
		passwordField.setText("");
	}
}
