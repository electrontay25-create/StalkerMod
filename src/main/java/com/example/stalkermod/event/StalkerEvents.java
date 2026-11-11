package com.example.stalkermod.event;

import com.example.stalkermod.entity.StalkerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = "stalkermod")
public class StalkerEvents {
    private static int stalkTimer = 0;
    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide) {
            Player player = event.player;
            ServerLevel level = (ServerLevel) player.level();

            stalkTimer++;
            if (stalkTimer > 6000) { // Cada ~5 minutos intenta un evento
                stalkTimer = 0;
                if (RANDOM.nextBoolean()) {
                    trySpawnStalkerStalk(player, level);
                } else {
                    tryDoorEvent(player, level);
                }
            }
        }
    }

    public static void trySpawnStalkerStalk(Player player, ServerLevel level) {
        BlockPos playerPos = player.blockPosition();
        for (int i = 0; i < 10; i++) {
            double angle = RANDOM.nextDouble() * 2 * Math.PI;
            int dist = 15 + RANDOM.nextInt(15);
            int x = playerPos.getX() + (int)(Math.cos(angle) * dist);
            int z = playerPos.getZ() + (int)(Math.sin(angle) * dist);
            BlockPos spawnPos = level.getHeightmapPos(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE, new BlockPos(x, 0, z));

            // Comprobar cobertura parcial (pared o bloque al lado)
            if (level.getBlockState(spawnPos.west()).isSolid() || level.getBlockState(spawnPos.east()).isSolid() ||
                level.getBlockState(spawnPos.north()).isSolid() || level.getBlockState(spawnPos.south()).isSolid()) {
                
                StalkerEntity stalker = new StalkerEntity(com.example.stalkermod.StalkerMod.STALKER.get(), level);
                stalker.setPos(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
                level.addFreshEntity(stalker);
                level.playSound(null, spawnPos, net.minecraft.sounds.SoundEvents.AMBIENT_CAVE.value(), SoundSource.HOSTILE, 1.0f, 0.5f);
                break;
            }
        }
    }

    public static void tryDoorEvent(Player player, ServerLevel level) {
        BlockPos playerPos = player.blockPosition();
        for (BlockPos pos : BlockPos.betweenClosed(playerPos.offset(-6, -2, -6), playerPos.offset(6, 2, 6))) {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof DoorBlock) {
                boolean hasWindow = false;
                for (BlockPos windowPos : BlockPos.betweenClosed(pos.offset(-2, 0, -2), pos.offset(2, 2, 2))) {
                    if (level.getBlockState(windowPos).getBlock() instanceof GlassBlock || level.getBlockState(windowPos).getBlock() instanceof StainedGlassBlock) {
                        hasWindow = true;
                        break;
                    }
                }
                if (hasWindow) {
                    executeDoorSequence(player, level, pos.immutable());
                    break;
                }
            }
        }
    }

    private static void executeDoorSequence(Player player, ServerLevel level, BlockPos doorPos) {
        level.playSound(null, doorPos, net.minecraft.sounds.SoundEvents.WOOD_BREAK, SoundSource.BLOCKS, 0.8f, 0.5f);
        
        level.getServer().execute(() -> {
            try { Thread.sleep(4000); } catch (Exception ignored) {}
            if (level.getBlockState(doorPos).getBlock() instanceof DoorBlock) {
                level.destroyBlock(doorPos, true);
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 2, false, false));
                level.playSound(null, doorPos, net.minecraft.sounds.SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, SoundSource.BLOCKS, 1.0f, 0.5f);
            }
        });
    }
}
