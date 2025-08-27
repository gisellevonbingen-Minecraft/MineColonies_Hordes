package steve_gall.minecolonies_hordes.core.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.minecolonies.api.MinecoloniesAPIProxy;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.smileycorp.atlas.api.util.VecMath;
import net.smileycorp.hordes.common.event.HordeFindSpawnPosEvent;
import net.smileycorp.hordes.config.HordeEventConfig;

@Mod(MineColoniesHordes.MOD_ID)
public class MineColoniesHordes
{
	public static final String MOD_ID = "minecolonies_hordes";
	public static final Logger LOGGER = LogManager.getLogger();

	public MineColoniesHordes()
	{
		NeoForge.EVENT_BUS.register(this);
	}

	/**
	 * @see net.smileycorp.hordes.hordeevent.capability.HordeEvent#getBasePos
	 * @param e
	 */
	@SubscribeEvent
	public void onHordeFindSpawnPos(HordeFindSpawnPosEvent e)
	{
		var basepos = e.getPlayer().blockPosition();
		var radius = HordeEventConfig.hordeSpawnDistance.get().doubleValue();
		var colonyManager = MinecoloniesAPIProxy.getInstance().getColonyManager();
		var pos = e.getPos();

		while (true)
		{
			if (colonyManager.isCoordinateInAnyColony(e.getLevel(), pos))
			{
				radius += 16.0D;

				if (e.checksLight())
				{
					pos = VecMath.closestLoadedPos(e.getLevel(), basepos, e.getDir(), radius, 7, 0);
				}
				else
				{
					pos = VecMath.closestLoadedPos(e.getLevel(), basepos, e.getDir(), radius);
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
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

}
