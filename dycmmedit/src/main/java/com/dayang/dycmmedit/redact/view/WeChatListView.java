package com.dayang.dycmmedit.redact.view;

import com.dayang.dycmmedit.base.BaseViewInterface;
import com.dayang.dycmmedit.info.ManuscriptListInfo;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/13.
 * e-mail 897840134@qq.com
 */

public interface WeChatListView extends BaseViewInterface {
    void refreshList(List<ManuscriptListInfo> list);

    void enterRedact(ManuscriptListInfo manuscriptListInfo);

    void showLoading();

    void removeLoading();
    void setHasChange(boolean hasChange);
    void refresh();
}
