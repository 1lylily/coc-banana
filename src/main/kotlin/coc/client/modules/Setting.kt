package coc.client.modules

sealed class Setting<Type>(val name: String, var value: Type) {
    fun setUnkown(input: String) {
        try {
            value = parse(input)
        } catch (_: Exception) {}
    }

    abstract fun parse(input: String): Type
}

class BooleanSetting(name: String, value: Boolean): Setting<Boolean>(name, value) {
    override fun parse(input: String): Boolean {
        return input.toBoolean()
    }
}