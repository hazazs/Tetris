package hu.hazazs.tetris.blocks;

final class SquareBlock extends Block {

	SquareBlock() {
		//   ▓▓██
		//   ████
		miniBlocks.add(new MiniBlock(0, 0));
		miniBlocks.add(new MiniBlock(0, 1));
		miniBlocks.add(new MiniBlock(1, 0));
		miniBlocks.add(new MiniBlock(1, 1));
	}

	@Override
	public Block copy() {
		return copy(this, new SquareBlock());
	}

	@Override
	public Block rotate() {
		return this;
	}

}