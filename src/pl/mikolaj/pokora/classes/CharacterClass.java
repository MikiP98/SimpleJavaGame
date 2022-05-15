package pl.mikolaj.pokora.classes;

//import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import pl.mikolaj.pokora.classes.characters.Monster;
import pl.mikolaj.pokora.classes.enums.AttackType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

//@Data
@ToString
@Getter
public abstract class CharacterClass implements BaseClass {
    public static int[][] occupiedCells = new int[(int) Math.floor((float) Constant.window_height / Constant.field_size)][(int) Math.floor((float) Constant.window_width / Constant.field_size)];
    private static int playerCount = 0;

    public int key1;
    public int key2;
    public int key3;
    public int key4;
    public int key5;
    public int key6;

    private final int id;
    private int healthPoints = 200;
    private int manaPoints;
    private int level;
    private AttackType attackType;
    private int attackAmount;
    private String name;
    private int maxHealthPoints;
    private int maxManaPoints;
    private int attackManaCost;
    private final int attackAnimationLength;

    private boolean attacking = false;

    private boolean godMode = false;


    public boolean monster;
    public CharacterClass(String name, int attackAnimationLength, boolean monster) {
        this.id = ++playerCount;
        this.attackAnimationLength = attackAnimationLength;
        this.monster = monster;
    }


    public CharacterClass(String name, int x, int y, int key1, int key2, int key3, int key4, int key5, int key6, int attackAnimationLength) { // x <--> y  :/
        this.id = ++playerCount;
        occupiedCells[(int) Math.floor(x/40)][(int) Math.floor(y/40)] = this.id;
        this.x = x;
        this.y = y;
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
        this.key4 = key4;
        this.key5 = key5;
        this.key6 = key6;
        this.attackAnimationLength = attackAnimationLength;
    }

    public CharacterClass(String name, int attackAnimationLength) {
        this.id = ++playerCount;
        this.attackAnimationLength = attackAnimationLength;
    }

    public void setHealthPoints(int healthPoints) {
        if (healthPoints < 0) {
            this.healthPoints = 0;
        } else this.healthPoints = Math.min(healthPoints, this.maxHealthPoints);
    }

    public void setManaPoints(int manaPoints) {
        if (manaPoints < 0) {
            this.manaPoints = 0;
        } else this.manaPoints = Math.min(manaPoints, this.maxManaPoints);
    }

    public void setLevel(int level) {
        if (level < 1) {
            System.out.println("We can't lose level");
        } else {
            this.level = level;
        }
    }

    public void changeAttackingState() {
        this.attacking = !this.attacking;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public void setAttackAmount(int attackAmount) {
        this.attackAmount = attackAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxHealthPoints(int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    public void setMaxManaPoints(int maxManaPoints) {
        this.maxManaPoints = maxManaPoints;
    }

    public void setAttackManaCost(int attackManaCost) { this.attackManaCost = attackManaCost; }

    public abstract boolean attack(CharacterClass attackedPlayer);
    public abstract boolean attack(CharacterClass attackedPlayer, int value, boolean cant_die);

    @Override
    public boolean restoreHealth(int amount) {
        setHealthPoints(this.getHealthPoints() + amount);
        return false;
    }

    @Override
    public boolean loseHealth(int amount, boolean cant_die) {
        if (!this.godMode) {
            int value_to_set = this.getHealthPoints() - amount;
            setHealthPoints(value_to_set);
            if (value_to_set <= 0) {
                if (cant_die) {
                    setHealthPoints(1);
                } else {
                    System.out.println("lost");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void restoreMana(int amount) {
        int value = this.getManaPoints() + amount;
        if (value > this.getMaxManaPoints()) {
            value = this.getMaxManaPoints();
        }
        setManaPoints(value);
    }

    @Override
    public void loseMana(int amount) {
        int value = this.getManaPoints() - amount;
        if (value < 0) {
            value = 0;
        }
        setManaPoints(value);
    }

    @Override
    public void levelUp() {
        setLevel(getLevel() + 1);
    }

    @Override
    public void info() {
        System.out.println("Name: " + this.name + "\nCurrentHP: " + this.healthPoints + "\nCurrentMana: " + this.manaPoints + "\nLevel: " + this.level);
    }

    private Image image, baseImage, attackLeftImage, attackRightImage;
    private Image rangedAttackLeftImage, rangedAttackRightImage;
    private int x, y;

    public void setX(int x) {
        if (x < 0) {
            x = 0;
        }
        this.x = x;
    }

    public void setY(int y) {
        if (y < 0) {
            y = 0;
        }
        this.y = y;
    }

    public void uploadImage(String baseImage, String attackLeftImage, String attackRightImage) throws IOException {
        //this.baseImage = new ImageIcon(baseImage).getImage();
        this.baseImage = ImageIO.read(new File(baseImage));
        this.attackLeftImage = ImageIO.read(new File(attackLeftImage));
        this.attackRightImage = ImageIO.read(new File(attackRightImage));
        setBaseImage();
    }

    public void uploadRangedImage(String rangedLeft, String rangedRight) throws IOException {
        this.rangedAttackLeftImage = ImageIO.read(new File(rangedLeft));
        this.rangedAttackRightImage = ImageIO.read(new File(rangedRight));
    }

    public void setBaseImage() {
        this.image = this.baseImage;
    }

    public void setAttackLeftImage() {
        this.image = this.attackLeftImage;
    }

    public void setAttackRightImage() {
        this.image = this.attackRightImage;
    }

    public void setRangedAttackLeftImage() {
        this.image = this.rangedAttackLeftImage;
    }

    public void setRangedAttackRightImage() {
        this.image = this.rangedAttackRightImage;
    }


    public void tryChangePosition(int newX, int newY) {
        if (newX >= 0 && newY >= Constant.field_size && newX < (Math.floor((float) Constant.window_width/Constant.field_size)-1)*Constant.field_size && newY < (Math.floor((float) Constant.window_height/Constant.field_size)-1)*Constant.field_size) {
            if (occupiedCells[newY/Constant.field_size][newX/Constant.field_size] == 0) {
                occupiedCells[this.y / Constant.field_size][this.x / Constant.field_size] = 0;
                this.x = newX;
                this.y = newY;
                occupiedCells[this.y / Constant.field_size][this.x / Constant.field_size] = this.id;
                return;
            }
        }
        this.wall();
        //attack(this, 50, true);
    }

    public void left(int value) {
        value = 1;
        tryChangePosition(this.getX() - (Constant.field_size * value), this.getY());
    }
    public void right(int value) {
        tryChangePosition(this.getX() + Constant.field_size, this.getY());
    }
    public void up(int value) {
        tryChangePosition(this.getX(), this.getY() - Constant.field_size);
    }
    public void down(int value) {
        tryChangePosition(this.getX(), this.getY() + Constant.field_size);
    }

    public void wall() {
        attack(this, 50, true);
    }

    public void setGodMode() {
        godMode = !godMode;
    }

    protected void initiatePosition(int x, int y) {
        setX(x);
        setY(y);

        occupiedCells[this.y / Constant.field_size][this.x / Constant.field_size] = this.id;
    }

    public void restart(Monster monster) {
        monster.restart();
    }
}
