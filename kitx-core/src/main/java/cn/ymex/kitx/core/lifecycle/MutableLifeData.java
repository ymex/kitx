package cn.ymex.kitx.core.lifecycle;


public class MutableLifeData<T> extends LifeData<T> {

    /**
     * Creates a MutableLifeData initialized with the given {@code value}.
     *
     * @param value initial value
     */
    public MutableLifeData(T value) {
        this(value, false);
    }

    public MutableLifeData(T value, boolean allowNull) {
        super(value);
        isAllowNullValue = allowNull;
    }

    /**
     * Creates a MutableLifeData with no value assigned to it.
     */
    public MutableLifeData() {
        super();
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
    }

    @Override
    public void postValue(T value) {
        super.postValue(value);
    }

}
