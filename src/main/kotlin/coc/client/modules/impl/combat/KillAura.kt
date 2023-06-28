package coc.client.modules.impl.combat

import coc.client.Client
import coc.client.modules.Category
import coc.client.modules.DoubleSetting
import coc.client.modules.Module
import coc.client.utils.PlayerUtils
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemSword
import net.minecraft.network.play.client.C02PacketUseEntity
import net.minecraft.network.play.client.C07PacketPlayerDigging
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumFacing
import net.weavemc.loader.api.event.SubscribeEvent
import net.weavemc.loader.api.event.TickEvent
import org.lwjgl.input.Keyboard

class KillAura : Module("KillAura", Keyboard.KEY_R, Category.COMBAT) {

    val range: DoubleSetting = DoubleSetting("Range", 4.5)
    val htAllow: DoubleSetting = DoubleSetting("HTAllow", 2.0) // trying my hardest not to flag ncp without using cps

    var target: EntityPlayer? = null
    var blocking = false

    var criticals: Criticals? = null

    override fun onEnable() {
        target = null
    }

    override fun onDisable() {
        PlayerUtils.sendPacket(
            C07PacketPlayerDigging(
                C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
                BlockPos(0, 0, 0),
                EnumFacing.DOWN
            )
        )
        blocking = false
    }

    @SubscribeEvent
    fun onTickPre(e: TickEvent.Pre) {
        val selfEntityId = Minecraft.getMinecraft().thePlayer.entityId

        target = Minecraft.getMinecraft().theWorld?.playerEntities?.find {
            it.entityId != selfEntityId &&
                    it.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) <= 8
        }

        val heldItem: ItemStack? = Minecraft.getMinecraft().thePlayer?.heldItem

        if(target == null && blocking) {
            PlayerUtils.sendPacket(
                C07PacketPlayerDigging(
                    C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
                    BlockPos(0, 0, 0),
                    EnumFacing.DOWN
                )
            )
            blocking = false
        } else if ((target != null) && ((heldItem != null) && (heldItem.getItem() is ItemSword))) {
            PlayerUtils.sendPacket(
                C08PacketPlayerBlockPlacement(
                    heldItem
                )
            )
        }
    }

    @SubscribeEvent
    fun onTickPost(e: TickEvent.Post) {
        if(criticals == null)
            criticals = Client.instance?.moduleManager?.modules?.get(Criticals::class) as Criticals?

        if(target != null) {
            Minecraft.getMinecraft().thePlayer.rotationPitch = 12.5F
            Minecraft.getMinecraft().thePlayer.rotationYaw =
                PlayerUtils.getRotationYawForTarget(
                    Minecraft.getMinecraft().thePlayer.positionVector,
                    target!!.positionVector
                )

            if(target!!.hurtTime <= htAllow.value.toInt()) {
                if(target!!.hurtTime <= 1)
                    criticals?.crit()

                Minecraft.getMinecraft().thePlayer.swingItem()
                PlayerUtils.sendPacket(
                    C02PacketUseEntity(
                        target,
                        C02PacketUseEntity.Action.ATTACK
                    )
                )
            }
        }

        if(blocking) {
            PlayerUtils.sendPacket(
                C07PacketPlayerDigging(
                    C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
                    BlockPos(0, 0, 0),
                    EnumFacing.DOWN
                )
            )
            blocking = false
        }
    }

    override fun registerSettings() {
        register(range)
    }

}