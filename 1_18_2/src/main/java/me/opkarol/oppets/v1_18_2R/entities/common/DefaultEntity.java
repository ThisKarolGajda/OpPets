package me.opkarol.oppets.v1_18_2R.entities.common;

import me.opkarol.oppets.entities.IEntityPet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public abstract class DefaultEntity extends PathfinderMob implements IEntityPet {
    protected DefaultEntity(EntityType<? extends PathfinderMob> entitytypes, Level world) {
        super(entitytypes, world);
    }

    @Override
    public Object getEntity() {
        return this;
    }

    @Override
    public Object getBukkitCraftEntity() {
        return getBukkitEntity();
    }

    @Override
    public int getEntityId() {
        return getId();
    }
}
