package com.github.Pandarix.beautify.common.block;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;

public class HangingPot extends LanternBlock
{
    private static final List<Item> validFlowers = Arrays.asList(Items.AIR, Items.ROSE_BUSH, Items.LILAC, Items.BLUE_ORCHID, Items.VINE, Items.SUNFLOWER, Items.PEONY, Items.AZURE_BLUET, Items.RED_TULIP, Items.ORANGE_TULIP, Items.WHITE_TULIP, Items.PINK_TULIP, Items.ALLIUM, Items.DANDELION,
            Items.POPPY, Items.GLOW_LICHEN, Items.OXEYE_DAISY, Items.LILY_OF_THE_VALLEY, Items.CORNFLOWER, Items.WEEPING_VINES, Items.TWISTING_VINES, Items.WITHER_ROSE, Items.GLOW_BERRIES, Items.SWEET_BERRIES, Items.SHORT_GRASS, Items.FERN);

    // POTFLOWER indicates which index of the flowers List below is active
    public static final IntegerProperty POTFLOWER = IntegerProperty.create("potflower", 0, validFlowers.size() - 1);

    public static final BooleanProperty GROWN = BooleanProperty.create("grown");

    public HangingPot(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(POTFLOWER, 0).setValue(GROWN, false));
    }

    // hitbox for the HangingPot
    private static final VoxelShape HANGING_SHAPE = Shapes.or(box(5, 0, 5, 11, 4, 11), box(3.75, 4, 3.75, 12.25, 8, 12.25), box(5, 8, 5, 11, 16, 11));
    private static final VoxelShape STANDING_SHAPE = Shapes.or(box(5, 0, 5, 11, 5, 11), box(4, 5, 4, 12, 8, 12));

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return state.getValue(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos blockPos, boolean bool)
    {
        //if the plant is grown long
        if (state.getValue(GROWN))
        {
            //if the neighbour is a model that clips into the pot
            if (blockPos.equals(pos.below()) && level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP))
            {
                //make pot ungrown again
                level.setBlock(pos, state.setValue(GROWN, false), 3);
                //spawn item from plant inside
                ItemEntity Item = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(validFlowers.get(state.getValue(POTFLOWER))));
                level.addFreshEntity(Item);
                //play breaking sound
                level.playSound(null, pos, SoundEvents.HANGING_ROOTS_BREAK, SoundSource.BLOCKS, 1, 1);
            }
        }
        super.neighborChanged(state, level, pos, block, blockPos, bool);
    }

    @Override
    @ParametersAreNonnullByDefault
    @NotNull
    public ItemInteractionResult useItemOn(ItemStack itemStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pResult)
    {
        if (pHand == InteractionHand.MAIN_HAND)
        {
            // growing plant
            // block below must not be sturdy to prevent clipping models
            if (itemStack.getItem().equals(Items.BONE_MEAL) && pState.getValue(POTFLOWER) != 0 && !pLevel.getBlockState(pPos.below()).isFaceSturdy(pLevel, pPos.below(), Direction.UP))
            {
                if (!pState.getValue(GROWN))
                {
                    pLevel.playSound(null, pPos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1, 1);
                    if (!pPlayer.isCreative())
                    {
                        itemStack.shrink(1);
                    }
                    pLevel.setBlock(pPos, pState.setValue(GROWN, true), 3);
                    return ItemInteractionResult.sidedSuccess(pLevel.isClientSide());
                }
            }

            // when plant is grown -> using shears cuts it down
            if (itemStack.canPerformAction(ItemAbilities.SHEARS_HARVEST) && pState.getValue(POTFLOWER) != 0 && pState.getValue(GROWN))
            {
                pLevel.playSound(null, pPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1, 1);
                pLevel.setBlock(pPos, pState.setValue(GROWN, false), 3);
                ItemEntity Item = new ItemEntity(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), new ItemStack(validFlowers.get(pState.getValue(POTFLOWER))));
                pLevel.addFreshEntity(Item);
                return ItemInteractionResult.SUCCESS;
            }

            // if there is a flower
            if (pState.getValue(POTFLOWER) != 0)
            {
                // giving flower and clearing pot if hand empty
                if (itemStack.isEmpty())
                {
                    pPlayer.setItemInHand(pHand, new ItemStack(validFlowers.get(pState.getValue(POTFLOWER)), pState.getValue(GROWN) ? 2 : 1)); // giving 1 or 2 of plant (grown or not)
                    pLevel.setBlock(pPos, pState.setValue(POTFLOWER, 0).setValue(GROWN, false), 3); // emptying the pot
                    pLevel.playSound(null, pPos, SoundEvents.COMPOSTER_READY, SoundSource.BLOCKS, 1, 1);
                    return ItemInteractionResult.SUCCESS;
                } else if (itemStack.is(validFlowers.get(pState.getValue(POTFLOWER))) && itemStack.getCount() < itemStack.getMaxStackSize())
                {
                    itemStack.grow(pState.getValue(GROWN) ? 2 : 1); // giving 1 or 2 of plant (grown or not)
                    pLevel.setBlock(pPos, pState.setValue(POTFLOWER, 0).setValue(GROWN, false), 3);
                    pLevel.playSound(null, pPos, SoundEvents.AZALEA_LEAVES_BREAK, SoundSource.BLOCKS, 1, 1);
                    return ItemInteractionResult.SUCCESS;
                }

                // else just return
                return ItemInteractionResult.FAIL;
            } else
            { // if there is no flower

                // checks if the flower in hand matches the available types
                for (Item flower : validFlowers)
                {
                    if (itemStack.getItem().equals(flower))
                    {
                        pLevel.setBlock(pPos, pState.setValue(POTFLOWER, validFlowers.indexOf(flower)), 3);
                        if (!flower.equals(Items.AIR))
                        {
                            pLevel.playSound(null, pPos, SoundEvents.AZALEA_PLACE, SoundSource.BLOCKS, 1, 1);
                        }
                        if (!pPlayer.isCreative())
                        {
                            itemStack.shrink(1);
                        }
                        return ItemInteractionResult.SUCCESS;
                    }
                }
                // if the flower is not a valid one
                return ItemInteractionResult.FAIL;
            }
        }
        // end of statement
        return ItemInteractionResult.FAIL;
    }

    // creating Blockstates
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(POTFLOWER, GROWN);
    }

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
            components.add(Component.translatable("hanging_pot.description1").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("hanging_pot.description2").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("hanging_pot.description3").withStyle(ChatFormatting.GRAY));
        }

        if (Screen.hasControlDown())
        {
            components.add(Component.translatable("hanging_pot.list1").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("hanging_pot.list2").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, components, flag);
    }
}