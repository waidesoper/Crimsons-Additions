package crimsonfluff.crimsonsadditions.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.event.GameEvent;

public class CopperShears extends ShearsItem {
    public CopperShears(Settings settings) { super(settings); }

    // Vanilla: it's down to the entity to check if its being sheered, SheepEntity.class
    // sheers do nothing when clicked on entity

    // if a vanilla sheep we must shear it ourselves because it only checks for Item.SHEARS
    // CrimsonGoats checks interactMob() like Vanilla Sheep does
    // I still say its down to the Shears to check `entity instanceof Shearable`, not the entity to check if its being interacted with by shears

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        if (! player.world.isClient) {
            ItemStack itemStack = player.getStackInHand(hand);

            if (entity instanceof SheepEntity shearable) {
                if (shearable.isShearable()) {
                    shearable.sheared(SoundCategory.PLAYERS);           // sound doesnt play
                    player.world.playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1f, 1f);
                    player.emitGameEvent(GameEvent.SHEAR, player);

                    itemStack.damage(1, player, plyr -> plyr.sendToolBreakStatus(hand));

                    return ActionResult.SUCCESS;

                } else
                    return ActionResult.CONSUME;
            }
        }

        return ActionResult.PASS;       // this is the super
    }
}
