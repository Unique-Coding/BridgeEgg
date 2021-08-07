package ga.uniquecoding.bridgeegg;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.WeakHashMap;

import static org.bukkit.Bukkit.createBlockData;
import static org.bukkit.Material.EGG;
import static org.bukkit.persistence.PersistentDataType.INTEGER;
import static org.bukkit.persistence.PersistentDataType.STRING;

/**
 * An ItemStack with the appropriate metadata to count as a bridge egg.
 */
public class BridgeEggStack extends DelegatingItemStack
{
	private static final Map<ItemStack, BridgeEggStack> WRAPPER_CACHE = new WeakHashMap<>();

	private static final BlockData WHITE_WOOL = createBlockData(Material.WHITE_WOOL);

	static NamespacedKey distanceKey;
	static NamespacedKey blockKey;

	static void onEnable(Plugin plugin)
	{
		distanceKey = new NamespacedKey(plugin, "distance");
		blockKey = new NamespacedKey(plugin, "block");
	}

	/**
	 * Determines whether this ItemStack was created by this class or not. DO NOT USE INSTANCEOF TO CHECK THIS.
	 * The runtime type of the ItemStack does not survive server resets. instanceof *might* work, but rely on this.
	 *
	 * @param stack the ItemStack to test
	 * @return true if the ItemStack given contains the metadata this class sets when making an instance, or false if
	 * one or more of these properties are missing
	 */
	public static boolean isBridgeEgg(ItemStack stack)
	{
		if (stack instanceof BridgeEggStack)
			return true;
		else if (stack.getType() != EGG)
			return false;

		PersistentDataContainer pdc = stack.getItemMeta().getPersistentDataContainer();

		return pdc.has(distanceKey, INTEGER) && pdc.has(blockKey, STRING);
	}

	/**
	 * Returns the BridgeEggStack that wraps the provided {@link ItemStack}.
	 *
	 * @param stack the ItemStack of bridge eggs
	 * @return an existing instance of BridgeEggStack if one is already wrapping this ItemStack. A new instance if not
	 */
	public static BridgeEggStack of(ItemStack stack)
	{
		BridgeEggStack existingWrapper = WRAPPER_CACHE.get(stack);

		if (existingWrapper == null)
		{
			if (isBridgeEgg(stack))
			{
				PersistentDataContainer pdc = stack.getItemMeta().getPersistentDataContainer();

				int dist = pdc.get(distanceKey, INTEGER);
				BlockData blockData = createBlockData(pdc.get(blockKey, STRING));

				BridgeEggStack wrapper = newInstance(stack, dist, blockData);
				WRAPPER_CACHE.put(stack, wrapper);

				return wrapper;
			}
			else
			{
				throw new IllegalArgumentException("The ItemStack given to wrap");
			}
		}

		return existingWrapper;
	}

	/**
	 * Creates a bridge egg stack of 1, Integer.MAX_VALUE distance, and white wool.
	 *
	 * @return a new instance
	 */
	public static BridgeEggStack newDefault()
	{
		return newInfinite(WHITE_WOOL);
	}

	/**
	 * Creates a bridge egg stack of 1, Integer.MAX_VALUE distance, and the block data given.
	 *
	 * @param blockData the type of block the egg will place
	 * @return a new instance
	 */
	public static BridgeEggStack newInfinite(BlockData blockData)
	{
		return newInstance(1, Integer.MAX_VALUE, blockData);
	}

	/**
	 * Creates a new bridge egg stack with the given properties.
	 *
	 * @param amount    the amount of eggs
	 * @param distance  how far an egg from this stack can travel before popping in the air
	 * @param blockData the kind of block an egg from this stack will leave in its trail
	 * @return a new instance wrapping a new instance of ItemStack too
	 */
	public static BridgeEggStack newInstance(int amount, int distance, BlockData blockData)
	{
		return newInstance(new ItemStack(EGG, amount), distance, blockData);
	}

	private static BridgeEggStack newInstance(ItemStack wrapped, int distance, BlockData blockData)
	{
		BridgeEggStack out = new BridgeEggStack(wrapped, distance, blockData);
		WRAPPER_CACHE.put(wrapped, out);
		return out;
	}

	private BridgeEggStack(ItemStack wrapped, int distance, BlockData blockData)
	{
		super(wrapped);
		setMeta(distance, blockData);
	}

	private void setMeta(int distance, BlockData blockData)
	{
		ItemMeta meta = wrapped.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();

		pdc.set(distanceKey, INTEGER, distance);
		pdc.set(blockKey, STRING, blockData.getAsString());

		wrapped.setItemMeta(meta);
	}

	/**
	 * Gets the distance an egg in this stack can travel from where it was thrown before popping in the air.
	 */
	public Integer getDistance()
	{
		return wrapped.getItemMeta()
					  .getPersistentDataContainer()
					  .get(distanceKey, INTEGER);
	}

	/**
	 * Gets the type of block an egg from this stack will leave in its trail when thrown.
	 *
	 * @return a new BlockData instance from the data in this stack
	 */
	public BlockData getBlockData()
	{
		String data = wrapped.getItemMeta()
							 .getPersistentDataContainer()
							 .get(blockKey, STRING);

		return data == null ? null : createBlockData(data);
	}

	public ItemStack getItemStack()
	{
		return wrapped;
	}
}
