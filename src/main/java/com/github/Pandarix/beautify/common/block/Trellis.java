package com.github.Pandarix.beautify.common.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Trellis extends HorizontalDirectionalBlock
{
    public static final MapCodec<Trellis> TRELLIS_MAP_CODEC = simpleCodec(Trellis::new);
    private static final List<Item> VALID_FLOWERS = Arrays.asList(Items.AIR, Items.ROSE_BUSH, Items.SUNFLOWER,
            Items.PEONY, Items.LILAC, Items.VINE, Items.WEEPING_VINES, Items.TWISTING_VINES, Items.GLOW_LICHEN);

    // FLOWERS indicates which index of the flowers List below is active
    public static final IntegerProperty FLOWERS = IntegerProperty.create("flowers", 0, VALID_FLOWERS.size() - 1);

    public static final BooleanProperty CEILLING = BooleanProperty.create("ceilling");

    private static final Map<Direction, VoxelShape> SHAPES_FOR_MODEL = ImmutableMap.of(
            Direction.NORTH, Block.box(0, 0, 14, 16, 16, 16),
            Direction.SOUTH, Block.box(0, 0, 0, 16, 16, 2),
            Direction.WEST, Block.box(14, 0, 0, 16, 16, 16),
            Direction.EAST, Block.box(0, 0, 0, 2, 16, 16)
    );
    private static final VoxelShape SHAPE_CEILLING = Block.box(0, 14, 0, 16, 16, 16);

    public Trellis(Properties p_49795_)
    {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(CEILLING, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return TRELLIS_MAP_CODEC;
    }

    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        // if the trellis placed on underside of block -> hanging from the ceilling
        if (context.getClickedFace() == Direction.DOWN)
        {
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
                    .setValue(CEILLING, true);
        }
        // otherwise on a wall
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(CEILLING, false);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context)
    {
        if (state.getValue(CEILLING))
        {
            return SHAPE_CEILLING;
        }

        return SHAPES_FOR_MODEL.get(state.getValue(FACING));
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        return true;
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity)
    {
        return state.is(this) || super.isLadder(state, level, pos, entity);
    }

    @Override
    @ParametersAreNonnullByDefault
    @NotNull
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        if (interactionHand == InteractionHand.MAIN_HAND)
        {
            // if there is a flower
            if (blockState.getValue(FLOWERS) != 0)
            {

                // giving flower and clearing pot if hand empty or same stack
                if (itemStack.isEmpty())
                {
                    player.setItemInHand(interactionHand, new ItemStack(VALID_FLOWERS.get(blockState.getValue(FLOWERS))));
                    level.setBlock(pos, blockState.setValue(FLOWERS, 0), 3);
                    level.playSound(null, pos, SoundEvents.AZALEA_LEAVES_BREAK, SoundSource.BLOCKS, 1, 1);
                    return ItemInteractionResult.SUCCESS;
                } else if (itemStack.is(VALID_FLOWERS.get(blockState.getValue(FLOWERS)))
                        && itemStack.getCount() < itemStack.getMaxStackSize())
                {
                    itemStack.grow(1);
                    level.setBlock(pos, blockState.setValue(FLOWERS, 0), 3);
                    level.playSound(null, pos, SoundEvents.AZALEA_LEAVES_BREAK, SoundSource.BLOCKS, 1, 1);
                    return ItemInteractionResult.SUCCESS;
                }
                // else just return
                return ItemInteractionResult.FAIL;
            } else
            { // if there is no flower

                // checks if the flower in hand matches the available types
                for (Item flower : VALID_FLOWERS)
                {
                    if (itemStack.getItem().equals(flower))
                    {
                        level.setBlock(pos, blockState.setValue(FLOWERS, VALID_FLOWERS.indexOf(flower)), 3);
                        if (!flower.equals(Items.AIR))
                        {
                            level.playSound(null, pos, SoundEvents.AZALEA_PLACE, SoundSource.BLOCKS, 1, 1);
                        }
                        itemStack.shrink(1);
                        return ItemInteractionResult.CONSUME_PARTIAL;
                    }
                }
                // if the flower is not a valid one
                return ItemInteractionResult.FAIL;
            }
        }
        // end of statement
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_57561_)
    {
        p_57561_.add(FACING, CEILLING, FLOWERS);
    }

    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag)
    {
        if (!Screen.hasShiftDown() && !Screen.hasControlDown())
        {
            components.add(Component.translatable("tooltip.shift").withStyle(ChatFormatting.YELLOW));
            components.add(Component.translatable("tooltip.control").withStyle(ChatFormatting.YELLOW));
        }

        if (Screen.hasShiftDown())
        {
            components.add(Component.translatable("trellis.description1").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("trellis.description2").withStyle(ChatFormatting.GRAY));
        }

        if (Screen.hasControlDown())
        {
            components.add(Component.translatable("trellis.list1").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("trellis.list2").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, components, flag);
    }
}
