package pl.mikolaj.pokora;

import pl.mikolaj.pokora.classes.Constant;

import javax.swing.*;
import java.awt.*;

public class MainMenuWindow extends JFrame {

    public MainMenuWindow(int width, int height) {

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

        JLabel title = new JLabel("Main menu");
        title.setFont(new Font("Serif", Font.PLAIN, (int) ((Constant.window_width/40)*0.75)));
        Dimension title_size = title.getPreferredSize();
        title.setSize(title_size.width, title_size.height);
        add(title);

        add(new MainMenuKeyListener());

        setFocusable(true);

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
}
