package immersive_aircraft.cobalt.registration;

import immersive_aircraft.Main;
import immersive_aircraft.entity.AircraftEntity;
import java.util.function.Supplier;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class Registration {
    private static Impl INSTANCE;

    public static <T extends AircraftEntity> void register(EntityType<?> type, EntityRendererProvider<T> constructor) {
        //noinspection unchecked
        INSTANCE.registerEntityRenderer((EntityType<T>) type, constructor);
    }

    public static <T> Supplier<T> register(Registry<? super T> registry, ResourceLocation id, Supplier<T> obj) {
        return INSTANCE.register(registry, id, obj);
    }

    public static void registerDataLoader(String id, SimpleJsonResourceReloadListener loader) {
        INSTANCE.registerDataLoader(Main.locate(id), loader);
    }

    public static abstract class Impl {
        protected Impl() {
            INSTANCE = this;
        }

        public abstract <T extends Entity> void registerEntityRenderer(EntityType<T> type, EntityRendererProvider<T> constructor);

        public abstract void registerDataLoader(ResourceLocation id, SimpleJsonResourceReloadListener loader);

        public abstract <T> Supplier<T> register(Registry<? super T> registry, ResourceLocation id, Supplier<T> obj);
    }
}
