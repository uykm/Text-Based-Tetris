package logic;

public class Score implements Comparable<Score> {

    private String playerName;
    private int score;

    public Score(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public int compareTo(Score other) {
        return Integer.compare(other.score, this.score); // 점수 내림차순 정렬
    }
}
