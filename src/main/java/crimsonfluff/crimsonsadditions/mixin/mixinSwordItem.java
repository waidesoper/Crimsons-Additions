package crimsonfluff.crimsonsadditions.mixin;

import crimsonfluff.crimsonsadditions.CrimsonsAdditions;
import crimsonfluff.crimsonsadditions.init.initEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwordItem.class)
public class mixinSwordItem {
//    @Inject(at = @At("TAIL"), cancellable = true, method = "postHit")
    @Inject(at = @At("TAIL"), cancellable = true, method = "postHit(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/LivingEntity;)Z")
    private void injectPostHit(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir) {
        if (! target.isAlive()) {
            if (attacker instanceof PlayerEntity player) {
                //player.sendMessage(new LiteralText("TARGET: DEAD"), false);

                if (EnchantmentHelper.getLevel(initEnchantments.KILLERS, stack) > 0) {
                    if (player.world.random.nextInt(100) < CrimsonsAdditions.CONFIG.killersFervourChance) {
                        Item itemEGG = SpawnEggItem.forEntity(target.getType());
                        if (itemEGG != null) target.dropItem(itemEGG);
                    }
                }
            }

            cir.setReturnValue(true);
        }
    }
}
