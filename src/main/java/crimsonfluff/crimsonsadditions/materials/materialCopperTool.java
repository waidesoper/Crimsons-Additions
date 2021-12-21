package crimsonfluff.crimsonsadditions.materials;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

// compared to Iron...
// Higher durability (+509)
// Slightly faster (+1)
// Lower enchantability (-6)
public class materialCopperTool implements ToolMaterial {
    public static final materialCopperTool MATERIAL = new materialCopperTool();

    @Override
    public int getDurability() {
        return 759;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 5.0F;
    }

    @Override
    public float getAttackDamage() {
        return 2.0F;
    }

    @Override
    public int getMiningLevel() {
        return 2;
    }

    @Override
    public int getEnchantability() {
        return 8;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.COPPER_INGOT);
    }
}
