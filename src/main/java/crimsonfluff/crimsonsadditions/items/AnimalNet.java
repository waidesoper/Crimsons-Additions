package crimsonfluff.crimsonsadditions.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AnimalNet extends Item {
    public AnimalNet() {
        super(new FabricItemSettings().group(ItemGroup.MISC).maxDamage(16));
    }

    @Override
    public ActionResult useOnEntity(ItemStack itemStack, PlayerEntity playerIn, LivingEntity entityIn, Hand handIn) {
        if (playerIn.world.isClient) return ActionResult.PASS;

        if (itemStack.hasNbt()) {
            if (itemStack.getOrCreateNbt().contains("entityCaptured"))
                return ActionResult.FAIL;
        }

        if (entityIn instanceof HostileEntity) return ActionResult.FAIL;
        if (entityIn instanceof PlayerEntity) return ActionResult.FAIL;

        // Moved damage to when releasing mob, to avoid 1 durability break when picking up chicken
        NbtCompound compoundStack = itemStack.getOrCreateNbt();
        NbtCompound compound = new NbtCompound();
        entityIn.saveNbt(compound);

        // strip out nonsense, not used for our purposes, efficiency, memory etc
        compound.remove("UUID");
        compound.remove("Motion");
        compound.remove("Pos");

        compoundStack.putInt("CustomModelData", 1);     // update model predicate to change texture to animal_net_full
        compoundStack.put("entityCaptured", compound);
        compoundStack.putString("entityDescription", entityIn.getType().getTranslationKey());

        playerIn.setStackInHand(handIn, itemStack);     // for Creative to work

        playerIn.world.playSound(null, playerIn.getBlockPos(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1f, 1f);
        playerIn.spawnSweepAttackParticles();

        entityIn.remove(Entity.RemovalReason.DISCARDED);
        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getPlayer() == null) return ActionResult.PASS;       // in case a mob can use items?
        if (context.getPlayer().world.isClient) return ActionResult.PASS;

        NbtCompound compound = context.getStack().getSubNbt("entityCaptured");
        if (compound == null) return ActionResult.FAIL;

        Entity entity = Registry.ENTITY_TYPE.get(new Identifier(compound.getString("id"))).create(context.getWorld());
        if (entity != null) {
            BlockPos pos = context.getBlockPos().offset(context.getSide());
            if (context.getSide() != Direction.UP) pos.add(0, 0.5, 0);

            entity.readNbt(compound);
            entity.refreshPositionAndAngles(pos, entity.getYaw(), entity.getPitch());  // cant use setPos() !
            context.getWorld().spawnEntity(entity);

            context.getStack().removeSubNbt("entityCaptured");
            context.getStack().removeSubNbt("entityDescription");
            context.getStack().removeSubNbt("CustomModelData");

            context.getWorld().playSound(null, context.getPlayer().getBlockPos(), SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.PLAYERS, 1f, 1f);
            context.getStack().damage(1, context.getPlayer(), plyr -> plyr.sendToolBreakStatus(context.getHand()));

            return ActionResult.SUCCESS;
        } else
            return ActionResult.FAIL;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, @Nullable World worldIn, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(itemStack, worldIn, tooltip, context);

        NbtCompound compound = itemStack.getSubNbt("entityCaptured");
        if (compound != null) {
            tooltip.add(new TranslatableText(itemStack.getOrCreateNbt().getString("entityDescription")));

            if (compound.contains("CustomName", 8))
                tooltip.add(Text.Serializer.fromJson(compound.getString("CustomName")).formatted(Formatting.ITALIC));
        }
    }
}
