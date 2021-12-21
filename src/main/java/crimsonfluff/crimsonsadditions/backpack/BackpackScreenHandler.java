package crimsonfluff.crimsonsadditions.backpack;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BackpackScreenHandler extends ScreenHandler {
    private final PlayerInventory playerInventory;
    private final Inventory inventory;

    public final int inventoryWidth;
    public final int inventoryHeight;

    public BackpackScreenHandler(final int syncId, final PlayerInventory playerInventory, final Inventory inventory) {
        super(BackpackScreenHandlers.BACKPACK_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.playerInventory = playerInventory;
        this.inventoryWidth = 9;
        this.inventoryHeight = 6;

        checkSize(inventory, inventoryWidth * inventoryHeight);
        inventory.onOpen(playerInventory.player);
        setupSlots();
    }

    @Override
    public void close(final PlayerEntity player) {
        super.close(player);
        inventory.onClose(player);
    }

    public void setupSlots() {
        int i = (this.inventoryHeight - 4) * 18;

        for (int rows = 0; rows < this.inventoryHeight; ++rows) {
            for (int cols = 0; cols < 9; ++cols) {
                this.addSlot(new BackpackSlot(inventory, cols + rows * 9, 8 + cols * 18, 18 + rows * 18));
            }
        }

        for (int rows = 0; rows < 3; ++rows) {
            for (int cols = 0; cols < 9; ++cols) {
                this.addSlot(new BackpackSlot(playerInventory, cols + rows * 9 + 9, 8 + cols * 18, 103 + rows * 18 + i));
            }
        }

        for (int cols = 0; cols < 9; ++cols) {
            this.addSlot(new BackpackSlot(playerInventory, cols, 8 + cols * 18, 161 + i));
        }
    }

    @Override
    public boolean canUse(final PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(final PlayerEntity player, final int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        ItemStack originalStack = slot.getStack();
        Item item = originalStack.getItem();

        if (BackpackSlot.BLACKLIST.contains(item)) return ItemStack.EMPTY;

        if (slot.hasStack()) {
            newStack = originalStack.copy();

            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);

            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }
}
