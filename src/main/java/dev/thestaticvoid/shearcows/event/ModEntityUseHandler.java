package dev.thestaticvoid.shearcows.event;

import dev.thestaticvoid.shearcows.entity.ModEntities;
import dev.thestaticvoid.shearcows.entity.ModShearedCowEntity;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ModEntityUseHandler implements UseEntityCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand,
                                 Entity entity, @Nullable EntityHitResult hitResult) {
        if(entity instanceof CowEntity && !(entity instanceof ModShearedCowEntity) && !player.isSpectator()) {
            if(hand == Hand.MAIN_HAND && player.getMainHandStack().isOf(Items.SHEARS)) {
                if(!world.isClient()) {
                    shearServerSide(player, world, entity);
                } else {
                    shearClientSide(player, world, entity);
                }
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    private static void shearServerSide(PlayerEntity player, World world, Entity entity) {
        player.sendMessage(Text.literal("Sheared cow"));

        // Play shear sound
        // Doesn't work?
        world.playSoundFromEntity(null, entity,
                SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0f, 1.0f);

        // Create a Sheared Cow entity in replacement of the original cow
        // Modified from the Pig -> Zombie Piglin conversion

        // Currently they spawn facing north, this is the same behavior as Minecraft has.
        // The client needs to be synced with the new rotation
        ModShearedCowEntity shearedCow = new ModShearedCowEntity(ModEntities.SHEARED_COW, world);
        shearedCow.copyPositionAndRotation(entity);
        shearedCow.setHealth(((CowEntity) entity).getHealth());
        if (entity.hasCustomName()) {
            shearedCow.setCustomName(entity.getCustomName());
            shearedCow.setCustomNameVisible(entity.isCustomNameVisible());
        }
        world.spawnEntity(shearedCow);
        entity.discard();
    }

    private static void shearClientSide(PlayerEntity player, World world, Entity entity) {
        player.swingHand(Hand.MAIN_HAND);
    }
}
