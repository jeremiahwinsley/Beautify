package com.github.Pandarix.beautify.common.block;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class LampLightBulb extends LanternBlock
{
    public static final BooleanProperty ON = BooleanProperty.create("on");

    private static final VoxelShape SHAPE_HANGING = Block.box(5, 3, 5, 11, 16, 11);
    private static final VoxelShape SHAPE_STANDING = Block.box(4, 0, 4, 12, 13, 12);

    public LampLightBulb(Properties p_153465_)
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
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos pos, Player player, BlockHitResult blockHitResult)
    {
        level.setBlock(pos, blockState.setValue(ON, !blockState.getValue(ON)), 3);
        float f = blockState.getValue(ON) ? 0.5F : 0.6F;
        level.playSound((Player) null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.25F, f);

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource randomSource)
    {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.7D;
        double d2 = (double) pos.getZ() + 0.5D;

        if (randomSource.nextBoolean() && blockState.getValue(ON))
        {
            if (blockState.getValue(HANGING))
            {
                level.addParticle(ParticleTypes.SMOKE, d0, d1 - 0.3, d2, 0.0D, 0.0D, 0.0D);
            } else
            {
                level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(ON);
    }

    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag)
    {
        if (!Screen.hasShiftDown())
        {
            components.add(Component.translatable("tooltip.shift").withStyle(ChatFormatting.YELLOW));
        } else
        {
            components.add(Component.translatable("light_bulb.description1").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("light_bulb.description2").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, components, flag);
    }
}
