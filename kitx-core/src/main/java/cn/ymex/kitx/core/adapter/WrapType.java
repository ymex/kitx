package cn.ymex.kitx.core.adapter;

/**
 * Created by ymexc on 2017/11/15.
 * About:item view 包装类型
 */

public class WrapType<T> implements Type {

    private int index = -0x1;
    private String typeTag;
    private T data;

    public static <E> WrapType<E> create(E data) {
        return new WrapType<E>(data, "");
    }

    public static <E> WrapType<E> create(E data, String tag) {
        return new WrapType<E>(data, tag);
    }

    public WrapType<T> typeTag(String tag) {
        this.typeTag = tag;
        return this;
    }

    private WrapType(T data, String tag) {
        this.typeTag = tag;
        this.data = data;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public T getData() {
        return data;
    }

    @Override
    public String getTypeTag() {
        return this.typeTag;
    }

}
