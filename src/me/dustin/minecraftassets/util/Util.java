package me.dustin.minecraftassets.util;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class Util {

    public static String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String inString;
            while ((inString = in.readLine()) != null) {
                sb.append(inString);
                sb.append("\n");
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if(!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new RandomAccessFile(sourceFile,"rw").getChannel();
            destination = new RandomAccessFile(destFile,"rw").getChannel();

            long position = 0;
            long count    = source.size();

            source.transferTo(position, count, destination);
        }
        finally {
            if(source != null) {
                source.close();
            }
            if(destination != null) {
                destination.close();
            }
        }
    }

    public static OS getPlatform() {
        final String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return OS.WINDOWS;
        }
        if (osName.contains("mac")) {
            return OS.MACOS;
        }
        if (osName.contains("linux")) {
            return OS.LINUX;
        }
        if (osName.contains("unix")) {
            return OS.LINUX;
        }
        return OS.UNKNOWN;
    }

    public static File getMCDir() {
        final String userHome = System.getProperty("user.home", ".");
        File workingDirectory = null;
        switch (getPlatform()) {
            case LINUX -> {
                workingDirectory = new File(userHome, ".minecraft/");
                break;
            }
            case WINDOWS -> {
                final String applicationData = System.getenv("APPDATA");
                final String folder = (applicationData != null) ? applicationData : userHome;
                workingDirectory = new File(folder, ".minecraft/");
                break;
            }
            case MACOS -> {
                workingDirectory = new File(userHome, "Library/Application Support/minecraft");
                break;
            }
            default -> {
                workingDirectory = new File(userHome, "minecraft/");
                break;
            }
        }
        return workingDirectory;
    }

    public enum OS {
        WINDOWS, MACOS, LINUX, UNKNOWN;
    }
}
