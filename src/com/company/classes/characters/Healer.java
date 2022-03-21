package com.company.classes.characters;

import com.company.classes.AttackType;
import com.company.classes.CharacterClass;

public class Healer  extends CharacterClass {
    public Healer(String name) {
        super(name);
        //this.setLevel(1);
        this.setMaxHealthPoints(1000);
        //this.setHealthPoints(1000);
        this.setManaPoints(200);
        //this.setMaxManaPoints(200);
        //this.setAttackType(AttackType.PHYSICAL);
        //this.setAttackAmount(5);
        //this.setName(name);

        this.setX(0);
        this.setY(300);
        this.uploadImage("1.png", "2.png", "3.png");
    }
}
