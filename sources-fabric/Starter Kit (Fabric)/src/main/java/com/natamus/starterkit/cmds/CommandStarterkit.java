/*
 * This is the latest source code of Starter Kit.
 * Minecraft version: 1.19.3, mod version: 4.2.
 *
 * Please don't distribute without permission.
 * For all Minecraft modding projects, feel free to visit my profile page on CurseForge or Modrinth.
 *  CurseForge: https://curseforge.com/members/serilum/projects
 *  Modrinth: https://modrinth.com/user/serilum
 *  Overview: https://serilum.com/
 *
 * If you are feeling generous and would like to support the development of the mods, you can!
 *  https://ricksouth.com/donate contains all the information. <3
 *
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.starterkit.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.natamus.collective_fabric.functions.PlayerFunctions;
import com.natamus.collective_fabric.functions.StringFunctions;
import com.natamus.starterkit.util.Util;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;
import net.minecraft.ChatFormatting;

public class CommandStarterkit {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
    	dispatcher.register(Commands.literal("starterkit").requires((iCommandSender) -> iCommandSender.hasPermission(2))
			.then(Commands.literal("set")
			.executes((command) -> {
				CommandSourceStack source = command.getSource();
				Player player;
				try {
					player = source.getPlayerOrException();
				}
				catch (CommandSyntaxException ex) {
					StringFunctions.sendMessage(source, "This command can only be executed as a player in-game.", ChatFormatting.RED);
					return 1;
				}
				
				String newskconfig = PlayerFunctions.getPlayerGearString(player);
				if (!Util.processNewGearString(newskconfig)) {
					StringFunctions.sendMessage(source, "Something went wrong while processing the new starterkit config.", ChatFormatting.RED);
					return 0;
				}
				
				StringFunctions.sendMessage(source, "Starter Kit config updated. All new players will now receive your current inventory.", ChatFormatting.DARK_GREEN);
				return 1;
			}))
		);
    }
}
