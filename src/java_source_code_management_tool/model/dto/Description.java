package java_source_code_management_tool.model.dto;

/**
 * This class consists of methods that operate on or return a description.
 * 
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class Description
{
	private String description;
	
	/**
	 * Constructs a new description.
	 */
	public Description()
	{
		
	}
	
	/**
	 * Constructs a new description with the specified description.
	 * 
	 * @param description the description of a version.
	 */
	public Description(String description)
	{
		this.description = description;
	}
	
	/**
	 * Returns the saved description.
	 * 
	 * @return the saved description.
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Sets the description.
	 * 
	 * @param description the description.
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	/**
	 * Translates the object to String for display.
	 * 
	 * @return object translated to String for display.
	 */
	public String toString()
	{
		return "description: " + description;
	}
}
