package com.tangzhangss.commonservice.client;

import com.tangzhangss.commonutils.base.SysBaseDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientDao extends SysBaseDao<ClientEntity, String> {
}
