package crimsonfluff.crimsonsadditions.entities;

import crimsonfluff.crimsonsadditions.networking.EntitySpawnPacket;
import crimsonfluff.crimsonsadditions.networking.ModPackets;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class TorchArrowEntity extends ArrowEntity {
    public TorchArrowEntity(EntityType<? extends ArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public TorchArrowEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public TorchArrowEntity(World world, LivingEntity owner) {
        super(world, owner);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockPos pos = blockHitResult.getBlockPos();
        Direction dir = blockHitResult.getSide();
        BlockState torch = Blocks.TORCH.getDefaultState();
        switch (dir){
            case DOWN:
                break;
            case UP:
                world.setBlockState(pos.up(), torch);
                break;
            default:
                break;
        }
        this.kill();
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.create(this, ModPackets.SPAWN_PACKET);
    }
}
