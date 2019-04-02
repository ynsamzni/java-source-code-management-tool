package java_source_code_management_tool;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class Test
{
	public static void main(String[] args)
	{
		// Create the user
		User user = new User("Yanis", "passw0rd", 1);
		
		// Create a file
		JavaSourceFile file = new JavaSourceFile("/home/yanis/Documents/test.txt");
		user.setJavaSourceFile(file);
		user.getJavaSourceFile().setContentFromPathFs();
		
		// Create a file version
		Version version = new Version("1.0.0", user.getUsername());
		user.getJavaSourceFile().addVersion(version);
				
		// Create file version description
		Description description = new Description("Fix probleme 1");
		user.getJavaSourceFile().getVersion(0).addDescription(description);
		
		// Test user
		System.out.println("User username: " + user.getUsername());
		System.out.println("User password: " + user.getPassword());
		System.out.println("User access level: " + user.getAccessLevel());
		
		// Test file
		System.out.println("File path: " + user.getJavaSourceFile().getPathFs());
		System.out.println("File content: " + user.getJavaSourceFile().getContent());
		
		// Test file version
		System.out.println("Version number: " + user.getJavaSourceFile().getVersion(0).getVersion());
		System.out.println("Version author: " + user.getJavaSourceFile().getVersion(0).getAuthor());
		System.out.println("Version date: " + user.getJavaSourceFile().getVersion(0).getDate());
		
		// Test file version description
		System.out.println("Version description: " + user.getJavaSourceFile().getVersion(0).getDescription(0).getDescription());
	}
}
