package github.iri.refinedbossbars;

import com.mojang.blaze3d.systems.*;
import github.iri.refinedbossbars.mixin.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.resources.*;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;

import java.util.*;

public class Events{
    public static final ResourceLocation VANILLA_LOC = new ResourceLocation("textures/gui/bars.png");
    public boolean customBossBarActive;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @OnlyIn(Dist.CLIENT)
    public void onBossInfoRender(CustomizeGuiOverlayEvent.BossEventProgress ev){
        Minecraft mc = Minecraft.getInstance();
        if(ev.isCanceled() || mc.level == null) return;
        Map<UUID, LerpingBossEvent> events = ((BossHealthOverlayAccessor)mc.gui.getBossOverlay()).getEvents();
        if(events.isEmpty()) return;
        GuiGraphics pGuiGraphics = ev.getGuiGraphics();
        int screenWidth = pGuiGraphics.guiWidth();
        int offset = 0;
        for(LerpingBossEvent event : events.values()){
            boolean flag = event.getName().getString().equals("Ender Dragon");
            boolean flag1 = event.getName().getString().equals("Wither");
            int yOffset = offset + 6;
            int xOffset = screenWidth / 2 - 91;
            int nameX = screenWidth / 2 - mc.font.width(event.getName()) / 2;
            int nameY = offset + 30;
            if(flag){
                ev.setIncrement(32);
                pGuiGraphics.blit(new ResourceLocation(Refinedbossbars.MODID, "textures/gui/ender_dragon.png"), xOffset, yOffset, 0, 0, 183, 24, 256, 64);
                int i = (int)(event.getProgress() * 177.0F);
                if(i > 0){
                    if(event.getOverlay() == BossEvent.BossBarOverlay.PROGRESS){
                        RenderSystem.enableBlend();
                        pGuiGraphics.blit(new ResourceLocation(Refinedbossbars.MODID, "textures/gui/ender_dragon.png"), xOffset + 3, yOffset + 14, 3, 30, i, 4, 256, 64);
                        RenderSystem.disableBlend();
                    }
                }

                customBossBarActive = true;
                pGuiGraphics.drawString(mc.font, event.getName(), nameX, nameY, 16777215);
            } else if(flag1){
                ev.setIncrement(32);
                pGuiGraphics.blit(new ResourceLocation(Refinedbossbars.MODID, "textures/gui/wither.png"), xOffset, yOffset, 0, 0, 183, 24, 256, 64);
                int i = (int)(event.getProgress() * 177.0F);
                if(i > 0){
                    if(event.getOverlay() == BossEvent.BossBarOverlay.PROGRESS){
                        RenderSystem.enableBlend();
                        pGuiGraphics.blit(new ResourceLocation(Refinedbossbars.MODID, "textures/gui/wither.png"), xOffset + 3, yOffset + 14, 3, 30, i, 4, 256, 64);
                        RenderSystem.disableBlend();
                    }
                }

                customBossBarActive = true;
                pGuiGraphics.drawString(mc.font, event.getName(), nameX, nameY, 16777215);
            } else {
                ev.setIncrement(18);
                drawVanillaBar(pGuiGraphics, screenWidth / 2 - 91, offset, event);
                pGuiGraphics.drawString(mc.font, event.getName(), nameX, nameY, 16777215);
            }

            offset += ev.getIncrement();
            if(offset >= pGuiGraphics.guiHeight() / 4) break;
        }

        if (customBossBarActive) {
            ev.setCanceled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void drawVanillaBar(GuiGraphics pGuiGraphics, int pX, int offset, BossEvent pBossEvent){
        drawVanillaBar(pGuiGraphics, pX, offset + 16, pBossEvent, 182, 0);
        int i = (int)(pBossEvent.getProgress() * 183.0F);
        if(i > 0){
            drawVanillaBar(pGuiGraphics, pX, offset + 16, pBossEvent, i, 5);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void drawVanillaBar(GuiGraphics pGuiGraphics, int pX, int pY, BossEvent pBossEvent, int pWidth, int p_281636_){
        pGuiGraphics.blit(VANILLA_LOC, pX, pY, 0, pBossEvent.getColor().ordinal() * 5 * 2 + p_281636_, pWidth, 5);
        if(pBossEvent.getOverlay() != BossEvent.BossBarOverlay.PROGRESS){
            RenderSystem.enableBlend();
            pGuiGraphics.blit(VANILLA_LOC, pX, pY, 0, 80 + (pBossEvent.getOverlay().ordinal() - 1) * 5 * 2 + p_281636_, pWidth, 5);
            RenderSystem.disableBlend();
        }
    }
}