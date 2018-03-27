package com.visionet.syh_mall.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseRepository<T,ID extends Serializable> extends PagingAndSortingRepository<T,ID>,JpaSpecificationExecutor<T>  {
	
	Page<T> findAll(Pageable page);
	
	
}
