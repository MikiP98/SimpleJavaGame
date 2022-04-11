package pl.mikolaj.pokora;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_S;

public class MainMenuKeyListener extends JLabel {

    public MainMenuKeyListener() {
        addKeyListener(new MainMenuKeyListener.FieldKeyListener());
    }

    public class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();

            System.out.println("key pressed");

            switch (key) {
                case (VK_S) -> {
                    MainMenu.setStart_game();
                    System.out.println("to true");
                }
            }

        }
    }
}
