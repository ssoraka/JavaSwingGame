package z_main;

public class Player {
    final static int DEFAULT_HP = 10;

    private String name;

    private int level;
    private int experience;
    private int expNextLevel;
    private int hp;

    //stats
    int helmet;
    int attack;
    int defence;

    // new player
    public Player(String name) {
        this.name = name;
        level = 1;
        expNextLevel = experienceForNextLevel(1);
        hp = DEFAULT_HP;
    }

    void addExperience(int exp) {
        experience += exp;
        if (experience >= expNextLevel) {
            experience = 0;
            level++;
            expNextLevel = experienceForNextLevel(level);
        }
    }

    private int experienceForNextLevel(int level) {
        return (level * 1000 + (level - 1)^2 * 450);
    }

}
