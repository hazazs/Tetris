package hu.hazazs.tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import hu.hazazs.tetris.rotate.FourStateRotateLogic;
import hu.hazazs.tetris.rotate.LongRotateLogic;
import hu.hazazs.tetris.rotate.NoRotateLogic;
import hu.hazazs.tetris.rotate.RotateLogic;
import hu.hazazs.tetris.rotate.ZMirroredRotateLogic;
import hu.hazazs.tetris.rotate.ZRotateLogic;

public final class Block {

	private final String[][] level;
	private int row = 0;
	private int column = 4;
	private final List<MiniBlock> miniBlocks = new ArrayList<>();
	private final RotateLogic rotateLogic;

	Block(String[][] level, BlockType blockType) {
		this.level = level;
		switch (blockType) {
			case LONG:
				// ██▓▓████
				miniBlocks.add(new MiniBlock(0, 0));
				miniBlocks.add(new MiniBlock(0, -1));
				miniBlocks.add(new MiniBlock(0, 1));
				miniBlocks.add(new MiniBlock(0, 2));
				rotateLogic = new LongRotateLogic();
				break;
			case SQUARE:
				//   ▓▓██
				//   ████
				miniBlocks.add(new MiniBlock(0, 0));
				miniBlocks.add(new MiniBlock(0, 1));
				miniBlocks.add(new MiniBlock(1, 0));
				miniBlocks.add(new MiniBlock(1, 1));
				rotateLogic = new NoRotateLogic();
				break;
			case Z:
				// ██▓▓
				//   ████
				miniBlocks.add(new MiniBlock(0, 0));
				miniBlocks.add(new MiniBlock(0, -1));
				miniBlocks.add(new MiniBlock(1, 0));
				miniBlocks.add(new MiniBlock(1, 1));
				rotateLogic = new ZRotateLogic();
				break;
			case Z_MIRRORED:
				//   ▓▓██
				// ████
				miniBlocks.add(new MiniBlock(0, 0));
				miniBlocks.add(new MiniBlock(0, 1));
				miniBlocks.add(new MiniBlock(1, -1));
				miniBlocks.add(new MiniBlock(1, 0));
				rotateLogic = new ZMirroredRotateLogic();
				break;
			case T:
				// ██▓▓██
				//   ██
				miniBlocks.add(new MiniBlock(0, 0));
				miniBlocks.add(new MiniBlock(0, -1));
				miniBlocks.add(new MiniBlock(0, 1));
				miniBlocks.add(new MiniBlock(1, 0));
				rotateLogic = new FourStateRotateLogic();
				break;
			case L:
				// ██▓▓██
				// ██
				miniBlocks.add(new MiniBlock(0, 0));
				miniBlocks.add(new MiniBlock(0, -1));
				miniBlocks.add(new MiniBlock(0, 1));
				miniBlocks.add(new MiniBlock(1, -1));
				rotateLogic = new FourStateRotateLogic();
				break;
			case L_MIRRORED:
				// ██▓▓██
				//     ██
				miniBlocks.add(new MiniBlock(0, 0));
				miniBlocks.add(new MiniBlock(0, -1));
				miniBlocks.add(new MiniBlock(0, 1));
				miniBlocks.add(new MiniBlock(1, 1));
				rotateLogic = new FourStateRotateLogic();
				break;
			default:
				rotateLogic = new NoRotateLogic();
				break;
		}
	}

	String[][] toDrawBuffer() {
		String[][] drawBuffer = new String[2][4];
		for (MiniBlock miniBlock : miniBlocks) {
			drawBuffer[0 + miniBlock.getRowOffset()][1 + miniBlock.getColumnOffset()] = MiniBlock.BLOCK;
		}
		return drawBuffer;
	}

	String[][] drawIntoCopy() {
		String[][] copy = Arrays.stream(level).map(String[]::clone).toArray(String[][]::new);
		for (MiniBlock miniBlock : miniBlocks) {
			int row = this.row + miniBlock.getRowOffset();
			int column = this.column + miniBlock.getColumnOffset();
			if (row >= 0) {
				copy[row][column] = MiniBlock.BLOCK;
			}
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
			if (row == Level.HEIGHT || row >= 0 && MiniBlock.BLOCK.equals(level[row][column])) {
				return true;
			}
		}
		return false;
	}

	private boolean isBlockedFromTheLeft() {
		for (MiniBlock miniBlock : miniBlocks) {
			int row = this.row + miniBlock.getRowOffset();
			int column = this.column + miniBlock.getColumnOffset();
			if (column == 0 || row >= 0 && MiniBlock.BLOCK.equals(level[row][column - 1])) {
				return true;
			}
		}
		return false;
	}

	private boolean isBlockedFromTheRight() {
		for (MiniBlock miniBlock : miniBlocks) {
			int row = this.row + miniBlock.getRowOffset();
			int column = this.column + miniBlock.getColumnOffset();
			if (column == Level.WIDTH - 1 || row >= 0 && MiniBlock.BLOCK.equals(level[row][column + 1])) {
				return true;
			}
		}
		return false;
	}

	boolean canRotate() {
		for (MiniBlock miniBlock : miniBlocks) {
			MiniBlock testMiniBlock = miniBlock.rotate(rotateLogic);
			int row = this.row + testMiniBlock.getRowOffset();
			int column = this.column + testMiniBlock.getColumnOffset();
			if (row > Level.HEIGHT - 1 || column < 0 || column > Level.WIDTH - 1
					|| row >= 0 && MiniBlock.BLOCK.equals(level[row][column])) {
				return false;
			}
		}
		return true;
	}

	void rotate() {
		for (int i = 0; i < miniBlocks.size(); i++) {
			miniBlocks.set(i, miniBlocks.get(i).rotate(rotateLogic));
		}
	}

}