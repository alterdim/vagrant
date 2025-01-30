package alternis.vagrant.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class RiftEntity extends Entity {
    public RiftEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
        this.level = level;
    }

    public int level = 1;
    public boolean open = false;

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }
}
