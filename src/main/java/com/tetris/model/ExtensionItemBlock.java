package com.tetris.model;

import com.tetris.logic.Block;

import java.awt.*;

public class ExtensionItemBlock extends Block {

    public BlockType getBlockType() {
        return BlockType.ExtensionItemBlock;
    }
    public ExtensionItemBlock() {
        super(new int[][] {
                {0, 0, 0, 0},
                {0, 14, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        }, new Color[]{Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE}
        , BlockType.ExtensionItemBlock); // 색상은 예시, 실제 게임에 맞게 조정
    }
}
