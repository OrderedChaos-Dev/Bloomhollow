package bloomhollow.common.biomes;

import bloomhollow.core.registry.world.BloomhollowPlacedFeatures;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

public class BloomhollowDefaultFeatures {

	public static void addGrassPatch(BiomeGenerationSettings.Builder builder) {
		builder.addFeature(Decoration.UNDERGROUND_DECORATION, BloomhollowPlacedFeatures.GRASS_PATCH.getHolder().get());
	}

}
