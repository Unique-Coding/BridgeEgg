package ga.uniquecoding.bridgeegg;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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
    private static final int BRIDGE_PLACE_DELAY = 5;

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
        private final Location locationThrownFrom;
        private final int maxDistance;
        private final BlockData blockData;
        private final World world;

        private Location lastLocation;

        public BridgePlacerTask(Egg eggThrown,
                                Location locationThrownFrom,
                                int maxDistance,
                                BlockData blockData)
        {
            this.egg = eggThrown;
            this.locationThrownFrom = locationThrownFrom;
            this.maxDistance = maxDistance;
            this.blockData = blockData;
            this.world = locationThrownFrom.getWorld();
        }

        public void begin()
        {
            runTaskTimer(plugin, 2, 1);
        }

        // Meant to run every tick until the egg dies somehow
        @Override
        public void run()
        {
            if (egg.isDead())
            {
                cancel();
                return;
            }

            // 2 blocks down to make the bridge walkable. Not really necessary
            var currentLocation = egg.getLocation().subtract(0, 2, 0);

            if (lastLocation == null) lastLocation = currentLocation;

            var distanceFromStart = currentLocation.distance(locationThrownFrom);
            if (distanceFromStart > maxDistance)
            {
                egg.remove();
                cancel();
                return;
            }

            placeBridgeSegmentAfterDelay(currentLocation);

            lastLocation = currentLocation;
        }

        private void placeBridgeSegmentAfterDelay(Location location)
        {
            Bukkit.getScheduler()
                  .runTaskLater(
                          plugin,
                          () -> placeSegment(location),
                          BRIDGE_PLACE_DELAY
                  );
        }

        private void placeSegment(Location location)
        {
            var deltaDistance = (int) ceil(lastLocation.distance(location));
            var segmentRaytrace = new BlockIterator(location, 0, deltaDistance);
            replaceNonSolidBlocks(segmentRaytrace);

            world.playSound(location, BLOCK_LAVA_POP, 2.0f, 3.0f);
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
