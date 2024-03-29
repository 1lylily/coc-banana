package coc.client.commands

import coc.client.Client
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.weavemc.loader.api.command.Command

class ConfigCommand: Command("config", "cfg") {
    override fun handle(args: Array<out String>) {
        if (args.size != 2) return

        when (args[0]) {
            "load" -> {
                Client.instance?.moduleManager?.loadConfig(args[1])
            }

            "save" -> {
                Client.instance?.moduleManager?.saveConfig(args[1])
            }
        }
    }
}