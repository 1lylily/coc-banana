package coc.client.modules

import net.weavemc.loader.api.event.EventBus

open class Module(
    val name: String,
    var bind: Int,
    val category: Category
) {
    val settings: MutableMap<String, Setting<*>> = HashMap()
    var enabled: Boolean = false
        set(value) {
            field = value

            if (value) {
                onEnable()
                EventBus.subscribe(this)
            } else {
                EventBus.unsubscribe(this)
                onDisable()
            }
        }

    protected fun register(setting: Setting<*>) {
        settings[setting.name] = setting
    }

    open fun registerSettings() {}
    open fun onEnable() {}
    open fun onDisable() {} //ig theres a better way to do this
}