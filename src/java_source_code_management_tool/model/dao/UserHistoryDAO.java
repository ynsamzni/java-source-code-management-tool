package java_source_code_management_tool.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java_source_code_management_tool.model.dto.UserHistory;
import java_source_code_management_tool.util.DBHelper;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class UserHistoryDAO
{
	private Connection con = null;

	public UserHistoryDAO(Connection con)
	{
		this.con = con;
	}
	
	public ArrayList<UserHistory> getUserHistory(String username)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<UserHistory> userHistory = new ArrayList<UserHistory>();
		UserHistory singleUserHistory;
		String versionNumber, versionMinorNumber, versionRevisionNumber, versionBuildNumber;

		try
		{
			// Prepare the SQL query
			ps = con.prepareStatement("SELECT ver_date, jsf_path_fs, ver_major_number, ver_minor_number, ver_revision_number, ver_build_number FROM version_ver JOIN user_usr ON (ver_usr_id = usr_id) JOIN javasourcefile_jsf ON (ver_jsf_id = jsf_id) WHERE usr_username = ? ORDER BY ver_date DESC");
			ps.setString(1, username);
			
			// Execute the SQL query
			rs = ps.executeQuery();
			
			// Get the list of versions
			while(rs.next())
			{
				// Convert version number for the app
				versionNumber = rs.getString("ver_major_number");
				versionMinorNumber = rs.getString("ver_minor_number");
				versionRevisionNumber = rs.getString("ver_revision_number");
				versionBuildNumber = rs.getString("ver_build_number");
				
				if(versionMinorNumber != null)
					versionNumber += "." + versionMinorNumber;
				if(versionRevisionNumber != null)
					versionNumber += "." + versionRevisionNumber;
				if(versionBuildNumber != null)
					versionNumber += "." + versionBuildNumber;
				
				// Get single user history
				singleUserHistory = new UserHistory();
				
				singleUserHistory.setUsername(username);
				singleUserHistory.setDate(rs.getTimestamp("ver_date"));
				singleUserHistory.setJavaSourceFilePathFs(rs.getString("jsf_path_fs"));
				singleUserHistory.setVersion(versionNumber);
				
				// Update user history
				userHistory.add(singleUserHistory);
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
		
		return userHistory;
	}
}
