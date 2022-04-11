package pl.mikolaj.pokora;

import lombok.Getter;
import pl.mikolaj.pokora.classes.Constant;
import pl.mikolaj.pokora.classes.arenas.Syberia;
import pl.mikolaj.pokora.classes.characters.*;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Scanner;

@Getter
public class Main {

    public static void main(String[] args) throws IOException {
        Constant.readAllOptions();

        Team team = new Team (
            new Warrior("Aragorn", 80, 80, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_Q, KeyEvent.VK_E),
            new Archer("Legolas", 400, 360, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_CONTROL, KeyEvent.VK_NUMPAD0),
            new Healer("Radogast", 40, 320, KeyEvent.VK_H, KeyEvent.VK_K, KeyEvent.VK_U, KeyEvent.VK_J, KeyEvent.VK_Y, KeyEvent.VK_I),
            new Mage("Gendolf", 200, 240, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD6, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD9),
            new Asasyn("Creed", 320, 120, KeyEvent.VK_SEMICOLON, KeyEvent.VK_ENTER, KeyEvent.VK_OPEN_BRACKET, KeyEvent.getExtendedKeyCodeForChar('\''), KeyEvent.VK_P, KeyEvent.VK_CLOSE_BRACKET)
        );
        team.info();

        /*
        Thread MenuTread = new Thread(() -> showMenu(team));
        MenuTread.start();
        */

        Thread GameThread = new Thread(() -> startGame(team));
        GameThread.start();


        Thread ConsoleThread = new Thread(() -> {
            try {
                startConsole(team);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ConsoleThread.start();
    }



    public static void showMenu(Team team) {
        MainMenu mainMenu = new MainMenu();
        if(mainMenu.runArena()) {
            Thread GameThread = new Thread(() -> startGame(team));
            GameThread.start();
        }
    }


    public static void startGame(Team team) {
        Syberia arena1 = new Syberia();
        if (team.enterArena(arena1)) {
            team.setArena(arena1);
            team.runArena();
        } else {
            System.out.println("Game over");
        }
    }


    public static void startConsole(Team team) throws IOException {
        System.out.println("Uruchomiono konsole (type \"help\" for help)");

        Scanner console = new Scanner(System.in);
        String response;

        boolean cheats = false;
        String no_permission = "You need to turn on the cheats to use this command!";

        do {
            response = console.nextLine();
            switch (response.toLowerCase()) {
                case ("help"), ("?") -> System.out.println("""
                        help/? -> command list
                        info -> all_characters_info
                        info {id} -> info about character with id = {id}
                        cheats -> changes state of cheats parameter
                        cheats 1 -> changes cheats to: true
                        cheats 0 -> changes cheats to: false
                        aKill {id} -> sets HP of character with id = {id} to 1
                        god {id}/godMode {id} -> makes character with id = {id} invincible for DMG or make it vincible if already in godMode
                        """);

                case ("info") -> team.info();
                case ("info 0") -> team.info(0);
                case ("info 1") -> team.info(1);
                case ("info 2") -> team.info(2);
                case ("info 3") -> team.info(3);
                case ("info 4") -> team.info(4);
                case ("info 5") -> team.info(5);
                case ("info 6") -> team.info(6);
                case ("info 7") -> team.info(7);
                case ("info 8") -> team.info(8);
                case ("info 9") -> team.info(9);
                case ("info 10") -> team.info(10);
                case ("info 11") -> team.info(11);
                //lepsze rozwiązanie?, obsługa większej ilości graczy?


                case ("cheats") -> {
                    cheats = !cheats;
                    System.out.println("Cheats were set to: " + cheats + "\n");
                }
                case ("cheats 0") -> {
                    cheats = false;
                    System.out.println("Cheats were set to: false\n");
                }
                case ("cheats 1") -> {
                    cheats = true;
                    System.out.println("Cheats were set to: true\n");
                }


                    //Cheat commands below

                //aKill = almost_kill
                case ("akill 0") -> {
                    if (cheats) {
                        team.aKill(0);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("akill 1") -> {
                    if (cheats) {
                        team.aKill(1);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("akill 2") -> {
                    if (cheats) {
                        team.aKill(2);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("akill 3") -> {
                    if (cheats) {
                        team.aKill(3);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("akill 4") -> {
                    if (cheats) {
                        team.aKill(4);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("akill 5") -> {
                    if (cheats) {
                        team.aKill(5);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("akill 6") -> {
                    if (cheats) {
                        team.aKill(6);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("akill 7") -> {
                    if (cheats) {
                        team.aKill(7);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("akill 8") -> {
                    if (cheats) {
                        team.aKill(8);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("akill 9") -> {
                    if (cheats) {
                        team.aKill(9);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("akill 10") -> {
                    if (cheats) {
                        team.aKill(10);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("akill 11") -> {
                    if (cheats) {
                        team.aKill(11);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                //lepsze rozwiązanie?, obsługa większej ilości graczy?

                case ("god 0"), ("godmode 0") -> {
                    if (cheats) {
                        team.setGodMode(0);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("god 1"), ("godmode 1") -> {
                    if (cheats) {
                        team.setGodMode(1);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("god 2"), ("godmode 2") -> {
                    if (cheats) {
                        team.setGodMode(2);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("god 3"), ("godmode 3") -> {
                    if (cheats) {
                        team.setGodMode(3);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("god 4"), ("godmode 4") -> {
                    if (cheats) {
                        team.setGodMode(4);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("god 5"), ("godmode 5") -> {
                    if (cheats) {
                        team.setGodMode(5);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("god 6"), ("godmode 6") -> {
                    if (cheats) {
                        team.setGodMode(6);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("god 7"), ("godmode 7") -> {
                    if (cheats) {
                        team.setGodMode(7);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("god 8"), ("godmode 8") -> {
                    if (cheats) {
                        team.setGodMode(8);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("god 9"), ("godmode 9") -> {
                    if (cheats) {
                        team.setGodMode(9);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("god 10"), ("godmode 10") -> {
                    if (cheats) {
                        team.setGodMode(10);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                case ("god 11"), ("godmode 11") -> {
                    if (cheats) {
                        team.setGodMode(11);
                        System.out.println("Done\n");
                    } else {
                        System.out.println(no_permission);
                    }
                }
                //lepsze rozwiązanie?, obsługa większej ilości graczy?


                default -> System.out.println("Nieznana komenda\n");
            }

        } while (!response.equals("Quit"));
    }
}
