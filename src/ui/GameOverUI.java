package ui;

import logic.ScoreController;
import logic.SettingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static component.Button.createBtn;
import static java.lang.System.exit;

import static component.ScreenSize.*;

public class GameOverUI extends JFrame implements ActionListener {

    ScoreController scoreController = new ScoreController();
    SettingController settingController = new SettingController();

    JTextField nameField;
    JButton exitButton;
    private int playerScore;

    public GameOverUI(int curr_score) {
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

        JLabel title = new JLabel("New Top10 Score!!", SwingConstants.CENTER); // 가운데 정렬
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 24)); // 제목의 폰트 설정
        scorePanel.add(title);

        playerScore = curr_score;
        JLabel score = new JLabel("Your score : " + curr_score, SwingConstants.CENTER);
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 18)); // 제목의 폰트 설정
        scorePanel.add(score);

        mainPanel.add(scorePanel);

        // 상단 패널과 중앙 패널 사이의 간격 추가
        mainPanel.add(Box.createVerticalStrut(10));

        // 중앙 : 이름 입력 칸 + 제출 버튼
        JPanel inputPanel = new JPanel();

        nameField = new JTextField("Please register playerName", 20);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setForeground(Color.GRAY);
        inputPanel.add(nameField);

        // 이름 필드에 대한 포커스 리스너 추가
        nameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameField.getText().equals("Please register playerName")) {
                    nameField.setText("");
                    nameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nameField.getText().isEmpty()) {
                    nameField.setForeground(Color.GRAY);
                    nameField.setText("Please register playerName");
                }
            }
        });

        // 등록 버튼
        JButton submitButton = createBtn("Submit", "submit", this);
        inputPanel.add(submitButton);

        mainPanel.add(inputPanel);

        // 하단 : 종료 버튼
        JPanel exitPanel = new JPanel();
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit(0);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        setVisible(false);
        if (command.equals("submit")) {
            String name = !nameField.getText().isEmpty() ? nameField.getText() : "익명";
            scoreController.addScore(name, playerScore);
            setVisible(false);
            new ScoreBoardUI();
        }
    }

    private class MyKeyListener extends KeyAdapter {
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
}