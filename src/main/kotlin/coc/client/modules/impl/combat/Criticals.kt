package coc.client.modules.impl.combat

import coc.client.modules.Category
import coc.client.modules.Module
import org.lwjgl.input.Keyboard

class Criticals : Module("Criticals", Keyboard.KEY_Y, Category.COMBAT) {

    fun crit() {
        if(!enabled)
            return


    }

}