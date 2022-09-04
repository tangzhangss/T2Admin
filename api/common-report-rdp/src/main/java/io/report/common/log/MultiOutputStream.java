package io.report.common.log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MultiOutputStream extends OutputStream {
	static List<OutputStream> outputStreams = new ArrayList<OutputStream>();
 
	public MultiOutputStream(OutputStream ...strams) throws IOException {
		if(outputStreams.size()==0) {
			for(OutputStream stram:strams) {
				outputStreams.add(stram);
			}
		}else {
			for(OutputStream stram:strams) {
				for(int i=0,len=outputStreams.size();i<len;i++) {
					OutputStream oStram = outputStreams.get(i);
					if(oStram!=stram) {
						outputStreams.add(stram);
					}
				}
			}
		}
		System.out.println(outputStreams.size());
	}
 
	@Override
	public void write(int b) throws IOException {
		for(OutputStream oStram:outputStreams) {
			oStram.write(b);
		}
	}
}
