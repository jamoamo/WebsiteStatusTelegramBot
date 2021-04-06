/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jamoamo.bot.websitestatus.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

/**
 * Default command handler used when the command provided is not supported.
 * 
 * @author James Amoore
 */
public class UnsupportedCommandHandler implements ICommandHandler
{
	@Override
	public SendMessage handleUpdate(Update update)
	{
		return new SendMessage(update.message().chat().id(), "Unsupported Command.");
	}
}
