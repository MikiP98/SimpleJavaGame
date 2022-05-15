package pl.mikolaj.pokora;

import pl.mikolaj.pokora.classes.characters.Monster;
import pl.mikolaj.pokora.classes.enums.AttackType;
import pl.mikolaj.pokora.classes.CharacterClass;
import pl.mikolaj.pokora.classes.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameField extends JPanel {
    private final Team team;
    private static CharacterClass[] players;
    public GameField(Team team) {
        this.team = team;
        players = team.getTeamMembers();

        setFocusable(true);
        addKeyListener(new FieldKeyListener());
    }

    public static void walk_to_nearest(CharacterClass monster) {
        double distance = -1;
        int x = monster.getX();
        int y = monster.getY();

        int destination_character_id = -1;

        int i = 0;
        for (CharacterClass player : players) {
            int distanceX = x - player.getX();
            int distanceY = y - player.getY();
            double new_distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            if ((distance == -1 || new_distance < distance) && i+1 != monster.getId()) {
                distance = new_distance;
                destination_character_id = i;
            }
            i++;
        }

        int destinationX;
        int destinationY = players[destination_character_id].getY();

        boolean attack_right = false;
        if (players[destination_character_id].getX() > x) {
            attack_right = true;
            destinationX = players[destination_character_id].getX() - 40;
        } else {
            destinationX = players[destination_character_id].getX() + 40;
        }

        if ( x == destinationX && y == destinationY) {
            if (attack_right) {
                tryAttackRight(monster);
            } else {
                tryAttackLeft(monster);
            }
        } else {
            if (Math.abs(destinationX - x) >= Math.abs(destinationY - y)) {
                if (destinationX > x) {
                    System.out.println("cordi: " + destinationX + ", " + x);
                    monster.right(1);
                } else {
                    monster.left(1);
                }
            } else {
                if (destinationY > y) {
                    monster.down(1);
                } else {
                    monster.up(1);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (CharacterClass player : players) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
            g.drawString("LV: " + player.getLevel(), player.getX(), player.getY()-30);
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
                        if (player.getManaPoints() >= (int) Math.floor(player.getAttackManaCost() / (1 + 0.2 * (player.getLevel() - 1)))) {
                            player.loseMana((int) Math.floor(player.getAttackManaCost() / (1 + 0.2 * (player.getLevel() - 1))));
                        } else {
                            player.changeAttackingState();
                            continue;
                        }
                    }

                    try {
                        int attackedPlayerId = CharacterClass.occupiedCells[(player.getY() / Constant.field_size)][(player.getX() / Constant.field_size) - 1];
                        if (attackedPlayerId > 0) {
                            if(player.attack(players[attackedPlayerId - 1])) {
                                if (players[attackedPlayerId - 1].monster) {
                                    player.levelUp();
                                    //restart the monster
                                    CharacterClass.occupiedCells[(player.getY() / Constant.field_size)][(player.getX() / Constant.field_size) - 1] = 0;
                                    players[attackedPlayerId - 1].restart((Monster) players[attackedPlayerId - 1]);

                                } else {
                                    team.mw.gameOver(players[attackedPlayerId - 1].getName());
                                }
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
                                                    }, (int) Math.floor(player.getAttackAnimationLength() / (1 + 0.2 * (player.getLevel() - 1)))
                                            );
                                        }
                                    }, (int) Math.floor(player.getAttackAnimationLength() / (1 + 0.2 * (player.getLevel() - 1)))
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
                                }, (int) Math.floor(player.getAttackAnimationLength() / (1 + 0.2 * (player.getLevel() - 1)))
                        );

                    } catch (Exception h) {
                        h.printStackTrace();
                    }


                }
                if (key == player.key6 && !player.isAttacking()) {
                    player.changeAttackingState();

                    if (player.getAttackType() == AttackType.MAGICAL || player.getAttackType() == AttackType.MAGICAL_RANGED) {
                        if (player.getManaPoints() >= (int) Math.floor(player.getAttackManaCost() / (1 + 0.2 * (player.getLevel() - 1)))) {
                            player.loseMana((int) Math.floor(player.getAttackManaCost() / (1 + 0.2 * (player.getLevel() - 1))));
                        } else {
                            player.changeAttackingState();
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
                                if (players[attackedPlayerId - 1].monster) {
                                    player.levelUp();
                                    //restart the monster
                                    CharacterClass.occupiedCells[(player.getY() / Constant.field_size)][(player.getX() / Constant.field_size) + 1] = 0;
                                    players[attackedPlayerId - 1].restart((Monster) players[attackedPlayerId - 1]);

                                } else {
                                    team.mw.gameOver(players[attackedPlayerId - 1].getName());
                                }
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
                                                    }, (int) Math.floor(player.getAttackAnimationLength() / (1 + 0.2 * (player.getLevel() - 1)))
                                            );
                                        }
                                    }, (int) Math.floor(player.getAttackAnimationLength() / (1 + 0.2 * (player.getLevel() - 1)))
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
                                }, (int) Math.floor(player.getAttackAnimationLength() / (1 + 0.2 * (player.getLevel() - 1)))
                        );

                    } catch (Exception g) {
                        g.printStackTrace();
                    }
                }
            }
        }
    }

    private static void tryAttackRight(CharacterClass player) {
        player.changeAttackingState();

        if (player.getAttackType() == AttackType.MAGICAL || player.getAttackType() == AttackType.MAGICAL_RANGED) {
            if (player.getManaPoints() >= player.getAttackManaCost()) {
                player.loseMana(player.getAttackManaCost());
            } else {
                return;
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
                    //team.mw.gameOver(players[attackedPlayerId - 1].getName());
                }

            } else if (player.getAttackType() == AttackType.PHYSICAL_RANGED || player.getAttackType() == AttackType.MAGICAL_RANGED) {

                attackedPlayerId = CharacterClass.occupiedCells[(player.getY() / Constant.field_size)][(player.getX() / Constant.field_size) + 2];
                if (attackedPlayerId > 0) {
                    if (player.attack(players[attackedPlayerId - 1])) {
                        //team.mw.gameOver(players[attackedPlayerId - 1].getName());
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
                                        }, (int) Math.floor(player.getAttackAnimationLength() / (1 + 0.2 * (player.getLevel() - 1)))
                                );
                            }
                        }, (int) Math.floor(player.getAttackAnimationLength() / (1 + 0.2 * (player.getLevel() - 1)))
                );
                return;
            }

            player.setAttackRightImage();
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            player.setBaseImage();
                            player.changeAttackingState();
                        }
                    }, (int) Math.floor(player.getAttackAnimationLength() / (1 + 0.2 * (player.getLevel() - 1)))
            );

        } catch (Exception g) {
            g.printStackTrace();
        }
    }

    private static void tryAttackLeft(CharacterClass player) {
        player.changeAttackingState();

        if (player.getAttackType() == AttackType.MAGICAL || player.getAttackType() == AttackType.MAGICAL_RANGED) {
            if (player.getManaPoints() >= player.getAttackManaCost()) {
                player.loseMana(player.getAttackManaCost());
            } else {
                return;
            }
        }

        try {
            int attackedPlayerId = CharacterClass.occupiedCells[(player.getY() / Constant.field_size)][(player.getX() / Constant.field_size) - 1];
            if (attackedPlayerId > 0) {
                if(player.attack(players[attackedPlayerId - 1])) {
                    MainWindow.gameOver_v2(players[attackedPlayerId - 1].getName());
                }

            } else if (player.getAttackType() == AttackType.PHYSICAL_RANGED || player.getAttackType() == AttackType.MAGICAL_RANGED) {

                attackedPlayerId = CharacterClass.occupiedCells[(player.getY() / Constant.field_size)][(player.getX() / Constant.field_size) - 2];
                if (attackedPlayerId > 0) {
                    if (player.attack(players[attackedPlayerId - 1])) {
                        //team.mw.gameOver(players[attackedPlayerId - 1].getName());
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
                                        }, (int) Math.floor(player.getAttackAnimationLength() / (1 + 0.2 * (player.getLevel() - 1)))
                                );
                            }
                        }, (int) Math.floor(player.getAttackAnimationLength() / (1 + 0.2 * (player.getLevel() - 1)))
                );
                return;
            }

            player.setAttackLeftImage();
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        public void run() {
                            player.setBaseImage();
                            player.changeAttackingState();
                        }
                    }, (int) Math.floor(player.getAttackAnimationLength() / (1 + 0.2 * (player.getLevel() - 1)))
            );

        } catch (Exception h) {
            h.printStackTrace();
        }
    }
}
