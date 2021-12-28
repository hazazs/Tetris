package hu.hazazs.tetris;

import hu.hazazs.tetris.rotate.RotateLogic;

public final class MiniBlock {

	static final String BLOCK = "██";
	private final int rowOffset;
	private final int columnOffset;

	public MiniBlock(int rowOffset, int columnOffset) {
		this.rowOffset = rowOffset;
		this.columnOffset = columnOffset;
	}

	public int getRowOffset() {
		return rowOffset;
	}

	public int getColumnOffset() {
		return columnOffset;
	}

	MiniBlock rotate(RotateLogic rotateLogic) {
		return rotateLogic.execute(this);
	}

}