package crimsonfluff.crimsonsadditions.enchantments;

import crimsonfluff.crimsonsadditions.init.initEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class enchMinersDelight extends Enchantment {
    public enchMinersDelight() {
        super(Rarity.RARE, EnchantmentTarget.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) { return 10; }

    @Override
    public int getMaxPower(int level) { return super.getMinPower(level) + 50; }

    @Override
    public int getMaxLevel() { return 1; }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != initEnchantments.VOID && other != Enchantments.SILK_TOUCH;
    }
}
