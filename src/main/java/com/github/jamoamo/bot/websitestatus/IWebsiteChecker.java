/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jamoamo.bot.websitestatus;

/**
 * Interface for website checker implementations.
 * @author James Amoore
 */
public interface IWebsiteChecker
{
	boolean isWebsiteUp(String website)
			  throws Exception;
}
