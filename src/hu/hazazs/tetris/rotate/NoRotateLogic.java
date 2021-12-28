package hu.hazazs.tetris.rotate;

import hu.hazazs.tetris.MiniBlock;

public final class NoRotateLogic implements RotateLogic {

	@Override
	public MiniBlock execute(MiniBlock miniBlock) {
		return miniBlock;
	}

}