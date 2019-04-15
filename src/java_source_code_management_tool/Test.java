package java_source_code_management_tool;

import java.util.ArrayList;

import java_source_code_management_tool.model.Description;
import java_source_code_management_tool.model.JavaSourceFile;
import java_source_code_management_tool.model.User;
import java_source_code_management_tool.model.Version;
import java_source_code_management_tool.service.JavaSourceFileService;
import java_source_code_management_tool.service.UserService;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class Test
{
	private static String pathFsFileTest = "/home/yanis/Documents/test.txt";
	private static String userUsernameTest = "Yanis";
	private static String userPasswordTest = "passw0rd";
	private static JavaSourceFileService javaSourceFileService;
	private static UserService userService;
	
	
	public static void main(String[] args)
	{
		javaSourceFileService = new JavaSourceFileService();
		userService = new UserService();
		
		// Create test data
		User user = createTestData();
		
		// Test database
		testWriteDb(user);
		displayUserData(testReadDb());
		
		// Test local
		//displayUserData(user);
		
	}
	
	public static void displayUserData(User user)
	{	
		ArrayList<Version> versions;
		ArrayList<Description> descriptions;
		
		// Test user
		System.out.println("USER => " + user.toStringForDisplay());
		
		// Test file
		System.out.println("FILE => " + user.getJavaSourceFile().toStringForDisplay());
		
		// Test file versions
		versions = user.getJavaSourceFile().getListVersions();
		for(int i=0; i<versions.size(); i++)
		{
			System.out.println("VERSION (" + i + ") => " + user.getJavaSourceFile().getVersion(i).toStringForDisplay());
			
			// Test file version descriptions
			descriptions = user.getJavaSourceFile().getVersion(i).getListDescriptions();
			for(int j=0; j<descriptions.size(); j++)
				System.out.println("| DESCRIPTION (" + j + ") => " + user.getJavaSourceFile().getVersion(i).getDescription(j).toStringForDisplay());
		}
	}
	
	public static void testWriteDb(User user)
	{		
		// Create user on database
		userService.addUser(user);
		
		// Create file on database
		javaSourceFileService.saveJavaSourceFileWithNewVersionAndDescriptions(user.getJavaSourceFile(), user.getJavaSourceFile().getVersion(0));
	}
	
	public static User testReadDb()
	{
		User user;
		
		// Get file from database
		user = userService.getUser(userUsernameTest, userPasswordTest);
		user.setJavaSourceFile(javaSourceFileService.getJavaSourceFileWithVersionsAndDescriptions(pathFsFileTest));
		
		return user;
	}
	
	public static User createTestData()
	{
		JavaSourceFile javaSourceFile;
		User user;
		Version version;
		ArrayList<Description> descriptions = new ArrayList<Description>();;
		
		// Create user
		user = new User(userUsernameTest, userPasswordTest, 1);
		
		// Create file
		javaSourceFile = new JavaSourceFile(pathFsFileTest);
		javaSourceFile.setContentFromPathFs();
		
		// Create version
		version = new Version("1.2.0", user.getUsername());
		
		// Create descriptions
		descriptions.add(new Description("Fix probleme 1"));
		descriptions.add(new Description("Fix probleme 2"));
		
		// Link data
		version.setListDescriptions(descriptions);
		javaSourceFile.addVersion(version);
		user.setJavaSourceFile(javaSourceFile);
		
		return user;
	}
}
