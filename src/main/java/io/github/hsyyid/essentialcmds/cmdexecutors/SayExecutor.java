/*
 * This file is part of EssentialCmds, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015 - 2015 HassanS6000
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.hsyyid.essentialcmds.cmdexecutors;

import io.github.hsyyid.essentialcmds.EssentialCmds;
import io.github.hsyyid.essentialcmds.internal.AsyncCommandExecutorBase;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.annotation.Nonnull;

public class SayExecutor extends AsyncCommandExecutorBase
{
	@Override
	public void executeAsync(CommandSource src, CommandContext ctx)
	{
		String message = ctx.<String> getOne("message").get();
		Text finalMessage = Text.builder()
			.append(Text.of(TextColors.DARK_GRAY, "[", TextColors.DARK_RED, src.getName(), TextColors.DARK_GRAY, "]", TextColors.GREEN, " "))
			.append(TextSerializers.formattingCode('&').deserialize(message))
			.build();

		if (src instanceof Player && EssentialCmds.muteList.contains(((Player) src).getUniqueId()))
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You are muted!"));
			return;
		}

		MessageChannel.TO_ALL.send(finalMessage);
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "say" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Say Command"))
			.permission("essentialcmds.say.use")
			.arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
			.executor(this)
			.build();
	}
}