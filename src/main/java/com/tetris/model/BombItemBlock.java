package com.tetris.model;

import com.tetris.logic.Block;

import java.awt.*;

public class BombItemBlock extends Block {
    public BombItemBlock() {
        super(new int[][] {
                {0, 0, 0, 0},
                {0, 0, 12, 0},
                {0, 11, 11, 0},
                {0, 11, 11, 0},
        }, new Color[]{Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE}
        , BlockType.BombItemBlock); // 색상은 예시, 실제 게임에 맞게 조정
    }

}
