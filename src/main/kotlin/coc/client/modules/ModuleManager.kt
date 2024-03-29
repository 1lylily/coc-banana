package coc.client.modules

import coc.client.Client
import coc.client.modules.impl.combat.Criticals
import coc.client.modules.impl.combat.KillAura
import coc.client.modules.impl.combat.Velocity
import coc.client.modules.impl.movement.Sprint
import coc.client.modules.impl.visual.HUD
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.weavemc.loader.api.event.EventBus
import net.weavemc.loader.api.event.KeyboardEvent
import net.weavemc.loader.api.event.SubscribeEvent
import java.io.File
import java.io.FileReader
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
        register(Sprint())
        register(HUD())
        register(Criticals())
        register(KillAura())
        register(Velocity())
    }

    private fun register(module: Module) {
        module.registerSettings()
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

        val writer = FileWriter(cfgFile)
        writer.write(gson.toJson(obj))
        writer.close()
    }

    fun loadConfig(name: String) {
        val cfgFile: File = Paths.get("weave-base", "$name.json").toFile()

        if(!cfgFile.exists()) return

        val elem = JsonParser().parse(FileReader(cfgFile))

        if(elem == null || elem.isJsonNull || !elem.isJsonObject) return

        val obj = elem.asJsonObject

        Client.instance?.moduleManager?.modules?.forEach { moduleEntry ->
            val module = moduleEntry.value
            val moduleObj = (obj.get(module.name) ?: return).asJsonObject

            module.bind = moduleObj["bind"].asInt
            module.enabled = moduleObj["state"].asBoolean

            val settingsObj = moduleObj["settings"].asJsonObject

            module.settings.forEach {
                val settingElem = settingsObj.get(it.key)

                if(settingElem != null) it.value.setUnknown(elem.asString)
            }
        }
    }

}
