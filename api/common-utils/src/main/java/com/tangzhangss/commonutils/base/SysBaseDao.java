package com.tangzhangss.commonutils.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

@NoRepositoryBean
public interface SysBaseDao<T,TD extends Serializable> extends JpaRepository<T,TD>, PagingAndSortingRepository<T,TD>,JpaSpecificationExecutor<T>{}
