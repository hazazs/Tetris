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

	boolean isDrawableInto(String[][] level) {
		for (MiniBlock miniBlock : getMiniBlocks()) {
			int row = this.row + miniBlock.getRowOffset();
			int column = this.column + miniBlock.getColumnOffset();
			if (row < 0 || row >= Level.HEIGHT || column < 0 || column >= Level.WIDTH
					|| MiniBlock.BLOCK.equals(level[row][column])) {
				return true;
			}
		}
		return false;
	}

	void drawItselfInto(String[][] copy) {
		for (MiniBlock miniBlock : getMiniBlocks()) {
			copy[this.row + miniBlock.getRowOffset()][this.column + miniBlock.getColumnOffset()] = MiniBlock.BLOCK;
		}
	}

	void moveDown() {
		row++;
	}

	void moveLeft() {
		column--;
	}

	void moveRight() {
		column++;
	}

	boolean isBlockedFromTheLeft(String[][] level) {
		for (MiniBlock miniBlock : getMiniBlocks()) {
			if (this.column + miniBlock.getColumnOffset() == 0 || MiniBlock.BLOCK.equals(
					level[this.row + miniBlock.getRowOffset()][this.column + miniBlock.getColumnOffset() - 1])) {
				return true;
			}
		}
		return false;
	}

	boolean isBlockedFromTheRight(String[][] level) {
		for (MiniBlock miniBlock : getMiniBlocks()) {
			if (this.column + miniBlock.getColumnOffset() == Level.WIDTH - 1 || MiniBlock.BLOCK.equals(
					level[this.row + miniBlock.getRowOffset()][this.column + miniBlock.getColumnOffset() + 1])) {
				return true;
			}
		}
		return false;
	}

}