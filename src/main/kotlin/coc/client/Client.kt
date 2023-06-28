package coc.client

import coc.client.commands.TestCommand
import coc.client.commands.ValueCommand
import coc.client.modules.ModuleManager
import net.weavemc.loader.api.ModInitializer
import net.weavemc.loader.api.command.CommandBus

class Client : ModInitializer {
    val moduleManager: ModuleManager = ModuleManager()

    override fun preInit() {
        instance = this

        moduleManager.registerModules()

        CommandBus.register(TestCommand())
        CommandBus.register(ValueCommand())
    }

    companion object {
        var instance: Client? = null
    }
}