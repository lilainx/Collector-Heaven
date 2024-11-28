package ui;

import javax.swing.SwingUtilities;

// runs the application
public class Main {
    public static void main(String[] args) throws Exception {
        // Initialize and start the GUI
        SwingUtilities.invokeLater(() -> {
            CollectorHeavenGUI gui = new CollectorHeavenGUI();
            gui.setVisible(true);
        });
    }
}
