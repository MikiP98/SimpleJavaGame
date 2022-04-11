package pl.mikolaj.pokora.classes.characters;

import pl.mikolaj.pokora.classes.enums.AttackType;
import pl.mikolaj.pokora.classes.CharacterClass;
import pl.mikolaj.pokora.classes.Constant;

import java.io.IOException;

public class Mage  extends CharacterClass {

    public Mage(String name, int x, int y, int key1, int key2, int key3, int key4, int key5, int key6) throws IOException {
        super(name, y, x, key1, key2, key3, key4, key5, key6, 375);
        this.setLevel(1);
        this.setMaxHealthPoints(700);
        this.setHealthPoints(700);
        this.setMaxManaPoints(600);
        this.setManaPoints(500);
        this.setAttackType(AttackType.MAGICAL_RANGED);
        this.setAttackAmount(20);
        this.setName(name);
        this.setAttackManaCost(10);

        this.setX(x);
        this.setY(y);
        this.uploadImage("img/qoi/size_40/characters/mage/wizard_base.qoi", "img/qoi/size_40/characters/mage/wizard_attack_left.qoi", "img/qoi/size_40/characters/mage/wizard_attack_right.qoi");
        this.uploadRangedImage("img/qoi/size_40/characters/mage/wizard_ranged_attack_left.qoi", "img/qoi/size_40/characters/mage/wizard_ranged_attack_right.qoi");

        Thread newThread = new Thread(() -> {
            while (true) {
                restoreMana(1);

                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        newThread.start();
    }

    public boolean attack(CharacterClass attackedCharacter, int value, boolean cant_die) {
        return attackedCharacter.loseHealth(value, cant_die);
    }
    public boolean attack(CharacterClass attackedCharacter) {
        return attack(attackedCharacter, this.getAttackAmount(), false);
    }

    @Override
    public void wall() {
        occupiedCells[this.getY() / 40][this.getX() / 40] = 0;

        if (this.getX() == 0) {
            int newX = (int) ((Math.floor((float) Constant.window_width/40)-2)) * 40;
            occupiedCells[this.getY() / 40][newX / 40] = this.getId();
            this.setX(newX);

        } else if (this.getX() == (Math.floor((float) Constant.window_width/40)-2)*40) {
            occupiedCells[this.getY() / 40][0] = this.getId();
            this.setX(0);
        }

        if (this.getY() == 40) {
            int newY = (int) ((Math.floor((float) Constant.window_height/40)-2)) * 40;
            occupiedCells[newY / 40][this.getX() / 40] = this.getId();
            this.setY(newY);

        } else if (this.getY() == (Math.floor((float) Constant.window_height/40)-2)*40) {
            occupiedCells[1][this.getX() / 40] = this.getId();
            this.setY(Constant.field_size);
        }
    }
}
