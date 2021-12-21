package crimsonfluff.crimsonsadditions.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class enchKillersFervour extends Enchantment {
    public enchKillersFervour() {
        super(Rarity.RARE, EnchantmentTarget.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) { return 10; }

    @Override
    public int getMaxPower(int level) { return super.getMinPower(level) + 50; }

    @Override
    public int getMaxLevel() { return 1; }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return super.isAcceptableItem(stack) || stack.getItem() instanceof SwordItem;
    }
}
