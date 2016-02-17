package com.hengye.share.ui.presenter;

import com.hengye.share.model.greenrobot.User;
import com.hengye.share.ui.mvpview.AccountManageMvpView;
import com.hengye.share.util.CommonUtil;
import com.hengye.share.util.UserUtil;

import java.util.List;

public class AccountManagePresenter extends BasePresenter<AccountManageMvpView> {

    public AccountManagePresenter(AccountManageMvpView mvpView){
        super(mvpView);
    }

    public void loadUsers(){
        List<User> users = UserUtil.queryUsers();
        if(!CommonUtil.isEmptyCollection(users)){
            getMvpView().loadSuccess(users);
        }else{
            getMvpView().loadFail();
        }
    }
}
