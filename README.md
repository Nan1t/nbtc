# NBT Converter

Simple command line tool to convert files between `SNBT` and `NBT`.

## Usage

```
Required argumets:
  -i <path> - Path to input file
  -to <nbt, snbt> - Output format
Optional argumets:
  -o <path> - Output directory
```

Example:
```
java -jar nbtc.jar -i ./file.nbt -to snbt
```