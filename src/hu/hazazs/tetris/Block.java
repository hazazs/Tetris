package hu.hazazs.tetris;

import java.util.ArrayList;
import java.util.List;
import hu.hazazs.tetris.rotate.FourStateRotateLogic;
import hu.hazazs.tetris.rotate.LongRotateLogic;
import hu.hazazs.tetris.rotate.NoRotateLogic;
import hu.hazazs.tetris.rotate.RotateLogic;
import hu.hazazs.tetris.rotate.ZMirroredRotateLogic;
import hu.hazazs.tetris.rotate.ZRotateLogic;

public final class Block {

	private int row = 0;
	private int column = 4;
	private final List<MiniBlock> miniBlocks = new ArrayList<>();
	private final RotateLogic rotateLogic;

	Block(BlockType blockType) {
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

	int getRow() {
		return row;
	}

	int getColumn() {
		return column;
	}

	List<MiniBlock> getMiniBlocks() {
		return miniBlocks;
	}

	RotateLogic getRotateLogic() {
		return rotateLogic;
	}

	String[][] toDrawBuffer() {
		String[][] drawBuffer = new String[2][4];
		for (MiniBlock miniBlock : miniBlocks) {
			drawBuffer[0 + miniBlock.getRowOffset()][1 + miniBlock.getColumnOffset()] = MiniBlock.BLOCK;
		}
		return drawBuffer;
	}

	void rotate() {
		for (int i = 0; i < miniBlocks.size(); i++) {
			miniBlocks.set(i, miniBlocks.get(i).rotate(rotateLogic));
		}
	}

	void moveLeft() {
		column--;
	}

	void moveDown() {
		row++;
	}

	void moveRight() {
		column++;
	}

}