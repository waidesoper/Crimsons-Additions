package crimsonfluff.crimsonsadditions.init;

import crimsonfluff.crimsonsadditions.CrimsonsAdditions;
import crimsonfluff.crimsonsadditions.blocks.LavaSponge;
import crimsonfluff.crimsonsadditions.blocks.LavaSpongeWet;
import crimsonfluff.crimsonsadditions.blocks.StoneTorchBlock;
import crimsonfluff.crimsonsadditions.blocks.StoneTorchWallBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class initBlocks {
    public static final Block ROSE_GOLD_BLOCK = new Block(AbstractBlock.Settings.of(Material.METAL, MapColor.PINK)
        .requiresTool()
        .strength(3.0f, 6.0f)
        .sounds(BlockSoundGroup.METAL));

    public static final Block LAVA_SPONGE = new LavaSponge(AbstractBlock.Settings.of(Material.SPONGE, MapColor.ORANGE)
        .strength(0.6f)
        .sounds(BlockSoundGroup.GRASS));

    public static final Block LAVA_SPONGE_WET = new LavaSpongeWet(AbstractBlock.Settings.of(Material.SPONGE, MapColor.ORANGE)
        .strength(0.6f)
        .sounds(BlockSoundGroup.GRASS));

    public static final StoneTorchBlock TORCH_STONE = new StoneTorchBlock(FabricBlockSettings.of(Material.DECORATION).nonOpaque().noCollision().luminance((state) -> 14), ParticleTypes.FLAME);
    public static final StoneTorchWallBlock TORCH_STONE_WALL = new StoneTorchWallBlock(FabricBlockSettings.of(Material.DECORATION).nonOpaque().noCollision().luminance((state) -> 14), ParticleTypes.FLAME);

    public static void register() {
        Registry.register(Registry.BLOCK, new Identifier(CrimsonsAdditions.MOD_ID, "rose_gold"), ROSE_GOLD_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(CrimsonsAdditions.MOD_ID, "lava_sponge"), LAVA_SPONGE);
        Registry.register(Registry.BLOCK, new Identifier(CrimsonsAdditions.MOD_ID, "lava_sponge_wet"), LAVA_SPONGE_WET);
        Registry.register(Registry.BLOCK, new Identifier(CrimsonsAdditions.MOD_ID, "stone_torch"), TORCH_STONE);
        Registry.register(Registry.BLOCK, new Identifier(CrimsonsAdditions.MOD_ID, "stone_torch_wall"), TORCH_STONE_WALL);
    }
}
