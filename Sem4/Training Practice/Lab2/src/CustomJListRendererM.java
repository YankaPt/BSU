import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class CustomJListRendererM extends JLabel implements ListCellRenderer {
    public CustomJListRendererM() {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        ImageIcon icon = new ImageIcon(((Map.Entry<Country, String>)value).getKey().getIconPath());
        setIcon(icon);
        setText(((Map.Entry<Country, String>)value).getKey().getName());
        return this;
    }
}
