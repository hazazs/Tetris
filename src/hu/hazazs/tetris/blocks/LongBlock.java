package hu.hazazs.tetris.blocks;

final class LongBlock extends TwoStateRotationBlock {

	LongBlock() {
		// ██▓▓████
		miniBlocks.add(new MiniBlock(0, 0));
		miniBlocks.add(new MiniBlock(0, -1));
		miniBlocks.add(new MiniBlock(0, 1));
		miniBlocks.add(new MiniBlock(0, 2));
		clockwise = true;
	}

	@Override
	public Block copy() {
		TwoStateRotationBlock copy = new LongBlock();
		copy.clockwise = this.clockwise;
		return copy(this, copy);
	}

}