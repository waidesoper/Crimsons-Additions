package crimsonfluff.crimsonsadditions.backpack;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Backpack extends Item {
    public Backpack(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canBeNested() {
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);

        // sneaking allows us to shift right-click a chest and transfer items from backpack to chest
        if (user.isSneaking()) return TypedActionResult.pass(itemStack);

        if (! world.isClient) openScreen(user, itemStack);

        return super.use(world, user, hand);
    }

    public static BackpackInventory getInventory(ItemStack stack) {
//        if (! stack.getOrCreateNbt().contains("backpack"))
//            stack.getNbt().put("backpack", new NbtCompound());

        return new BackpackInventory(stack);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, @Nullable World world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(itemStack, world, tooltip, options);

        if (itemStack.hasNbt()) {
            NbtCompound backpack = itemStack.getNbt().getCompound("backpack");
            if (backpack.isEmpty()) return;

            if (backpack.contains("Items", 9)) {
                NbtList listnbt = backpack.getList("Items", 10);
                int listsize = Integer.min(5, listnbt.size());

                for (int i = 0; i < listsize; ++i) {
                    NbtCompound compoundnbt = listnbt.getCompound(i);
                    ItemStack item = ItemStack.fromNbt(compoundnbt);

                    MutableText mutableText = item.getName().shallowCopy();
                    mutableText.append(" x").append(String.valueOf(item.getCount()));
                    tooltip.add(mutableText);
                }

                if (listnbt.size() > 5)
                    tooltip.add(new TranslatableText("container.shulkerBox.more", listnbt.size() - 5).formatted(Formatting.ITALIC));
            }
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient) return ActionResult.PASS;

        Inventory outputINV = HopperBlockEntity.getInventoryAt(context.getWorld(), context.getBlockPos());
        if (outputINV == null) return ActionResult.PASS;

        BackpackInventory packINV = Backpack.getInventory(context.getStack());
        if (packINV.isEmpty()) {
            for (int a = 0; a < outputINV.size(); a++) {
                ItemStack itemStack = outputINV.getStack(a);    // move from inventory to backpack
                invcontrol.insertItem(itemStack, packINV);
                outputINV.setStack(a, itemStack);
            }
        } else {
            for (int a = 0; a < packINV.size(); a++) {
                ItemStack itemStack = packINV.getStack(a);     // move from backpack to inventory
                invcontrol.insertItem(itemStack, outputINV);
                packINV.setStack(a, itemStack);
            }
        }

        outputINV.markDirty();
        packINV.markDirty();

        return super.useOnBlock(context);
    }

    public static void openScreen(PlayerEntity user, ItemStack stack) {
        user.openHandledScreen(new ExtendedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return stack.getName();
            }

            @Override
            public ScreenHandler createMenu(int syncID, PlayerInventory playerInventory, PlayerEntity player) {
                return new BackpackScreenHandler(syncID, playerInventory, new BackpackInventory(stack));
            }

            @Override
            public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf buf) { }
        });
    }
}
