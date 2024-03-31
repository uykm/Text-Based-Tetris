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

import static component.Button.createBtn;
import static component.ScreenSize.setWidthHeight;

public class ScoreboardScreen extends JFrame implements ActionListener {

    SettingController settingController = new SettingController();
    ScoreController manager = new ScoreController();
    JButton menuBtn;
    public ScoreboardScreen() {

        setTitle("Tetris - ScoreBoard"); // 창의 제목 설정
        String screenSize = settingController.getScreenSize("screenSize", "small");
        switch (screenSize) {
            case "small":
                setWidthHeight(400, 550, this);
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

        // 메인 패널
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // 스코어 보드 패널
        JPanel scorePanel = createScorePanel();
        mainPanel.add(scorePanel);

        // 메뉴 버튼
        JPanel btnPanel = new JPanel();
        menuBtn = createBtn("Menu", "menu", this);
        btnPanel.add(menuBtn);
        mainPanel.add(btnPanel);

        // 설정이 끝난 패널을 JFrame에 추가
        menuBtn.addKeyListener(new MyKeyListener());
        add(mainPanel);
        setVisible(true);
    }

    private JPanel createScorePanel() {

        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(11, 1)); // 제목 행(1칸) + 10개의 스코어(10칸)

        JLabel title = new JLabel("Ranking", SwingConstants.CENTER); // 가운데 정렬
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 24)); // 제목의 폰트 설정
        scorePanel.add(title);

        // 예시 데이터 추가
        List<Score> topScores = manager.getScores();

        // 상위 10개 스코어 표시
        for (int i = 0; i < topScores.size(); i++) {
            Score score = topScores.get(i);
            JLabel scoreLabel = new JLabel((i + 1) + ". " + score.getPlayerName() + " - " + score.getScore(), SwingConstants.CENTER);
            scorePanel.add(scoreLabel);
        }
        return scorePanel;
    }

    class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {
                setVisible(false);
                new MainMenuScreen();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("menu")) {
            new MainMenuScreen();
            setVisible(false);
        }
    }
}
