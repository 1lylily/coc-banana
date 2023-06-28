package coc.client.commands

import coc.client.Client
import net.weavemc.loader.api.command.Command
import org.lwjgl.input.Keyboard

class BindCommand: Command("bind") {
    override fun handle(args: Array<out String>) {
        if (args.size != 2) return

        Client.instance?.moduleManager?.modules
            ?.values?.find {
                it.name.equals(args[0], true)
            }?.run {
                this.bind = Keyboard.getKeyIndex(args[1].uppercase())
            }
    }
}