package crimsonfluff.crimsonsadditions.backpack;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EnderBackpack extends Item {
    public EnderBackpack(Item.Settings settings) { super(settings); }

    public static final TranslatableText TITLE = new TranslatableText("container.crimsonsadditions.backpack_ender");

    @Override
    public boolean canBeNested() { return false; }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        player.setCurrentHand(hand);
        EnderChestInventory enderChest = player.getEnderChestInventory();

        if (enderChest != null) {
            if (! world.isClient) {
                player.playSound(SoundEvents.BLOCK_ENDER_CHEST_OPEN, SoundCategory.PLAYERS, 1f, 1f);

                player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncID, playerInventory, playerEntity) -> EnderBackpackScreenHandler.createGeneric9x3(syncID, playerInventory, enderChest), TITLE));
            }
        }

        return TypedActionResult.success(itemStack);
    }
}
