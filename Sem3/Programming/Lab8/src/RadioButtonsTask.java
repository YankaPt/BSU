import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by jan on 10.12.17.
 */
public class RadioButtonsTask extends JPanel {
    public static final String[] IMAGES_PATHS = {"Cancel.png", "Ok.jpeg", "Select.jpeg", "Pressed.jpeg"};
    public RadioButtonsTask() {
        setLayout(new GridLayout(4,2));
        ButtonGroup buttonGroup = new ButtonGroup();

        ImageIcon[] imageIcons = new ImageIcon[4];
        try {
            for (int i = 0; i < 4; i++) {
                BufferedImage buff = ImageIO.read(new File(IMAGES_PATHS[i]));
                imageIcons[i] = new ImageIcon(buff);
            }
            for (int i = 0; i < 6; i++) {
                JRadioButton radioButton = new JRadioButton();
                radioButton.setIcon(imageIcons[0]);
                radioButton.setSelectedIcon(imageIcons[1]);
                radioButton.setRolloverIcon(imageIcons[2]);
                radioButton.setPressedIcon(imageIcons[3]);
                buttonGroup.add(radioButton);
                add(radioButton);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Images loading error");
        }
    }
    }
