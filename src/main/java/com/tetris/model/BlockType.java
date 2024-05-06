package com.tetris.model;

public enum BlockType {
    IBlock,
    JBlock,
    LBlock,
    OBlock,
    SBlock,
    TBlock,
    ZBlock,
    NullBlock,
    LineEraseItemBlock,
    WeightItemBlock,
    BombItemBlock,
    ExtensionItemBlock,
    WaterItemBlock;

    public static BlockType getBlockTypeByIndex(int index) {
        return values()[index];
    }
}
