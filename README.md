# Lang Extractor
A simple java program designed to automatically grab all Minecraft lang files 

## How to use
```
1. Run Minecraft for the specified version atleast once
2. Open file explorer and navigate to ".minecraft/assets/indexes"
3. Verify the index file exists, even if it is for a slightly different version (e.g, 1.12.json comes from 1.12.2 MC)
4. Run the jar in terminal with the specified version. "java -jar LangExtractor.jar 1.18"
5. Output files should be in a folder called "lang_(version)"
```