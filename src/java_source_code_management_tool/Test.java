package java_source_code_management_tool;

import java.util.ArrayList;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class Test
{
	public static void main(String[] args)
	{
		testLocalData();
	}
	
	public static void testLocalData()
	{
		ArrayList<Version> versions;
		ArrayList<Description> descriptions;
		
		// Create user
		User user = new User("Yanis", "passw0rd", 1);
		
		// Create file
		user.setJavaSourceFile(new JavaSourceFile("/home/yanis/Documents/test.txt"));
		user.getJavaSourceFile().setContentFromPathFs();
		
		// Create file versions
		user.getJavaSourceFile().addVersion(new Version("1.0.0", user.getUsername()));
		user.getJavaSourceFile().addVersion(new Version("1.0.1", user.getUsername()));
				
		// Create file version descriptions
		user.getJavaSourceFile().getVersion(0).addDescription(new Description("Fix probleme 1"));
		user.getJavaSourceFile().getVersion(0).addDescription(new Description("Fix probleme 2"));
		user.getJavaSourceFile().getVersion(1).addDescription(new Description("Fix probleme 3"));
		
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
}
