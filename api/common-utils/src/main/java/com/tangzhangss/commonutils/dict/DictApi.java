package com.tangzhangss.commonutils.dict;

import com.tangzhangss.commonutils.base.SysBaseApi;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "系统字典")
@RestController
@RequestMapping("/dict")
public class DictApi extends SysBaseApi<DictEntity,DictService> {

}
