package hu.hazazs.tetris.blocks;

abstract class TwoStateRotationBlock extends Block {

	boolean clockwise;

	Block copy(TwoStateRotationBlock original, TwoStateRotationBlock copy) {
		copy.clockwise = original.clockwise;
		return super.copy(original, copy);
	}

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