package crimsonfluff.crimsonsadditions.items;

import crimsonfluff.crimsonsadditions.backpack.Backpack;
import crimsonfluff.crimsonsadditions.backpack.BackpackInventory;
import crimsonfluff.crimsonsadditions.backpack.invcontrol;
import crimsonfluff.crimsonsadditions.init.initEnchantments;
import crimsonfluff.crimsonsadditions.init.initItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// Could just add silk-touch enchant when mined a block, but this could be exploited by enchant removal machines/tools

public class GlassCutter extends ToolItem {
    public GlassCutter(ToolMaterial material, Settings settings) { super(material, settings); }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return state.getMaterial() == Material.GLASS ? 8.0f : 0f;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return state.getMaterial() == Material.GLASS;
    }

    // TODO: Should I check loot-tables? we are essentially silk-touching glass, so return glass?
    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (miner instanceof ServerPlayerEntity player) {
            if (state.getMaterial() == Material.GLASS) {
                ItemStack itemStack = new ItemStack(state.getBlock());

                if (EnchantmentHelper.getLevel(initEnchantments.BAGNET, stack) != 0) {
                    if (player.getStackInHand(Hand.OFF_HAND).getItem() == initItems.BACKPACK) {
                        BackpackInventory INV = Backpack.getInventory(player.getStackInHand(Hand.OFF_HAND));

                        // this will fill existing stacks and fill empty slots with each item
                        // ie: transferring as much as it can from itemsOUT into BackPack.Inventory
                        if (invcontrol.insertItem(itemStack, INV))
                            player.getStackInHand(Hand.OFF_HAND).getOrCreateNbt().put("backpack", Inventories.writeNbt(new NbtCompound(), INV.items));
                    }
                }

                // in case backpack took entire stack, check if remainder is empty
                if (! itemStack.isEmpty())
                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);

                if (! player.isCreative()) {
                    stack.damage(1, miner, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                    player.addExhaustion(0.005f);
                    player.increaseStat(Stats.MINED.getOrCreateStat(state.getBlock()), 1);
                }

                return false;
            }
        }

        return super.postMine(stack, world, state, pos, miner);
    }
}
