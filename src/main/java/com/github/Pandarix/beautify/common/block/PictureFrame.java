package com.github.Pandarix.beautify.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

public class PictureFrame extends HorizontalDirectionalBlock
{
    public static final MapCodec<PictureFrame> PICTURE_FRAME_MAP_CODEC = simpleCodec(PictureFrame::new);
    private static final int MODELCOUNT = 13; // number of models the frame has
    public static final IntegerProperty FRAME_MOTIVE = IntegerProperty.create("frame_motive", 0, MODELCOUNT - 1);
    protected static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 8, 11);

    public PictureFrame(Properties p_49795_)
    {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(FRAME_MOTIVE, 0).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return PICTURE_FRAME_MAP_CODEC;
    }

    @Override
    @ParametersAreNonnullByDefault
    @NotNull
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos pos, Player player, BlockHitResult blockHitResult)
    {
        if (player.isShiftKeyDown())
        {
            int currentModel = blockState.getValue(FRAME_MOTIVE); // current index
            // reset if it surpasses the number of possible models
            if (currentModel + 1 > MODELCOUNT - 1)
            {
                level.setBlock(pos, blockState.setValue(FRAME_MOTIVE, 0), 3);
                level.playSound(null, pos, SoundEvents.PAINTING_PLACE, SoundSource.BLOCKS, 1, 1);
                return InteractionResult.SUCCESS;
            } else
            { // increases index
                level.setBlock(pos, blockState.setValue(FRAME_MOTIVE, currentModel + 1), 3);
                level.playSound(null, pos, SoundEvents.PAINTING_PLACE, SoundSource.BLOCKS, 1, 1);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext)
    {
        return SHAPE;
    }

    public PushReaction getPistonPushReaction(BlockState p_153494_)
    {
        return PushReaction.DESTROY;
    }

    public VoxelShape getOcclusionShape(BlockState p_56336_, BlockGetter p_56337_, BlockPos p_56338_)
    {
        return Shapes.empty();
    }

    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Random rand = new Random();
        int randomNum = rand.nextInt((MODELCOUNT));

        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(FRAME_MOTIVE, randomNum);
    }

    // creates blockstate
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FRAME_MOTIVE, FACING);
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
            components.add(Component.translatable("frame.description1").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("frame.description2").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, components, flag);
    }
}
