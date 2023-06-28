package coc.client.commands

import coc.client.Client
import net.weavemc.loader.api.command.Command

class ToggleCommand: Command("toggle", "t") {
    override fun handle(args: Array<out String>) {
        if (args.size != 1) return

        Client.instance?.moduleManager?.modules
            ?.values?.find {
                it.name.equals(args[0], true)
            }?.run {
                this.enabled = !this.enabled
            }
    }
}