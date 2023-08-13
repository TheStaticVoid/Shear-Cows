package dev.thestaticvoid.shearcows.client.render.entity;

import dev.thestaticvoid.shearcows.ShearCows;
import dev.thestaticvoid.shearcows.client.render.entity.model.ModShearedCowEntityModel;
import dev.thestaticvoid.shearcows.entity.ModEntities;
import dev.thestaticvoid.shearcows.entity.ModShearedCowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(value=EnvType.CLIENT)
public class ModShearedCowEntityRenderer
        extends MobEntityRenderer<ModShearedCowEntity, ModShearedCowEntityModel<ModShearedCowEntity>> {
    private static final Identifier TEXTURE = new Identifier(ShearCows.MOD_ID, "textures/entity/sheared_cow.png");

    public ModShearedCowEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ModShearedCowEntityModel(context.getPart(ModEntities.SHEARED_COW_MODEL_LAYER)), 0.7f);
    }

    @Override
    public Identifier getTexture(ModShearedCowEntity shearedCowEntity) {
        return TEXTURE;
    }
}

