package self.mf.task;

import self.mf.comm.ResultInfo;


public class TaskTest {
	
	public static void main(String[] args) {
		try {
			Task task = new Task();
			for (int i = 0; i < 5; i++) {
				final int temp = i;
				task.addTask(new BatchTask() {
					
					@Override
					protected void handler(ResultInfo result) throws Exception {
						System.out.println("test task:"+temp);
					}
				});
				
			}
			task.execute();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
