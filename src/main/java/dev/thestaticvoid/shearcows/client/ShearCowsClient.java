package dev.thestaticvoid.shearcows.client;

import dev.thestaticvoid.shearcows.ShearCows;
import dev.thestaticvoid.shearcows.client.render.entity.ShearedCowEntityRenderer;
import dev.thestaticvoid.shearcows.client.render.entity.model.ShearedCowEntityModel;
import dev.thestaticvoid.shearcows.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ShearCowsClient implements ClientModInitializer {
    public static final EntityModelLayer SHEARED_COW_MODEL_LAYER = new EntityModelLayer(new Identifier(ShearCows.MOD_ID,
            "sheared_cow"), "main");
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(SHEARED_COW_MODEL_LAYER, ShearedCowEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.SHEARED_COW, ShearedCowEntityRenderer::new);
    }
}
