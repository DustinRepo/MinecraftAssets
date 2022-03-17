package me.dustin.langextractor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.dustin.langextractor.util.Util;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class LangExtractor {

    public static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Error! have to specify a version");
            return;
        }
        String v = args[0];

        File mcDir = Util.getMCDir();
        if (!mcDir.exists()) {
            System.out.println("Error! No Minecraft found!");
            return;
        }
        File assetDir = new File(mcDir, "assets");
        File indexesDir = new File(assetDir, "indexes");
        File objectsDir = new File(assetDir, "objects");

        File indexJson = new File(indexesDir, v + ".json");

        if (!indexJson.exists()) {
            System.out.println("Error! Index file not found at: " + indexJson.getAbsolutePath());
            return;
        }

        File outputFolder = new File(new File("").getAbsolutePath(), "lang_" + v);

        String s = Util.readFile(indexJson);
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.get("objects").getAsJsonObject().entrySet();
        for(Map.Entry<String,JsonElement> entry : entrySet){
            if (entry.getKey().contains("minecraft/lang")) {
                JsonObject o = entry.getValue().getAsJsonObject();
                String hash = o.get("hash").getAsString();

                File containingFolder = new File(objectsDir, hash.substring(0, 2));
                File langFile = new File(containingFolder, hash);
                File outputFile = new File(outputFolder, entry.getKey().replace("minecraft/lang/", ""));
                try {
                    if (!outputFolder.exists())
                        outputFolder.mkdirs();
                    Util.copyFile(langFile, outputFile);
                    System.out.println("copied file " + entry.getKey());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Finished...");
    }

}
