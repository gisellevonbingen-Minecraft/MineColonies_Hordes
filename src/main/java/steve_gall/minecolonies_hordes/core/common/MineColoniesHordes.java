package steve_gall.minecolonies_hordes.core.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.minecolonies.api.MinecoloniesAPIProxy;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.smileycorp.atlas.api.util.DirectionUtils;
import net.smileycorp.hordes.common.event.HordeFindSpawnPosEvent;
import net.smileycorp.hordes.config.HordeEventConfig;

@Mod(MineColoniesHordes.MOD_ID)
public class MineColoniesHordes
{
	public static final String MOD_ID = "minecolonies_hordes";
	public static final Logger LOGGER = LogManager.getLogger();

	public MineColoniesHordes()
	{
		MinecraftForge.EVENT_BUS.addListener(this::onHordeFindSpawnPos);
	}

	/**
	 * @see net.smileycorp.hordes.hordeevent.capability.HordeEvent#getBasePos
	 * @param e
	 */
	public void onHordeFindSpawnPos(HordeFindSpawnPosEvent e)
	{
		var basepos = e.getPlayer().blockPosition();
		var radius = HordeEventConfig.hordeSpawnDistance.get().doubleValue();
		var colonyManager = MinecoloniesAPIProxy.getInstance().getColonyManager();
		var pos = e.getPos();

		while (true)
		{
			if (colonyManager.isCoordinateInAnyColony(e.getEntityWorld(), pos))
			{
				radius += 16.0D;

				if (e.checksLight())
				{
					pos = DirectionUtils.getClosestLoadedPos(e.getEntityWorld(), basepos, e.getDir(), radius, 7, 0);
				}
				else
				{
					pos = DirectionUtils.getClosestLoadedPos(e.getEntityWorld(), basepos, e.getDir(), radius);
				}

			}
			else
			{
				break;
			}

		}

		e.setPos(pos);
	}

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MOD_ID, path);
	}

}
