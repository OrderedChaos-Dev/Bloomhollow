package bloomhollow.core.data;

import org.jetbrains.annotations.Nullable;

import bloomhollow.core.Bloomhollow;
import bloomhollow.core.registry.BloomhollowBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BloomhollowBlockTagsProvider extends BlockTagsProvider {

	public BloomhollowBlockTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
		super(generator, Bloomhollow.MOD_ID, existingFileHelper);
	}
	
    @Override
    protected void addTags() {
    	BloomhollowBlocks.BLOCKS.getEntries().forEach((block) -> {
    		ResourceKey<Block> key = block.getKey();
    		String name = block.get().getRegistryName().getPath();
    		
    		if(name.endsWith("_log")) {
    			this.tag(BlockTags.LOGS).add(key);
    			this.tag(BlockTags.LOGS_THAT_BURN).add(key);
    			this.tag(BlockTags.MINEABLE_WITH_AXE).add(key);
    		} else if(block.get() instanceof LeavesBlock) {
    			this.tag(BlockTags.LEAVES).add(key);
    		} else if(block.get() instanceof SaplingBlock) {
    			this.tag(BlockTags.SAPLINGS).add(key);
    		} else if(name.endsWith("planks")) {
    			this.tag(BlockTags.PLANKS).add(key);
    			this.tag(BlockTags.MINEABLE_WITH_AXE).add(key);
    		} else if(block.get() instanceof StairBlock) {
    			this.tag(BlockTags.STAIRS).add(key);
    			this.tag(BlockTags.WOODEN_STAIRS).add(key);
    			this.tag(BlockTags.MINEABLE_WITH_AXE).add(key);
    		} else if(block.get() instanceof SlabBlock) {
    			this.tag(BlockTags.SLABS).add(key);
    			this.tag(BlockTags.WOODEN_SLABS).add(key);
    			this.tag(BlockTags.MINEABLE_WITH_AXE).add(key);
    		} else if(block.get() instanceof WoodButtonBlock) {
    			this.tag(BlockTags.WOODEN_BUTTONS).add(key);
    			this.tag(BlockTags.BUTTONS).add(key);
    			this.tag(BlockTags.MINEABLE_WITH_AXE).add(key);
    		} else if(block.get() instanceof PressurePlateBlock) {
    			this.tag(BlockTags.PRESSURE_PLATES).add(key);
    			this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(key);
    			this.tag(BlockTags.MINEABLE_WITH_AXE).add(key);
    		} else if(block.get() instanceof DoorBlock) {
    			this.tag(BlockTags.DOORS).add(key);
    			this.tag(BlockTags.WOODEN_DOORS).add(key);
    			this.tag(BlockTags.MINEABLE_WITH_AXE).add(key);
    		} else if(block.get() instanceof TrapDoorBlock) {
    			this.tag(BlockTags.TRAPDOORS).add(key);
    			this.tag(BlockTags.WOODEN_TRAPDOORS).add(key);
    			this.tag(BlockTags.MINEABLE_WITH_AXE).add(key);
    		} else if(block.get() instanceof FenceBlock) {
    			this.tag(BlockTags.FENCES).add(key);
    			this.tag(BlockTags.WOODEN_FENCES).add(key);
    			this.tag(BlockTags.MINEABLE_WITH_AXE).add(key);
    			this.tag(Tags.Blocks.FENCES).add(key);
    			this.tag(Tags.Blocks.FENCES_WOODEN).add(key);
    		} else if(block.get() instanceof FenceGateBlock) {
    			this.tag(BlockTags.FENCE_GATES).add(key);
    			this.tag(BlockTags.MINEABLE_WITH_AXE).add(key);
    			this.tag(Tags.Blocks.FENCE_GATES).add(key);
    			this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(key);
    		} else if(block.get() instanceof FlowerPotBlock) {
    			this.tag(BlockTags.FLOWER_POTS).add(key);
    		}
    	});
    	
    	this.tag(BlockTags.BASE_STONE_OVERWORLD).add(BloomhollowBlocks.SHADESTONE.get());
    	this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BloomhollowBlocks.SHADESTONE.get(), BloomhollowBlocks.GRASSY_SHADESTONE.get());
    	this.tag(BlockTags.MOSS_REPLACEABLE).add(BloomhollowBlocks.GRASSY_SHADESTONE.get(), BloomhollowBlocks.SHADESTONE.get());
    	this.tag(BlockTags.LUSH_GROUND_REPLACEABLE).add(BloomhollowBlocks.GRASSY_SHADESTONE.get(), BloomhollowBlocks.SHADESTONE.get());
    	this.tag(BlockTags.DIRT).add(BloomhollowBlocks.GRASSY_SHADESTONE.get());
    	this.tag(BlockTags.BAMBOO_PLANTABLE_ON).add(BloomhollowBlocks.GRASSY_SHADESTONE.get());
    }
}
