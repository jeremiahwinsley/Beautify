package com.github.Pandarix.beautify.core.init;

import com.github.Pandarix.beautify.Beautify;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModVillagers {
	public static final DeferredRegister<PoiType> POI_TYPES =
			DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, Beautify.MODID);
	public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
			DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, Beautify.MODID);

	public static final Supplier<PoiType> BOTANIST_WORKBENCH_POI = POI_TYPES.register("botanist_workbench_poi",
			() -> new PoiType(
					ImmutableSet.copyOf(BlockInit.BOTANIST_WORKBENCH.get().getStateDefinition().getPossibleStates()), 1,
					1));

	public static final Supplier<VillagerProfession> BOTANIST = VILLAGER_PROFESSIONS.register("botanist",
			() -> new VillagerProfession("botanist", holder -> holder.value().equals(BOTANIST_WORKBENCH_POI.get()), holder -> holder.value().equals(BOTANIST_WORKBENCH_POI.get()), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.CAVE_VINES_PLACE));
}
