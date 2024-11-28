package ui;

import javax.swing.*;
import java.awt.*;

// Partially adapted from lecture lab C03
// A JPanel that displays an image temporarily.
// The panel contains a lightPanel with a JLabel to show the image.
public class ImagePanel extends JPanel {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;
    private ImageIcon imageIcon;
    private JLabel imageLabel;
    private JPanel lightPanel;

    // Constructs an ImagePanel instance and initializes the components.
    // Sets up the layout and loads the image to be displayed.
    public ImagePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        lightPanel = new JPanel();
        lightPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(lightPanel);

        imageLabel = new JLabel();
        lightPanel.add(imageLabel);

        loadImage();
    }

    
    // Requires: The image file "pikachu.png" exists in the resources directory.
    // Modifies: The imageIcon field is updated with the loaded image.
    // Effects: Loads the image into the imageIcon field.
    private void loadImage() {
        String sep = System.getProperty("file.separator");
        String imagePath = System.getProperty("user.dir") + sep + "src" + sep + "resources" + sep + "pikachu.png";
        imageIcon = new ImageIcon(imagePath);
        if (imageIcon.getIconWidth() == -1) {
            System.out.println("Failed to load image: " + imagePath);
        } else {
            System.out.println("Image loaded successfully: " + imagePath);
        }
    }

	
    // Requires: The imageIcon field must be initialized with a valid ImageIcon.
    // Modifies: The icon of the imageLabel is set to the loaded imageIcon.
    // Effects: Shows the image and then hides it after 2 seconds.
    public void showImage() {
        imageLabel.setIcon(imageIcon);
        revalidate();
        repaint();
        Timer timer = new Timer(2000, e -> hideImage()); // Show image for 2 seconds
        timer.setRepeats(false);
        timer.start();
    }

    // Requires: The imageLabel must be showing an image.
    // Modifies: The icon of the imageLabel is set to null.
    // Effects: Removes the image from the JLabel.
    private void hideImage() {
        imageLabel.setIcon(null);
        revalidate();
        repaint();
    }
}
