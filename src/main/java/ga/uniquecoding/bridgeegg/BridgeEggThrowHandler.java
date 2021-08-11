package ga.uniquecoding.bridgeegg;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;

import java.util.Iterator;

import static ga.uniquecoding.bridgeegg.BridgeEggStack.isBridgeEgg;
import static java.lang.Math.ceil;
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
        Projectile entityThrown = event.getEntity();

        if (entityThrown instanceof Egg)
        {
            var egg = (Egg) entityThrown;
            var shooter = egg.getShooter();

            if (shooter instanceof Player)
            {
                var player = (Player) shooter;
                var playerLocation = player.getLocation();
                var item = egg.getItem();

                if (isBridgeEgg(item))
                {
                    var bes = BridgeEggStack.of(item);
                    var distance = bes.getDistance();
                    var blockData = bes.getBlockData();

                    new BridgePlacerTask(egg, playerLocation, distance, blockData)
                            .begin();
                }
            }
        }
    }

    @EventHandler
    public void onEggHit(PlayerEggThrowEvent event)
    {
        var eggThrown = event.getEgg().getItem();

        if (isBridgeEgg(eggThrown))
        {
            event.setHatching(false);
        }
    }

    private class BridgePlacerTask extends BukkitRunnable
    {
        private final Egg egg;
        private final Location playerLocation;
        private final int maxDistance;
        private final BlockData blockData;

        private Location lastLocation;

        public BridgePlacerTask(Egg egg,
                                Location playerLocation,
                                int distance,
                                BlockData blockData)
        {
            this.egg = egg;
            this.playerLocation = playerLocation;
            this.maxDistance = distance;
            this.blockData = blockData;
        }

        public void begin()
        {
            runTaskTimer(plugin, 2, 1);
        }

        @Override
        public void run()
        {
            if (egg.isDead())
            {
                cancel();
            }
            else
            {
                var twoBlocksDown = egg.getLocation().subtract(0, 2, 0);
                var distanceFromStart = twoBlocksDown.distance(playerLocation);

                if (lastLocation == null)
                    lastLocation = twoBlocksDown;

                if (distanceFromStart < maxDistance)
                {
                    scheduleSegmentPlace(twoBlocksDown);
                }
                else
                {
                    egg.remove();
                    cancel();
                }

                lastLocation = twoBlocksDown;
            }
        }

        private void scheduleSegmentPlace(Location location)
        {
            Bukkit.getScheduler()
                  .runTaskLater(
                          plugin,
                          () -> placeSegment(location),
                          5
                  );
        }

        private void placeSegment(Location location)
        {
            var deltaDistance = (int) ceil(lastLocation.distance(location));
            var segmentRaytrace = new BlockIterator(location, 0, deltaDistance);
            replaceNonSolidBlocks(segmentRaytrace);

            location.getWorld()
                    .playSound(location, BLOCK_LAVA_POP, 2.0f, 3.0f);
        }

        private void replaceNonSolidBlocks(Iterator<Block> blocks)
        {
            blocks.forEachRemaining(this::setData);
        }

        private void setData(Block block)
        {
            if (!block.getType().isSolid())
            {
                block.setBlockData(blockData);
            }
        }
    }
}
