package io.report.common.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;


public class RdpConsole extends AppenderSkeleton {  
	
	Logger root = Logger.getRootLogger();
	private String scheduledFilename;
	private String datePattern = "yyyy-MM-dd hh:mm:ss"; 
	static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
	@Override  
    protected void append(LoggingEvent event) { 
        //String console = super.layout.format(event);//getTime(event.getStartTime())+"RDPC ["+event.getThreadName()+"] - "+ event.getMessage();  
        String console = getTime(event.getStartTime())+"RDPC ["+event.getThreadName()+"] - "+ event.getMessage();  
        String exception = "";
        exception = "<span class='excehtext'>"+console+"</span></br>";
        if(event.getThrowableStrRep()!=null) {
        	for(int i=0; i<event.getThrowableStrRep().length;i++){
        		exception += "<span class='excetext'>"+event.getThrowableStrRep()[i]+"</span></br>";
        	}
        }
        LoggerMessage loggerMessage = new LoggerMessage(
        		event.getMessage().toString(),
        		DateFormat.getDateTimeInstance().format(new Date(event.getTimeStamp())).toString(),
        		event.getThreadName().toString(),
        		event.getLoggerName().toString(),
        		event.getLevel().toString(),
        		exception,""
        		);
        LoggerQueue.getInstance().push(loggerMessage);
    }
    
    protected String getTime(Long l) {
    	 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
         simpleDateFormat.setTimeZone(gmtTimeZone);
         Date date = new Date(l);
         return simpleDateFormat.format(date);
    }
  
    @Override  
    public void close() {  
        // TODO Auto-generated method stub  
  
    }  
  
	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	public String getScheduledFilename() {
		return scheduledFilename;
	}

	public void setScheduledFilename(String scheduledFilename) {
		this.scheduledFilename = scheduledFilename;
	}

	@Override
	public boolean requiresLayout() {
		// TODO Auto-generated method stub
		return false;
	}  
}  