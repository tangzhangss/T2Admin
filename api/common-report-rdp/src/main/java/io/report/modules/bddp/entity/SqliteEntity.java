package io.report.modules.bddp.entity;

import java.util.List;

public class SqliteEntity {
	    private List<String> columns;

	    private List<List<Object>> values;

	    public void setColumns(List<String> columns){
	        this.columns = columns;
	    }
	    public List<String> getColumns(){
	        return this.columns;
	    }
	    public void setValues(List<List<Object>> values){
	        this.values = values;
	    }
	    public List<List<Object>> getValues(){
	        return this.values;
	    }

}
