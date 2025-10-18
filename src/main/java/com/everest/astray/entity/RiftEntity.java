package com.everest.astray.entity;

import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.light.data.PointLightData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RiftEntity extends HostileEntity {
    PointLightData light = new PointLightData();

    private float timer = 0;

    public RiftEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
        this.setInvulnerable(true);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0);
    }

    public float getTimer() {
        return timer;
    }

    public void addTimer(float time){
        timer+=time;
    }


    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        VeilRenderSystem.renderer().getLightRenderer().addLight(light);
        super.onSpawnPacket(packet);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        // Override to prevent this entity from colliding with others
    }

    @Override
    public boolean isPushable() {
        return false; // Prevents this entity from being pushed by others
    }

    @Override
    protected void tickCramming() {
        // Override to do nothing, disables entity cramming effects
    }

    @Override
    public void tick() {
        super.tick();

        double radius = 14.0;
        for (PlayerEntity player : this.getWorld().getPlayers()) {
            //if (player.squaredDistanceTo(this) <= radius * radius) {
                //pullPlayer(player, 14);
            //} else {
            //    player.noClip = false;
            //}
        }

        if (!this.getWorld().isClient()) return;

        int particleCount = 15;

        for (int i = 0; i < particleCount; i++) {
            double theta = Math.random() * 2 * Math.PI;
            double phi = Math.acos(2 * Math.random() - 1);
            double r = radius * Math.cbrt(Math.random());

            double x = this.getX() + r * Math.sin(phi) * Math.cos(theta);
            double y = this.getY() + r * Math.sin(phi) * Math.sin(theta);
            double z = this.getZ() + r * Math.cos(phi);

            double velocityX = (Math.random() - 0.5) * 0.02;
            double velocityY = (Math.random() - 0.5) * 0.02;
            double velocityZ = (Math.random() - 0.5) * 0.02;

            this.getWorld().addParticle(ParticleTypes.END_ROD, x, y, z, velocityX, velocityY, velocityZ);
        }


        light.setPosition(this.getX(), this.getY(), this.getZ()).setBrightness(0.01F).setRadius(17).setColor(200, 0, 255);
    }

    @Override
    public void onRemoved() {
        VeilRenderSystem.renderer().getLightRenderer().getLights(light.getType()).removeIf(l -> l.getLightData().equals(light));
        super.onRemoved();
    }

    @Override
    public void travel(Vec3d movementInput) {

    }
}
