/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jamoamo.bot.websitestatus.command;

import com.github.jamoamo.bot.websitestatus.IWebsiteChecker;
import com.pengrad.telegrambot.model.MessageEntity;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

/**
 *	Command handler that handles "/check" commands.
 * 
 * @author James Amoore
 */
public class CheckCommandHandler implements ICommandHandler
{
	private static final String URL_PREFIX_HTTP = "http://";
	private static final String URL_PREFIX_HTTPS = "https://";
	
	private final IWebsiteChecker websiteChecker;
	
	public CheckCommandHandler(IWebsiteChecker checker)
	{
		this.websiteChecker = checker;
	}
	
	@Override
	public SendMessage handleUpdate(Update update)
	{
		String url = parseUrl(update);
		
		boolean isWebsiteUp = checkWebsite(url);

		SendMessage message = createStatusMessage(isWebsiteUp, update, url);
		
		return message;
		/*
		SendResponse response = this.telegramBot.execute(message);
		System.out.println(response.errorCode() + ": " + response.description());
		System.out.println(response);*/
	}

	private SendMessage createStatusMessage(boolean isWebsiteUp, Update update, String url)
	{
		String status = (isWebsiteUp ? "online" : "offline");
		SendMessage message =
				  new SendMessage(update.message().chat().id(), url + " is " + status)
							 .disableWebPagePreview(true)
							 .replyToMessageId(update.message().messageId());
		return message;
	}

	private boolean checkWebsite(String url)
	{
		boolean isWebsiteUp = false;
		try
		{
			isWebsiteUp = this.websiteChecker.isWebsiteUp(url);
		}
		catch(Exception ex)
		{
			isWebsiteUp = false;
		}
		return isWebsiteUp;
	}

	private String parseUrl(Update update)
	{
		String url = "";
		for(MessageEntity entity : update.message().entities())
		{
			if(entity.type() == MessageEntity.Type.url)
			{
				url = update.message().text().substring(entity.offset(), entity.offset() + entity.length());
			}
		}
		
		System.out.println("Handle Check Command");
		if(!url.startsWith(URL_PREFIX_HTTP) && !url.startsWith(URL_PREFIX_HTTPS))
		{
			url = URL_PREFIX_HTTPS + url;
		}
		
		return url;
	}
}
