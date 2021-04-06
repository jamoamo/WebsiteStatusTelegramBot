/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jamoamo.bot.websitestatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The application main class. Initializes the spring boot context and runs the website checker bot.
 * @author James Amoore
 */
@SpringBootApplication
public class WebsiteStatusBotApp implements CommandLineRunner
{
	public static void main(String[] args)
	{
		SpringApplication.run(WebsiteStatusBotApp.class, args);
	}
	
	@Autowired
	private WebsiteStatusBot bot;

	@Override
	public void run(String... args)
			  throws Exception
	{
		bot.configure();
		bot.run();
	}
}
