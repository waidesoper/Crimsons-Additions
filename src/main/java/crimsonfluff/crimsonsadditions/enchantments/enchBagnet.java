package crimsonfluff.crimsonsadditions.enchantments;

import crimsonfluff.crimsonsadditions.init.initEnchantments;
import crimsonfluff.crimsonsadditions.init.initItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class enchBagnet extends Enchantment {
    public enchBagnet() {
        super(Rarity.UNCOMMON, EnchantmentTarget.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) { return 10; }

    @Override
    public int getMaxPower(int level) { return super.getMinPower(level) + 50; }

    @Override
    public int getMaxLevel() { return 1; }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return super.isAcceptableItem(stack) || stack.getItem() == initItems.GLASS_CUTTER;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != initEnchantments.VOID;
    }
}
