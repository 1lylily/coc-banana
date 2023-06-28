package coc.client.modules.impl.visual

import coc.client.Client
import coc.client.modules.Category
import coc.client.modules.Module
import net.minecraft.client.Minecraft
import net.weavemc.loader.api.event.RenderGameOverlayEvent
import net.weavemc.loader.api.event.SubscribeEvent
import org.lwjgl.input.Keyboard
import java.util.concurrent.atomic.AtomicInteger

class HUD: Module("HUD", Keyboard.KEY_V, Category.VISUAL) {
    @SubscribeEvent
    fun onRender(e: RenderGameOverlayEvent.Post) {
        val index: AtomicInteger = AtomicInteger(0)

        Client.instance?.moduleManager?.modules?.values
            ?.stream()?.filter {
                it.enabled
            }?.forEach {
                Minecraft.getMinecraft()?.fontRendererObj?.drawString(it.name, 3, 3 + (index.getAndIncrement() * Minecraft.getMinecraft()?.fontRendererObj?.FONT_HEIGHT!!), -1)
            }
    }
}