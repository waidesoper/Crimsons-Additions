package crimsonfluff.crimsonsadditions.backpack;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;

public class BackpackInventory implements Inventory {
    public DefaultedList<ItemStack> items = DefaultedList.ofSize(54, ItemStack.EMPTY);  // double chest worth
    private final ItemStack backpackItem;

    public BackpackInventory(ItemStack backpackItem) {
        this.backpackItem = backpackItem;

        read();
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    @Override
    public int size() { return items.size(); }

    @Override
    public boolean isEmpty() { return this.items.stream().allMatch(ItemStack::isEmpty); }

    @Override
    public ItemStack getStack(int slot) { return items.get(slot); }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.items, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.items, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.items.set(slot, stack);
    }

    @Override
    public void markDirty() { write(); }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void onOpen(PlayerEntity player) {
        Inventory.super.onOpen(player);
        player.playSound(SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.PLAYERS, 1f, 1f);

        read();
    }

    @Override
    public void onClose(PlayerEntity player) {
        Inventory.super.onClose(player);
        player.playSound(SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.PLAYERS, 1f, 1f);

        write();
    }

    private void read() {
        if (this.backpackItem != null && !this.backpackItem.isEmpty())
            Inventories.readNbt(this.backpackItem.getOrCreateNbt().getCompound("backpack"), this.items);
    }

    private void write() {
        if (this.backpackItem != null && !this.backpackItem.isEmpty())
            this.backpackItem.getOrCreateNbt().put("backpack", Inventories.writeNbt(new NbtCompound(), this.items));
    }
}
