package crimsonfluff.crimsonsadditions.blocks;

import com.google.common.collect.Lists;
import crimsonfluff.crimsonsadditions.init.initBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Queue;

public class LavaSponge extends Block {
    public LavaSponge(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock())) this.update(world, pos, state);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        this.update(world, pos, state);
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    protected void update(World world, BlockPos pos, BlockState state) {
        if (this.absorbLava(world, pos)) {
            world.syncWorldEvent(2001, pos, Block.getRawIdFromState(Blocks.LAVA.getDefaultState()));
            world.setBlockState(pos, initBlocks.LAVA_SPONGE_WET.getDefaultState());

            ((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 250, 0.5, 0.5, 0.5, 0);
            ((ServerWorld) world).playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
        }
    }

    private boolean absorbLava(World world, BlockPos pos) {
        Queue<Pair<BlockPos, Integer>> queue = Lists.newLinkedList();
        queue.add(new Pair<>(pos, 0));
        int i = 0;

        while (!queue.isEmpty()) {
            Pair<BlockPos, Integer> pair = (Pair) queue.poll();
            BlockPos blockPos = pair.getLeft();
            int j = pair.getRight();
            Direction[] var8 = Direction.values();

            for (Direction direction : var8) {
                BlockPos blockPos2 = blockPos.offset(direction);
                FluidState fluidState = world.getFluidState(blockPos2);

                if (fluidState.isIn(FluidTags.LAVA)) {
                    BlockState blockState = world.getBlockState(blockPos2);

                    if (blockState.getBlock() instanceof FluidDrainable && !((FluidDrainable) blockState.getBlock()).tryDrainFluid(world, blockPos2, blockState).isEmpty()) {
                        ++i;
                        if (j < 6) {
                            queue.add(new Pair<>(blockPos2, j + 1));
                        }

                    } else if (blockState.getBlock() instanceof FluidBlock) {
                        world.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 3);
                        ++i;
                        if (j < 6) {
                            queue.add(new Pair<>(blockPos2, j + 1));
                        }
                    }
                }
            }

            if (i > 64) break;
        }

        return i > 0;
    }
}
