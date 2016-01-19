package com.hengye.share.ui.presenter;

import com.hengye.share.model.TopicComment;
import com.hengye.share.model.sina.WBTopicComments;
import com.hengye.share.model.sina.WBTopicReposts;
import com.hengye.share.ui.base.BasePresenter;
import com.hengye.share.ui.mvpview.TopicDetailMvpView;
import com.hengye.share.util.UrlBuilder;
import com.hengye.share.util.UserUtil;
import com.hengye.share.util.retrofit.ObjectConverter;
import com.hengye.share.util.retrofit.RetrofitManager;
import com.hengye.share.util.retrofit.WBService;
import com.hengye.share.util.thirdparty.WBUtil;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class TopicDetailPresenter extends BasePresenter<TopicDetailMvpView> {

    public TopicDetailPresenter(TopicDetailMvpView mvpView) {
        super(mvpView);
    }

    public Map<String, String> getParameter(String token, String topicId, String id, final boolean isRefresh) {
        final UrlBuilder ub = new UrlBuilder();
        ub.addParameter("access_token", token);
        ub.addParameter("id", topicId);
        if (isRefresh) {
            ub.addParameter("since_id", id);
        } else {
            ub.addParameter("max_id", id);
        }
        ub.addParameter("count", WBUtil.MAX_COUNT_REQUEST);
        return ub.getParameters();
    }

    public void loadWBCommentAndRepost(String topicId, String id, final boolean isRefresh) {
        WBService service = RetrofitManager.getWBService();
        Map<String, String> params = getParameter(UserUtil.getToken(), topicId, id, isRefresh);
        Observable.zip(
                service.listComment(params),
                service.listRepost(params),
                ObjectConverter.getObjectConverter2())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object[]>() {
            @Override
            public void onCompleted() {
                getMvpView().loadSuccess(isRefresh);
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().loadFail(isRefresh);
            }

            @Override
            public void onNext(Object[] obj) {
                getMvpView().handleCommentData(true, TopicComment.getComments((WBTopicComments)obj[0]), isRefresh);
                getMvpView().handleCommentData(false, TopicComment.getComments((WBTopicReposts)obj[1]), isRefresh);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void loadWBCommentOrRepost(String topicId, String id, final boolean isRefresh, final boolean isComment) {
        WBService service = RetrofitManager.getWBService();
        Map<String, String> params = getParameter(UserUtil.getToken(), topicId, id, isRefresh);

        Observable observable = isComment ? service.listComment(params) : service.listRepost(params);

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().loadSuccess(isRefresh);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().loadFail(isRefresh);
                    }

                    @Override
                    public void onNext(Object obj) {
                        if(isComment){
                            getMvpView().handleCommentData(isComment, TopicComment.getComments((WBTopicComments)obj), isRefresh);
                        }else{
                            getMvpView().handleCommentData(isComment, TopicComment.getComments((WBTopicReposts)obj), isRefresh);
                        }

                    }
                });
    }
}