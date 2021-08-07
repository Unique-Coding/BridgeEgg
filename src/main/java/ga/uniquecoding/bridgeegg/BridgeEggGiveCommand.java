package ga.uniquecoding.bridgeegg;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.BlockStateArgument;
import dev.jorel.commandapi.arguments.EntitySelectorArgument;
import dev.jorel.commandapi.arguments.IntegerArgument;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

import static dev.jorel.commandapi.arguments.EntitySelectorArgument.EntitySelector.MANY_PLAYERS;

class BridgeEggGiveCommand extends CommandAPICommand
{
	private static final Argument[] ARGS =
			{
					new EntitySelectorArgument("target", MANY_PLAYERS),
					new IntegerArgument("amount"),
					new IntegerArgument("distance"),
					new BlockStateArgument("block_data")
			};

	BridgeEggGiveCommand()
	{
		super("bridge-egg");
		withArguments(ARGS);
		withPermission("bridgeegg.give");
		executes(BridgeEggGiveCommand::execute);
	}

	private static void execute(CommandSender sender, Object[] args)
	{
		//noinspection unchecked
		Collection<Player> targetPlayers = (Collection<Player>) args[0];

		for (Player player : targetPlayers)
		{
			int amount = (int) args[1];
			int distance = (int) args[2];
			BlockData blockData = (BlockData) args[3];
			BridgeEggStack eggStack = BridgeEggStack.newInstance(amount, distance, blockData);

			player.getInventory()
				  .addItem(eggStack);
		}
	}
}
