package immersive_aircraft.entity;

import immersive_aircraft.Config;
import immersive_aircraft.Items;
import immersive_aircraft.Sounds;
import immersive_aircraft.entity.misc.AircraftProperties;
import immersive_aircraft.entity.misc.Trail;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

public class AirshipEntity extends Rotorcraft {
    private final AircraftProperties properties = new AircraftProperties()
            .setYawSpeed(5.0f)
            .setEngineSpeed(0.02f)
            .setVerticalSpeed(0.025f)
            .setGlideFactor(0.0f)
            .setDriftDrag(0.01f)
            .setLift(0.1f)
            .setRollFactor(5.0f)
            .setWindSensitivity(0.05f)
            .setMass(12.0f);

    public AirshipEntity(EntityType<? extends AircraftEntity> entityType, World world) {
        super(entityType, world);
    }

    SoundEvent getEngineSound() {
        return Sounds.PROPELLER_SMALL.get();
    }

    @Override
    public AircraftProperties getProperties() {
        return properties;
    }

    @Override
    float getGroundVelocityDecay() {
        return 0.5f;
    }

    @Override
    float getHorizontalVelocityDelay() {
        return 0.97f;
    }

    @Override
    float getVerticalVelocityDelay() {
        return 0.925f;
    }

    @Override
    float getStabilizer() {
        return 0.1f;
    }

    @Override
    public Item asItem() {
        return Items.AIRSHIP.get();
    }

    final List<List<Vector3f>> PASSENGER_POSITIONS = List.of(
            List.of(
                    new Vector3f(0.0f, -0.1f, 0.0f)
            ),
            List.of(
                    new Vector3f(0.0f, -0.1f, 0.4f),
                    new Vector3f(0.0f, -0.1f, -0.3f)
            )
    );

    private final List<Trail> trails = List.of(new Trail(15, 0.5f));

    public List<Trail> getTrails() {
        return trails;
    }

    private void trail(Matrix4f transform, float y) {
        Vector4f p0 = transformPosition(transform, (float)0.0 - 0.15f, y, (float)0.0);
        Vector4f p1 = transformPosition(transform, (float)0.0 + 0.15f, y, (float)0.0);

        float trailStrength = Math.max(0.0f, Math.min(1.0f, (float)(getVelocity().length() - 0.05f)));
        trails.get(0).add(p0, p1, trailStrength);
    }

    protected List<List<Vector3f>> getPassengerPositions() {
        return PASSENGER_POSITIONS;
    }

    @Override
    protected float getGravity() {
        return touchingWater ? 0.04f : (1.0f - getEnginePower()) * super.getGravity();
    }

    @Override
    void updateController() {
        super.updateController();

        setEngineTarget(1.0f);

        // up and down
        setVelocity(getVelocity().add(0.0f, getEnginePower() * properties.getVerticalSpeed() * pressingInterpolatedY.getSmooth(), 0.0f));

        // get pointing direction
        Vector3f direction = getDirection();

        // accelerate
        float thrust = (float)(Math.pow(getEnginePower(), 5.0) * properties.getEngineSpeed()) * pressingInterpolatedZ.getSmooth();
        Vector3f f = direction.mul(thrust);
        setVelocity(getVelocity().add(f.x, f.y, f.z));
    }

    @Override
    public void tick() {
        super.tick();

        if (world.isClient) {
            if (isWithinParticleRange()) {
                Matrix4f transform = getVehicleTransform();

                // Trails
                Matrix4f tr = new Matrix4f(transform);
                tr.translate(0.0f, 0.4f, -1.2f);
                tr.rotate(RotationAxis.POSITIVE_Z.rotationDegrees(engineRotation.getSmooth() * 50.0f));
                trail(tr, 0.0f);

                // Smoke
                if (enginePower.getValue() > 0.0 && age % 2 == 0) {
                    Vector4f p = transformPosition(transform, (random.nextFloat() - 0.5f) * 0.4f, 0.8f, -0.8f);
                    Vec3d velocity = getVelocity();
                    world.addParticle(ParticleTypes.SMOKE, p.x, p.y, p.z, velocity.x, velocity.y, velocity.z);
                }
            } else {
                trails.get(0).add(ZERO_VEC4, ZERO_VEC4, 0.0f);
            }
        }
    }

    public boolean shouldRender(double distance) {
        double d = Config.getInstance().renderDistance * getRenderDistanceMultiplier();
        return distance < d * d;
    }
}
