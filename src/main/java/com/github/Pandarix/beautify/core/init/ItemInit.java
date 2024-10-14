package com.github.Pandarix.beautify.core.init;

import com.github.Pandarix.beautify.Beautify;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;


public final class ItemInit {
	private ItemInit() {
	}

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Beautify.MODID);

	// trellis
	public static final DeferredItem<BlockItem> OAK_TRELLIS_ITEM = blockItemWithBurnTime(BlockInit.OAK_TRELLIS, 300);

	public static final DeferredItem<BlockItem> SPRUCE_TRELLIS_ITEM = blockItemWithBurnTime(BlockInit.SPRUCE_TRELLIS, 300);

	public static final DeferredItem<BlockItem> BIRCH_TRELLIS_ITEM = blockItemWithBurnTime(BlockInit.BIRCH_TRELLIS, 300);

	public static final DeferredItem<BlockItem> JUNGLE_TRELLIS_ITEM = blockItemWithBurnTime(BlockInit.JUNGLE_TRELLIS, 300);

	public static final DeferredItem<BlockItem> ACACIA_TRELLIS_ITEM = blockItemWithBurnTime(BlockInit.ACACIA_TRELLIS, 300);

	public static final DeferredItem<BlockItem> DARK_OAK_TRELLIS_ITEM = blockItemWithBurnTime(BlockInit.DARK_OAK_TRELLIS, 300);

	public static final DeferredItem<BlockItem> MANGROVE_TRELLIS_ITEM = blockItemWithBurnTime(BlockInit.MANGROVE_TRELLIS, 300);

	public static final DeferredItem<BlockItem> CRIMSON_TRELLIS_ITEM = blockItemWithBurnTime(BlockInit.CRIMSON_TRELLIS, 300);

	public static final DeferredItem<BlockItem> WARPED_TRELLIS_ITEM = blockItemWithBurnTime(BlockInit.WARPED_TRELLIS, 300);

	// blinds
	public static final DeferredItem<BlockItem> OAK_BLINDS_ITEM = blockItemWithBurnTime(BlockInit.OAK_BLINDS, 300);

	public static final DeferredItem<BlockItem> SPRUCE_BLINDS_ITEM = blockItemWithBurnTime(BlockInit.SPRUCE_BLINDS, 300);

	public static final DeferredItem<BlockItem> BIRCH_BLINDS_ITEM = blockItemWithBurnTime(BlockInit.BIRCH_BLINDS, 300);

	public static final DeferredItem<BlockItem> JUNGLE_BLINDS_ITEM = blockItemWithBurnTime(BlockInit.JUNGLE_BLINDS, 300);

	public static final DeferredItem<BlockItem> ACACIA_BLINDS_ITEM = blockItemWithBurnTime(BlockInit.ACACIA_BLINDS, 300);

	public static final DeferredItem<BlockItem> DARK_OAK_BLINDS_ITEM = blockItemWithBurnTime(BlockInit.DARK_OAK_BLINDS, 300);

	public static final DeferredItem<BlockItem> CRIMSON_BLINDS_ITEM = blockItemWithBurnTime(BlockInit.CRIMSON_BLINDS, 300);

	public static final DeferredItem<BlockItem> WARPED_BLINDS_ITEM = blockItemWithBurnTime(BlockInit.WARPED_BLINDS, 300);

	public static final DeferredItem<BlockItem> MANGROVE_BLINDS_ITEM = blockItemWithBurnTime(BlockInit.MANGROVE_BLINDS, 300);

	public static final DeferredItem<BlockItem> IRON_BLINDS_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.IRON_BLINDS);

	// picture frame
	public static final DeferredItem<BlockItem> OAK_PICTURE_FRAME_ITEM = blockItemWithBurnTime(BlockInit.OAK_PICTURE_FRAME, 300);

	public static final DeferredItem<BlockItem> SPRUCE_PICTURE_FRAME_ITEM = blockItemWithBurnTime(BlockInit.SPRUCE_PICTURE_FRAME, 300);

	public static final DeferredItem<BlockItem> BIRCH_PICTURE_FRAME_ITEM = blockItemWithBurnTime(BlockInit.BIRCH_PICTURE_FRAME, 300);

	public static final DeferredItem<BlockItem> JUNGLE_PICTURE_FRAME_ITEM = blockItemWithBurnTime(BlockInit.JUNGLE_PICTURE_FRAME, 300);

	public static final DeferredItem<BlockItem> ACACIA_PICTURE_FRAME_ITEM = blockItemWithBurnTime(BlockInit.ACACIA_PICTURE_FRAME, 300);

	public static final DeferredItem<BlockItem> DARK_OAK_PICTURE_FRAME_ITEM = blockItemWithBurnTime(BlockInit.DARK_OAK_PICTURE_FRAME, 300);

	public static final DeferredItem<BlockItem> CRIMSON_PICTURE_FRAME_ITEM = blockItemWithBurnTime(BlockInit.CRIMSON_PICTURE_FRAME, 300);

	public static final DeferredItem<BlockItem> WARPED_PICTURE_FRAME_ITEM = blockItemWithBurnTime(BlockInit.WARPED_PICTURE_FRAME, 300);

	public static final DeferredItem<BlockItem> MANGROVE_PICTURE_FRAME_ITEM = blockItemWithBurnTime(BlockInit.MANGROVE_PICTURE_FRAME, 300);

	public static final DeferredItem<BlockItem> QUARTZ_PICTURE_FRAME_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.QUARTZ_PICTURE_FRAME);

	public static final DeferredItem<BlockItem> ROPE_ITEM = blockItemWithBurnTime(BlockInit.ROPE, 100);

	public static final DeferredItem<BlockItem> HANGING_POT_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.HANGING_POT);

	public static final DeferredItem<BlockItem> BOOKSTACK_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.BOOKSTACK);

	public static final DeferredItem<BlockItem> LAMP_LIGHT_BULB_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_LIGHT_BULB);

	public static final DeferredItem<BlockItem> LAMP_BAMBOO_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_BAMBOO);

	public static final DeferredItem<BlockItem> LAMP_JAR_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_JAR);

	// candleabras
	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_LIGHT_BLUE_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_LIGHT_BLUE);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_LIGHT_GRAY_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_LIGHT_GRAY);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_BLACK_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_BLACK);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_BLUE_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_BLUE);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_BROWN_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_BROWN);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_CYAN_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_CYAN);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_GRAY_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_GRAY);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_GREEN_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_GREEN);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_LIME_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_LIME);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_MAGENTA_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_MAGENTA);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_ORANGE_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_ORANGE);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_PINK_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_PINK);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_PURPLE_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_PURPLE);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_RED_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_RED);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_WHITE_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_WHITE);

	public static final DeferredItem<BlockItem> LAMP_CANDELABRA_YELLOW_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.LAMP_CANDELABRA_YELLOW);

	// workbench
	public static final DeferredItem<BlockItem> BOTANIST_WORKBENCH_ITEM = ITEMS.registerSimpleBlockItem(BlockInit.BOTANIST_WORKBENCH);

	/**
	 * Register a BlockItem for a Block with burn time
	 * @deprecated in favor of NeoForge datamaps
	 *
	 * @param holder the Block
	 * @param burnTime the burn time
	 * @return the new registry object
	 */
	private static DeferredItem<BlockItem> blockItemWithBurnTime(Holder<Block> holder, int burnTime) {
		return ITEMS.register(holder.unwrapKey().map(ResourceKey::location).map(ResourceLocation::getPath).orElseThrow(),
				() -> new BlockItem(holder.value(), new Item.Properties()) {
					@Override
					public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
						return burnTime;
					}
				});
	}
}
