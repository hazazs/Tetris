package hu.hazazs.tetris;

import java.util.ArrayList;
import java.util.List;

public final class Block {

	private final String[][] level;
	private int row = 0;
	private int column = Level.WIDTH / 2;
	private final List<MiniBlock> miniBlocks = new ArrayList<>();

	Block(String[][] level, BlockType blockType) {
		this.level = level;
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
				// ██▓▓██
				//   ██
				miniBlocks.add(new MiniBlock(0, 0));
				miniBlocks.add(new MiniBlock(0, -1));
				miniBlocks.add(new MiniBlock(0, 1));
				miniBlocks.add(new MiniBlock(1, 0));
				break;
			case L:
				// ██▓▓██
				// ██
				miniBlocks.add(new MiniBlock(0, 0));
				miniBlocks.add(new MiniBlock(0, -1));
				miniBlocks.add(new MiniBlock(0, 1));
				miniBlocks.add(new MiniBlock(1, -1));
				break;
		}
	}

	private List<MiniBlock> getMiniBlocks() {
		return miniBlocks;
	}

	void drawItselfInto(String[][] copy) {
		for (MiniBlock miniBlock : getMiniBlocks()) {
			copy[row + miniBlock.getRowOffset()][column + miniBlock.getColumnOffset()] = MiniBlock.BLOCK;
		}
	}

	void moveDown() {
		row++;
	}

	void moveLeft() {
		if (!isBlockedFromTheLeft()) {
			column--;
		}
	}

	void moveRight() {
		if (!isBlockedFromTheRight()) {
			column++;
		}
	}

	boolean hasReachedTheBottom() {
		for (MiniBlock miniBlock : getMiniBlocks()) {
			int row = this.row + miniBlock.getRowOffset();
			int column = this.column + miniBlock.getColumnOffset();
			if (row == Level.HEIGHT - 1 || MiniBlock.BLOCK.equals(level[row + 1][column])) {
				return true;
			}
		}
		return false;
	}

	private boolean isBlockedFromTheLeft() {
		for (MiniBlock miniBlock : getMiniBlocks()) {
			int row = this.row + miniBlock.getRowOffset();
			int column = this.column + miniBlock.getColumnOffset();
			if (column == 0 || MiniBlock.BLOCK.equals(level[row][column - 1])) {
				return true;
			}
		}
		return false;
	}

	private boolean isBlockedFromTheRight() {
		for (MiniBlock miniBlock : getMiniBlocks()) {
			int row = this.row + miniBlock.getRowOffset();
			int column = this.column + miniBlock.getColumnOffset();
			if (column == Level.WIDTH - 1 || MiniBlock.BLOCK.equals(level[row][column + 1])) {
				return true;
			}
		}
		return false;
	}
	
	boolean isBlockFree() {
		for (MiniBlock miniBlock : getMiniBlocks()) {
			int row = this.row + miniBlock.getRowOffset();
			int column = this.column + miniBlock.getColumnOffset();
			if (MiniBlock.BLOCK.equals(level[row][column])) {
				return false;
			}
		}
		return true;
	}

}