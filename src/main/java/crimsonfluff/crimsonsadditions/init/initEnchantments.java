package crimsonfluff.crimsonsadditions.init;

import crimsonfluff.crimsonsadditions.CrimsonsAdditions;
import crimsonfluff.crimsonsadditions.enchantments.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class initEnchantments {
    public static final Enchantment AUTO_SMELT = new enchAutoSmelt();
    public static final Enchantment VOID = new enchVoid();
    public static final Enchantment BAGNET = new enchBagnet();
    public static final Enchantment MINERS = new enchMinersDelight();
    public static final Enchantment KILLERS = new enchKillersFervour();

    public static void register() {
        Registry.register(Registry.ENCHANTMENT, new Identifier(CrimsonsAdditions.MOD_ID, "ench_autosmelt"), AUTO_SMELT);
        Registry.register(Registry.ENCHANTMENT, new Identifier(CrimsonsAdditions.MOD_ID, "ench_void"), VOID);
        Registry.register(Registry.ENCHANTMENT, new Identifier(CrimsonsAdditions.MOD_ID, "ench_bagnet"), BAGNET);
        Registry.register(Registry.ENCHANTMENT, new Identifier(CrimsonsAdditions.MOD_ID, "ench_miners"), MINERS);
        Registry.register(Registry.ENCHANTMENT, new Identifier(CrimsonsAdditions.MOD_ID, "ench_killers"), KILLERS);
    }
}
