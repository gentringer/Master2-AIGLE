package swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OuvrirFenetre {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FenetreSwing jtfToolbar = new FenetreSwing(); // Extends Frame.
		jtfToolbar.pack();
		jtfToolbar.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		jtfToolbar.setVisible(true);
		
		
	}

}
