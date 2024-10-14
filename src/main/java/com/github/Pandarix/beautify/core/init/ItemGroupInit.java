package com.github.Pandarix.beautify.core.init;

import com.github.Pandarix.beautify.Beautify;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ItemGroupInit {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Beautify.MODID);

	public static final Supplier<CreativeModeTab> BEAUTIFY_TAB = CREATIVE_MODE_TABS.register("beautify",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.HANGING_POT_ITEM.get()))
					.title(Component.translatable("itemGroup.beautify"))
					.displayItems((parameters, output) -> ItemInit.ITEMS.getEntries().stream()
							.map(Supplier::get)
							.map(Item::getDefaultInstance)
							.forEach(output::accept))
					.build());
}
