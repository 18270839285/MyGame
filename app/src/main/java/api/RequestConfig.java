package api;

import java.util.Map;

/**
 * Author ： Android
 * Time ： 2017/2/27
 * Function ： RequestConfig
 */

public class RequestConfig {
    private String requestCode = null;
    private Map<String, String> data = null;
    private Class<?> cls;
    private Class<?> element;

    public RequestConfig() {

    }

    public RequestConfig(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public Class<?> getElement() {
        return element;
    }

    public void setElement(Class<?> element) {
        this.element = element;
    }


    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }


}
