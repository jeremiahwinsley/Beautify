package com.github.Pandarix.beautify.common.block;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class LampBamboo extends LanternBlock
{
    public static final BooleanProperty ON = BooleanProperty.create("on");

    private static final VoxelShape SHAPE_HANGING = Block.box(2, 1, 2, 14, 12, 14);
    private static final VoxelShape SHAPE_STANDING = Block.box(4, 0, 4, 12, 13, 12);

    public LampBamboo(Properties p_153465_)
    {
        super(p_153465_);
        this.registerDefaultState(this.defaultBlockState().setValue(ON, true));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return state.getValue(HANGING) ? SHAPE_HANGING : SHAPE_STANDING;
    }

    @Override
    @ParametersAreNonnullByDefault
    @NotNull
    public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pResult)
    {
        pLevel.setBlock(pPos, pState.setValue(ON, !pState.getValue(ON)), 3);
        float f = pState.getValue(ON) ? 0.5F : 0.6F;
        pLevel.playSound((Player) null, pPos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.25F, f);

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(ON);
    }

    @ParametersAreNonnullByDefault
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag)
    {
        if (!Screen.hasShiftDown())
        {
            components.add(Component.translatable("tooltip.shift").withStyle(ChatFormatting.YELLOW));
        } else
        {
            components.add(Component.translatable("bamboo_lamp.description1").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("bamboo_lamp.description2").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, components, flag);
    }
}
