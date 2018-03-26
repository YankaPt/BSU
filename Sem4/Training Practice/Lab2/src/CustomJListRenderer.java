import javax.swing.*;
import java.awt.*;

public class CustomJListRenderer extends JLabel implements ListCellRenderer {
    public CustomJListRenderer() {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        ImageIcon icon = new ImageIcon(((Country)value).getIconPath());
        setIcon(icon);
        setText(((Country)value).getName());
        return this;
    }
}
