package ga.uniquecoding.bridgeegg;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;

import static ga.uniquecoding.bridgeegg.BridgeEggStack.isBridgeEgg;
import static org.bukkit.Sound.BLOCK_LAVA_POP;

public class BridgeEggThrowHandler implements Listener
{
	private final Plugin plugin;

	public BridgeEggThrowHandler(Plugin plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onEggThrow(ProjectileLaunchEvent event)
	{
		if (event.getEntity() instanceof Egg egg &&
				egg.getShooter() instanceof Player player)
		{
			var playerLocation = player.getLocation();
			var itemThrown = egg.getItem();

			if (isBridgeEgg(itemThrown))
			{
				var bes = BridgeEggStack.of(itemThrown);
				var distance = bes.getDistance();
				var blockData = bes.getBlockData();

				new BridgePlacerTask(plugin, egg, playerLocation, distance, blockData)
						.runTaskTimer(plugin, 2, 1);
			}
		}
	}

	@EventHandler
	public void onEggHit(PlayerEggThrowEvent event)
	{
		var eggThrown = event.getEgg().getItem();

		if (isBridgeEgg(eggThrown))
			event.setHatching(false);
	}

	private static class BridgePlacerTask extends BukkitRunnable
	{
		private final Plugin plugin;
		private final Egg egg;
		private final Location playerLocation;
		private final int distance;
		private final BlockData blockData;

		public BridgePlacerTask(Plugin plugin, Egg egg, Location playerLocation, int distance, BlockData blockData)
		{
			this.plugin = plugin;
			this.egg = egg;
			this.playerLocation = playerLocation;
			this.distance = distance;
			this.blockData = blockData;
		}

		@Override
		public void run()
		{
			if (egg.isDead())
			{
				cancel();
				return;
			}

			var location = egg.getLocation().subtract(0, 2, 0);
			var distanceFromPlayer = location.distance(playerLocation);

			if (distanceFromPlayer < distance)
			{
				Bukkit.getScheduler()
					  .runTaskLater(plugin, () -> {
						  new BlockIterator(location, 1, 1)
								  .forEachRemaining(block -> {
									  if (!block.getType().isSolid())
									  	block.setBlockData(blockData);
								  });

						  location.getWorld()
								  .playSound(location, BLOCK_LAVA_POP, 2.0f, 3.0f);
					  }, 5);
			}
			else
			{
				egg.remove();
				cancel();
			}
		}
	}
}
