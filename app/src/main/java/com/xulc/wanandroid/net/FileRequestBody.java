package com.xulc.wanandroid.net;

import com.xulc.wanandroid.utils.UploadUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Date：2018/11/20
 * Author: xuliangchun
 * Desc: 构建一个外观实现进度的回调
 */
public final class FileRequestBody extends RequestBody {
    /**
     * 实际请求体
     */
    private RequestBody requestBody;
    /**
     * 上传回调接口
     */
    private UploadUtil.UploadListener listener;

    public FileRequestBody(RequestBody requestBody, UploadUtil.UploadListener listener) {
        super();
        this.requestBody = requestBody;
        this.listener = listener;
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        //包装完成的BufferedSink
        BufferedSink bufferedSink = Okio.buffer(sink(sink));
        //写入
        requestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }
    /**
     * 写入，回调进度接口
     * @param sink Sink
     * @return Sink
     */
    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调
                listener.onProgress((int) ((bytesWritten * 100)/contentLength));
            }
        };
    }


}

