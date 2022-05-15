package pl.mikolaj.pokora.classes.characters;

import pl.mikolaj.pokora.classes.CharacterClass;
import pl.mikolaj.pokora.classes.enums.AttackType;

import java.io.IOException;

public class Asasyn extends CharacterClass {

    public Asasyn(String name, int x, int y, int key1, int key2, int key3, int key4, int key5, int key6) throws IOException {
        super(name, y, x, key1, key2, key3, key4, key5, key6, 750);
        this.setLevel(1);
        this.setMaxHealthPoints(600);
        this.setHealthPoints(600);
        this.setMaxManaPoints(240);
        this.setManaPoints(100);
        this.setAttackType(AttackType.MAGICAL);
        this.setAttackAmount(320);
        this.setName(name);
        this.setAttackManaCost(80);

        this.setX(x);
        this.setY(y);
        this.uploadImage("img/qoi/size_40/characters/asasyn/asasyn_base.qoi", "img/qoi/size_40/characters/asasyn/asasyn_attack_left.qoi", "img/qoi/size_40/characters/asasyn/asasyn_attack_right.qoi");

        Thread newThread = new Thread(() -> {
            while (true) {
                restoreMana(1);

                try {
                    Thread.sleep((int) Math.floor(420 / (1 + 0.2 * (getLevel() - 1))));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        newThread.start();
    }

    public boolean attack(CharacterClass attackedCharacter, int value, boolean cant_die) {
        boolean val = attackedCharacter.loseHealth(value, cant_die);
        return val;
    }
    public boolean attack(CharacterClass attackedCharacter) {
        return attack(attackedCharacter, (int) Math.ceil(this.getAttackAmount() * (1 + 0.2 * (this.getLevel() - 1))), false);
    }
}
