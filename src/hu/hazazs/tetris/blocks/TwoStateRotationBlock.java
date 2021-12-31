package hu.hazazs.tetris.blocks;

abstract class TwoStateRotationBlock extends Block {

	boolean clockwise;

	@Override
	public Block rotate() {
		if (clockwise) {
			rotateClockwise();
		} else {
			rotateCounterClockwise();
		}
		clockwise = !clockwise;
		return this;
	}

}