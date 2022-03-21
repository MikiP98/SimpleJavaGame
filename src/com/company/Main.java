package com.company;

import com.company.classes.arenas.Syberia;
import com.company.classes.characters.Archer;
import com.company.classes.characters.Healer;
import com.company.classes.characters.Mage;
import com.company.classes.characters.Warrior;

import java.awt.event.KeyEvent;

public class Main {

    public static void main(String[] args) {
        //Warrior war1 = new Warrior("NAme");
        //war1.info();

        Team team = new Team (
            new Warrior("Aragorn", 100, 100, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_Q, KeyEvent.VK_E),
            //new Mage("Gendolf", 200, 200, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_Q, KeyEvent.VK_E),
            new Archer("Legolas", 300, 300, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_O, KeyEvent.VK_P)
            //new Healer("Radogast", 400, 400, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_Q, KeyEvent.VK_E)
        );
        team.info();
        Syberia arena1 = new Syberia();
        if (team.enterArena(arena1)) {
            team.setArena(arena1);
            team.runArena();
        } else {
            System.out.println("Game over");
        }
    }
}
