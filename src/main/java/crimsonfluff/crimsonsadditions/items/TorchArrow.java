package crimsonfluff.crimsonsadditions.items;

import crimsonfluff.crimsonsadditions.entities.TorchArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TorchArrow extends ArrowItem {
    public TorchArrow(Settings settings) {
        super(settings);
    }

    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return new TorchArrowEntity(world, shooter);
    }
}
