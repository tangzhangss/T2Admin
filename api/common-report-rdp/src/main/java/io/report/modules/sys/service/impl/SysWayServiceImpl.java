/**
 * Copyright 2018 RDP http://product.mftcc.cn/rdp/
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.report.modules.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.report.common.utils.PageUtils;
import io.report.common.utils.Query;
import io.report.modules.ser.entity.DbTypeEntity;
import io.report.modules.sys.dao.SysWayDao;
import io.report.modules.sys.entity.SysWayEntity;
import io.report.modules.sys.service.SysWayService;

@Service("sysWayService")
public class SysWayServiceImpl extends ServiceImpl<SysWayDao, SysWayEntity> implements SysWayService {
	@Override
    public 	List<SysWayEntity> getList(SysWayEntity sysWayEntity){
    	 List<SysWayEntity> list = this.selectList(new EntityWrapper<SysWayEntity>());
    	 return list;
    }
}
