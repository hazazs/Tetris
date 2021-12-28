package hu.hazazs.tetris.rotate;

import hu.hazazs.tetris.MiniBlock;

public final class ZMirroredRotateLogic implements RotateLogic {

	@Override
	public MiniBlock execute(MiniBlock miniBlock) {
		switch (miniBlock.getRowOffset()) {
			case -1:
				if (miniBlock.getColumnOffset() == 0) {
					return new MiniBlock(1, -1);
				}
			case 1:
				switch (miniBlock.getColumnOffset()) {
					case -1:
						return new MiniBlock(-1, 0);
					case 0:
						return new MiniBlock(1, 1);
					case 1:
						return new MiniBlock(1, 0);
				}
				break;
		}
		return miniBlock;
	}

}