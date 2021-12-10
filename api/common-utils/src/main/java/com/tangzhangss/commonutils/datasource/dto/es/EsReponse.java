package com.tangzhangss.commonutils.datasource.dto.es;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EsReponse {
    private List<Column>columns = new ArrayList<>();
    private List<String[]>rows = new ArrayList<>();
    private String cursor;
    private Integer status;
    private Error error;
    private String version;

    @Data
    public class Error{
        private String type;
        private String reason;
    }

    @Data
    public class Column {
        private String name;
        private String type;
    }

}
