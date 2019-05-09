package java_source_code_management_tool.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java_source_code_management_tool.controller.NavigationController;
import java_source_code_management_tool.controller.UserController;
import java_source_code_management_tool.model.dto.UserHistory;

public class UserHistoryPanel extends JPanel implements ActionListener, ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	private JButton buttonCancel;
	private JScrollPane scrollPaneUsers;
	private JList<String> listUsers;
	private DefaultListModel<String> listModelUsers;
	private JPanel userSelectionPanel;
	private JPanel userHistoryPanel;
	private GridBagConstraints gbc;
	private NavigationController navigationController;
	private UserController userController;
	private JScrollPane scrollPaneUserHistory;
	private DefaultTableModel tableModelUserHistory;

	public UserHistoryPanel(NavigationController navigationController, UserController userController)
	{
		// Set controller
		this.userController = userController;
		this.navigationController = navigationController;
		
		// Configure this JPanel
		this.setLayout(new GridBagLayout());
		
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(4, 4, 4, 4);
		
		// Create and configure components
		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(this);
		
		// Attach JPanels to this JPanel
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weightx = 0.3;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(createUserSelectionPanel(), gbc);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weightx = 0.7;
		gbc.weighty = 1.0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(createUserHistoryPanel(), gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_END;
		gbc.weighty = 0.0;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(buttonCancel, gbc);
	}
	
	public JPanel createUserSelectionPanel()
	{
		// Create and configure JPanel
		userSelectionPanel = new JPanel();
		userSelectionPanel.setLayout(new BoxLayout(userSelectionPanel, BoxLayout.PAGE_AXIS));
		userSelectionPanel.setBorder(new CompoundBorder(new TitledBorder("Users"), new EmptyBorder(8, 0, 0, 0)));
		
		// Create components
		listModelUsers = new DefaultListModel<String>();
		
		listUsers = new JList<String>(listModelUsers);
		listUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listUsers.addListSelectionListener(this);
		
		scrollPaneUsers = new JScrollPane(listUsers);

		// Attach components to JPanel
		userSelectionPanel.add(scrollPaneUsers);
		
		return userSelectionPanel;
	}
	
	public JPanel createUserHistoryPanel()
	{
		// Create and configure JPanel
		userHistoryPanel = new JPanel();
		userHistoryPanel.setLayout(new BoxLayout(userHistoryPanel, BoxLayout.PAGE_AXIS));
		userHistoryPanel.setBorder(new CompoundBorder(new TitledBorder("Selected user history"), new EmptyBorder(8, 0, 0, 0)));
		
		// Create components
		tableModelUserHistory = new DefaultTableModel();
		tableModelUserHistory.addColumn("Date");
		tableModelUserHistory.addColumn("File");
		tableModelUserHistory.addColumn("Added version");
		
		JTable tableUserHistory = new JTable(tableModelUserHistory)
		{
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		
		scrollPaneUserHistory = new JScrollPane(tableUserHistory);

		// Attach components to JPanel
		userHistoryPanel.add(scrollPaneUserHistory);
		
		return userHistoryPanel;
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			if(ae.getSource() == buttonCancel)
			{
				navigationController.goBackActionPerformed();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void valueChanged(ListSelectionEvent lse)
	{
		try
		{
			if(lse.getSource() == listUsers)
			{
				if(!lse.getValueIsAdjusting())
					userController.selectUserForHistoryActionPerformed(listUsers.getSelectedValue());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void showListDbUserUsernames(ArrayList<String> userUsernames)
	{
		// Clear list
		listModelUsers.removeAllElements();
		
		// Fill list
		for(int i=0; i<userUsernames.size(); i++)
			listModelUsers.addElement(userUsernames.get(i));
		
		// Select first list item by default
		listUsers.setSelectedIndex(0);
	}
	
	public void showUserHistory(ArrayList<UserHistory> userHistory)
	{
		for(int i=0; i<userHistory.size(); i++)
		{
			tableModelUserHistory.addRow(new Object[]{userHistory.get(i).getDate(), userHistory.get(i).getJavaSourceFilePathFs(), userHistory.get(i).getVersion()});
		}
	}
	
	public void clearUserHistoryPanel()
	{
		tableModelUserHistory.setRowCount(0);
	}
}
