/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jamoamo.bot.websitestatus;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Checks whether a website is online by trying to create a connection to it.
 * @author James Amoore
 */
public class WebsiteStatusChecker implements IWebsiteChecker
{
	@Override
	public boolean isWebsiteUp(String website)
			  throws Exception
	{
		HttpURLConnection conn = null;
		try
		{
			conn = (HttpURLConnection) new URL(website).openConnection();
			conn.getContent();
			int responseCode = conn.getResponseCode();
			System.out.println("Response Code: " + conn.getResponseCode());
			return conn.getResponseCode() == HttpURLConnection.HTTP_OK;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return false;
		}
		finally
		{
			if(conn != null)
			{
				conn.disconnect();
			}
		}
	}
}
