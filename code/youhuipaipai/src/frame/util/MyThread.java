package frame.util;

public class MyThread extends Thread{

	public boolean isStop;
	public int times;
	
	
	public void stopRun(){
		isStop=true;
	}
	
	
	public boolean again(int t){
		if(times<t){
			times++;
			run();
			return true;
		}
		return false;
	}
}
