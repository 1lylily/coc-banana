package coc.client.modules

import coc.client.modules.impl.movement.SprintModule
import net.weavemc.loader.api.event.EventBus
import net.weavemc.loader.api.event.KeyboardEvent
import net.weavemc.loader.api.event.SubscribeEvent
import kotlin.reflect.KClass

class ModuleManager {
    init {
        EventBus.subscribe(this)
    }

    val modules: MutableMap<KClass<out Module>, Module> = HashMap()

    fun registerModules() {
        register(SprintModule())
    }

    //IM TYPOING SO MUCH
    private fun register(module: Module) {
        modules[module::class] = module
    }

    @SubscribeEvent
    fun onKeyPress(e: KeyboardEvent) {
        if (!e.keyState) return;

        for (module in modules.values) {
            if (module.bind == e.keyCode) {
                module.enabled = !module.enabled
            }
        }
    }
}
