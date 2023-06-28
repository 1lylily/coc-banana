package coc.client.modules

import coc.client.modules.impl.movement.SprintModule
import coc.client.modules.impl.visual.HUD
import com.google.gson.Gson
import com.google.gson.JsonObject
import net.weavemc.loader.api.event.EventBus
import net.weavemc.loader.api.event.KeyboardEvent
import net.weavemc.loader.api.event.SubscribeEvent
import java.io.File
import java.io.FileWriter
import java.nio.file.Paths
import kotlin.reflect.KClass

class ModuleManager {
    private val gson: Gson = Gson()

    init {
        EventBus.subscribe(this)
    }

    val modules: MutableMap<KClass<out Module>, Module> = HashMap()

    fun registerModules() {
        register(SprintModule())
        register(HUD())
    }

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

    fun saveConfig(name: String) {
        val cfgFile: File = Paths.get("weave-base", "$name.json").toFile()

        val obj = JsonObject()

        for (module in modules.values) {
            val moduleObject = JsonObject()

            moduleObject.addProperty("bind", module.bind)
            moduleObject.addProperty("state", module.enabled)

            val settingObject = JsonObject()

            for (setting in module.settings.values) {
                settingObject.addProperty(setting.name, setting.value.toString())
            }

            moduleObject.add("settings", settingObject)

            obj.add(module.name, moduleObject)
        }

        var writer = FileWriter(cfgFile)
        writer.write(gson.toJson(obj))
        writer.close()
    }
}
