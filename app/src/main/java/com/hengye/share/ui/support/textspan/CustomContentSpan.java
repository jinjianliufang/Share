package com.hengye.share.ui.support.textspan;

/**
 * Created by yuhy on 2016/10/24.
 */

public class CustomContentSpan {
    public int start;
    public int end;
    public String content;

    public CustomContentSpan(SimpleContentSpan span) {
        this(span.getStart(), span.getEnd(), span.getContent());
    }

    public CustomContentSpan(int start, int end, String content) {
        this.start = start;
        this.end = end;
        this.content = content;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
