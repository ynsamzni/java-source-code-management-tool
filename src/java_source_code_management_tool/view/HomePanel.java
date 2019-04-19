package java_source_code_management_tool.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import java_source_code_management_tool.controller.JavaSourceFileController;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class HomePanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JButton buttonDisplayFile;
	private JButton buttonReferenceFile;
	private JavaSourceFileController javaSourceFileController;
	
	public HomePanel(JavaSourceFileController javaSourceFileController)
	{
		this.javaSourceFileController = javaSourceFileController;
		
		// Configure JPanel
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Create and configure components
		buttonDisplayFile = new JButton("Display a file");
		buttonDisplayFile.setAlignmentX(CENTER_ALIGNMENT);
		buttonDisplayFile.addActionListener(this);
		
		buttonReferenceFile = new JButton("Reference a file");
		buttonReferenceFile.setAlignmentX(CENTER_ALIGNMENT);
		buttonReferenceFile.addActionListener(this);

		// Attach components to JPanel
		this.add(Box.createVerticalGlue());
		this.add(buttonDisplayFile);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(buttonReferenceFile);
		this.add(Box.createVerticalGlue());			
	}
	
	public void actionPerformed(ActionEvent ae)
	{		
		try
		{
			if(ae.getSource() == buttonReferenceFile)
			{
				javaSourceFileController.manageVersionActionPerformed();
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
