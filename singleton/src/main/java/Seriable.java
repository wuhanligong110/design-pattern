import java.io.Serializable;

public class Seriable implements Serializable{
    public  final static Seriable INSTANCE = new Seriable();
    private Seriable(){}

    public static  Seriable getInstance(){
        return INSTANCE;
    }

    /**
     * 反序列化时返回单例对象，避免重新创建
     * @return
     */
    private  Object readResolve(){
        return  INSTANCE;
    }

}
