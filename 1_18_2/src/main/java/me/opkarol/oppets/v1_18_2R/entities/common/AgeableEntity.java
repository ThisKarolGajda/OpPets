package me.opkarol.oppets.v1_18_2R.entities.common;

import me.opkarol.oppets.entities.IAgeablePet;
import me.opkarol.oppets.entities.IEntityPet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public abstract class AgeableEntity extends AgeableMob implements IAgeablePet, IEntityPet {
    protected AgeableEntity(EntityType<? extends AgeableMob> entitytypes, Level world) {
        super(entitytypes, world);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
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
