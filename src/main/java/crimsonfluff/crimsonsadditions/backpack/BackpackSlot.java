package crimsonfluff.crimsonsadditions.backpack;

import com.google.common.collect.Sets;
import crimsonfluff.crimsonsadditions.init.initItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

import java.util.Set;

public class BackpackSlot extends Slot {
    public static final Set<Item> BLACKLIST;

    static {
        BLACKLIST = Sets.newHashSet(Items.SHULKER_BOX, Items.BLACK_SHULKER_BOX, Items.BLUE_SHULKER_BOX,
            Items.BROWN_SHULKER_BOX, Items.CYAN_SHULKER_BOX, Items.GRAY_SHULKER_BOX, Items.GREEN_SHULKER_BOX,
            Items.LIGHT_BLUE_SHULKER_BOX, Items.LIGHT_GRAY_SHULKER_BOX, Items.LIME_SHULKER_BOX,
            Items.MAGENTA_SHULKER_BOX, Items.ORANGE_SHULKER_BOX, Items.PINK_SHULKER_BOX, Items.RED_SHULKER_BOX,
            Items.WHITE_SHULKER_BOX, Items.YELLOW_SHULKER_BOX, Items.PURPLE_SHULKER_BOX, initItems.BACKPACK);
    }

    public BackpackSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        return stackMovementIsAllowed(getStack());
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stackMovementIsAllowed(stack);
    }

    public boolean stackMovementIsAllowed(ItemStack stack) {
        Item item = stack.getItem();
        return !BLACKLIST.contains(item);
    }
}
