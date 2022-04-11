package pl.mikolaj.pokora;

import pl.mikolaj.pokora.classes.CharacterClass;
import pl.mikolaj.pokora.classes.Constant;
import pl.mikolaj.pokora.classes.enums.AttackType;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;

public class MainMenu{ //przenieść keyListener?

    private MainMenuWindow mmw;

    private static boolean start_game = false;

    public boolean runArena() {
        this.mmw = new MainMenuWindow(Constant.window_width, Constant.window_height);

        while(!start_game) {

        }
        System.out.println("game start?");
        return true;
    }

    public static void setStart_game() {
        start_game = true;
    }
}
