package hu.hazazs.tetris.rotate;

import hu.hazazs.tetris.MiniBlock;

public final class ZRotateLogic implements RotateLogic {

	@Override
	public MiniBlock execute(MiniBlock miniBlock) {
		switch (miniBlock.getRowOffset()) {
			case -1:
				if (miniBlock.getColumnOffset() == 1) {
					return new MiniBlock(0, -1);
				}
			case 0:
				switch (miniBlock.getColumnOffset()) {
					case -1:
						return new MiniBlock(-1, 1);
					case 1:
						return new MiniBlock(1, 1);
				}
				break;
			case 1:
				if (miniBlock.getColumnOffset() == 1) {
					return new MiniBlock(0, 1);
				}
		}
		return miniBlock;
	}

}