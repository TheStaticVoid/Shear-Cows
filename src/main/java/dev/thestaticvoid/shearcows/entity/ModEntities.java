package dev.thestaticvoid.shearcows.entity;

import dev.thestaticvoid.shearcows.ShearCows;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<ShearedCowEntity> SHEARED_COW = Registry.register(
        Registries.ENTITY_TYPE, new Identifier(ShearCows.MOD_ID, "sheared_cow"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ShearedCowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9f, 1.4f))
                    .trackRangeChunks(8).build());
}
