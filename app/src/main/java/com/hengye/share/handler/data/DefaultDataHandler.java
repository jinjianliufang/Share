package com.hengye.share.handler.data;

import com.hengye.share.handler.data.base.DataAdapter;
import com.hengye.share.handler.data.base.DataHandler;

import java.util.List;

import static com.hengye.share.handler.data.base.DataType.LOAD_DATA_SIZE_EQUAL;
import static com.hengye.share.handler.data.base.DataType.LOAD_NO_MORE_DATA;
import static com.hengye.share.handler.data.base.DataType.REFRESH_DATA_SIZE_EQUAL;
import static com.hengye.share.handler.data.base.DataType.REFRESH_DATA_SIZE_LESS;
import static com.hengye.share.handler.data.base.DataType.getType;

/**
 * Created by yuhy on 16/7/28.
 */
public class DefaultDataHandler<T> implements DataHandler<T> {

    DataAdapter<T> mAdapter;

    public DefaultDataHandler(DataAdapter<T> adapter){
        mAdapter = adapter;
    }

    @Override
    public int handleData(boolean isRefresh, List<T> data) {
        int type = getType(isRefresh, data, mAdapter.getData());
        handleAdapter(type, data, mAdapter);
        return type;
    }

    public static <T> void handleAdapter(int type, List<T> data, DataAdapter<T> adapter){
        switch (type) {
            case REFRESH_DATA_SIZE_LESS:
            case REFRESH_DATA_SIZE_EQUAL:
                adapter.refresh(data);
                break;
            case LOAD_NO_MORE_DATA:
            case LOAD_DATA_SIZE_EQUAL:
                adapter.addAll(data);
                break;
        }
    }


}
