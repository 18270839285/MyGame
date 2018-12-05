package mvp.rx.retrofit.base;

/**
 * Description:
 * Data：2018/10/23-15:56
 * Author: lin
 */
public class BaseRequestEntity<T> {
    private T data;
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
