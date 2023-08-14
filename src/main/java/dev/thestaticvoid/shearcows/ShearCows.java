package dev.thestaticvoid.shearcows;

import dev.thestaticvoid.shearcows.entity.ModEntities;
import dev.thestaticvoid.shearcows.entity.ShearedCowEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShearCows implements ModInitializer {
    public static final String MOD_ID = "shearcows";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(ModEntities.SHEARED_COW, ShearedCowEntity.setAttributes());

        LOGGER.debug("Initialized mod: " + MOD_ID);
    }
}
