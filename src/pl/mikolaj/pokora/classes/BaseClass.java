package pl.mikolaj.pokora.classes;

public interface BaseClass {
    boolean attack(CharacterClass attackedCharacter);
    boolean restoreHealth(int amount);
    //boolean loseHealth(int amount);

    boolean loseHealth(int amount, boolean cant_die);

    void restoreMana(int amount);
    void loseMana(int amount);
    void levelUp();
    void info();
}
