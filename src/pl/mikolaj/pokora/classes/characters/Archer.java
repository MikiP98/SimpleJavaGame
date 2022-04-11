package pl.mikolaj.pokora.classes.characters;

import pl.mikolaj.pokora.classes.enums.AttackType;
import pl.mikolaj.pokora.classes.CharacterClass;

import java.io.IOException;

public class Archer extends CharacterClass {

    public Archer(String name, int x, int y, int key1, int key2, int key3, int key4, int key5, int key6) throws IOException {
        super(name, y, x, key1, key2, key3, key4, key5, key6, 300);
        this.setLevel(1);
        this.setMaxHealthPoints(800);
        this.setHealthPoints(800);
        this.setMaxManaPoints(100);
        this.setManaPoints(100);
        this.setAttackType(AttackType.PHYSICAL_RANGED);
        this.setAttackAmount(5);
        this.setName(name);

        this.setX(x);
        this.setY(y);
        this.uploadImage("img/qoi/size_40/characters/archer/archer_base.qoi", "img/qoi/size_40/characters/archer/archer_attack_left.qoi", "img/qoi/size_40/characters/archer/archer_attack_right.qoi");
        this.uploadRangedImage("img/qoi/size_40/characters/archer/archer_ranged_attack_left.qoi", "img/qoi/size_40/characters/archer/archer_ranged_attack_right.qoi");
    }

    public boolean attack(CharacterClass attackedCharacter, int value, boolean cant_die) {
        boolean val = attackedCharacter.loseHealth(value, cant_die);
        if(val == true) {System.out.println("+1: " + val); }
        return val;
    }
    public boolean attack(CharacterClass attackedCharacter) {
        return attack(attackedCharacter, this.getAttackAmount(), false);
    }

    public void wall() {
        attack(this, 50, true);
    }
}
