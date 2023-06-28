package coc.client.utils

import net.minecraft.client.Minecraft
import net.minecraft.network.Packet
import net.minecraft.util.Vec3
import kotlin.math.PI
import kotlin.math.atan2

object PlayerUtils {

    fun getRotationYawForTarget(pos: Vec3, target: Vec3): Float {
        return ((atan2(target.zCoord - pos.zCoord, target.xCoord - pos.xCoord) * (180 / PI)) - 90).toFloat()
    }

    fun sendPacket(packet: Packet<*>) {
        Minecraft.getMinecraft().thePlayer?.sendQueue?.addToSendQueue(packet)
    }

}