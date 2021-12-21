package crimsonfluff.crimsonsadditions.backpack;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class invcontrol {
    public static boolean insertItem(ItemStack stack, Inventory toINV) {
        boolean bl = false;
        int i = 0;

        ItemStack itemStack;
        if (stack.isStackable()) {
            while(! stack.isEmpty()) {
                if (i >= toINV.size()) break;

                itemStack = toINV.getStack(i);
                if (! itemStack.isEmpty() && ItemStack.canCombine(stack, itemStack)) {
                    int j = itemStack.getCount() + stack.getCount();

                    if (j <= stack.getMaxCount()) {
                        stack.setCount(0);
                        itemStack.setCount(j);
                        bl = true;

                    } else if (itemStack.getCount() < stack.getMaxCount()) {
                        stack.decrement(stack.getMaxCount() - itemStack.getCount());
                        itemStack.setCount(stack.getMaxCount());

                        bl = true;
                    }
                }

                ++i;
            }
        }

        if (! stack.isEmpty()) {
            i = 0;

            while(true) {
                if (i >= toINV.size()) break;

                itemStack = toINV.getStack(i);
                if (itemStack.isEmpty()) {
                    if (stack.getCount() > 64) {
                        toINV.setStack(i, stack.split(64));
                    } else {
                        toINV.setStack(i, stack.split(stack.getCount()));
                    }

                    bl = true;
                    break;
                }

                ++i;
            }
        }

        return bl;
    }
}
