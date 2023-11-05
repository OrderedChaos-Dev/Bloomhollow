package bloomhollow.common.blocks;

import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BloomhollowMushroomBlock extends HugeMushroomBlock {

	public BloomhollowMushroomBlock(int lightLevel) {
		super(Properties.of(Material.WOOD, MaterialColor.COLOR_BLUE).strength(0.2F).sound(SoundType.WOOD).lightLevel((state) -> lightLevel).emissiveRendering((state, level, pos) -> true));
	}

}
