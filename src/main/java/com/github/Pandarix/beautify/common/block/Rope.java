package com.github.Pandarix.beautify.common.block;

import com.github.Pandarix.beautify.core.init.BlockInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class Rope extends ChainBlock
{
    public Rope(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false))
                .setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity)
    {
        if (state.is(BlockInit.ROPE.get()))
        {
            return true;
        }
        return super.isLadder(state, level, pos, entity);
    }

    public PushReaction getPistonPushReaction(BlockState p_153494_)
    {
        return PushReaction.DESTROY;
    }

    @Override
    public boolean makesOpenTrapdoorAboveClimbable(BlockState state, LevelReader level, BlockPos pos,
                                                   BlockState trapdoorState)
    {
        if (state.is(BlockInit.ROPE.get()))
        {
            return true;
        } else
        {
            return super.makesOpenTrapdoorAboveClimbable(state, level, pos, trapdoorState);
        }
    }

	@OnlyIn(Dist.CLIENT)
	@ParametersAreNonnullByDefault
	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag)
	{
        if (!Screen.hasShiftDown())
        {
			components.add(Component.translatable("tooltip.shift").withStyle(ChatFormatting.YELLOW));
        }

        if (Screen.hasShiftDown())
        {
			components.add(Component.translatable("rope.description1").withStyle(ChatFormatting.GRAY));
			components.add(Component.translatable("rope.description2").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, components, flag);
    }
}
