# Lang Extractor
A simple java program designed to automatically grab and collect all Minecraft asset files

## How to use
```
1. Run Minecraft for the specified version atleast once
2. Open file explorer and navigate to ".minecraft/assets/indexes"
3. Verify the index file exists, even if it is for a slightly different version (e.g, 1.12.json comes from 1.12.2 MC)
4. Run the jar in terminal with the specified version. "java -jar MinecraftAssets.jar --version=1.18"
5. Output files will be in a folder named after the version specified.
```

## For custom Minecraft directories
If you don't have minecraft in your normal location, you can specify it using --mcDir=/path/to/.minecraft For example:
`java -jar --version=1.18 --mcDir=/home/user/.minecraft`

## What does this do?
Reads the MC assets index file for the specified version, that tell us what the file is named and where it is (based on the hash),
After that, it automatically finds each file then copies it in it's original directory structure to readily usable files