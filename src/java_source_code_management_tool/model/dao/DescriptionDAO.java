package java_source_code_management_tool.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java_source_code_management_tool.model.dto.Description;
import java_source_code_management_tool.util.DBHelper;

/**
 * This class consists of methods that operate on or return descriptions from the database.
 * .
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class DescriptionDAO
{
	private Connection con = null;
	
	/**
	 * Constructs a new description DAO.
	 * 
	 * @param con the connection to the database.
	 */
	public DescriptionDAO(Connection con)
	{
		this.con = con;
	}
	
	/**
	 * Inserts a list of descriptions for a specified version in the database.
	 * 
	 * @param descriptions the list of descriptions to insert.
	 * @param versionId the database version ID to which the descriptions belong.
	 */
	public void insertListDescriptions(ArrayList<Description> descriptions, Integer versionId)
	{
		PreparedStatement ps = null;
		
		try
		{
			// Prepare the SQL queries		
			ps = con.prepareStatement("INSERT INTO description_descr (descr_description, descr_ver_id) VALUES (?, ?)");
			for(int i=0; i<descriptions.size(); i++)
			{
				ps.setString(1, descriptions.get(i).getDescription());
				ps.setInt(2, versionId);
				ps.addBatch();
			}
			
			// Execute the SQL queries
			ps.executeBatch();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// Close the preparedStatement
			DBHelper.close(ps);
		}
	}
	
	/**
	 * Returns a list of descriptions which are linked to the specified file system Java source file path and version number.
	 * 
	 * @param pathFs the descriptions linked Java source file path on the file system.
	 * @param versionNumber the descriptions linked version number.
	 * @return the list of descriptions.
	 */
	public ArrayList<Description> getListDescriptions(String pathFs, String versionNumber)
	{
		ArrayList<Description> descriptions = new ArrayList<Description>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		// Convert version number for the database
		String[] splitedStrVersionNumber = versionNumber.split(("\\."));
		Integer[] splitedVersionNumber = new Integer[4];

		for(int i=0; i<splitedStrVersionNumber.length; i++)
		{
			splitedVersionNumber[i] = Integer.parseInt(splitedStrVersionNumber[i]);
		}
		
		try
		{			
			// Prepare the SQL query
			ps = con.prepareStatement("SELECT descr_description FROM description_descr JOIN version_ver ON (ver_id = descr_ver_id) JOIN javasourcefile_jsf ON (ver_jsf_id = jsf_id) WHERE (ver_major_number = ? OR ver_major_number IS NULL) AND (ver_minor_number = ? OR ver_minor_number IS NULL) AND (ver_revision_number = ? OR ver_revision_number IS NULL) AND (ver_build_number = ? OR ver_build_number IS NULL) AND jsf_path_fs=?");
			ps.setObject(1, splitedVersionNumber[0], java.sql.Types.INTEGER);
			ps.setObject(2, splitedVersionNumber[1], java.sql.Types.INTEGER);
			ps.setObject(3, splitedVersionNumber[2], java.sql.Types.INTEGER);
			ps.setObject(4, splitedVersionNumber[3], java.sql.Types.INTEGER);
			ps.setString(5, pathFs);
			
			// Execute the SQL query
			rs = ps.executeQuery();
			
			// Get the list of descriptions
			while(rs.next())
			{			
				// Get the description
				descriptions.add(new Description(rs.getString("descr_description")));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// Close the resultSet
			DBHelper.close(rs);
			
			// Close the preparedStatement
			DBHelper.close(ps);
		}
		
		return descriptions;
	}
}
