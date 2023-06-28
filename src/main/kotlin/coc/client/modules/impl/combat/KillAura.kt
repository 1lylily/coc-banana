package coc.client.modules.impl.combat

import coc.client.modules.Category
import coc.client.modules.Module
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.weavemc.loader.api.event.SubscribeEvent
import net.weavemc.loader.api.event.TickEvent
import org.lwjgl.input.Keyboard

class KillAura : Module("KillAura", Keyboard.KEY_R, Category.COMBAT) {

    var target: EntityPlayer? = null

    override fun onEnable() {
        target = null
    }

    @SubscribeEvent
    fun onTickPre(e: TickEvent.Pre) {
        val selfEntityId = Minecraft.getMinecraft().thePlayer.entityId

        target = Minecraft.getMinecraft().theWorld?.playerEntities?.find {
            it.entityId != selfEntityId &&
                    it.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) <= 8
        }

    }

    @SubscribeEvent
    fun onTickPost(e: TickEvent.Post) {

    }

}