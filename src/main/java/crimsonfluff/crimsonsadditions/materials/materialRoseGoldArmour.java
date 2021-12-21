package crimsonfluff.crimsonsadditions.materials;

import crimsonfluff.crimsonsadditions.init.initItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

// Durability multiplier is 12

public class materialRoseGoldArmour implements ArmorMaterial {
    public static final ArmorMaterial ROSE_GOLD_ARMOUR_MATERIAL = new materialRoseGoldArmour();
    private static final int[] DURABILITY = {156, 180, 192, 132};
    private static final int[] PROTECTION = {1, 3, 5, 2};

    @Override
    public int getDurability(EquipmentSlot slot) {
        return DURABILITY[slot.getEntitySlotId()];
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION[slot.getEntitySlotId()];
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_GOLD;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(initItems.ROSE_GOLD_INGOT);
    }

    @Override
    public String getName() {
        return "rose_gold";
    }

    @Override
    public float getToughness() {
        return 0F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0F;
    }

    @Override
    public int getEnchantability() {
        return 25;
    }
}