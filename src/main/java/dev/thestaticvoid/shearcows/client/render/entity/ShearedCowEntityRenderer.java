package dev.thestaticvoid.shearcows.client.render.entity;

import dev.thestaticvoid.shearcows.ShearCows;
import dev.thestaticvoid.shearcows.client.ShearCowsClient;
import dev.thestaticvoid.shearcows.client.render.entity.model.ShearedCowEntityModel;
import dev.thestaticvoid.shearcows.entity.ShearedCowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(value=EnvType.CLIENT)
public class ShearedCowEntityRenderer
        extends MobEntityRenderer<ShearedCowEntity, ShearedCowEntityModel<ShearedCowEntity>> {
    private static final Identifier TEXTURE = new Identifier(ShearCows.MOD_ID, "textures/entity/sheared_cow.png");

    public ShearedCowEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ShearedCowEntityModel<>(context.getPart(ShearCowsClient.SHEARED_COW_MODEL_LAYER)), 0.7f);
    }

    @Override
    public Identifier getTexture(ShearedCowEntity shearedCowEntity) {
        return TEXTURE;
    }
}

