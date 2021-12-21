package crimsonfluff.crimsonsadditions;

import crimsonfluff.crimsonsadditions.init.*;
import crimsonfluff.crimsonsadditions.networking.ModPacketsC2S;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrimsonsAdditions implements ModInitializer {
    public static final String MOD_ID = "crimsonsadditions";
    public static final Logger LOGGER = LogManager.getLogger(CrimsonsAdditions.class);

    public static final initConfigs CONFIG = initConfigs.getOrCreateInstance();

    @Override
    public void onInitialize() {
        initConfigs.load_config();
        ModPacketsC2S.register();
        initEnchantments.register();
        initBlocks.register();
        initItems.register();
        initLoot.register();
    }
}
