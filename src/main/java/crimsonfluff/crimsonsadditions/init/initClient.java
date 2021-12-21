package crimsonfluff.crimsonsadditions.init;

import crimsonfluff.crimsonsadditions.CrimsonsAdditions;
import crimsonfluff.crimsonsadditions.backpack.BackpackClientScreen;
import crimsonfluff.crimsonsadditions.backpack.BackpackScreenHandlers;
import crimsonfluff.crimsonsadditions.networking.ModPackets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class initClient implements ClientModInitializer {
    private static KeyBinding keyMagnetToggle;
    private static KeyBinding keyMagnetMode;

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(initBlocks.TORCH_STONE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(initBlocks.TORCH_STONE_WALL, RenderLayer.getCutout());

        ScreenRegistry.register(BackpackScreenHandlers.BACKPACK_SCREEN_HANDLER, BackpackClientScreen::new);

        keyMagnetToggle = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + CrimsonsAdditions.MOD_ID + ".magnet", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "Crimson Additions"));
        keyMagnetMode = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + CrimsonsAdditions.MOD_ID + ".magnetmode", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "Crimson Additions"));

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (keyMagnetMode.wasPressed())
                ClientPlayNetworking.send(ModPackets.MAGNET_MODE, PacketByteBufs.empty());

            if (keyMagnetToggle.wasPressed())
                ClientPlayNetworking.send(ModPackets.MAGNET_TOGGLE, PacketByteBufs.empty());
        });
    }
}
