package cn.ymex.kitx.core.adapter.empty;

/**
 * Created by ymexc on 2018/8/23.
 * About:TODO
 */
public class EmptyDataWrap<T> {
    public EmptyDataWrap() {
    }

    public EmptyDataWrap(T data) {
        this.data = data;
    }


    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
