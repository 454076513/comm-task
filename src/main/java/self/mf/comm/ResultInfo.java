package self.mf.comm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ResultInfo {
	private String costTime;
	private List<String> errorInfo = new ArrayList<String>();
	private Lock lock = new ReentrantLock();
	private final static String LINE = "\r\n";
	public boolean isSuccess() {
		return errorInfo.isEmpty();
	}
	public String getCostTime() {
		return costTime;
	}
	public void setCostTime(String costTime) {
		this.costTime = costTime;
	}
	public void addErrorInfo(Exception e){
		lock.lock();
		try {
			if(e != null){
				errorInfo.add(e.toString());
			}
		} finally{
			lock.unlock();
		}
	}
	
	public String getErrorInfo(){
		lock.lock();
		StringBuilder s = new StringBuilder();
		try {
			for (String error : errorInfo) {
				s.append(error).append(LINE);
			}
		} finally{
			lock.unlock();
		}
		return s.toString();
	}
	
}
