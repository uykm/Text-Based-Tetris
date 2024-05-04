package com.tetris.logic;

import java.util.LinkedList;
import java.util.Queue;

public class InGameScoreController {

    private int score;

    final private Queue<String> Messages;

    public InGameScoreController() {
        this.score = 0;
        this.Messages = new LinkedList<>();
    }

    public int getScore() {return score;}

    public String getScoreMessages() {
        StringBuilder sb = new StringBuilder();
        for (String message : Messages) {
            sb.append(message).append("\n");
        }
        return sb.toString();
    }


    // 점수 추가
    private void addScore(int score) {
        this.score += score;
    }

    // 블록 이동 시 점수 추가
    public void addScoreOnBlockMoveDown() {
        addScore(1);
    }

    public void addScoreOnBlockMoveDown(int changedY) {
        addScore(changedY);
    }


    // 라인 삭제 시 점수 추가, 한번에 여러 라인 삭제 시 추가 점수, 3초 안에 라인 삭제 시 추가 점수
    public void addScoreOnLineEraseWithBonus(int lineCount, long diff) {
        int multiplier = 1;
        if (diff < 3000) {
            multiplier = 2;
        }
        int addedScore = (13 * lineCount - 3) * multiplier;
        addScore(addedScore);
        if (multiplier == 2) {
            if (lineCount == 1) {
                addScoreMessage("+" + addedScore + ": Line erased (x2)");
            } else {
                addScoreMessage("+" + addedScore + ": Lines erased (x2)");
            }
        } else {
            if (lineCount == 1) {
                addScoreMessage("+" + addedScore + ": Line erased");
            } else {
                addScoreMessage("+" + addedScore + ": Lines erased");
            }
        }
    }

    // 10개의 블럭이 생성된 경우에도 지워진 라인이 하나도 없을 때 50점 감점
    public void subScoreOnLineNotEraseIn10Blocks() {
        addScore(-50);
        addScoreMessage("-50: No line erased in 10 blocks");
    }

    public void addScoreMessage(String message) {
        if (Messages.size() > 7) {
            Messages.remove();
        }
        Messages.add(message);
    }
}
