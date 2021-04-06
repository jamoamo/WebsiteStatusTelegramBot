/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jamoamo.bot.websitestatus;

import com.github.jamoamo.bot.websitestatus.command.CheckCommandHandler;
import com.github.jamoamo.bot.websitestatus.command.ICommandHandler;
import com.github.jamoamo.bot.websitestatus.command.UnsupportedCommandHandler;
import com.github.jamoamo.bot.websitestatus.model.TelegramUpdate;
import com.github.jamoamo.bot.websitestatus.repository.UpdateRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.MessageEntity;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The Telegram Bot that retrieves updates from Telegram and actions them.
 * The following commands are supported:
 * <ul>
 *	<li>/check
 * </ul>
 * @author James Amoore
 */
@Component
public class WebsiteStatusBot implements Runnable
{
	private static final String PROP_TELEGRAM_BOT_TOKEN = "telegram-bot-token";
	private static final String COMMAND_CHECK = "/check";
	private static final long THROTTLE_WAIT = 10000l;

	private final HashMap<String, ICommandHandler> commands = new HashMap<>();

	private final TelegramBot telegramBot;
	
	@Autowired
	private UpdateRepository updateRepository;
	
	@Autowired
	private IWebsiteChecker websiteChecker;

	public WebsiteStatusBot()
	{
		this.telegramBot = new TelegramBot(getToken());
		registerCommands();
	}
	
	public void configure()
	{
		registerCommands();
	}

	private String getToken()
			  throws RuntimeException
	{
		String token = System.getProperty(PROP_TELEGRAM_BOT_TOKEN);
		if(token == null)
		{
			throw new RuntimeException("No Telegram Bot Token found!");
		}
		else
		{
			System.out.println("Using token: " + token);
		}
		return token;
	}

	private void registerCommands()
	{
		commands.put(COMMAND_CHECK, new CheckCommandHandler(websiteChecker));
	}

	@Override
	public void run()
	{
		int nextId = getLastUpdateId() +1;
		while(true)
		{
			GetUpdates updateRequest = new GetUpdates().limit(100).offset(nextId).timeout(1000);
			GetUpdatesResponse response = this.telegramBot.execute(updateRequest);

			if(!response.isOk())
			{
				//Failed to get Updates
				System.out.println("Failed to get Updates.");
				return;
			}
			
			List<Update> updateList = response.updates();
			
			
			for(Update update : updateList)
			{
				processUpdate(update);
				storeUpdate(update);
				nextId++;
			}

			throttle(THROTTLE_WAIT);
		}
	}

	private int getLastUpdateId()
	{
		TelegramUpdate lastUpdate = updateRepository.findFirstByOrderByUpdateIdDesc();
		if(lastUpdate != null)
		{
			return lastUpdate.getUpdateId();
		}
		return 593869041;
	}
	
	private void storeUpdate(Update update)
	{
		TelegramUpdate telegramUpdate = new TelegramUpdate();
		telegramUpdate.setUpdateId(update.updateId());
		
		updateRepository.save(telegramUpdate);
	}

	private void throttle(long throttleMillis)
	{
		try
		{
			Thread.sleep(throttleMillis);
		}
		catch(InterruptedException ie)
		{
			//Do Nothing
		}
	}

	private void processUpdate(Update update)
	{
		int updateId = update.updateId();
		System.out.println("Process update id: " + updateId);
		MessageEntity[] entities = update.message().entities();
		String command = null;
		for(MessageEntity entity : entities)
		{
			if(entity.type() == MessageEntity.Type.bot_command)
			{
				command = update.message().text().substring(entity.offset(), entity.offset() + entity.length());
			}
		}

		if(command == null)
		{
			//No command was specified. Nothing to process.
			return;
		}

		ICommandHandler handler = commands.get(command);
		if(handler == null)
		{
			//Unsupported Command
			handler = new UnsupportedCommandHandler();
		}

		SendMessage message = handler.handleUpdate(update);
		if(message != null)
		{
			SendResponse response = this.telegramBot.execute(message);
			System.out.println(response.errorCode() + ": " + response.description());
			System.out.println(response);
		}
	}
}
