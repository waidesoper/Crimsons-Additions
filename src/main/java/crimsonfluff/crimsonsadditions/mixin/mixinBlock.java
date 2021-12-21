package crimsonfluff.crimsonsadditions.mixin;

import crimsonfluff.crimsonsadditions.CrimsonsAdditions;
import crimsonfluff.crimsonsadditions.backpack.Backpack;
import crimsonfluff.crimsonsadditions.backpack.BackpackInventory;
import crimsonfluff.crimsonsadditions.backpack.invcontrol;
import crimsonfluff.crimsonsadditions.init.initEnchantments;
import crimsonfluff.crimsonsadditions.init.initItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(Block.class)
public class mixinBlock {
    private static final Identifier OVERWORLD = new Identifier("minecraft:overworld_ores");
    private static final Identifier NETHER = new Identifier("minecraft:nether_ores");
    private static final Identifier END = new Identifier("minecraft:end_ores");

    @Inject(
        method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;",
        at = @At("RETURN"),
        cancellable = true)

    private static void injectGetDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack, CallbackInfoReturnable<List<ItemStack>> cir) {
//        List<ItemStack> itemsIn = cir.getReturnValue();
//        List<ItemStack> itemsOUT = new ArrayList<>();

        if (entity instanceof ServerPlayerEntity player) {
            List<ItemStack> itemsOUT = cir.getReturnValue();

            if (EnchantmentHelper.getLevel(initEnchantments.MINERS, stack) != 0) {
                Tag<Item> iTag = null;

                if (state.getBlock() == Blocks.STONE)
                    iTag = ItemTags.getTagGroup().getTag(OVERWORLD);

                else if (state.getBlock() == Blocks.NETHERRACK)
                    iTag = ItemTags.getTagGroup().getTag(NETHER);

                else if (state.getBlock() == Blocks.END_STONE)
                    iTag = ItemTags.getTagGroup().getTag(END);

                if (iTag != null) {
                    if (world.random.nextInt(100) < CrimsonsAdditions.CONFIG.minersDelightChance)
                        itemsOUT.add(new ItemStack(iTag.getRandom(world.random)));
                }
            }

            if (EnchantmentHelper.getLevel(initEnchantments.AUTO_SMELT, stack) != 0) {
                for (int a = 0; a < itemsOUT.size(); a++) {
                    ItemStack itemStack = itemsOUT.get(a);
                    Optional<SmeltingRecipe> recipe = world.getRecipeManager().listAllOfType(RecipeType.SMELTING).stream().filter(rcpe -> rcpe.getIngredients().get(0).test(itemStack)).findFirst();

                    if (recipe.isPresent()) {
                        ItemStack smelted = recipe.get().getOutput().copy();       // copy because we re-use `smelted`
                        smelted.setCount(itemStack.getCount());

                        itemsOUT.set(a, smelted);
                    }
                }

                // if we have 'Bagnet' enchant then continue to handle it
                // else just return new list of 'smelted' items
//                if (EnchantmentHelper.getLevel(initEnchantments.BAGNET, stack) == 0) {
//                    cir.setReturnValue(itemsOUT);
//                    return;
//                }
            }

            if (EnchantmentHelper.getLevel(initEnchantments.BAGNET, stack) != 0) {
                if (player.getStackInHand(Hand.OFF_HAND).getItem() == initItems.BACKPACK) {
                    BackpackInventory INV = Backpack.getInventory(player.getStackInHand(Hand.OFF_HAND));

                    // this will fill existing stacks and fill empty slots with each item
                    // ie: transferring as much as it can from itemsOUT into BackPack.Inventory
                    itemsOUT.forEach(itm -> invcontrol.insertItem(itm, INV));
                    player.getStackInHand(Hand.OFF_HAND).getOrCreateNbt().put("backpack", Inventories.writeNbt(new NbtCompound(), INV.items));

                    // remove any empty items
                    itemsOUT.removeIf(ItemStack::isEmpty);

                    // let vanilla process any items that weren't moved to BackPack
                    if (itemsOUT.size() != 0) {
                        cir.setReturnValue(itemsOUT);

                    } else {
                        cir.setReturnValue(new ArrayList<>());
                    }

                    return;
                }
            }

            if (EnchantmentHelper.getLevel(initEnchantments.VOID, stack) != 0) {
                cir.setReturnValue(new ArrayList<>());
                return;
            }

            cir.setReturnValue(itemsOUT);
//            cir.setReturnValue(cir.getReturnValue());
        }
    }
}
