package steve_gall.minecolonies_hordes.mixin.common.hordes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.minecolonies.api.MinecoloniesAPIProxy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.smileycorp.atlas.api.util.DirectionUtils;
import net.smileycorp.hordes.hordeevent.capability.HordeEvent;

@Mixin(value = HordeEvent.class, remap = false)
public abstract class HordeEventMixin
{
	@Redirect(method = "spawnWave(Lnet/minecraft/server/level/ServerPlayer;I)V", remap = false, at = @At(value = "INVOKE", target = "Lnet/smileycorp/atlas/api/util/DirectionUtils;getClosestLoadedPos(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/Vec3;DII)Lnet/minecraft/core/BlockPos;", remap = false))
	private BlockPos spawnWave_getClosestLoadedPos1(Level level, BlockPos basepos, Vec3 direction, double radius, int maxlight, int minlight)
	{
		var colonyManager = MinecoloniesAPIProxy.getInstance().getColonyManager();

		while (true)
		{
			var pos = DirectionUtils.getClosestLoadedPos(level, basepos, direction, radius, maxlight, minlight);

			if (colonyManager.isCoordinateInAnyColony(level, pos))
			{
				radius += 16.0D;
				continue;
			}

			return pos;
		}

	}

	@Redirect(method = "spawnWave(Lnet/minecraft/server/level/ServerPlayer;I)V", remap = false, at = @At(value = "INVOKE", target = "Lnet/smileycorp/atlas/api/util/DirectionUtils;getClosestLoadedPos(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/Vec3;D)Lnet/minecraft/core/BlockPos;", remap = false))
	private BlockPos spawnWave_getClosestLoadedPos2(Level level, BlockPos basepos, Vec3 direction, double radius)
	{
		var colonyManager = MinecoloniesAPIProxy.getInstance().getColonyManager();

		while (true)
		{
			var pos = DirectionUtils.getClosestLoadedPos(level, basepos, direction, radius);

			if (colonyManager.isCoordinateInAnyColony(level, pos))
			{
				radius += 16.0D;
				continue;
			}

			return pos;
		}

	}

}
