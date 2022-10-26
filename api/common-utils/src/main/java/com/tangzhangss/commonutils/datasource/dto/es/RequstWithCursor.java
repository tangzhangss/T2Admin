package com.tangzhangss.commonutils.datasource.dto.es;

import lombok.Data;

@Data
public class RequstWithCursor extends Requst{
    private String cursor;
}
