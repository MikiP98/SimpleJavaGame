package pl.mikolaj.pokora.classes.arenas;

import pl.mikolaj.pokora.Team;

public class Syberia implements BaseArena{
    public boolean open(Team team) {
        boolean isOpened;
        if (team.getTeamMembers().length < 1) {
            System.out.println("Not enough party members!");
            isOpened = false;
        } else {
            System.out.println("Welcome, heroes");
            isOpened = true;
        }
        return isOpened;
    }
}
