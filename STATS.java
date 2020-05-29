public class STATS {

    private static int level = 1;
    private static int score = 0;


    public static int getLevel() {
        return level;
    }

    public static void levelUp(){STATS.level++;}


    public static int getScore() {
        return score;
    }

    public static void addScore(int score) {
        STATS.score += score;
    }
}
