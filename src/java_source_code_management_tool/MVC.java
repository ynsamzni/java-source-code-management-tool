package java_source_code_management_tool;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java_source_code_management_tool.controller.JavaSourceFileController;
import java_source_code_management_tool.controller.LoginController;
import java_source_code_management_tool.controller.NavigationController;
import java_source_code_management_tool.controller.UserController;
import java_source_code_management_tool.model.service.JavaSourceFileService;
import java_source_code_management_tool.model.service.UserService;
import java_source_code_management_tool.view.MainFrame;

/**
 * This class instantiates models, views and controllers following the MVC pattern.
 * 
 * @author Jordan and Yanis (Group 4 - Pair 10)
 *
 */
public class MVC
{
	/**
	 * Main method to run on program start.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		// Instantiate models
		JavaSourceFileService javaSourceFileService = new JavaSourceFileService();
		UserService userService = new UserService();
		
		// Instantiate controllers
		JavaSourceFileController javaSourceFileController = new JavaSourceFileController(javaSourceFileService, userService);
		LoginController loginController = new LoginController(userService);
		NavigationController navigationController = new NavigationController(userService, javaSourceFileService);
		UserController userController = new UserController(userService);
		
		// Set system look and feel in views
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
		{
			ex.printStackTrace();
		}
		
		// Instantiate view
		MainFrame view = new MainFrame(javaSourceFileService, javaSourceFileController, loginController, navigationController, userService, userController);
		
		// Add views to controllers
		loginController.setView(view);
		navigationController.setView(view);
		javaSourceFileController.setView(view);
		userController.setView(view);
	}
}
