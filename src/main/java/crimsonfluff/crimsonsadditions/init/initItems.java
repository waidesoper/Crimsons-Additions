package crimsonfluff.crimsonsadditions.init;

import crimsonfluff.crimsonsadditions.CrimsonsAdditions;
import crimsonfluff.crimsonsadditions.backpack.Backpack;
import crimsonfluff.crimsonsadditions.backpack.EnderBackpack;
import crimsonfluff.crimsonsadditions.entities.TorchArrowEntity;
import crimsonfluff.crimsonsadditions.items.*;
import crimsonfluff.crimsonsadditions.materials.materialCopperArmour;
import crimsonfluff.crimsonsadditions.materials.materialCopperTool;
import crimsonfluff.crimsonsadditions.materials.materialRoseGoldArmour;
import crimsonfluff.crimsonsadditions.materials.materialRoseGoldTool;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class initItems {
    public static final Item ROSE_GOLD_HELMET = new ArmorItem(materialRoseGoldArmour.ROSE_GOLD_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROSE_GOLD_CHESTPLATE = new ArmorItem(materialRoseGoldArmour.ROSE_GOLD_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROSE_GOLD_LEGGINGS = new ArmorItem(materialRoseGoldArmour.ROSE_GOLD_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROSE_GOLD_BOOTS = new ArmorItem(materialRoseGoldArmour.ROSE_GOLD_ARMOUR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROSE_GOLD_SWORD = new SwordItem(materialRoseGoldTool.MATERIAL, 4, -2.4F, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROSE_GOLD_PICKAXE = new basePickaxe(materialRoseGoldTool.MATERIAL, 1, -2.8F, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item ROSE_GOLD_AXE = new baseAxe(materialRoseGoldTool.MATERIAL, 6, -3.1F, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item ROSE_GOLD_HOE = new baseHoe(materialRoseGoldTool.MATERIAL, -2, -1F, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item ROSE_GOLD_SHOVEL = new ShovelItem(materialRoseGoldTool.MATERIAL, 1.5F, -3F, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item ROSE_GOLD_INGOT = new Item(new Item.Settings().group(ItemGroup.MATERIALS));

    public static final Item COPPER_HELMET = new ArmorItem(materialCopperArmour.COPPER_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item COPPER_CHESTPLATE = new ArmorItem(materialCopperArmour.COPPER_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item COPPER_LEGGINGS = new ArmorItem(materialCopperArmour.COPPER_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item COPPER_BOOTS = new ArmorItem(materialCopperArmour.COPPER_ARMOUR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item COPPER_SWORD = new SwordItem(materialCopperTool.MATERIAL, 4, -2.4F, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item COPPER_PICKAXE = new basePickaxe(materialCopperTool.MATERIAL, 1, -2.8F, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item COPPER_AXE = new baseAxe(materialCopperTool.MATERIAL, 6, -3.1F, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item COPPER_HOE = new baseHoe(materialCopperTool.MATERIAL, -2, -1F, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item COPPER_SHOVEL = new ShovelItem(materialCopperTool.MATERIAL, 1.5F, -3F, new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item COPPER_SHEARS = new CopperShears(new Item.Settings().group(ItemGroup.TOOLS).maxDamage(350));

    public static final Item GLASS_CUTTER = new GlassCutter(ToolMaterials.DIAMOND, new Item.Settings().group(ItemGroup.TOOLS).maxDamage(CrimsonsAdditions.CONFIG.glasscutterMaxDamage));
    public static final Item MAGNET = new MagnetItem();

    public static final Item ANIMAL_NET = new AnimalNet();
    public static final Item BACKPACK = new Backpack(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
    public static final Identifier ID_BACKPACK = new Identifier(CrimsonsAdditions.MOD_ID, "backpack");
    public static final Item ENDER_BACKPACK = new EnderBackpack(new Item.Settings().group(ItemGroup.MISC).maxCount(1));

    public static final Item TINY_COAL = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item TINY_CHARCOAL = new Item(new Item.Settings().group(ItemGroup.MATERIALS));

    public static final TorchArrow TORCH_ARROW = new TorchArrow(new Item.Settings().group(ItemGroup.COMBAT));
    public static final EntityType<TorchArrowEntity> TORCH_ARROW_ENTITY_TYPE = FabricEntityTypeBuilder.<TorchArrowEntity>create(SpawnGroup.MISC, TorchArrowEntity::new)
            .dimensions(EntityDimensions.fixed(0.25F,0.25F))
            .trackRangeBlocks(10)
            .build();


    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "rose_gold_helmet"), ROSE_GOLD_HELMET);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "rose_gold_chestplate"), ROSE_GOLD_CHESTPLATE);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "rose_gold_leggings"), ROSE_GOLD_LEGGINGS);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "rose_gold_boots"), ROSE_GOLD_BOOTS);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "rose_gold_sword"), ROSE_GOLD_SWORD);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "rose_gold_pickaxe"), ROSE_GOLD_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "rose_gold_axe"), ROSE_GOLD_AXE);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "rose_gold_shovel"), ROSE_GOLD_SHOVEL);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "rose_gold_hoe"), ROSE_GOLD_HOE);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "rose_gold_ingot"), ROSE_GOLD_INGOT);

        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "copper_helmet"), COPPER_HELMET);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "copper_chestplate"), COPPER_CHESTPLATE);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "copper_leggings"), COPPER_LEGGINGS);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "copper_boots"), COPPER_BOOTS);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "copper_sword"), COPPER_SWORD);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "copper_pickaxe"), COPPER_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "copper_axe"), COPPER_AXE);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "copper_shovel"), COPPER_SHOVEL);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "copper_hoe"), COPPER_HOE);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "copper_shears"), COPPER_SHEARS);

        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "glass_cutter"), GLASS_CUTTER);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "magnet"), MAGNET);

        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "rose_gold_block"), new BlockItem(initBlocks.ROSE_GOLD_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));

        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "apple_cooked"), new Item(new FabricItemSettings()
            .group(ItemGroup.FOOD)
            .food(new FoodComponent.Builder()
                .hunger(6)
                .saturationModifier(0.3f)
                .build())));

        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "tiny_coal"), TINY_COAL);
        FuelRegistry.INSTANCE.add(TINY_COAL, 200);

        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "tiny_charcoal"), TINY_CHARCOAL);
        FuelRegistry.INSTANCE.add(TINY_CHARCOAL, 200);

        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "torch_arrow"), TORCH_ARROW);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(CrimsonsAdditions.MOD_ID, "torch_arrow"), TORCH_ARROW_ENTITY_TYPE);

        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "animal_net"), ANIMAL_NET);
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "backpack_ender"), ENDER_BACKPACK);
        Registry.register(Registry.ITEM, ID_BACKPACK, BACKPACK);

        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "lava_sponge"), new BlockItem(initBlocks.LAVA_SPONGE, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "lava_sponge_wet"), new BlockItem(initBlocks.LAVA_SPONGE_WET, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));

        Registry.register(Registry.ITEM, new Identifier(CrimsonsAdditions.MOD_ID, "stone_torch"), new WallStandingBlockItem(initBlocks.TORCH_STONE, initBlocks.TORCH_STONE_WALL, new Item.Settings().group(ItemGroup.DECORATIONS)));

    }
}
