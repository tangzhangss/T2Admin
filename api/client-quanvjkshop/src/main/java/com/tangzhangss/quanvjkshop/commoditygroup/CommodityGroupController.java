package com.tangzhangss.quanvjkshop.commoditygroup;

import com.tangzhangss.commonutils.base.SysBaseController;
import com.tangzhangss.quanvjkshop.specification.value.SpecificationValueEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commodity_group")
public class CommodityGroupController extends SysBaseController<SpecificationValueEntity, CommodityGroupService>{

}
