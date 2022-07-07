package ua.nanit.nbtc

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path

data class Command(
    var inFile: Path?,
    var outDir: Path?,
    var outFormat: NbtFormat?,
) {
    fun validate() {
        if (inFile == null || outFormat == null) {
            throw IllegalArgumentException("Missing required args")
        }

        if (!Files.exists(inFile!!) || !Files.isRegularFile(inFile!!)) {
            throw IllegalArgumentException("Input file not exists")
        }
    }
}

fun printHelp() {
    println("""
        Required args:
          -i <path> - Path to input file
          -to <nbt, snbt> - Output format
        Optional args:
          -o <path> - Output directory
    """.trimIndent())
}

fun parseCommand(args: Array<String>): Command {
    val command = Command(null, null, null)

    if (args.size % 2 != 0) {
        throw IllegalArgumentException("Invalid number of arguments")
    }

    for (i in args.indices step 2) {
        val arg = args[i]

        if (arg.startsWith('-')) {
            val key = arg.substring(1)
            val value = args[i+1]
            parseArgument(command, key, value)
            continue
        }

        throw IllegalArgumentException("Argument name must start with '-' symbol")
    }

    command.validate()

    return command
}

fun parseArgument(cmd: Command, key: String, value: String) {
    when (key) {
        "i" -> cmd.inFile = Path(value)
        "o" -> cmd.outDir = Path(value)
        "to" -> cmd.outFormat = NbtFormat.valueOf(value.uppercase())
    }
}