package ui;

import score.Score;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoardUI extends JFrame {

    static int WIDTH = 500, HEIGHT = 500;

    public ScoreBoardUI() {
        setTitle("Tetris - ScoreBoard"); // 창의 제목 설정
        setSize(WIDTH, HEIGHT); // 창 크기 : 600px x 600px
        setLocationRelativeTo(null); // 창을 화면 가운데에 위치시킴
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 닫으면 프로그램 종료

        // ScoreBoard 패널 설정
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12, 2)); // 제목 행(1칸) + 10개의 스코어(10칸) + Back 버튼(1칸)

        JLabel title = new JLabel("Ranking", SwingConstants.CENTER); // 가운데 정렬
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 24)); // 제목의 폰트 설정
        panel.add(title);

        // 예시 데이터 추가
        List<Score> topScores = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            topScores.add(new Score("Player" + (i + 1), (10 - i) * 500));
        }
        // 상위 10개 스코어 표시
        for (int i = 0; i < topScores.size(); i++) {
            Score score = topScores.get(i);
            JLabel scoreLabel = new JLabel((i + 1) + ". " + score.getPlayerName() + " - " + score.getScore(), SwingConstants.CENTER);
            panel.add(scoreLabel);
        }

        // Back 버튼
        JPanel backPanel = new JPanel();
        JButton backButton = new JButton("Back");
        backPanel.add(backButton);
        panel.add(backPanel);

        // 설정이 끝난 패널을 JFrame에 추가
        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {

        // 스윙 컴포넌트들은 이벤트 디스패치 스레드에서 실행.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ScoreBoardUI();
            }
        });
    }
}
