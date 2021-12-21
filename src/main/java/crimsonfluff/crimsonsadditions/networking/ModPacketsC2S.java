package crimsonfluff.crimsonsadditions.networking;

import crimsonfluff.crimsonsadditions.items.MagnetItem;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModPacketsC2S {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ModPackets.MAGNET_TOGGLE, ModPacketsC2S::magnetToggle);
        ServerPlayNetworking.registerGlobalReceiver(ModPackets.MAGNET_MODE, ModPacketsC2S::magnetMode);
    }

    public static void magnetToggle(MinecraftServer minecraftServer, ServerPlayerEntity playerEntity, ServerPlayNetworkHandler handler, PacketByteBuf buffer, PacketSender sender) {
        minecraftServer.execute(() -> {
            if (playerEntity != null) {
                ItemStack itemStack = ItemStack.EMPTY;

                if (playerEntity.getMainHandStack().getItem() instanceof MagnetItem)
                    itemStack = playerEntity.getMainHandStack();

                else if (playerEntity.getOffHandStack().getItem() instanceof MagnetItem)
                    itemStack = playerEntity.getOffHandStack();

                else {
//                    if (FabricLoader.getInstance().isModLoaded("trinkets")) {
//                        //itemStack = TrinketSlots.
//                    }

                    if (itemStack.isEmpty()) {
                        for (int a = 0; a < playerEntity.getInventory().size(); a++) {
                            if (playerEntity.getInventory().getStack(a).getItem() instanceof MagnetItem) {
                                itemStack = playerEntity.getInventory().getStack(a);
                                break;
                            }
                        }
                    }
                }

                if (!itemStack.isEmpty())
                    ((MagnetItem) itemStack.getItem()).changeMagnetToggle(itemStack.getOrCreateNbt().getInt("CustomModelData"), playerEntity, itemStack);
            }
        });
    }

    public static void magnetMode(MinecraftServer minecraftServer, ServerPlayerEntity playerEntity, ServerPlayNetworkHandler handler, PacketByteBuf buffer, PacketSender sender) {
        minecraftServer.execute(() -> {
            if (playerEntity != null) {
                ItemStack itemStack = ItemStack.EMPTY;

                if (playerEntity.getMainHandStack().getItem() instanceof MagnetItem)
                    itemStack = playerEntity.getMainHandStack();

                else if (playerEntity.getOffHandStack().getItem() instanceof MagnetItem)
                    itemStack = playerEntity.getOffHandStack();

                else {
//                    if (FabricLoader.getInstance().isModLoaded("trinkets")) {
//                        //itemStack = TrinketSlots.
//                    }

                    if (itemStack.isEmpty()) {
                        for (int a = 0; a < playerEntity.getInventory().size(); a++) {
                            if (playerEntity.getInventory().getStack(a).getItem() instanceof MagnetItem) {
                                itemStack = playerEntity.getInventory().getStack(a);
                                break;
                            }
                        }
                    }
                }

                if (!itemStack.isEmpty())
                    ((MagnetItem) itemStack.getItem()).changeMagnetMode(itemStack.getOrCreateNbt().getInt("CustomModelData"), playerEntity, itemStack);
            }
        });
    }
}
