package score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreManager {

    private List<Score> scores;

    public ScoreManager() {
        this.scores = new ArrayList<>();
    }
    
    public void addScore(Score score) {
        scores.add(score);
        Collections.sort(scores);
        if (scores.size() > 10) {
            scores = new ArrayList<>(scores.subList(0, 10));
        }
    }

    public List<Score> getScores() {
        return scores;
    }
}
