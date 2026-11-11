package com.example.stalkermod;

import com.example.stalkermod.command.StalkerCommand;
import com.example.stalkermod.config.StalkerConfig;
import com.example.stalkermod.entity.StalkerEntity;
import com.example.stalkermod.entity.client.StalkerRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod("stalkermod")
public class StalkerMod {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "stalkermod");
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "stalkermod");

    public static final RegistryObject<EntityType<StalkerEntity>> STALKER = ENTITIES.register("stalker",
            () -> EntityType.Builder.of(StalkerEntity::new, MobCategory.MONSTER)
                    .sized(0.8f, 2.2f)
                    .build("stalker"));

    public static final RegistryObject<Item> STALKER_SPAWN_EGG = ITEMS.register("stalker_spawn_egg",
            () -> new SpawnEggItem(STALKER, 0x111111, 0xDDDDDD, new Item.Properties()));

    public static void setupClient(final FMLClientSetupEvent event) {
        EntityRenderers.register(STALKER.get(), StalkerRenderer::new);
    }

    public static void registerCommands(RegisterCommandsEvent event) {
        StalkerCommand.register(event.getDispatcher());
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(STALKER.get(), StalkerEntity.createAttributes().build());
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(STALKER.get(), StalkerRenderer::new);
    }

    public StalkerMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ENTITIES.register(modEventBus);
        ITEMS.register(modEventBus);

        modEventBus.addListener(StalkerMod::onClientSetup);
        modEventBus.addListener(StalkerMod::registerAttributes);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(StalkerMod::registerCommands);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, StalkerConfig.SPEC, "stalkermod-server.toml");
    }
}
