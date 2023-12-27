public class Score {
    private int score;

    public Score() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int newScore){this.score = newScore;};

    public void increaseScore(int points) {
        score += points;
    }

    public void resetScore() {
        score = 0;
    }
}
