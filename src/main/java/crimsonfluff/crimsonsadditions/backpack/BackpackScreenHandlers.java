package crimsonfluff.crimsonsadditions.backpack;

import crimsonfluff.crimsonsadditions.init.initItems;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public final class BackpackScreenHandlers {
    public static final ScreenHandlerType<BackpackScreenHandler> BACKPACK_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(initItems.ID_BACKPACK,
        (syncID, playerInventory, buf) -> new BackpackScreenHandler(syncID, playerInventory, new BackpackInventory(null)));
}
