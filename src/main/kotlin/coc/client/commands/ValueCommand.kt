package coc.client.commands

import coc.client.Client
import net.weavemc.loader.api.command.Command

class ValueCommand: Command("value", "setting") {
    override fun handle(args: Array<out String>) {
        if (args.size != 3) return

        Client.instance?.moduleManager?.modules
            ?.values?.find {
                it.name.equals(args[0], true)
            }?.settings?.values?.find {
                it.name.equals(args[1], true)
            }?.setUnkown(args[2])
    }
}