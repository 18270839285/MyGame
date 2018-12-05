package api;

/**
 * Author ： Android
 * Time ： 2017/2/27
 * Function ： BaseMode
 */
public class BaseModel<T> {
    private String code;
    private String message;
    private T data;
    private T object;
    private boolean isRefresh;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setIsRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

}
