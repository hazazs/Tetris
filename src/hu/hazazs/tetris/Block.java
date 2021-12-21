package hu.hazazs.tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Block {

	private final String[][] level;
	private int row = 0;
	private int column = 4;
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
				//   ▓▓██
				//   ████
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
			case Z_MIRRORED:
				//   ▓▓██
				// ████
				miniBlocks.add(new MiniBlock(0, 0));
				miniBlocks.add(new MiniBlock(0, 1));
				miniBlocks.add(new MiniBlock(1, -1));
				miniBlocks.add(new MiniBlock(1, 0));
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
			case L_MIRRORED:
				// ██▓▓██
				//     ██
				miniBlocks.add(new MiniBlock(0, 0));
				miniBlocks.add(new MiniBlock(0, -1));
				miniBlocks.add(new MiniBlock(0, 1));
				miniBlocks.add(new MiniBlock(1, 1));
				break;
		}
	}

	String[][] drawIntoCopy() {
		String[][] copy = Arrays.stream(level).map(String[]::clone).toArray(String[][]::new);
		for (MiniBlock miniBlock : miniBlocks) {
			copy[row + miniBlock.getRowOffset()][column + miniBlock.getColumnOffset()] = MiniBlock.BLOCK;
		}
		return copy;
	}

	void drawIntoLevel() {
		for (MiniBlock miniBlock : miniBlocks) {
			level[row + miniBlock.getRowOffset() - 1][column + miniBlock.getColumnOffset()] = MiniBlock.BLOCK;
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
		for (MiniBlock miniBlock : miniBlocks) {
			int row = this.row + miniBlock.getRowOffset();
			int column = this.column + miniBlock.getColumnOffset();
			if (row == Level.HEIGHT || MiniBlock.BLOCK.equals(level[row][column])) {
				return true;
			}
		}
		return false;
	}

	private boolean isBlockedFromTheLeft() {
		for (MiniBlock miniBlock : miniBlocks) {
			int row = this.row + miniBlock.getRowOffset();
			int column = this.column + miniBlock.getColumnOffset();
			if (column == 0 || MiniBlock.BLOCK.equals(level[row][column - 1])) {
				return true;
			}
		}
		return false;
	}

	private boolean isBlockedFromTheRight() {
		for (MiniBlock miniBlock : miniBlocks) {
			int row = this.row + miniBlock.getRowOffset();
			int column = this.column + miniBlock.getColumnOffset();
			if (column == Level.WIDTH - 1 || MiniBlock.BLOCK.equals(level[row][column + 1])) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String[][] drawBuffer = new String[2][4];
		for (MiniBlock miniBlock : miniBlocks) {
			drawBuffer[0 + miniBlock.getRowOffset()][1 + miniBlock.getColumnOffset()] = MiniBlock.BLOCK;
		}
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < drawBuffer.length; row++) {
			for (int column = 0; column < drawBuffer[0].length; column++) {
				builder.append(Objects.isNull(drawBuffer[row][column]) ? "  " : drawBuffer[row][column]);
			}
			builder.append("\n");
		}
		return builder.toString();
	}

}