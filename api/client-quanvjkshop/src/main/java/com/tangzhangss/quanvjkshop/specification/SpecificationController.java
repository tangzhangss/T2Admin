package com.tangzhangss.quanvjkshop.specification;

import com.tangzhangss.commonutils.base.SysBaseController;
import com.tangzhangss.quanvjkshop.specification.value.SpecificationValueEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/specification")
public class SpecificationController extends SysBaseController<SpecificationValueEntity, SpecificationService>{

}
