package dev.thestaticvoid.shearcows.client;

import dev.thestaticvoid.shearcows.client.render.entity.ShearedCowEntityRenderer;
import dev.thestaticvoid.shearcows.client.render.entity.model.ShearedCowEntityModel;
import dev.thestaticvoid.shearcows.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ShearCowsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ModEntities.SHEARED_COW_MODEL_LAYER, ShearedCowEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.SHEARED_COW, ShearedCowEntityRenderer::new);
    }
}
