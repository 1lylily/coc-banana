package coc.client.modules.impl.movement

import coc.client.modules.BooleanSetting
import coc.client.modules.Category
import coc.client.modules.Module;
import coc.client.modules.Setting
import net.minecraft.client.Minecraft
import net.weavemc.loader.api.event.SubscribeEvent
import net.weavemc.loader.api.event.TickEvent
import org.lwjgl.input.Keyboard

class SprintModule : Module("Sprint", Keyboard.KEY_F, Category.MOVEMENT) {
     @SubscribeEvent
     fun onTick(e: TickEvent.Pre) {
         Minecraft.getMinecraft().thePlayer?.isSprinting = true
     }
}