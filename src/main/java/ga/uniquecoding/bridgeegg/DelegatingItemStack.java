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
	@Override
	public Material getType()
	{
		return wrapped.getType();
	}

	@Override
	public void setType(Material type)
	{
		wrapped.setType(type);
	}

	@Override
	public int getAmount()
	{
		return wrapped.getAmount();
	}

	@Override
	public void setAmount(int amount)
	{
		wrapped.setAmount(amount);
	}

	@Override
	public MaterialData getData()
	{
		return wrapped.getData();
	}

	@Override
	public void setData(MaterialData data)
	{
		wrapped.setData(data);
	}

	@Override
	@Deprecated
	public void setDurability(short durability)
	{
		wrapped.setDurability(durability);
	}

	@Override
	@Deprecated
	public short getDurability()
	{
		return wrapped.getDurability();
	}

	@Override
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

	@Override
	public boolean isSimilar(ItemStack stack)
	{
		return wrapped.isSimilar(stack);
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

	@Override
	public boolean containsEnchantment(Enchantment ench)
	{
		return wrapped.containsEnchantment(ench);
	}

	@Override
	public int getEnchantmentLevel(Enchantment ench)
	{
		return wrapped.getEnchantmentLevel(ench);
	}

	@Override
	public Map<Enchantment, Integer> getEnchantments()
	{
		return wrapped.getEnchantments();
	}

	@Override
	public void addEnchantments(Map<Enchantment, Integer> enchantments)
	{
		wrapped.addEnchantments(enchantments);
	}

	@Override
	public void addEnchantment(Enchantment ench, int level)
	{
		wrapped.addEnchantment(ench, level);
	}

	@Override
	public void addUnsafeEnchantments(Map<Enchantment, Integer> enchantments)
	{
		wrapped.addUnsafeEnchantments(enchantments);
	}

	@Override
	public void addUnsafeEnchantment(Enchantment ench, int level)
	{
		wrapped.addUnsafeEnchantment(ench, level);
	}

	@Override
	public int removeEnchantment(Enchantment ench)
	{
		return wrapped.removeEnchantment(ench);
	}

	@Override
	public Map<String, Object> serialize()
	{
		return wrapped.serialize();
	}

	public static ItemStack deserialize(Map<String, Object> args)
	{
		return ItemStack.deserialize(args);
	}

	@Override
	@UndefinedNullability
	public ItemMeta getItemMeta()
	{
		return wrapped.getItemMeta();
	}

	@Override
	public boolean hasItemMeta()
	{
		return wrapped.hasItemMeta();
	}

	@Override
	public boolean setItemMeta(ItemMeta itemMeta)
	{
		return wrapped.setItemMeta(itemMeta);
	}

	@Override
	public ItemStack ensureServerConversions()
	{
		return wrapped.ensureServerConversions();
	}

	public static ItemStack deserializeBytes(byte[] bytes)
	{
		return ItemStack.deserializeBytes(bytes);
	}

	@Override
	public byte[] serializeAsBytes()
	{
		return wrapped.serializeAsBytes();
	}

	@Override
	public String getI18NDisplayName()
	{
		return wrapped.getI18NDisplayName();
	}

	@Override
	public int getMaxItemUseDuration()
	{
		return wrapped.getMaxItemUseDuration();
	}

	@Override
	public ItemStack asOne()
	{
		return wrapped.asOne();
	}

	@Override
	public ItemStack asQuantity(int qty)
	{
		return wrapped.asQuantity(qty);
	}

	@Override
	public ItemStack add()
	{
		return wrapped.add();
	}

	@Override
	public ItemStack add(int qty)
	{
		return wrapped.add(qty);
	}

	@Override
	public ItemStack subtract()
	{
		return wrapped.subtract();
	}

	@Override
	public ItemStack subtract(int qty)
	{
		return wrapped.subtract(qty);
	}

	@Override
	public List<String> getLore()
	{
		return wrapped.getLore();
	}

	@Override
	public void setLore(List<String> lore)
	{
		wrapped.setLore(lore);
	}

	@Override
	public void addItemFlags(ItemFlag... itemFlags)
	{
		wrapped.addItemFlags(itemFlags);
	}

	@Override
	public void removeItemFlags(ItemFlag... itemFlags)
	{
		wrapped.removeItemFlags(itemFlags);
	}

	@Override
	public Set<ItemFlag> getItemFlags()
	{
		return wrapped.getItemFlags();
	}

	@Override
	public boolean hasItemFlag(ItemFlag flag)
	{
		return wrapped.hasItemFlag(flag);
	}

	protected final ItemStack wrapped;

	public DelegatingItemStack(ItemStack wrapped)
	{
		this.wrapped = wrapped;
	}


}
