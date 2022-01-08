package hu.hazazs.tetris.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public abstract class Block {

	private int row = 0;
	private int column = 4;
	final List<MiniBlock> miniBlocks = new ArrayList<>();

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public List<MiniBlock> getMiniBlocks() {
		return miniBlocks;
	}

	public static Block random() {
		BlockType randomBlockType = BlockType.values()[new Random().nextInt(BlockType.values().length)];
		switch (randomBlockType) {
			case LONG:
				return new LongBlock();
			case SQUARE:
				return new SquareBlock();
			case Z:
				return new ZBlock();
			case Z_MIRRORED:
				return new ZMirroredBlock();
			case T:
				return new TBlock();
			case L:
				return new LBlock();
			case L_MIRRORED:
				return new LMirroredBlock();
			default:
				throw new NoSuchElementException();
		}
	}

	public String[][] toDrawBuffer() {
		String[][] drawBuffer = new String[2][4];
		for (MiniBlock miniBlock : miniBlocks) {
			drawBuffer[0 + miniBlock.getRowOffset()][1 + miniBlock.getColumnOffset()] = MiniBlock.BLOCK;
		}
		return drawBuffer;
	}

	public abstract Block copy();

	Block copy(Block original, Block copy) {
		copy.row = original.row;
		copy.column = original.column;
		copy.miniBlocks.clear();
		for (MiniBlock miniBlock : original.getMiniBlocks()) {
			copy.miniBlocks.add(new MiniBlock(miniBlock));
		}
		return copy;
	}

	public Block rotate() {
		rotateClockwise();
		return this;
	}

	void rotateClockwise() {
		for (MiniBlock miniBlock : miniBlocks) {
			miniBlock.rotateClockwise(true);
		}
	}

	void rotateCounterClockwise() {
		for (MiniBlock miniBlock : miniBlocks) {
			miniBlock.rotateClockwise(false);
		}
	}

	public Block moveLeft() {
		column--;
		return this;
	}

	public Block moveDown() {
		row++;
		return this;
	}

	public Block moveRight() {
		column++;
		return this;
	}

}