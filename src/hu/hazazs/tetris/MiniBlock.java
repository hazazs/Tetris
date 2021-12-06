package hu.hazazs.tetris;

public final class MiniBlock {

	public static final String BLOCK = "██";
	private final int rowOffset;
	private final int columnOffset;

	MiniBlock(int rowOffset, int columnOffset) {
		this.rowOffset = rowOffset;
		this.columnOffset = columnOffset;
	}

	int getRowOffset() {
		return rowOffset;
	}

	int getColumnOffset() {
		return columnOffset;
	}

}