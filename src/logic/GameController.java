package logic;

import ui.InGameScreen;

import java.awt.event.KeyListener;

public class GameController {
    private BoardController boardController; // 게임 로직 관련
    private InGameScreen inGameScreen; // UI 관련
    private Board board;

    public GameController() {
        this.boardController = new BoardController();
        // this.inGameScreen = new InGameScreen(board);
    }

    // 사용자 입력을 처리하는 메서드
    public void processInput(String input) {
        // 입력에 따라 게임 상태 변경
        // 예: "LEFT" 입력시 블록을 왼쪽으로 이동
        updateGameState();
    }

    // 게임 상태 업데이트 및 UI 반영
    private void updateGameState() {
        // 게임 상태를 업데이트하고
    }
}
