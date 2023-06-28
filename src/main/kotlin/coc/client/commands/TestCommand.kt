package coc.client.commands

import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.weavemc.loader.api.command.Command

class TestCommand: Command("test", "velocity") {
    override fun handle(args: Array<out String>) {
        Minecraft.getMinecraft().thePlayer?.addChatMessage(
            ChatComponentText("Andy stop")
        )
    }
}