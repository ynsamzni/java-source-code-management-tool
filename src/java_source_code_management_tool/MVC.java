package java_source_code_management_tool;

import java_source_code_management_tool.controller.JavaSourceFileController;
import java_source_code_management_tool.controller.LoginController;
import java_source_code_management_tool.controller.NavigationController;
import java_source_code_management_tool.service.JavaSourceFileService;
import java_source_code_management_tool.service.UserService;
import java_source_code_management_tool.view.MainFrame;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class MVC
{
	public static void main(String[] args)
	{
		// Instantiate models
		JavaSourceFileService javaSourceFileService = new JavaSourceFileService();
		UserService userService = new UserService();
		
		// Instantiate controllers
		JavaSourceFileController javaSourceFileController = new JavaSourceFileController(javaSourceFileService, userService);
		LoginController loginController = new LoginController(userService);
		NavigationController viewController = new NavigationController(userService);
		
		// Instantiate view
		MainFrame view = new MainFrame(javaSourceFileService, javaSourceFileController, loginController, viewController);	
		
		// Add views to controllers
		loginController.setView(view);
		viewController.setView(view);
		javaSourceFileController.setView(view);
	}
}
