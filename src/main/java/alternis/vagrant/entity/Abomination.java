package alternis.vagrant.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Abomination extends HostileEntity implements GeoEntity {

    public static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("abomination.idle");
    public static final RawAnimation RUN_ANIM = RawAnimation.begin().thenLoop("abomination.run");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    public Abomination(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void registerControllers(final AnimatableManager.@NotNull ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Idle", 5, this::predicate));
    }

    public boolean isMoving() {
        return this.getVelocity().horizontalLengthSquared() > 1e-6; // Approximate movement check
    }

    protected <E extends Abomination> PlayState predicate(final AnimationState<E> event) {
        if (this.isMoving())
        {
            event.getController().setAnimation(RUN_ANIM);
        }
        else
        {
            event.getController().setAnimation(IDLE_ANIM);
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    public static DefaultAttributeContainer.Builder createMobAttributes()
    {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new LookAtPlayerGoal(this)); // Custom goal (see step 3)
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 5f, true));
    }

    public class LookAtPlayerGoal extends Goal {
        private final Abomination entity;
        private LivingEntity target;
        private int stareTimer = 0;

        public LookAtPlayerGoal(Abomination entity) {
            this.entity = entity;
        }

        @Override
        public boolean canStart() {
            // Target the nearest player within 10 blocks
            this.target = this.entity.getWorld().getClosestPlayer(this.entity, 10.0);
            return this.target != null;
        }

        @Override
        public void start() {
            this.stareTimer = 60; // 3 seconds (20 ticks = 1 second)
            this.entity.getNavigation().stop(); // Stop moving initially
        }

        @Override
        public void tick() {
            // Stare at the player
            this.entity.getLookControl().lookAt(target, 30.0F, 30.0F);

            if (this.stareTimer > 0) {
                this.stareTimer--;
            } else {
                // After staring, run toward the player

            }
        }

        @Override
        public boolean shouldContinue() {
            return this.target.isAlive() && this.entity.squaredDistanceTo(target) < 100.0; // 10 blocks radius
        }
    }
}
