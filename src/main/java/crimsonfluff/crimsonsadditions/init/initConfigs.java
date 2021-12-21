package crimsonfluff.crimsonsadditions.init;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class initConfigs {
    private static initConfigs INSTANCE = null;

    public int magnetPullRadius = 16;
    public int magnetMaxDamage = 256;
    public int glasscutterMaxDamage = 128;
    public int minersDelightChance = 10;
    public int killersFervourChance = 5;

    public static void load_config() {
        INSTANCE = new initConfigs();
        Gson gson = new Gson();
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toString(), "crimsonsadditions.json");

        try (FileReader reader = new FileReader(configFile)) {
            INSTANCE = gson.fromJson(reader, initConfigs.class);
//            System.out.println("Config: " + INSTANCE);

            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(INSTANCE));
//                System.out.println("Config updated!");

            } catch (IOException e2) {
//                System.out.println("Failed to update config file!");
            }

//            System.out.println("Config loaded!");

        } catch (IOException e) {
//            System.out.println("No config found, generating!");
            INSTANCE = new initConfigs();

            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(INSTANCE));

            } catch (IOException e2) {
//                System.out.println("Failed to generate config file!");
            }
        }
    }

    public static initConfigs getOrCreateInstance() {
        if (INSTANCE == null) load_config();

        return INSTANCE;
    }
}
