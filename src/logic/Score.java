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

    // 점수에 따라 Score 객체를 비교하는 메소드
    @Override
    public int compareTo(Score other) {
        return Integer.compare(other.score, this.score); // 내림차순 정렬
    }

    // 파일에 저장하거나 읽어올 때 사용할 문자열 형식
    @Override
    public String toString() {
        return playerName + ":" + score;
    }
}