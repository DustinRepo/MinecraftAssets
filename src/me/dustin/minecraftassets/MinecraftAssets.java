package me.dustin.minecraftassets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.dustin.minecraftassets.util.Util;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class MinecraftAssets {

    public static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    public static void main(String[] args) {
        String version = null;
        String customMcDir = null;
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--help")) {
                System.out.println("--version=<v>   |   Sets the version to get the assets from");
                System.out.println("--mcDir=<path>  |   Sets the directory for .minecraft folder (optional)");

                System.out.println("\nPlease read the README file on the github if you need more information");
                System.out.println("Example: java -jar MinecraftAssets.jar --version=1.12");
                return;
            } else if (arg.startsWith("--version=")) {
                version = arg.split("=")[1];
            } else if (arg.startsWith("--mcDir=")) {
                customMcDir = arg.split("=")[1];
            }
        }
        if (version == null) {
            System.out.println("Error! have to specify a version");
            return;
        }

        File mcDir = customMcDir == null ? Util.getMCDir() : new File(customMcDir);
        if (!mcDir.exists()) {
            System.out.println("Error! No Minecraft found!");
            return;
        }
        File assetDir = new File(mcDir, "assets");
        File indexesDir = new File(assetDir, "indexes");
        File objectsDir = new File(assetDir, "objects");

        File indexJson = new File(indexesDir, version + ".json");

        if (!indexJson.exists()) {
            System.out.println("Error! Index file not found at: " + indexJson.getAbsolutePath());
            return;
        }

        File outputFolder = new File(new File("").getAbsolutePath(), version.replace(".", "_"));

        String s = Util.readFile(indexJson);
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.get("objects").getAsJsonObject().entrySet();
        for(Map.Entry<String,JsonElement> entry : entrySet) {
            JsonObject o = entry.getValue().getAsJsonObject();
            String hash = o.get("hash").getAsString();
            File containingFolder = new File(objectsDir, hash.substring(0, 2));
            File langFile = new File(containingFolder, hash);
            File outputFile = new File(outputFolder, entry.getKey());
            try {
                if (!outputFile.getParentFile().exists())
                    outputFile.getParentFile().mkdirs();
                Util.copyFile(langFile, outputFile);
                System.out.println("Copied file " + entry.getKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finished...");
    }

}
