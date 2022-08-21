package cn.ymex.kitx.core.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 源码来自 KunMinX 的 UnPeek-LiveData
 * https://github.com/KunMinX/UnPeek-LiveData
 * Create by KunMinX
 */
public class LifeData<T> extends LiveData<T> {

  public LifeData(T value) {
    super(value);
  }

  public LifeData() {
    super();
  }

  private final static int START_VERSION = -1;

  private final AtomicInteger mCurrentVersion = new AtomicInteger(START_VERSION);

  protected boolean isAllowNullValue;

  /**
   * 当 liveData 用作 event 时，可使用该方法观察 "生命周期敏感" 非粘性消息
   * <p>
   * state 可变且私有，event 只读且公有，
   * state 倒灌应景，event 倒灌不符预期，
   * <p>
   * 如这么说无体会，详见《吃透 LiveData 本质，享用可靠消息鉴权机制》解析：
   * https://xiaozhuanlan.com/topic/6017825943
   *
   * @param owner    activity 传入 this，fragment 建议传入 getViewLifecycleOwner
   * @param observer observer
   */
  @Override
  public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
    super.observe(owner, createObserverWrapper(observer, mCurrentVersion.get()));
  }

  /**
   * 当 liveData 用作 event 时，可使用该方法观察 "生命周期不敏感" 非粘性消息
   * @param observer observer
   */
  @Override
  public void observeForever(@NonNull Observer<? super T> observer) {
    super.observeForever(createObserverWrapper(observer, mCurrentVersion.get()));
  }

  /**
   * 当 liveData 用作 state 时，可使用该方法来观察 "生命周期敏感" 粘性消息
   *
   * @param owner    activity 传入 this，fragment 建议传入 getViewLifecycleOwner
   * @param observer observer
   */
  public void observeSticky(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
    super.observe(owner, createObserverWrapper(observer, START_VERSION));
  }

  /**
   * 当 liveData 用作 state 时，可使用该方法来观察 "生命周期不敏感" 粘性消息
   * @param observer observer
   */
  public void observeStickyForever(@NonNull Observer<? super T> observer) {
    super.observeForever(createObserverWrapper(observer, START_VERSION));
  }

  /**
   * 只需重写 setValue
   * postValue 最终还是会经过这里
   *
   * @param value value
   */
  @Override
  protected void setValue(T value) {
    mCurrentVersion.getAndIncrement();
    super.setValue(value);
  }

  /**
   * 1.添加一个包装类，自己维护一个版本号判断，用于无需 map 帮助也能逐一判断消费情况
   * 2.重写 equals 方法和 hashCode，在用于手动 removeObserver 时，忽略版本号的变化引起的变化
   */
  class ObserverWrapper implements Observer<T> {
    private final Observer<? super T> mObserver;
    private int mVersion = START_VERSION;

    public ObserverWrapper(@NonNull Observer<? super T> observer, int version) {
      this.mObserver = observer;
      this.mVersion = version;
    }

    @Override
    public void onChanged(T t) {
      if (mCurrentVersion.get() > mVersion && (t != null || isAllowNullValue)) {
        mObserver.onChanged(t);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      ObserverWrapper that = (ObserverWrapper) o;
      return Objects.equals(mObserver, that.mObserver);
    }

    @Override
    public int hashCode() {
      return Objects.hash(mObserver);
    }
  }

  /**
   * 通过 ObserveForever Observe，需记得 remove，不然存在 LiveData 内存泄漏隐患，
   * 保险做法是，在页面 onDestroy 环节安排 removeObserver 代码，
   * 具体可参见 app module ObserveForeverFragment 案例
   *
   * @param observer observeForever 注册的 observer，或 observe 注册的 observerWrapper
   */
  @Override
  public void removeObserver(@NonNull Observer<? super T> observer) {
    if (observer.getClass().isAssignableFrom(ObserverWrapper.class)) {
      super.removeObserver(observer);
    } else {
      super.removeObserver(createObserverWrapper(observer, START_VERSION));
    }
  }

  private ObserverWrapper createObserverWrapper(@NonNull Observer<? super T> observer, int version) {
    return new ObserverWrapper(observer, version);
  }

  /**
   * 手动将消息从内存中清空，
   * 以免无用消息随着 SharedViewModel 长时间驻留而导致内存溢出发生。
   */
  public void clear() {
    super.setValue(null);
  }

}
