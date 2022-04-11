package pl.mikolaj.pokora;

import pl.mikolaj.pokora.classes.CharacterClass;
import pl.mikolaj.pokora.classes.Constant;
import pl.mikolaj.pokora.classes.arenas.Syberia;

public class Team {
    private final CharacterClass[] teamMembers;
    public MainWindow mw;
    public void setArena(Syberia arena) {
    }

    public Team(CharacterClass... members) {
        teamMembers = new CharacterClass[members.length];
        System.arraycopy(members, 0, teamMembers, 0, teamMembers.length);
    }

    public CharacterClass[] getTeamMembers() {
        return teamMembers;
    }

    public boolean enterArena(Syberia arena1) {
        return arena1.open(this);
    }

    public void runArena() {
        this.mw = new MainWindow(Constant.window_width, Constant.window_height, this);
    }



    public void info(){
        //for(int i = 0; i < teamMembers.length; i++){
        for(CharacterClass teamMembers : teamMembers){
            teamMembers.info();
            //System.out.println(teamMembers.toString());
            System.out.println();
        }
    }

    public void info(int id){
        teamMembers[id].info();
        System.out.println();
    }

    public void aKill(int id) {
        teamMembers[id].setHealthPoints(1);
    }

    public void setGodMode(int id) {
        teamMembers[id].setGodMode();
    }
}
