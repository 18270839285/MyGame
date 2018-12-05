package mvp.rx.retrofit.view;

import mvp.rx.retrofit.base.BaseView;
import mvp.rx.retrofit.model.Version;
import mvp.rx.retrofit.model.Versions;

/**
 * Description:
 * Data：2018/10/23-15:45
 * Author: lin
 */
public interface  VersionView extends BaseView {
    void getVersion(Versions version);

}
