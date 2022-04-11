package pl.mikolaj.pokora;

import pl.mikolaj.pokora.classes.Constant;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    Component game;

    public MainWindow(int width, int height, Team team) {
        /*
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int locationX = (int) ((screenSize.getWidth() - width) / 2);
        int locationY = (int) ((screenSize.getHeight() - height) / 2);
        setLocation(locationX, locationY);
        */

        switch (Constant.displayOption) {
            case FULL_SCREEN:
                GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice device = graphics.getDefaultScreenDevice();

                setUndecorated(true);
                device.setFullScreenWindow(this);
                setResizable(false);
                break;
            case BORDERLESS_WINDOW:
                setUndecorated(true);
            default:
                setSize(width, height);
        }

        setLocationRelativeTo(null);
        this.game = new GameField(team);
        add(this.game);
        setVisible(true);


        if (Constant.force_min_frame_rate > 0) {
            Thread newThread = new Thread(() -> {
                while (true) {
                    repaint();

                    try {
                        Thread.sleep(1000 / Constant.force_min_frame_rate);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            newThread.start();
        }
    }

    public void gameOver(String name) {
        System.out.println("lost");

        remove(this.game);


        JLabel label1 = new JLabel("GAME OVER");
        label1.setFont(new Font("Serif", Font.PLAIN, (int) ((Constant.window_width/40)*2.5)));

        Dimension size1 = label1.getPreferredSize();
        label1.setSize(size1.width, size1.height);

        label1.setHorizontalAlignment(JLabel.CENTER);
        add(label1);

        if (Constant.show_who_lost) {
            JLabel label2 = new JLabel("Player: \"" + name + "\" lost the battle!");
            label2.setFont(new Font("Serif", Font.PLAIN, (int) ((Constant.window_width/40)*1.5)));

            Dimension size2 = label2.getPreferredSize();
            label2.setSize(size2.width, size2.height);

            System.out.println("JL: " + JLabel.CENTER);
            label2.setBounds((int) (Math.floor(Constant.window_width/2) - size2.width), (int) (Math.floor(Constant.window_height/2) - size2.height), size2.width, size2.height);
            add(label2);
        }

        setVisible(true);
    }
}
