package crimsonfluff.crimsonsadditions.items;

import crimsonfluff.crimsonsadditions.CrimsonsAdditions;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class MagnetItem extends Item {
    //public class MagnetItem extends Item implements Trinket {
    public MagnetItem() {
        super(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).maxDamage(CrimsonsAdditions.CONFIG.magnetMaxDamage));
    }

    @Override
    public boolean canRepair(ItemStack toRepair, ItemStack repair) {
        return repair.isItemEqual(new ItemStack(Items.IRON_INGOT)) || super.canRepair(toRepair, repair);
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getStackInHand(handIn);
        if (worldIn.isClient) return TypedActionResult.consume(itemStack);

        // 0=off, 1=items, 2=xp (bitwise)
        int magnetMode = itemStack.getOrCreateNbt().getInt("CustomModelData");
        if (playerIn.isSneaking())
            changeMagnetMode(magnetMode, playerIn, itemStack);
        else
            changeMagnetToggle(magnetMode, playerIn, itemStack);

        return TypedActionResult.success(itemStack);
    }

    public void changeMagnetMode(int magnetMode, PlayerEntity player, ItemStack itemStack) {
        magnetMode++;
        if (magnetMode == 4) magnetMode = 1;    // dont start from off

        String modeString = switch (magnetMode) {
            default -> "tip.crimsonsadditions.magnet1";
            case 2 -> "tip.crimsonsadditions.magnet2";
            case 3 -> "tip.crimsonsadditions.magnet3";
        };

        player.sendMessage(new TranslatableText(modeString, itemStack.getName()), true);

        player.world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_BELL, SoundCategory.PLAYERS, 1f, 0.9f);

        itemStack.getNbt().putInt("CustomModelData", magnetMode);
    }

    public void changeMagnetToggle(int magnetMode, PlayerEntity player, ItemStack itemStack) {
        boolean active = !itemStack.getOrCreateNbt().getBoolean("active");
        itemStack.getNbt().putBoolean("active", active);

        // set default to items
        if (active && magnetMode == 0) itemStack.getNbt().putInt("CustomModelData", 1);

        if (active)
            player.sendMessage(new TranslatableText("tip.crimsonsadditions.active", itemStack.getName()), true);
        else
            player.sendMessage(new TranslatableText("tip.crimsonsadditions.inactive", itemStack.getName()), true);

        player.world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_BELL, SoundCategory.PLAYERS, 1f, (active) ? 0.9f : 0.1f);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return (stack.getOrCreateNbt().getBoolean("active"));
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isClient) {
            if (!(entityIn instanceof PlayerEntity)) return;        // stop zombies and the like using the magnet, lol

            if (stack.getOrCreateNbt().getBoolean("active")) {
                PlayerEntity playerIn = (PlayerEntity) entityIn;

                double x = entityIn.getX();
                double y = entityIn.getY();
                double z = entityIn.getZ();
                boolean shouldBreak = false;
                int magnetMode = stack.getNbt().getInt("CustomModelData");

                int r = (CrimsonsAdditions.CONFIG.magnetPullRadius);
                Box area = new Box(x - r, y - r, z - r, x + r, y + r, z + r);

                if ((magnetMode & 0b00000001) == 1) {
//                    List<ItemEntity> items = worldIn.getEntitiesOfClass(ItemEntity.class, area, itm -> { if (itm.getThrower() != playerIn.getUUID()) return;});
                    List<ItemEntity> items = worldIn.getEntitiesByClass(ItemEntity.class, area, EntityPredicates.VALID_ENTITY);

                    if (items.size() != 0) {
                        for (ItemEntity itemIE : items) {
                            ((ServerWorld) worldIn).spawnParticles(ParticleTypes.POOF, itemIE.getX(), itemIE.getY(),
                                    itemIE.getZ(), 2, 0D, 0D, 0D, 0D);

                            //r = itemIE.getItem().getCount();
                            itemIE.setPickupDelay(0);
                            itemIE.onPlayerCollision(playerIn);
                            //if (r != 0) {
                            if (itemIE.distanceTo(playerIn) > 1.5f) shouldBreak = true;
                            itemIE.setPos(x, y, z);
                            //}
                        }
                    }
                }

                // Handle the XP
                if ((magnetMode & 0b00000010) == 2) {
                    List<ExperienceOrbEntity> orbs = worldIn.getEntitiesByClass(ExperienceOrbEntity.class, area, EntityPredicates.VALID_ENTITY);

                    if (orbs.size() != 0) {
                        shouldBreak = true;
                        worldIn.playSound(null, x, y, z, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1f, 1f);

                        ArrayList<ItemStack> MendingItems = new ArrayList<>();
                        ItemStack stacks;

                        // getRandomEquippedWithEnchantment only works with offhand, main hand, armor slots
                        // so make a list of valid items, add magnet to it, then randomly choose an item to repair
                        for (int a = 36; a < 41; a++) {
                            stacks = playerIn.getInventory().getStack(a);
                            if (EnchantmentHelper.getLevel(Enchantments.MENDING, stacks) > 0)
                                if (stacks.isDamaged()) MendingItems.add(stacks);
                        }

                        // if Magnet is MENDING then add to list
                        if (EnchantmentHelper.getLevel(Enchantments.MENDING, stack) > 0)
                            if (stack.isDamaged()) MendingItems.add(stack);

                        for (ExperienceOrbEntity orb : orbs) {
                            ((ServerWorld) worldIn).spawnParticles(ParticleTypes.POOF, orb.getBlockPos().getX(), orb.getBlockPos().getY(), orb.getBlockPos().getZ(), 2, 0D, 0D, 0D, 0D);
                            int xpAmount = orb.getExperienceAmount();
                            // Choose random item from MendingItems list
                            if (MendingItems.size() > 0) {
                                r = worldIn.random.nextInt(MendingItems.size());
                                stacks = MendingItems.get(r);

                                int i = Math.min((int) (xpAmount * stacks.getRepairCost()), stacks.getDamage());
                                xpAmount -= i / 2;     //orb.durabilityToXp(i);
                                stacks.setDamage(stacks.getDamage() - i);

                                if (stacks.getDamage() == 0) MendingItems.remove(r);
                            }

                            if (xpAmount > 0) playerIn.addExperience(xpAmount);
                            orb.remove(Entity.RemovalReason.DISCARDED);
                        }
                    }
                }

                // NOTE: DamageItem checks Creative mode !
                if (shouldBreak) {
                    stack.damage(1, playerIn, plyr -> plyr.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                    stack.setBobbingAnimationTime(5);
                }
            }
        }
    }
}
