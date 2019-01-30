import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @ClassName JavaReference
 * @Description java引用  强引用、软引用、弱引用、虚引用
 * @Author Hxb
 * @Date 2019/1/16 9:55
 * @Version 1.0
 **/
public class JavaReference {

    public static void main(String[] args) {
        strongeRef();
        softRef();
        weakRef();
        weakRefNotGC();
        phantomRef();
        while (true){}
    }

    private static void strongeRef(){
        Object object = new Object();
        Object strongeRef = object;
        System.out.println(object);
        System.out.println(strongeRef);
        object = null;
        System.gc();
        System.out.println(strongeRef);
    }

    private static void softRef(){
        Object object = new Object();
        SoftReference softRef = new SoftReference(object);
        object = null;
        System.gc();//只有程序在发生OOM之前才会被回收
        System.out.println(softRef.get());
    }

    private static void weakRef(){
        Object object = new Object();
        WeakReference weakRef = new WeakReference(object);
        object = null;
        System.gc();//GC立即回收
        System.out.println(weakRef.get());
    }

    private static void weakRefNotGC(){
        String str = "Avail";
        WeakReference weakRef = new WeakReference(str);
        str = null;
        System.gc();//GC回收,但是String类型存储在常量池中（方法区），System.gc()无法回收方法区中的内容
        System.out.println(weakRef.get());
    }

    private static void phantomRef(){
        ReferenceQueue queue = new ReferenceQueue();
        Object object = new Object();
        PhantomReference phantomRef = new PhantomReference(object,queue);
        System.out.println(phantomRef.get());
        object = null;
        System.gc();
        System.out.println(phantomRef.get());
        System.out.println(queue.poll());
        System.gc();
        System.out.println(queue.poll());
    }

}
