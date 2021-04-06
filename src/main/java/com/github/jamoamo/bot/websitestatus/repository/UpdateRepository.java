/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jamoamo.bot.websitestatus.repository;

import com.github.jamoamo.bot.websitestatus.model.TelegramUpdate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for accessing the telegram bot database.
 * @author James Amoore
 */
@Repository
public interface UpdateRepository extends CrudRepository<TelegramUpdate, Long>
{
	public TelegramUpdate findFirstByOrderByUpdateIdDesc();
}
