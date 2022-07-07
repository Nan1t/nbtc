package ua.nanit.nbtc

import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.name

fun main(args: Array<String>) {
    val cmd = try {
        parseCommand(args)
    } catch (t: Throwable) {
        println("Invalid command: ${t.message}")
        printHelp()
        return
    }

    val outFile = buildFilePath(cmd)
    val converter = Converter(cmd.outFormat!!)

    converter.convert(cmd.inFile!!, outFile)
}

fun buildFilePath(cmd: Command): Path {
    val name = cmd.inFile?.name?.substringBefore(".")
    val filename = "$name.${cmd.outFormat?.toString()?.lowercase()}"
    return if (cmd.outDir != null) {
        cmd.outDir!!.resolve(filename)
    } else {
        Path("./$filename")
    }
}