package self.mf.comm;

import java.util.List;

/**
 * Created by mindfreak on 15/4/19.
 */
public class DelayParam {
    private Object instance;

    private String method;

    private List params;

    public DelayParam instance(Object obj){
        this.instance = obj;
        return this;
    }
    public DelayParam method(String method){
        this.method = method;
        return this;
    }
    public DelayParam params(List params){
        this.params = params;
        return this;
    }

    public List params(){
        return this.params;
    }
    public String method(){
        return this.method;
    }
    public Object instance(){
        return this.instance;
    }





}
