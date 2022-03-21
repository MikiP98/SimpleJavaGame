package com.company.classes.characters;

import com.company.classes.AttackType;
import com.company.classes.CharacterClass;

public class Warrior extends CharacterClass {

     public int key1;
     public int key2;
     public int key3;
     public int key4;
     public int key5;
     public int key6;

   public Warrior(String name, int x, int y, int key1, int key2, int key3, int key4, int key5, int key6) {
        super(name, x, y, key1, key2, key3, key4, key5, key6);
        this.setLevel(1);
        this.setMaxHealthPoints(1000);
        this.setHealthPoints(1000);
        this.setManaPoints(200);
        this.setMaxManaPoints(200);
        this.setAttackType(AttackType.PHYSICAL);
        this.setAttackAmount(5);
        this.setName(name);

        this.setX(0);
        this.setY(0);
        this.uploadImage("1.png", "2.png", "3.png");

        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
        this.key4 = key4;
   }
}
