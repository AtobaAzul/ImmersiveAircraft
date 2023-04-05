package immersive_aircraft.entity.misc;

import immersive_aircraft.entity.InventoryVehicleEntity;
import immersive_aircraft.item.upgrade.AircraftStat;

public class AircraftProperties {
    private final InventoryVehicleEntity vehicle;
    private float yawSpeed, pitchSpeed, engineSpeed, glideFactor, driftDrag, lift, verticalSpeed, windSensitivity, rollFactor, groundPitch, mass;

    public AircraftProperties(InventoryVehicleEntity vehicle) {
        this.vehicle = vehicle;
    }

    public float getYawSpeed() {
        return yawSpeed;
    }

    // Speed of rotation
    public AircraftProperties setYawSpeed(float yawSpeed) {
        this.yawSpeed = yawSpeed;
        return this;
    }

    public float getPitchSpeed() {
        return pitchSpeed;
    }

    // Speed of up and down movement
    public AircraftProperties setPitchSpeed(float pitchSpeed) {
        this.pitchSpeed = pitchSpeed;
        return this;
    }

    public float getEngineSpeed() {
        return engineSpeed * vehicle.getTotalUpgrade(AircraftStat.STRENGTH);
    }

    public AircraftProperties setEngineSpeed(float engineSpeed) {
        this.engineSpeed = engineSpeed;
        return this;
    }

    public float getGlideFactor() {
        return glideFactor;
    }

    public AircraftProperties setGlideFactor(float glideFactor) {
        this.glideFactor = glideFactor;
        return this;
    }

    public float getDriftDrag() {
        return driftDrag * vehicle.getTotalUpgrade(AircraftStat.FRICTION);
    }

    // How much energy is lost by drift drag
    public AircraftProperties setDriftDrag(float driftDrag) {
        this.driftDrag = driftDrag;
        return this;
    }

    public float getLift() {
        return lift;
    }

    // How strong the existing velocity can be transformed into the new direction
    public AircraftProperties setLift(float lift) {
        this.lift = lift;
        return this;
    }

    public float getVerticalSpeed() {
        return verticalSpeed * vehicle.getTotalUpgrade(AircraftStat.STRENGTH);
    }

    public AircraftProperties setVerticalSpeed(float verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
        return this;
    }

    public float getWindSensitivity() {
        return windSensitivity * vehicle.getTotalUpgrade(AircraftStat.WIND);
    }

    public AircraftProperties setWindSensitivity(float windSensitivity) {
        this.windSensitivity = windSensitivity;
        return this;
    }

    public float getRollFactor() {
        return rollFactor;
    }

    public AircraftProperties setRollFactor(float rollFactor) {
        this.rollFactor = rollFactor;
        return this;
    }

    public float getGroundPitch() {
        return groundPitch;
    }

    public AircraftProperties setGroundPitch(float groundPitch) {
        this.groundPitch = groundPitch;
        return this;
    }

    public float getMass() {
        return mass;
    }

    public AircraftProperties setMass(float mass) {
        this.mass = mass;
        return this;
    }
}
