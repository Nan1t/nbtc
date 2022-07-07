package ua.nanit.nbtc

import net.kyori.adventure.nbt.BinaryTagIO
import net.kyori.adventure.nbt.CompoundBinaryTag
import net.kyori.adventure.nbt.TagStringIO
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.readText

class Converter(private val to: NbtFormat) {

    fun convert(input: Path, out: Path) {
        val tag = read(input)

        when (to) {
            NbtFormat.NBT -> {
                BinaryTagIO.writer().write(tag, out)
            }
            NbtFormat.SNBT -> {
                val str = TagStringIO.get().asString(tag)
                Files.write(out, str.toByteArray())
            }
        }
    }

    private fun read(file: Path): CompoundBinaryTag {
        return when (getNbtFormat(file)) {
            NbtFormat.NBT -> BinaryTagIO.unlimitedReader().read(file)
            NbtFormat.SNBT -> TagStringIO.get().asCompound(file.readText())
        }
    }

    private fun getNbtFormat(file: Path) =
        NbtFormat.valueOf(file.extension.uppercase())

}

enum class NbtFormat {
    NBT,
    SNBT
}