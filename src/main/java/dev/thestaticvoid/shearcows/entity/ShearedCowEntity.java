package dev.thestaticvoid.shearcows.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ShearedCowEntity extends CowEntity {
    public ShearedCowEntity(EntityType<? extends CowEntity> entityType, World world) {
        super(entityType, world);
    }

    private int eatGrassTimer;
    private EatGrassGoal eatGrassGoal;

    public static DefaultAttributeContainer setAttributes() {
           return CowEntity.createCowAttributes().build();
    }

    @Override
    protected void initGoals() {
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.1, Ingredient.ofItems(Items.WHEAT), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(5, this.eatGrassGoal);
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    @Override
    public void copyPositionAndRotation(Entity entity) {
        super.copyPositionAndRotation(entity);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return super.getAmbientSound();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return super.getHurtSound(source);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    @Override
    protected float getSoundVolume() {
        return super.getSoundVolume();
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return super.getActiveEyeHeight(pose, dimensions);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        // Don't let the player reshear the cow
        if (player.getStackInHand(hand).isOf(Items.SHEARS)) {
            return ActionResult.SUCCESS;
        } else {
            return super.interactMob(player, hand);
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.SET_SHEEP_EAT_GRASS_TIMER_OR_PRIME_TNT_MINECART) {
            this.eatGrassTimer = 40;
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    protected void mobTick() {
        this.eatGrassTimer = this.eatGrassGoal.getTimer();
        super.mobTick();
    }

    @Override
    public void tickMovement() {
        if (this.world.isClient) {
            this.eatGrassTimer = Math.max(0, this.eatGrassTimer - 1);
        }
        super.tickMovement();
    }

    public float getNeckAngle(float delta) {
        if (this.eatGrassTimer <= 0) {
            return 0.0f;
        }
        if (this.eatGrassTimer >= 4 && this.eatGrassTimer <= 36) {
            return 1.0f;
        }
        if (this.eatGrassTimer < 4) {
            return ((float) this.eatGrassTimer - delta) / 4.0f;
        }
        return -((float) (this.eatGrassTimer -40) - delta) / 4.0f;
    }

    public float getHeadAngle(float delta) {
        if (this.eatGrassTimer > 4 && this.eatGrassTimer <= 36) {
            float f = ((float)(this.eatGrassTimer - 4) - delta) / 32.0f;
            return 0.62831855f + 0.21991149f * MathHelper.sin(f * 28.7f);
        }
        if (this.eatGrassTimer > 0) {
            return 0.62831855f;
        }
        return this.getPitch() * ((float)Math.PI / 180);
    }

    @Override
    public void onEatingGrass() {
        super.onEatingGrass();

        // Animation is not currently played for the conversion
        if (!this.isBaby()) {
            CowEntity cow = new CowEntity(EntityType.COW, world);
            cow.copyPositionAndRotation(this);
            cow.setHealth(this.getHealth());
            if (this.hasCustomName()) {
                cow.setCustomName(this.getCustomName());
                cow.setCustomNameVisible(this.isCustomNameVisible());
            }
            world.spawnEntity(cow);
            this.discard();
        } else {
            this.growUp(60);
        }
    }
}
