/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jamoamo.bot.websitestatus.model;

import org.springframework.data.annotation.Id;

/**
 * Represents a telegram update. Only contains the updateId as that is all we currently need.
 * @author James Amoore
 */
public class TelegramUpdate
{
	@Id
	private Long id;
	private int updateId;
	
	public TelegramUpdate()
	{
		
	}
	
	public Long getId()
	{
		return this.id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getUpdateId()
	{
		return this.updateId;
	}

	public void setUpdateId(Integer updateId)
	{
		this.updateId = updateId;
	}
}
