package ga.uniquecoding.bridgeegg;


import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

@SuppressWarnings("unused")
public final class BridgeEgg extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        CommandAPI.onEnable(this);
        BridgeEggStack.onEnable(this);

        new BridgeEggGiveCommand().register();

        Bukkit.getPluginManager()
              .registerEvents(new BridgeEggThrowHandler(this), this);
    }

    @Override
    public void onLoad()
    {
        CommandAPI.onLoad(new CommandAPIConfig());
    }
}
