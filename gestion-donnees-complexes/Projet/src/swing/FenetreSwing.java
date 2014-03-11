package swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class FenetreSwing extends JFrame {

	protected JTextArea textArea;
	protected String newline = "\n";
	private JComboBox liste1;
	
	public FenetreSwing() {
			super("ToolBarDemo");
			// Create the toolbar.
			JToolBar jtbMainToolbar = new JToolBar();
			// setFloatable(false) to make the toolbar non movable
			addButtons(jtbMainToolbar);
			// Create the text area
			textArea = new JTextArea(30, 50);
			JScrollPane jsPane = new JScrollPane(textArea);
			// Lay out the content pane.
			JPanel jplContentPane = new JPanel();
			Object[] elements = new Object[]{"Element 1", "Element 2", "Element 3", "Element 4", "Element 5"};
			 
			liste1 = new JComboBox(elements);
	 
			jplContentPane.setLayout(new BorderLayout());
			jplContentPane.setPreferredSize(new Dimension(1200, 700));
			jplContentPane.add(jsPane, BorderLayout.CENTER);
			jplContentPane.add(jtbMainToolbar, BorderLayout.NORTH);

			jplContentPane.add(liste1,BorderLayout.NORTH);

			setContentPane(jplContentPane);
		}
		public void addButtons(JToolBar jtbToolBar) {
			JButton jbnToolbarButtons = null;
			// first button
			jbnToolbarButtons = new JButton(new ImageIcon("left.gif"));
			jbnToolbarButtons.setToolTipText("left");
			jbnToolbarButtons.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					displayInTextArea("This is Left Toolbar Button Reporting");
				}
			});
			jtbToolBar.add(jbnToolbarButtons);
			// 2nd button
			jbnToolbarButtons = new JButton(new ImageIcon("right.gif"));
			jbnToolbarButtons.setToolTipText("right");
			jbnToolbarButtons.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					displayInTextArea("This is right Toolbar Button Reporting");
				}
			});
			jtbToolBar.add(jbnToolbarButtons);
			jtbToolBar.addSeparator();
			// 3rd button
			jbnToolbarButtons = new JButton(new ImageIcon("open.gif"));
			jbnToolbarButtons.setToolTipText("open");
			jbnToolbarButtons.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					displayInTextArea("This is open Toolbar Button Reporting");
				}
			});
			jtbToolBar.add(jbnToolbarButtons);
			// 4th button
			jbnToolbarButtons = new JButton(new ImageIcon("save.gif"));
			jbnToolbarButtons.setToolTipText("save");
			jbnToolbarButtons.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					displayInTextArea("This is save Toolbar Button Reporting");
				}
			});
			jtbToolBar.add(jbnToolbarButtons);
			// We can add separators to group similar components
			jtbToolBar.addSeparator();
			// fourth button
			jbnToolbarButtons = new JButton("Text button");
			jbnToolbarButtons.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					displayInTextArea("Text button");
				}
			});
			jtbToolBar.add(jbnToolbarButtons);
			// fifth component is NOT a button!
			JTextField jtfButton = new JTextField("Text field");
			jtfButton.setEditable(false);
			jtfButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					displayInTextArea("TextField component can also be placed");
				}
			});
			jtbToolBar.add(jtfButton);
		}
		protected void displayInTextArea(String actionDescription) {
			textArea.append(actionDescription + newline);
		}
		
	
	
}
