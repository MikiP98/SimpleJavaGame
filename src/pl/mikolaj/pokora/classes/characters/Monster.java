package pl.mikolaj.pokora.classes.characters;

import lombok.Getter;
import lombok.Setter;
import pl.mikolaj.pokora.GameField;
import pl.mikolaj.pokora.classes.CharacterClass;
import pl.mikolaj.pokora.classes.Constant;
import pl.mikolaj.pokora.classes.Things;
import pl.mikolaj.pokora.classes.enums.AttackType;

import java.io.IOException;

@Getter
@Setter
public class Monster extends CharacterClass { //to create!!!

    private boolean monster = true;

    public Monster(String name) throws IOException, InterruptedException {

        super(name, 500, true);

        int destinationX;
        int destinationY;

        //wait(Math.round(Math.random() * 30 * 1000));

        Thread initiationThread = new Thread(() -> {
            try {
                Thread.sleep(Math.round(Math.random() * 2 * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            initiation(name);

            Thread aiThread = new Thread(() -> {
                while (true) {
                    try {
                        //Thread.sleep(500);
                        Thread.sleep(300 + Math.round(Math.random() * 250));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    GameField.walk_to_nearest(this);
                }
            });
            aiThread.start();

        });
        initiationThread.start();

        /*
        int x = randomX();
        int y = randomY();

        this.setLevel(1);
        this.setMaxHealthPoints(4000);
        this.setHealthPoints(4000);
        this.setMaxManaPoints(100);
        this.setManaPoints(100);
        this.setAttackType(AttackType.PHYSICAL);
        this.setAttackAmount(5);
        this.setName(name);

        this.setX(x);
        this.setY(y);
        this.uploadImage("img/qoi/size_40/characters/warrior/warrior_base.qoi", "img/qoi/size_40/characters/warrior/warrior_attack_left.qoi", "img/qoi/size_40/characters/warrior/warrior_attack_right.qoi");
        */
    }

    private void initiation(String name) {
        int x = randomX();
        int y = randomY();

        this.setLevel(1);
        this.setMaxHealthPoints(400);
        this.setHealthPoints(400);
        this.setMaxManaPoints(100);
        this.setManaPoints(100);
        this.setAttackType(AttackType.PHYSICAL);
        this.setAttackAmount(5);
        this.setName(name);

        this.initiatePosition(x, y);
        this.setX(x);
        this.setY(y);
        try {
            this.uploadImage("img/qoi/size_40/characters/warrior/warrior_base.qoi", "img/qoi/size_40/characters/warrior/warrior_attack_left.qoi", "img/qoi/size_40/characters/warrior/warrior_attack_right.qoi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restart() {
        int x = randomX();
        int y = randomY();

        this.setLevel(2);
        this.setHealthPoints(400);

        this.initiatePosition(x, y);
        this.setX(x);
        this.setY(y);
    }

    private static int randomX() {
        return (int) ((Math.floor(Math.random() * Constant.window_width / Constant.field_size) - 1) * Constant.field_size);
    }

    private static int randomY() {
        return (int) (Constant.field_size + ((Math.floor(Math.random() * Constant.window_height / Constant.field_size) - 2) * Constant.field_size));
    }

    public boolean attack(CharacterClass attackedCharacter, int value, boolean cant_die) {
        boolean val = attackedCharacter.loseHealth(value, cant_die);
        return val;
    }
    public boolean attack(CharacterClass attackedCharacter) {
        return attack(attackedCharacter, (int) Math.ceil(this.getAttackAmount() * (1 + 0.2 * (this.getLevel() - 1))), true);
    }
}
