package steve_gall.minecolonies_hordes.core.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod(MineColoniesHordes.MOD_ID)
public class MineColoniesHordes
{
	public static final String MOD_ID = "minecolonies_hordes";
	public static final Logger LOGGER = LogManager.getLogger();

	public MineColoniesHordes()
	{

	}

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MOD_ID, path);
	}

}
