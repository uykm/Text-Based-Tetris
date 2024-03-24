package game.Blocks;

public enum BlockType {
    IBlock,
    JBlock,
    LBlock,
    OBlock,
    SBlock,
    TBlock,
    ZBlock;
    public static BlockType getRandomBlockType() {
        return values()[(int) (Math.random() * values().length)];
    }
}
