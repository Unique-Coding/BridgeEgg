package ga.uniquecoding.bridgeegg;


import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class BridgeEgg extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		saveDefaultConfig();
		FileConfiguration config = getConfig();

		CommandAPI.onEnable(this);
		BridgeEggStack.onEnable(this);

		new BridgeEggGiveCommand().register();

		Bukkit.getServer()
			  .getPluginManager()
			  .registerEvents(new BridgeEggThrowHandler(this), this);
	}

	@Override
	public void onLoad()
	{
		CommandAPI.onLoad(new CommandAPIConfig());
	}
}
