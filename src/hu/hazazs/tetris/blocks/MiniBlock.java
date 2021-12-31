package hu.hazazs.tetris.blocks;

public final class MiniBlock {

	public static final String BLOCK = "██";
	private int rowOffset;
	private int columnOffset;

	MiniBlock(int rowOffset, int columnOffset) {
		this.rowOffset = rowOffset;
		this.columnOffset = columnOffset;
	}

	MiniBlock(MiniBlock miniBlock) {
		this.rowOffset = miniBlock.rowOffset;
		this.columnOffset = miniBlock.columnOffset;
	}

	public int getRowOffset() {
		return rowOffset;
	}

	public int getColumnOffset() {
		return columnOffset;
	}

	void rotateClockwise(boolean clockwise) {
		int newRowOffset = clockwise ? columnOffset : -columnOffset;
		int newColumnOffset = clockwise ? -rowOffset : rowOffset;
		rowOffset = newRowOffset;
		columnOffset = newColumnOffset;
	}

}