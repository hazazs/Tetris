package hu.hazazs.tetris;

import java.util.ArrayList;
import java.util.List;

public final class Block {

	private int row = 0;
	private int column = Level.WIDTH / 2;
	private final List<MiniBlock> miniBlocks = new ArrayList<>();

	Block(BlockType blockType) {
		switch (blockType) {
			case LONG:
				// ██▓▓████
				miniBlocks.add(new MiniBlock(0, 0));
				miniBlocks.add(new MiniBlock(0, -1));
				miniBlocks.add(new MiniBlock(0, 1));
				miniBlocks.add(new MiniBlock(0, 2));
				break;
			case SQUARE:
				// ▓▓██
				// ████
				miniBlocks.add(new MiniBlock(0, 0));
				miniBlocks.add(new MiniBlock(0, 1));
				miniBlocks.add(new MiniBlock(1, 0));
				miniBlocks.add(new MiniBlock(1, 1));
				break;
			case Z:
				// ██▓▓
				//   ████
				miniBlocks.add(new MiniBlock(0, 0));
				miniBlocks.add(new MiniBlock(0, -1));
				miniBlocks.add(new MiniBlock(1, 0));
				miniBlocks.add(new MiniBlock(1, 1));
				break;
			case T:
				//   ██
				// ██▓▓██
				miniBlocks.add(new MiniBlock(0, 0));
				miniBlocks.add(new MiniBlock(-1, 0));
				miniBlocks.add(new MiniBlock(0, -1));
				miniBlocks.add(new MiniBlock(0, 1));
				break;
		}
	}

	private List<MiniBlock> getMiniBlocks() {
		return miniBlocks;
	}

	void drawItselfInto(String[][] copy) {
		for (MiniBlock miniBlock : getMiniBlocks()) {
			int row = this.row + miniBlock.getRowOffset();
			int column = this.column + miniBlock.getColumnOffset();
			if (isValidCoordinates(row, column)) {
				copy[row][column] = MiniBlock.BLOCK;
			}
		}
	}

	private boolean isValidCoordinates(int row, int column) {
		return row >= 0 && row < Level.HEIGHT && column >= 0 && column < Level.WIDTH;
	}
	
	void moveDown() {
		row++;
	}
	
	boolean isValid() {
		for (MiniBlock miniBlock : getMiniBlocks()) {
			if (!isValidCoordinates(this.row + miniBlock.getRowOffset(), this.column + miniBlock.getColumnOffset())) {
				return false;
			}
		}
		return true;
	}

}