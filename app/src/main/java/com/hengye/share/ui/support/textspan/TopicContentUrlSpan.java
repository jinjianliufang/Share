package com.hengye.share.ui.support.textspan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Browser;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.view.View;

import com.hengye.share.module.profile.PersonalHomepageActivity;
import com.hengye.share.util.DataUtil;
import com.hengye.share.util.L;
import com.hengye.share.util.thirdparty.WBUtil;

@SuppressLint("ParcelCreator")
public class TopicContentUrlSpan extends CharacterStyle implements ParcelableSpan, SimpleClickableSpan, SimpleContentSpan {

    public int mStart, mEnd;
    public final String mURL;

    public TopicContentUrlSpan(CustomContentSpan ccs) {
        this(ccs.start, ccs.end, ccs.content);
    }

    public TopicContentUrlSpan(int start, int end, String url) {
        mStart = start;
        mEnd = end;
        mURL = url;
    }

    public TopicContentUrlSpan(Parcel src) {
        mStart = src.readInt();
        mEnd = src.readInt();
        mURL = src.readString();
    }

    @Override
    public int getSpanTypeId() {
        return 11;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mStart);
        dest.writeInt(mEnd);
        dest.writeString(mURL);
    }

    public String getURL() {
        return mURL;
    }

    @Override
    public int getStart() {
        return mStart;
    }

    @Override
    public int getEnd() {
        return mEnd;
    }

    @Override
    public String getContent() {
        return getURL();
    }

    @Override
    public void onClick(View widget) {
        Uri uri = Uri.parse(getURL());
        Context context = widget.getContext();
        // TODO: 16/9/20 需要优化
        //如果是网页已经替换成DataUtil.WEB_SCHEME
        String url = uri.toString();
        if (WBUtil.isWBAccountIdLink(url)) {
            Intent intent = new Intent(context, PersonalHomepageActivity.class);
            intent.putExtra("id", WBUtil.getIdFromWBAccountLink(url));
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
            context.startActivity(intent);
        }
    }

    @Override
    public void onLongClick(View widget) {
        Uri data = Uri.parse(getURL());
        if (data != null) {
            String value = data.toString();
            String newValue = "";
            if (value.startsWith(DataUtil.MENTION_SCHEME)) {
//                int index = value.lastIndexOf("/");
//                newValue = value.substring(index + 1);
                newValue = value.substring(DataUtil.MENTION_SCHEME.length());
            } else if (value.startsWith("http")) {
                newValue = value;
            }

            L.debug("long click value : {}", newValue);
        }
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setColor(0xFF5061BB);
//        tp.setUnderlineText(true);
    }

    public static final Parcelable.Creator<TopicContentUrlSpan> CREATOR = new Creator<TopicContentUrlSpan>()
    {
        @Override
        public TopicContentUrlSpan[] newArray(int size)
        {
            return new TopicContentUrlSpan[size];
        }

        @Override
        public TopicContentUrlSpan createFromParcel(Parcel in)
        {
            return new TopicContentUrlSpan(in);
        }
    };
}