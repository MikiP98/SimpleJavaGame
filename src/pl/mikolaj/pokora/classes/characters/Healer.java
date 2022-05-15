package pl.mikolaj.pokora.classes.characters;

import pl.mikolaj.pokora.classes.enums.AttackType;
import pl.mikolaj.pokora.classes.CharacterClass;

import java.io.IOException;

public class Healer extends CharacterClass {
    public Healer(String name, int x, int y, int key1, int key2, int key3, int key4, int key5, int key6) throws IOException {
        super(name, y, x, key1, key2, key3, key4, key5, key6, 300);
        this.setLevel(1);
        this.setMaxHealthPoints(600);
        this.setHealthPoints(600);
        this.setMaxManaPoints(600);
        this.setManaPoints(500);
        this.setAttackType(AttackType.MAGICAL);
        this.setAttackAmount(20);
        this.setName(name);
        this.setAttackManaCost(10);

        this.setX(x);
        this.setY(y);
        this.uploadImage("img/qoi/size_40/characters/healer/healer_base.qoi", "img/qoi/size_40/characters/healer/healer_attack_left.qoi", "img/qoi/size_40/characters/healer/healer_attack_right.qoi");


        //Thread newThread = new Thread(this.restoreMana(1));
        Thread newThread = new Thread(() -> {
            while (true) {
                restoreMana(1);

                try {
                    Thread.sleep((int) Math.floor(300 / (1 + 0.2 * (getLevel() - 1))));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        newThread.start();
    }

    public boolean attack(CharacterClass attackedCharacter, int value, boolean cant_die) {
        boolean val = attackedCharacter.restoreHealth(value);
        return val;
    }
    public boolean attack(CharacterClass attackedCharacter) {
        return attack(attackedCharacter, (int) Math.ceil(this.getAttackAmount() * (1 + 0.2 * (this.getLevel() - 1))), true);
    }
}
