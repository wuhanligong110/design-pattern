/**
 * @ClassName ThreadInfo
 * @Description TODO
 * @Author Hxb
 * @Date 2019/1/14 15:14
 * @Version 1.0
 **/
public class ThreadInfo {

    public static void main(String[] args){
        Thread threads[] = new Thread[10];
        for (int i=0; i< 10;i++){
            threads[i] = new Thread(new Number(i));
            threads[i].setName("thread:"+i);
            if( (i%2) == 0){
                threads[i].setPriority(Thread.MIN_PRIORITY);
            }else{
                threads[i].setPriority(Thread.MAX_PRIORITY);
            }
        }
        for (int i=0; i< 10;i++){
            threads[i].start();
        }
        boolean finish = false;
        while (!finish){

            for (int i=0; i< 10;i++){
                System.out.printf("%s status:%s\n",threads[i].getName(),threads[i].getState());
            }
            finish = true;
            for (int i=0; i< 10;i++){
                finish = finish &&(threads[i].getState()== Thread.State.TERMINATED);
            }

        }


    }
}

class Number implements Runnable{

    private int number;

    private Object sc = new Object();
    public Number(int n){
        number = n;
    }

    public void run() {
        for(int i= 1;i < 10;i++){
            System.out.printf("%s: %d*%d = %d \n",
                    Thread.currentThread().getName(),
                    number,i,i*number );
            synchronized (sc){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
