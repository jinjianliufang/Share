package com.hengye.share.module.util.encapsulation.mvp;

import com.hengye.share.module.util.encapsulation.base.ListTaskCallBack;
import com.hengye.share.module.util.encapsulation.base.TaskState;

/**
 * Created by yuhy on 2016/10/20.
 */

public class ListTaskPresenter<V extends MvpView & ListTaskCallBack> extends RxPresenter<V> {

    public ListTaskPresenter(V mvpView){
        super(mvpView);
    }

    public class ListTaskObserver<T> extends BaseObserver<T> {

        protected boolean isRefresh;

        public ListTaskObserver(boolean isRefresh){
            this.isRefresh = isRefresh;
        }

        @Override
        public void onError(V v, Throwable e) {
            super.onError(v, e);
            //根据e来判断是网络异常还是服务器异常;
            v.onTaskComplete(isRefresh, TaskState.getFailState(e));
        }

        @Override
        public void onComplete(V v) {
            super.onComplete(v);
            v.onTaskComplete(isRefresh, TaskState.STATE_SUCCESS);
        }

        @Override
        public void onNext(V v, T t) {
        }
    }

    public class ListTaskSingleObserver<T> extends BaseSingleObserver<T>{

        protected boolean isRefresh;

        public ListTaskSingleObserver(boolean isRefresh){
            this.isRefresh = isRefresh;
        }

        @Override
        public void onSuccess(T t) {
            super.onSuccess(t);
            //分页处理需要在添加完数据后再调用onTaskComplete()方法处理数据切换view的显示
            if(isViewAttached()){
                getMvpView().onTaskComplete(isRefresh, TaskState.STATE_SUCCESS);
            }
        }

        @Override
        public void onSuccess(V v, T t) {
        }

        @Override
        public void onError(V v, Throwable e) {
            v.onTaskComplete(isRefresh, TaskState.getFailState(e));
        }
    }
}
