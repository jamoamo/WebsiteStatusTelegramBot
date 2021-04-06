/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jamoamo.bot.websitestatus.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

/**
 * Base Interface for all command handlers.
 * 
 * @author James Amoore
 */
public interface ICommandHandler
{
	public SendMessage handleUpdate(Update update);
}
