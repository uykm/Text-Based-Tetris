package logic;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreController {
    private final String filePath = "ranking.txt";
    private List<Score> scores;

    public ScoreController() {
        scores = new ArrayList<>();
        loadScores();
    }

    // 파일로부터 점수를 불러오는 메소드
    private void loadScores() {
        File file = new File(filePath);

        // 파일이 존재하지 않는 경우, 새 파일을 생성
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        // 파일로부터 점수를 불러오기
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":"); // ":"를 구분자로 사용
                if (parts.length == 2) {
                    scores.add(new Score(parts[0], Integer.parseInt(parts[1])));
                }
            }
            Collections.sort(scores); // 점수를 내림차순으로 정렬합니다.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 점수를 파일에 저장
    private void saveScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Score score : scores) {
                writer.write(score.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 점수가 Top10 안에 드는지 검사
    public boolean isScoreInTop10(int score) {

        if (scores.size() < 10) {
            return true;
        }

        return score > scores.get(9).getScore();
    }

    // 새로운 점수를 추가
    public void addScore(String playerName, int score) {
        scores.add(new Score(playerName, score));
        Collections.sort(scores); // 점수를 내림차순으로 정렬합니다.

        while (scores.size() > 10) {
            scores.remove(scores.size() - 1);
        }
        saveScores();
    }

    // 모든 점수를 초기화하는 메소드
    public void resetScores() {
        scores.clear(); // 점수 목록을 비웁니다.
        saveScores(); // 변경사항을 파일에 저장합니다.
    }

    // 점수 목록을 가져오가
    public List<Score> getScores() {
        return scores;
    }
}

