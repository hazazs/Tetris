package hu.hazazs.tetris.blocks;

final class LMirroredBlock extends Block {

	LMirroredBlock() {
		// ██▓▓██
		//     ██
		miniBlocks.add(new MiniBlock(0, 0));
		miniBlocks.add(new MiniBlock(0, -1));
		miniBlocks.add(new MiniBlock(0, 1));
		miniBlocks.add(new MiniBlock(1, 1));
	}

	@Override
	public Block copy() {
		return copy(this, new LMirroredBlock());
	}

}