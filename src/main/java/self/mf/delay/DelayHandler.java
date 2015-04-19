package self.mf.delay;

import self.mf.comm.DelayParam;
import self.mf.utils.TraceUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by mindfreak on 15/4/13.
 */
public class DelayHandler {

    public void execCurrent(DelayParam param,long delayTime)  {
        String path = TraceUtils.callCurrentMethodPath();

        DelayCache.getInstance()
                .put(path, param, 2, TimeUnit.SECONDS)
                .executeListener(new ExecuteListener<String,DelayParam>() {
                    @Override
                    public void toDo(String key, DelayParam param) {
                        Object instance = param.instance();
                        String method = param.method();
                        List params = param.params();
                        Class[] classes = new Class[params.size()];
                        for (int i = 0; i < params.size(); i++) {
                            classes[i] = params.get(i).getClass();
                        }
                        try {
                            Class clz = instance.getClass();
                            Method[] med = clz.getDeclaredMethods();
                            for (Method m : med){
                                System.out.println(m.getName());
                                Class[] paramType = m.getParameterTypes();
                                for (Class pt : paramType){
                                    System.out.print(pt.getName()+"\t");
                                }
                            }
                            instance.getClass().getDeclaredMethod(method, classes).invoke(instance, params);
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public static void main(String[] args) {
        DelayHandler instance = new DelayHandler();
        DelayParam dp = new DelayParam().instance(instance).method("execCurrent");
        List list = new ArrayList();
        list.add(dp);
        list.add(3L);
        dp.params(list);
        instance.execCurrent(dp, 2);
    }




}
