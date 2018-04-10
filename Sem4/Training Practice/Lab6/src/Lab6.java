import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class Lab6 extends JFrame{
    BufferedImage bufferedImage;
    int rows = 3;
    int cols = 3;
    int curX = 0;
    int curY = 0;
    private Border usualBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
    private Border selectedBorder = BorderFactory.createLineBorder(Color.MAGENTA, 5);
    HashMap<Integer, JLabel> labels = new HashMap<>();
    ArrayList<Integer> permutation = new ArrayList<>();

    Lab6(){
        JTabbedPane tabbedPane = new JTabbedPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200, 200, 500, 500);
        setPreferredSize(new Dimension(500, 500));
        setTitle("Puzzle");
        pack();

        JPanel panel = new JPanel();
        JPanel imagePanel = new JPanel();
        panel.setLayout(new BorderLayout());
        imagePanel.setLayout(new GridLayout(rows, cols));
        tabbedPane.add("Puzzle", panel);
        panel.add(imagePanel, BorderLayout.CENTER);
        imagePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT: {
                        int curIdx = curX + curY*cols;
                        curX = (cols + curX - 1) % cols;
                        int newIdx = curX + curY*cols;
                        JLabel curLabel = labels.get(curIdx), newLabel = labels.get(newIdx);
                        Collections.swap(permutation, curIdx, newIdx);
                        System.out.println(permutation);
                        curIdx = newIdx;
                        Icon temp = curLabel.getIcon();
                        curLabel.setIcon(newLabel.getIcon());
                        newLabel.setIcon(temp);
                        newLabel.setBorder(selectedBorder);
                        curLabel.setBorder(usualBorder);
                        checkPermutation();
                        break;
                    }
                    case KeyEvent.VK_RIGHT: {
                        int curIdx = curX + curY*cols;
                        curX = (curX + 1) % cols;
                        int newIdx = curX + curY*cols;
                        Collections.swap(permutation, curIdx, newIdx);
                        JLabel curLabel = labels.get(curIdx), newLabel = labels.get(newIdx);
                        curIdx = newIdx;
                        Icon temp = curLabel.getIcon();
                        curLabel.setIcon(newLabel.getIcon());
                        newLabel.setIcon(temp);
                        newLabel.setBorder(selectedBorder);
                        curLabel.setBorder(usualBorder);
                        checkPermutation();
                        break;
                    }
                    case KeyEvent.VK_DOWN: {
                        int curIdx = curX + curY*rows;
                        curY = (curY + 1) % rows;
                        int newIdx = curX + curY*rows;
                        Collections.swap(permutation, curIdx, newIdx);
                        JLabel curLabel = labels.get(curIdx), newLabel = labels.get(newIdx);
                        curIdx = newIdx;
                        Icon temp = curLabel.getIcon();
                        curLabel.setIcon(newLabel.getIcon());
                        newLabel.setIcon(temp);
                        newLabel.setBorder(selectedBorder);
                        curLabel.setBorder(usualBorder);
                        checkPermutation();
                        break;
                    }
                    case KeyEvent.VK_UP: {
                        int curIdx = curX + curY*rows;
                        curY = (rows + curY - 1) % rows;
                        int newIdx = curX + curY*rows;
                        Collections.swap(permutation, curIdx, newIdx);
                        JLabel curLabel = labels.get(curIdx), newLabel = labels.get(newIdx);
                        curIdx = newIdx;
                        Icon temp = curLabel.getIcon();
                        curLabel.setIcon(newLabel.getIcon());
                        newLabel.setIcon(temp);
                        newLabel.setBorder(selectedBorder);
                        curLabel.setBorder(usualBorder);
                        checkPermutation();
                        break;
                    }
                }
            }
        });
        MouseAdapter listenerMouse = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                imagePanel.requestFocus();
                for(int i = 0; i < rows * cols; i++){
                    labels.get(i).setBorder(usualBorder);
                }
                JLabel label = (JLabel) e.getSource();
                label.setBorder(selectedBorder);
                for(int y = 0; y < rows; y++){
                    for(int x = 0; x < cols; x++){
                        if(labels.get(x + y*rows).equals(label)){
                            curX = x;
                            curY = y;
                            break;
                        }
                    }
                }
            }
        };
        JButton button = new JButton("Load Image");
        button.addActionListener(e->{
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open File");
            //fileChooser.setCurrentDirectory(new File("C:\\Users\\anton\\OneDrive\\Изображения\\Пленка"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "png", "gif", "jpeg", "jpg"));
            if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                try{
                    labels.clear();
                    permutation.clear();
                    imagePanel.removeAll();
                    bufferedImage = ImageIO.read(file);
                    //panel.add(new JLabel(new ImageIcon(bufferedImage)), BorderLayout.NORTH);
                    BufferedImage[] images = new BufferedImage[rows*cols];
                    int chunkWidth = bufferedImage.getWidth() / (rows);
                    int chunkHeight = bufferedImage.getHeight() / (cols);
                    int count = 0;
                    for (int x = 0; x < rows; x++) {
                        for (int y = 0; y < cols; y++) {
                            images[count] = new BufferedImage(chunkWidth, chunkHeight, bufferedImage.getType());
                            Graphics2D gr = images[count++].createGraphics();
                            gr.drawImage(bufferedImage, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                            gr.dispose();
                        }
                    }
                    for (int i = 0; i < rows*cols; i++) {
                        JLabel label = new JLabel();
                        labels.put(i, label);
                        label.setBorder(usualBorder);
                        imagePanel.add(label);
                        permutation.add(i);
                    }
                    Collections.shuffle(permutation);
                    labels.get(0).setBorder(selectedBorder);
                    for (int i = 0; i < rows*cols; i++) {
                        JLabel label = labels.get(i);
                        label.addMouseListener(listenerMouse);
                        label.setIcon(new ImageIcon(images[permutation.get(i)]));
                    }
                    repaint();
                }catch(Exception exc){}
            }
        });
        panel.add(button, BorderLayout.SOUTH);
        this.add(tabbedPane);
    }

    private void checkPermutation() {
        for (int i = 0; i < rows*cols; ++i) {
            if (i != permutation.get(i)) return;
        }
        JOptionPane.showMessageDialog(this, "Well done!", "Finished", JOptionPane.PLAIN_MESSAGE);
    }


    public static void main(String[] args){
        Lab6 lab6 = new Lab6();
        lab6.setVisible(true);
    }
}