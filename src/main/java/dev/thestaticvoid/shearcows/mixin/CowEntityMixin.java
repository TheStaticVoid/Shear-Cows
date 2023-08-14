package dev.thestaticvoid.shearcows.mixin;

import dev.thestaticvoid.shearcows.entity.ModEntities;
import dev.thestaticvoid.shearcows.entity.ShearedCowEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends AnimalEntity {
    protected CowEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
    private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player.getStackInHand(hand).isOf(Items.SHEARS) && !this.isBaby()) {
            player.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0f, 1.0f);
            player.playSound(SoundEvents.ENTITY_COW_HURT, 0.5f, 1.2f);
            ItemStack drops = new ItemStack(Items.LEATHER, 1 + this.getRandom().nextInt(2));
            this.dropStack(drops);

            ShearedCowEntity newCow = new ShearedCowEntity(ModEntities.SHEARED_COW, world);
            newCow.copyPositionAndRotation(this);
            newCow.setHealth(this.getHealth());
            if (this.hasCustomName()) {
                newCow.setCustomName(this.getCustomName());
                newCow.setCustomNameVisible(this.isCustomNameVisible());
            }
            world.spawnEntity(newCow);

            this.discard();

            cir.setReturnValue(ActionResult.success(this.world.isClient));
        }
    }
}
