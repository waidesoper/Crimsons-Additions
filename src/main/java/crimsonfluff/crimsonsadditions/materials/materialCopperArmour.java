package crimsonfluff.crimsonsadditions.materials;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

// Durability multiplier is 15

public class materialCopperArmour implements ArmorMaterial {
    public static final ArmorMaterial COPPER_ARMOUR_MATERIAL = new materialCopperArmour();
    private static final int[] DURABILITY = {195, 225, 240, 165};
    private static final int[] PROTECTION = {2, 5, 6, 2};

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
        return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.COPPER_INGOT);
    }

    @Override
    public String getName() {
        return "copper";
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
        return 9;
    }
}
