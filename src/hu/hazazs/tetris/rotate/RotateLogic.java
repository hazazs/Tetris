package hu.hazazs.tetris.rotate;

import hu.hazazs.tetris.MiniBlock;

@FunctionalInterface
public interface RotateLogic {

	MiniBlock execute(MiniBlock miniBlock);

}