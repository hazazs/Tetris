package hu.hazazs.tetris.blocks;

final class ZBlock extends TwoStateRotationBlock {

	ZBlock() {
		// ██▓▓
		//   ████
		miniBlocks.add(new MiniBlock(0, 0));
		miniBlocks.add(new MiniBlock(0, -1));
		miniBlocks.add(new MiniBlock(1, 0));
		miniBlocks.add(new MiniBlock(1, 1));
	}

	@Override
	public Block copy() {
		TwoStateRotationBlock copy = new ZBlock();
		copy.clockwise = this.clockwise;
		return copy(this, copy);
	}

}