package crimsonfluff.crimsonsadditions.materials;

import crimsonfluff.crimsonsadditions.init.initItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

// compared to Gold...
// Same enchantability
// Slightly slower (-2)
// Higher durability (+129)
public class materialRoseGoldTool implements ToolMaterial {
    public static final materialRoseGoldTool MATERIAL = new materialRoseGoldTool();

    @Override
    public int getDurability() { return 161; }

    @Override
    public float getMiningSpeedMultiplier() { return 9.0F; }

    @Override
    public float getAttackDamage() { return 2.0F; }

    @Override
    public int getMiningLevel() { return 2; }

    @Override
    public int getEnchantability() { return 22; }

    @Override
    public Ingredient getRepairIngredient() { return Ingredient.ofItems(initItems.ROSE_GOLD_INGOT); }
}
