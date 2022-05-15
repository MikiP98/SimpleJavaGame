package pl.mikolaj.pokora.classes.characters;

import pl.mikolaj.pokora.classes.enums.AttackType;
import pl.mikolaj.pokora.classes.CharacterClass;

import java.io.IOException;

public class Warrior extends CharacterClass {

    public Warrior(String name, int x, int y, int key1, int key2, int key3, int key4, int key5, int key6) throws IOException {
        super(name, y, x, key1, key2, key3, key4, key5, key6, 360);
        this.setLevel(1);
        this.setMaxHealthPoints(1000);
        this.setHealthPoints(1000);
        this.setMaxManaPoints(100);
        this.setManaPoints(100);
        this.setAttackType(AttackType.PHYSICAL);
        this.setAttackAmount(10);
        this.setName(name);

        this.setX(x);
        this.setY(y);
        this.uploadImage("img/qoi/size_40/characters/warrior/warrior_base.qoi", "img/qoi/size_40/characters/warrior/warrior_attack_left.qoi", "img/qoi/size_40/characters/warrior/warrior_attack_right.qoi");
    }

    public boolean attack(CharacterClass attackedCharacter, int value, boolean cant_die) {
        boolean val = attackedCharacter.loseHealth(value, cant_die);
        return val;
    }
    public boolean attack(CharacterClass attackedCharacter) {
        return attack(attackedCharacter, (int) Math.ceil(this.getAttackAmount() * (1 + 0.2 * (this.getLevel() - 1))), false);
    }
}
