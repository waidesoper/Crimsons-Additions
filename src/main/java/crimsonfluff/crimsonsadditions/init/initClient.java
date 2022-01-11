package crimsonfluff.crimsonsadditions.init;

import crimsonfluff.crimsonsadditions.CrimsonsAdditions;
import crimsonfluff.crimsonsadditions.backpack.BackpackClientScreen;
import crimsonfluff.crimsonsadditions.backpack.BackpackScreenHandlers;
import crimsonfluff.crimsonsadditions.networking.EntitySpawnPacket;
import crimsonfluff.crimsonsadditions.networking.ModPackets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

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

        EntityRendererRegistry.register(initItems.TORCH_ARROW_ENTITY_TYPE, ArrowEntityRenderer::new);
        ClientPlayNetworking.registerGlobalReceiver(ModPackets.SPAWN_PACKET, this::receiveEntityPacket);
    }

    public void receiveEntityPacket(MinecraftClient minecraftClient, ClientPlayNetworkHandler handler, PacketByteBuf byteBuf, PacketSender responseSender) {
        EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
        UUID uuid = byteBuf.readUuid();
        int entityId = byteBuf.readVarInt();
        Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
        float pitch = byteBuf.readFloat();  // EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
        float yaw = byteBuf.readFloat();    //EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);

        minecraftClient.execute(() -> {
            if (minecraftClient.world == null)
                throw new IllegalStateException("Tried to spawn entity in a null world!");

            Entity e = et.create(minecraftClient.world);
            if (e == null)
                throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");

            e.updateTrackedPosition(pos);
            e.setPos(pos.x, pos.y, pos.z);
            e.setPitch(pitch);
            e.setYaw(yaw);
            e.setId(entityId);
            e.setUuid(uuid);

            minecraftClient.world.addEntity(entityId, e);
        });
    }
}
