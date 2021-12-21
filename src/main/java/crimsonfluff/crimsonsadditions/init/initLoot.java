package crimsonfluff.crimsonsadditions.init;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class initLoot {
    private static final Identifier DESERT_PYRAMID = new Identifier("minecraft", "chests/desert_pyramid");
    private static final Identifier JUNGLE_TEMPLE = new Identifier("minecraft", "chests/jungle_temple");
    private static final Identifier DUNGEON = new Identifier("minecraft", "chests/simple_dungeon");
    private static final Identifier MINESHAFT = new Identifier("minecraft", "chests/abandoned_mineshaft");
    private static final Identifier BASTION_TREASURE = new Identifier("minecraft", "chests/bastion_treasure");
    private static final Identifier BASTION_OTHER = new Identifier("minecraft", "chests/bastion_other");
    private static final Identifier END_CITY = new Identifier("minecraft", "chests/end_city_treasure");

    public static void register() {
        LootTableLoadingCallback.EVENT.register((resourceManager, manager, id, supplier, setter) -> {
            if (id.equals(DESERT_PYRAMID)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.25f))
                    .with(ItemEntry.builder(initItems.ROSE_GOLD_INGOT))
                    .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)).build());
                supplier.withPool(poolBuilder.build());
            }

            if (id.equals(MINESHAFT)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.05f))
                    .with(ItemEntry.builder(initItems.ROSE_GOLD_INGOT))
                    .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)).build());
                supplier.withPool(poolBuilder.build());
            }

            if (id.equals(JUNGLE_TEMPLE)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.25f))
                    .with(ItemEntry.builder(initItems.ROSE_GOLD_INGOT))
                    .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)).build());
                supplier.withPool(poolBuilder.build());
            }

            if (id.equals(BASTION_TREASURE)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.15f))
                    .with(ItemEntry.builder(initBlocks.ROSE_GOLD_BLOCK))
                    .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)).build());
                supplier.withPool(poolBuilder.build());
            }
        });
    }
}
