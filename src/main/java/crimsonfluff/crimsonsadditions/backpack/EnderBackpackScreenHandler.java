package crimsonfluff.crimsonsadditions.backpack;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class EnderBackpackScreenHandler extends GenericContainerScreenHandler {
    public EnderBackpackScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, int rows) {
        super(type, syncId, playerInventory, inventory, rows);
    }

    public static EnderBackpackScreenHandler createGeneric9x3(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new EnderBackpackScreenHandler(ScreenHandlerType.GENERIC_9X3, syncId, playerInventory, inventory, 3);
    }

    // this whole extension just to play a close sound !!, lolz
    @Override
    public void close(PlayerEntity player) {
//        player.world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_ENDER_CHEST_CLOSE, SoundCategory.PLAYERS, 1f, 1f);
        player.playSound(SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.PLAYERS, 1f, 1f);

        super.close(player);
    }
}
