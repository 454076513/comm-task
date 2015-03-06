package self.mf.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import self.mf.comm.CommPool;
import self.mf.comm.ResultInfo;

/**
 * 
* @Description: 分批执行任务
* @version V1.0
 */
public class Task {
    
    private ResultInfo result = new ResultInfo();
    
	private List<BatchTask> list = new ArrayList<BatchTask>();
	
	/**
	* @Description: 添加任务
	* @throws
	 */
	public void addTask(BatchTask thread){
		if (thread == null) return; 
		list.add(thread);
	}
	
	/**
	 * 
	* @Description:执行分批任务 
	* @throws
	 */
	public ResultInfo execute() throws InterruptedException{
		if(list == null || list.isEmpty()) return result;
		
	    CountDownLatch latch = new CountDownLatch(list.size());
		for (BatchTask t : list) {
			t.setLatch(latch);
			t.setInfo(result);
			CommPool.THREAD_POOL.execute(t);
		}
		latch.await();
		
		return 	result;
	}
}
