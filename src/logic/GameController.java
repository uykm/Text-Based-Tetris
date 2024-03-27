package logic;

import model.Direction;
import ui.InGameScreen;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// 게임의 전반적인 흐름을 제어하는 클래스
public class GameController {
    private BoardController boardController;
    private InGameScreen inGameScreen;
    private JFrame frame;
    private Timer timer;

    // 게임 컨트롤러 생성자
    public GameController() {
        initUI();
        this.boardController = new BoardController();
        this.inGameScreen = new InGameScreen(this.boardController);
        startGame();
    }

    // 게임 UI 초기화
    private void initUI() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Tetris Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(inGameScreen);
            frame.pack();
            frame.setLocationRelativeTo(null);
            setupKeyListener(frame);
            frame.setVisible(true);
            frame.setResizable(false);

            // 콘솔에서 상태 확인을 위한 임시 코드
            // 실제 게임에서는 게임 로직에 따라 점수를 업데이트하게 됩니다.
            // TODO: 3/24/24 : 현재 점수 계산 로직 없음, BoardController 또는 GameController에서 점수 계산 로직 추가 필요
            inGameScreen.updateScore(0); // 점수를 임시로 0으로 설정
        });
    }

    // 키보드 이벤트 처리
    // TODO: 3/24/24 : 효정이가 KeyListener 구현 하면 바꿀 예정
    private void setupKeyListener(JFrame frame) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        boardController.moveBlock(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        boardController.moveBlock(Direction.RIGHT); 
                        break;
                    case KeyEvent.VK_DOWN:
                        boardController.moveBlock(Direction.DOWN);
                        inGameScreen.updateBoard();
                        break;
                    case KeyEvent.VK_UP:
                        boardController.moveBlock(Direction.UP);
                        break;
                    case KeyEvent.VK_SPACE:
                        boardController.moveBlock(Direction.SPACE);
                        inGameScreen.updateBoard();
                        break;
                        //esc 누르면 게임 중지, 한번 더 누르면 다시 실행
                    case KeyEvent.VK_ESCAPE:
                        if(timer.isRunning()){
                            timer.stop();
                        } else {
                            timer.start();
                        }
                        break;
                }
                inGameScreen.repaint();
            }
        });
    }

    private void startGame() {
        timer = new Timer(1000, e -> {
            boardController.moveBlock(Direction.DOWN);
            inGameScreen.updateBoard(); // Assuming InGameScreen has a method to update the UI based on the current game state
        });
        timer.start();
    }
}
