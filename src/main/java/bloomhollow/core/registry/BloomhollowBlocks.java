package bloomhollow.core.registry;

import java.util.function.Supplier;

import bloomhollow.common.blocks.AbyssalLotusBlock;
import bloomhollow.common.blocks.AzureWillowVinesBlock;
import bloomhollow.common.blocks.BerylWillowVinesBlock;
import bloomhollow.common.blocks.BloomhollowPortalBlock;
import bloomhollow.common.blocks.BrightshroomBlock;
import bloomhollow.common.blocks.DownpourFlowerBlock;
import bloomhollow.common.blocks.DownpourStemBlock;
import bloomhollow.common.blocks.DrizzlewoodLeavesBlock;
import bloomhollow.common.blocks.FluxVinesBlock;
import bloomhollow.common.blocks.FluxVinesPlantBlock;
import bloomhollow.common.blocks.GleamingLichenBlock;
import bloomhollow.common.blocks.GlowingFungusBlock;
import bloomhollow.common.blocks.GlowingLeavesBlock;
import bloomhollow.common.blocks.WillowSaplingBlock;
import bloomhollow.common.blocks.WillowSaplingBlock.BasicTreeGrower;
import bloomhollow.core.Bloomhollow;
import bloomhollow.core.registry.world.BloomhollowConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BloomhollowBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Bloomhollow.MOD_ID);
	
	public static final RegistryObject<Block> SHADESTONE = registerBlock("shadestone", () -> new Block(Properties.copy(Blocks.STONE).color(MaterialColor.DEEPSLATE)));
	public static final RegistryObject<Block> GRASSY_SHADESTONE = registerBlock("grassy_shadestone", () -> new Block(Properties.copy(Blocks.STONE).color(MaterialColor.GRASS).sound(SoundType.MOSS)));
	
	public static final RegistryObject<Block> STRIPPED_DRIZZLEWOOD_LOG = registerBlock("stripped_drizzlewood_log", () -> createStrippedLogBlock(MaterialColor.WOOD, MaterialColor.WOOD));
	public static final RegistryObject<Block> STRIPPED_AZURE_WILLOW_LOG = registerBlock("stripped_azure_willow_log", () -> createStrippedLogBlock(MaterialColor.COLOR_LIGHT_BLUE, MaterialColor.COLOR_LIGHT_BLUE));
	public static final RegistryObject<Block> STRIPPED_BERYL_WILLOW_LOG = registerBlock("stripped_beryl_willow_log", () -> createStrippedLogBlock(MaterialColor.COLOR_LIGHT_BLUE, MaterialColor.COLOR_LIGHT_BLUE));
	
	public static final RegistryObject<Block> DRIZZLEWOOD_LOG = registerBlock("drizzlewood_log", () -> createLogBlock(MaterialColor.WOOD, MaterialColor.WOOD, STRIPPED_DRIZZLEWOOD_LOG));
	public static final RegistryObject<Block> AZURE_WILLOW_LOG = registerBlock("azure_willow_log", () -> createLogBlock(MaterialColor.WOOD, MaterialColor.WOOD, STRIPPED_AZURE_WILLOW_LOG));
	public static final RegistryObject<Block> BERYL_WILLOW_LOG = registerBlock("beryl_willow_log", () -> createLogBlock(MaterialColor.WOOD, MaterialColor.WOOD, STRIPPED_BERYL_WILLOW_LOG));
	
	public static final RegistryObject<Block> DRIZZLEWOOD_LEAVES = registerBlock("drizzlewood_leaves", () -> new DrizzlewoodLeavesBlock());
	public static final RegistryObject<Block> AZURE_WILLOW_LEAVES = registerBlock("azure_willow_leaves", () -> new GlowingLeavesBlock(1));
	public static final RegistryObject<Block> BERYL_WILLOW_LEAVES = registerBlock("beryl_willow_leaves", () -> new GlowingLeavesBlock(1));
	
	public static final RegistryObject<Block> AZURE_WILLOW_SAPLING = registerBlock("azure_willow_sapling", () -> new WillowSaplingBlock(new BasicTreeGrower(() -> BloomhollowConfiguredFeatures.AZURE_WILLOW_TREE), 1));
	public static final RegistryObject<Block> BERYL_WILLOW_SAPLING = registerBlock("beryl_willow_sapling", () -> new WillowSaplingBlock(new BasicTreeGrower(() -> BloomhollowConfiguredFeatures.BERYL_WILLOW_TREE), 1));
	
	public static final RegistryObject<Block> AZURE_WILLOW_VINES = registerBlock("azure_willow_vines", () -> new AzureWillowVinesBlock());
	public static final RegistryObject<Block> BERYL_WILLOW_VINES = registerBlock("beryl_willow_vines", () -> new BerylWillowVinesBlock());
	
	public static final RegistryObject<Block> DOWNPOUR_STEM = registerBlock("downpour_stem", () -> new DownpourStemBlock());
	public static final RegistryObject<Block> DOWNPOUR_FLOWER = registerBlock("downpour_flower", () -> new DownpourFlowerBlock());
	public static final RegistryObject<Block> FLUX_VINES = registerBlock("flux_vines", () -> new FluxVinesBlock());
	public static final RegistryObject<Block> FLUX_VINES_PLANT = registerBlock("flux_vines_plant", () -> new FluxVinesPlantBlock());
	public static final RegistryObject<Block> BRIGHTSHROOM = registerBlock("brightshroom", () -> new BrightshroomBlock());
	public static final RegistryObject<Block> SHINING_ANGEL_FUNGUS = registerBlock("shining_angel_fungus", () -> new GlowingFungusBlock());
	public static final RegistryObject<Block> SHINING_TEAL_FUNGUS = registerBlock("shining_teal_fungus", () -> new GlowingFungusBlock());
	public static final RegistryObject<Block> SHINING_INDIGO_FUNGUS = registerBlock("shining_indigo_fungus", () -> new GlowingFungusBlock());
	public static final RegistryObject<Block> GLEAMING_LICHEN = registerBlock("gleaming_lichen", () -> new GleamingLichenBlock());
	public static final RegistryObject<Block> ABYSSAL_LOTUS = registerBlock("abyssal_lotus", () -> new AbyssalLotusBlock());
	
	public static final RegistryObject<Block> BLOOMHOLLOW_PORTAL = registerBlock("bloomhollow_portal", () -> new BloomhollowPortalBlock());
	
	private static RegistryObject<Block> registerBlock(String name, Supplier<Block> block) {
		RegistryObject<Block> temp = BLOCKS.register(name, block);
		
		BloomhollowItems.ITEMS.register(name, () -> new ItemNameBlockItem(temp.get(), new Item.Properties().tab(BloomhollowCreativeModeTab.INSTANCE)));
		return temp;
	}
	
	public static RotatedPillarBlock createStrippedLogBlock(MaterialColor topColor, MaterialColor barkColor) {
		return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) -> {
			return state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor;
		}).strength(2.0F).sound(SoundType.WOOD));
	}
	
	public static RotatedPillarBlock createLogBlock(MaterialColor topColor, MaterialColor barkColor, Supplier<Block> stripped) {
		return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) -> {
			return state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor;
		}).strength(2.0F).sound(SoundType.WOOD)) {
			@Override
			public BlockState getToolModifiedState(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack, ToolAction toolType) {
				if(toolType == ToolActions.AXE_STRIP && stripped != null) {
					Axis axis = state.getValue(RotatedPillarBlock.AXIS);
					return stripped.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis);
				}

				return super.getToolModifiedState(state, world, pos, player, stack, toolType);
			}
		};
	}
}
