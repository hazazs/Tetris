package hu.hazazs.tetris.rotate;

import hu.hazazs.tetris.MiniBlock;

public final class LongRotateLogic implements RotateLogic {

	@Override
	public MiniBlock execute(MiniBlock miniBlock) {
		return new MiniBlock(miniBlock.getColumnOffset(), miniBlock.getRowOffset());
	}

}