package ui;

import logic.Score;
import logic.ScoreController;
import logic.SettingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import static component.ScreenSize.*;

public class GameOverUI extends JFrame {

    ScoreController scoreController = new ScoreController();
    SettingController settingController = new SettingController();
    JButton exitButton;
    public GameOverUI() {
        setTitle("Tetris - GameOver"); // 창의 제목 설정
        String screenSize = settingController.getSetting("screenSize", "small");
        switch (screenSize) {
            case "small":
                setWidthHeight(400, 550, this);
                break;
            case "medium":
                setWidthHeight(600, 750, this);
                break;
            case "big":
                setWidthHeight(800, 950, this);
                break;
            default:
                setWidthHeight(600, 750, this);
                break;
        }
        setLocationRelativeTo(null); // 창을 화면 가운데에 위치시킴
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 닫으면 프로그램 종료

        // 메인 패널 : 상단(ScoreBoard) + 중앙(입력 폼) + 하단(종료 버튼)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // 수직으로 정렬

        // 상단 : ScoreBoard
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(11, 1)); // 제목 행(1칸) + 10개의 스코어(10칸)

        JLabel title = new JLabel("Ranking", SwingConstants.CENTER); // 가운데 정렬
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 24)); // 제목의 폰트 설정
        scorePanel.add(title);

        // 예시 데이터 추가
        List<Score> topScores = scoreController.getScores();

        // 상위 10개 스코어 표시
        for (int i = 0; i < topScores.size(); i++) {
            Score score = topScores.get(i);
            JLabel scoreLabel = new JLabel((i + 1) + ". " + score.getPlayerName() + " - " + score.getScore(), SwingConstants.CENTER);
            scorePanel.add(scoreLabel);
        }
        mainPanel.add(scorePanel);

        // 상단 패널과 중앙 패널 사이의 간격 추가
        mainPanel.add(Box.createVerticalStrut(10));

        // 중앙 : 이름 입력 및 제출 버튼
        JPanel inputPanel = new JPanel();
        JTextField nameField = new JTextField(20);
        JButton submitButton = new JButton("Register");
        inputPanel.add(nameField);
        inputPanel.add(submitButton);
        mainPanel.add(inputPanel);

        // 하단 : 종료 버튼
        JPanel exitPanel = new JPanel();
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        exitButton.addKeyListener(new MyKeyListener());

        exitPanel.add(exitButton);
        mainPanel.add(exitPanel);

        // 설정이 끝난 패널을 JFrame에 추가
        add(mainPanel);

        exitButton.requestFocusInWindow();
        setFocusable(true);
        setVisible(true);
    }

    class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            System.out.println("Keycode: " + keyCode);
            if (keyCode == KeyEvent.VK_ENTER) {
                System.out.println("KEYCODE: ENTER");
                new StartScreen();
                setVisible(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameOverUI();
            }
        });
    }
}

