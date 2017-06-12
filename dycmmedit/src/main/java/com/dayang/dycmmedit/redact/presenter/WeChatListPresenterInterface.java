package com.dayang.dycmmedit.redact.presenter;

import com.dayang.dycmmedit.adapter.WeChatListAdapter;
import com.dayang.dycmmedit.info.ExchangeManuscript;
import com.dayang.dycmmedit.info.ManuscriptListInfo;

import java.util.ArrayList;

/**
 * Created by 冯傲 on 2017/5/9.
 * e-mail 897840134@qq.com
 */

public interface WeChatListPresenterInterface {
    void loadList(ManuscriptListInfo createManuscriptInfo);

    void addManuscript(WeChatListAdapter weChatListAdapter);

    void deleteManuscript(WeChatListAdapter weChatListAdapter, ManuscriptListInfo createManuscriptInfo);

    void exchange(ArrayList<ExchangeManuscript> exchangeManuscripts);
}
