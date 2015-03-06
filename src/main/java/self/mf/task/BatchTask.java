package self.mf.task;

import java.util.concurrent.CountDownLatch;

import self.mf.comm.ResultInfo;


public abstract class BatchTask implements Runnable{

		
		private CountDownLatch latch;
		
		private ResultInfo info;

		public void setLatch(CountDownLatch latch) {
			this.latch = latch;
		}

		public void setInfo(ResultInfo info) {
			this.info = info;
		}

		@Override
		public void run() {
			
			try {
				handler(info);
			} catch (Exception e) {
				if(info != null){
					info.addErrorInfo(e);
				}
			}finally{
				latch.countDown();
			}
		}
		
		
		protected abstract void handler(ResultInfo result) throws Exception;
		
	}