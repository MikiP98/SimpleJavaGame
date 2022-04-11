package pl.mikolaj.pokora;

import pl.mikolaj.pokora.classes.enums.AttackType;
import pl.mikolaj.pokora.classes.CharacterClass;
import pl.mikolaj.pokora.classes.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameField extends JPanel {
    private Team team;
    private CharacterClass[] players;
    public GameField(Team team) {
        this.team = team;
        this.players = team.getTeamMembers();

        setFocusable(true);
        addKeyListener(new FieldKeyListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (CharacterClass player : players) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
            g.drawString("HP: " + player.getHealthPoints(), player.getX(), player.getY()-16);
            g.drawString("Mana: " + player.getManaPoints(), player.getX(), player.getY()-2);
        }
    }

    public class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            for (CharacterClass player : players) {
                if (key == player.key1) {
                    //player.setX(player.getX() - 40);
                    player.left(40);
                }
                if (key == player.key2) {
                    //player.setX(player.getX() + 40);
                    player.right(40);
                }
                if (key == player.key3) {
                    //player.setY(player.getY() - 40);
                    player.up(40);
                }
                if (key == player.key4) {
                    //player.setY(player.getY() + 40);
                    player.down(40);
                }
                if (key == player.key5 && !player.isAttacking()) {
                    player.changeAttackingState();

                    if (player.getAttackType() == AttackType.MAGICAL || player.getAttackType() == AttackType.MAGICAL_RANGED) {
                        if (player.getManaPoints() >= player.getAttackManaCost()) {
                            player.loseMana(player.getAttackManaCost());
                        } else {
                            continue;
                        }
                    }

                    try {
                        int attackedPlayerId = CharacterClass.occupiedCells[(player.getY() / Constant.field_size)][(player.getX() / Constant.field_size) - 1];
                        if (attackedPlayerId > 0) {
                            if(player.attack(players[attackedPlayerId - 1])) {
                                team.mw.gameOver(players[attackedPlayerId - 1].getName());
                            }

                        } else if (player.getAttackType() == AttackType.PHYSICAL_RANGED || player.getAttackType() == AttackType.MAGICAL_RANGED) {

                            attackedPlayerId = CharacterClass.occupiedCells[(player.getY() / Constant.field_size)][(player.getX() / Constant.field_size) - 2];
                            if (attackedPlayerId > 0) {
                                if (player.attack(players[attackedPlayerId - 1])) {
                                    team.mw.gameOver(players[attackedPlayerId - 1].getName());
                                }
                            }
                            player.setAttackLeftImage();
                            new java.util.Timer().schedule(
                                    new java.util.TimerTask() {
                                        public void run() {
                                            player.setX(player.getX() - 40);
                                            player.setRangedAttackLeftImage();
                                            new java.util.Timer().schedule(
                                                    new java.util.TimerTask() {
                                                        public void run() {
                                                            player.setX(player.getX() + 40);
                                                            player.setBaseImage();
                                                            player.changeAttackingState();
                                                        }
                                                    }, player.getAttackAnimationLength()
                                            );
                                        }
                                    }, player.getAttackAnimationLength()
                            );
                            continue;
                        }

                        player.setAttackLeftImage();
                        new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                    public void run() {
                                        player.setBaseImage();
                                        player.changeAttackingState();
                                    }
                                }, player.getAttackAnimationLength()
                        );

                    } catch (Exception h) {
                        h.printStackTrace();
                    }


                }
                if (key == player.key6 && !player.isAttacking()) {
                    player.changeAttackingState();

                    if (player.getAttackType() == AttackType.MAGICAL || player.getAttackType() == AttackType.MAGICAL_RANGED) {
                        if (player.getManaPoints() >= player.getAttackManaCost()) {
                            player.loseMana(player.getAttackManaCost());
                        } else {
                            continue;
                        }
                    }

                    /*
                    player.setAttackRightImage();
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    player.setBaseImage();
                                }
                            }, 360
                    );
                    */
                    try {
                        int attackedPlayerId = CharacterClass.occupiedCells[(player.getY() / Constant.field_size)][(player.getX() / Constant.field_size) + 1];
                        if (attackedPlayerId > 0) {
                            if(player.attack(players[attackedPlayerId - 1])) {
                                team.mw.gameOver(players[attackedPlayerId - 1].getName());
                            }

                        } else if (player.getAttackType() == AttackType.PHYSICAL_RANGED || player.getAttackType() == AttackType.MAGICAL_RANGED) {

                            attackedPlayerId = CharacterClass.occupiedCells[(player.getY() / Constant.field_size)][(player.getX() / Constant.field_size) + 2];
                            if (attackedPlayerId > 0) {
                                if (player.attack(players[attackedPlayerId - 1])) {
                                    team.mw.gameOver(players[attackedPlayerId - 1].getName());
                                }
                            }

                            player.setAttackRightImage();
                            new java.util.Timer().schedule(
                                    new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                            player.setRangedAttackRightImage();
                                            new java.util.Timer().schedule(
                                                    new java.util.TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            player.setBaseImage();
                                                            player.changeAttackingState();
                                                        }
                                                    }, player.getAttackAnimationLength()
                                            );
                                        }
                                    }, player.getAttackAnimationLength()
                            );
                            continue;
                        }

                        player.setAttackRightImage();
                        new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                        player.setBaseImage();
                                        player.changeAttackingState();
                                    }
                                }, player.getAttackAnimationLength()
                        );

                    } catch (Exception g) {
                        g.printStackTrace();
                    }
                }
            }
        }
    }
}
