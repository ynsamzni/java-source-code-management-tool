package java_source_code_management_tool.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java_source_code_management_tool.model.Version;
import java_source_code_management_tool.util.DBHelper;
import oracle.jdbc.OracleTypes;

public class VersionDAO
{
	private Connection con = null;
	
	public VersionDAO(Connection con)
	{
		this.con = con;
	}
	
	public Integer insertVersion(Version version, Integer javaSourceFileID)
	{
		CallableStatement cs = null;
		Integer generatedId = null;
		
		// Convert version number for the database
		String[] splitedStrVersionNumber = version.getVersion().split(("\\."));
		Integer[] splitedVersionNumber = new Integer[4];

		for(int i=0; i<splitedStrVersionNumber.length; i++)
		{
			splitedVersionNumber[i] = Integer.parseInt(splitedStrVersionNumber[i]);
		}
		
		try
		{
			// Prepare the SQL query			
			cs = con.prepareCall("BEGIN INSERT INTO version_ver (ver_major_number, ver_minor_number, ver_revision_number, ver_build_number, ver_date, ver_jsf_id, ver_usr_id) VALUES (?, ?, ?, ?, ?, ?, (SELECT usr_id FROM user_usr WHERE usr_username=?)) RETURNING ver_id INTO ?; END;");
			cs.setObject(1, splitedVersionNumber[0], java.sql.Types.INTEGER);
			cs.setObject(2, splitedVersionNumber[1], java.sql.Types.INTEGER);
			cs.setObject(3, splitedVersionNumber[2], java.sql.Types.INTEGER);
			cs.setObject(4, splitedVersionNumber[3], java.sql.Types.INTEGER);
			cs.setTimestamp(5, version.getDate());
			cs.setInt(6, javaSourceFileID);
			cs.setString(7, version.getAuthor());
			cs.registerOutParameter(8, OracleTypes.NUMBER);
			
			// Execute the SQL query
			cs.execute();
			
			// Get the generated version ID
			generatedId = cs.getInt(8);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// Close the preparedStatement
			DBHelper.close(cs);
		}
		
		return generatedId;
	}
	
	public ArrayList<Version> getListVersions(String pathFs)
	{
		ArrayList<Version> versions = new ArrayList<Version>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String versionNumber, versionMinorNumber, versionRevisionNumber, versionBuildNumber;

		try
		{			
			// Prepare the SQL query
			ps = con.prepareStatement("SELECT ver_major_number, ver_minor_number, ver_revision_number, ver_build_number, usr_username, ver_date FROM version_ver JOIN user_usr ON (ver_usr_id = usr_id) JOIN javasourcefile_jsf ON (ver_jsf_id = jsf_id) WHERE jsf_path_fs = ?");
			ps.setString(1, pathFs);
			
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
				
				// Get the version
				versions.add(new Version(
							versionNumber,
							rs.getString("usr_username"),
							rs.getTimestamp("ver_date")
							));
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
		
		return versions;
	}
}
