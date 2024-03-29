package ui;

import logic.Score;
import logic.ScoreController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import static component.Button.createBtn;
import static component.ScreenSize._height;
import static component.ScreenSize._width;
import static component.ScreenSize.setWidthHeight;

public class ScoreBoardUI extends JFrame implements ActionListener {

    ScoreController manager = new ScoreController();
    JButton backButton;
    public ScoreBoardUI() {

        setTitle("Tetris - ScoreBoard"); // 창의 제목 설정
        setWidthHeight(_width, _height, this);
        setLocationRelativeTo(null); // 창을 화면 가운데에 위치시킴
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 닫으면 프로그램 종료

        // ScoreBoard 패널 설정
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12, 2)); // 제목 행(1칸) + 10개의 스코어(10칸) + Back 버튼(1칸)

        JLabel title = new JLabel("Ranking", SwingConstants.CENTER); // 가운데 정렬
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 24)); // 제목의 폰트 설정
        panel.add(title);

        // 예시 데이터 추가
        List<Score> topScores = manager.getScores();

        // 상위 10개 스코어 표시
        for (int i = 0; i < topScores.size(); i++) {
            Score score = topScores.get(i);
            JLabel scoreLabel = new JLabel((i + 1) + ". " + score.getPlayerName() + " - " + score.getScore(), SwingConstants.CENTER);
            panel.add(scoreLabel);
        }

        // 메뉴 버튼
        JPanel backPanel = new JPanel();
        backButton = createBtn("Menu", "back", this);
        backPanel.add(backButton);
        panel.add(backPanel);

        // 설정이 끝난 패널을 JFrame에 추가
        backButton.addKeyListener(new MyKeyListener());
        add(panel);
        setVisible(true);
    }

    class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {
                setVisible(false);
                new StartScreen();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("back")) {
            new StartScreen();
            setVisible(false);
        }
    }
}
