package com.visionet.syh_mall.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * 基础dao实现类  提供一些常用的公共方法
 * @author xiaofb
 * @time 2017年9月6日
 * @param <T>
 * @param <ID>
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends
				SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
	
	protected final EntityManager em;
	
	public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.em = em;
	}

}
