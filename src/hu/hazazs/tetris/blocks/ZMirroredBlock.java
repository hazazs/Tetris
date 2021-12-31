package hu.hazazs.tetris.blocks;

final class ZMirroredBlock extends TwoStateRotationBlock {

	ZMirroredBlock() {
		//   ▓▓██
		// ████
		miniBlocks.add(new MiniBlock(0, 0));
		miniBlocks.add(new MiniBlock(0, 1));
		miniBlocks.add(new MiniBlock(1, -1));
		miniBlocks.add(new MiniBlock(1, 0));
	}

	@Override
	public Block copy() {
		return copy(this, new ZMirroredBlock());
	}

}