package coc.client.modules.impl.combat

import coc.client.modules.Category
import coc.client.modules.Module
import coc.client.utils.PlayerUtils
import net.minecraft.client.Minecraft
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition
import org.lwjgl.input.Keyboard

class Criticals : Module("Criticals", Keyboard.KEY_Y, Category.COMBAT) {

    fun crit() {
        if(!enabled)
            return

        val x = Minecraft.getMinecraft().thePlayer.posX
        val y = Minecraft.getMinecraft().thePlayer.posY
        val z = Minecraft.getMinecraft().thePlayer.posZ

        PlayerUtils.sendPacket(
            C04PacketPlayerPosition(
                x, y+0.0675, z, false
            )
        )
        PlayerUtils.sendPacket(
            C04PacketPlayerPosition(
                x, y+0.65, z, false
            )
        )
    }

}