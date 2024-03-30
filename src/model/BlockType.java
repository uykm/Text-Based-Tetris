package model;

public enum BlockType {
    IBlock,
    JBlock,
    LBlock,
    OBlock,
    SBlock,
    TBlock,
    ZBlock,
    NullBlock;
    public static BlockType getRandomBlockType() {
        return values()[(int) (Math.random() * (values().length-1))];
    }
    public static BlockType getBlockTypeByIndex(int index) {
        return values()[index];
    }
}