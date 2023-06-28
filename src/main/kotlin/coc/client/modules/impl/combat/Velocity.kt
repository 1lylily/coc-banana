package coc.client.modules.impl.combat

import coc.client.modules.Category
import coc.client.modules.Module
import net.minecraft.client.Minecraft
import net.minecraft.network.play.server.S12PacketEntityVelocity
import net.weavemc.loader.api.event.PacketEvent
import net.weavemc.loader.api.event.SubscribeEvent
import org.lwjgl.input.Keyboard

class Velocity: Module("Velocity", Keyboard.KEY_Z, Category.COMBAT) {
    @SubscribeEvent
    fun onPacketRecieve(e: PacketEvent.Receive) {
        if (e.packet is S12PacketEntityVelocity) {
            val s12: S12PacketEntityVelocity = e.packet as S12PacketEntityVelocity

            if (Minecraft.getMinecraft()?.thePlayer != null && s12.getEntityID() == Minecraft.getMinecraft()?.thePlayer?.entityId) {
                s12.motionX *= 5 / 100
                s12.motionZ *= 5 / 100
                s12.motionY *= 100 / 100
            }
        }
    }
}
