package ga.uniquecoding.bridgeegg;

import org.bukkit.Material;
import org.bukkit.UndefinedNullability;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.List;
import java.util.Map;
import java.util.Set;

class DelegatingItemStack extends ItemStack
{
	protected final ItemStack wrapped;

	public DelegatingItemStack(ItemStack wrapped)
	{
		this.wrapped = wrapped;
	}

	public Material getType()
	{
		return wrapped.getType();
	}

	public void setType(Material type)
	{
		wrapped.setType(type);
	}

	public int getAmount()
	{
		return wrapped.getAmount();
	}

	public void setAmount(int amount)
	{
		wrapped.setAmount(amount);
	}

	public MaterialData getData()
	{
		return wrapped.getData();
	}

	public void setData(MaterialData data)
	{
		wrapped.setData(data);
	}

	@Deprecated
	public void setDurability(short durability)
	{
		wrapped.setDurability(durability);
	}

	@Deprecated
	public short getDurability()
	{
		return wrapped.getDurability();
	}

	public int getMaxStackSize()
	{
		return wrapped.getMaxStackSize();
	}

	@Override
	public String toString()
	{
		return wrapped.toString();
	}

	@Override
	public boolean equals(Object obj)
	{
		return wrapped.equals(obj);
	}

	public boolean isSimilar(ItemStack stack)
	{
		return this.wrapped.isSimilar(stack);
	}

	@Override
	public ItemStack clone()
	{
		return wrapped.clone();
	}

	@Override
	public int hashCode()
	{
		return wrapped.hashCode();
	}

	public boolean containsEnchantment(Enchantment ench)
	{
		return wrapped.containsEnchantment(ench);
	}

	public int getEnchantmentLevel(Enchantment ench)
	{
		return wrapped.getEnchantmentLevel(ench);
	}

	public Map<Enchantment, Integer> getEnchantments()
	{
		return wrapped.getEnchantments();
	}

	public void addEnchantments(Map<Enchantment, Integer> enchantments)
	{
		wrapped.addEnchantments(enchantments);
	}

	public void addEnchantment(Enchantment ench, int level)
	{
		wrapped.addEnchantment(ench, level);
	}

	public void addUnsafeEnchantments(Map<Enchantment, Integer> enchantments)
	{
		wrapped.addUnsafeEnchantments(enchantments);
	}

	public void addUnsafeEnchantment(Enchantment ench, int level)
	{
		wrapped.addUnsafeEnchantment(ench, level);
	}

	public int removeEnchantment(Enchantment ench)
	{
		return wrapped.removeEnchantment(ench);
	}

	public Map<String, Object> serialize()
	{
		return wrapped.serialize();
	}

	public static ItemStack deserialize(Map<String, Object> args)
	{
		return ItemStack.deserialize(args);
	}

	@UndefinedNullability
	public ItemMeta getItemMeta()
	{
		return wrapped.getItemMeta();
	}

	public boolean hasItemMeta()
	{
		return wrapped.hasItemMeta();
	}

	public boolean setItemMeta(ItemMeta itemMeta)
	{
		return wrapped.setItemMeta(itemMeta);
	}

	public ItemStack ensureServerConversions()
	{
		return wrapped.ensureServerConversions();
	}

	public static ItemStack deserializeBytes(byte[] bytes)
	{
		return ItemStack.deserializeBytes(bytes);
	}

	public byte[] serializeAsBytes()
	{
		return wrapped.serializeAsBytes();
	}

	public String getI18NDisplayName()
	{
		return wrapped.getI18NDisplayName();
	}

	public int getMaxItemUseDuration()
	{
		return wrapped.getMaxItemUseDuration();
	}

	public ItemStack asOne()
	{
		return wrapped.asOne();
	}

	public ItemStack asQuantity(int qty)
	{
		return wrapped.asQuantity(qty);
	}

	public ItemStack add()
	{
		return wrapped.add();
	}

	public ItemStack add(int qty)
	{
		return wrapped.add(qty);
	}

	public ItemStack subtract()
	{
		return wrapped.subtract();
	}

	public ItemStack subtract(int qty)
	{
		return wrapped.subtract(qty);
	}

	public List<String> getLore()
	{
		return wrapped.getLore();
	}

	public void setLore(List<String> lore)
	{
		wrapped.setLore(lore);
	}

	public void addItemFlags(ItemFlag... itemFlags)
	{
		wrapped.addItemFlags(itemFlags);
	}

	public void removeItemFlags(ItemFlag... itemFlags)
	{
		wrapped.removeItemFlags(itemFlags);
	}

	public Set<ItemFlag> getItemFlags()
	{
		return wrapped.getItemFlags();
	}

	public boolean hasItemFlag(ItemFlag flag)
	{
		return wrapped.hasItemFlag(flag);
	}
}
