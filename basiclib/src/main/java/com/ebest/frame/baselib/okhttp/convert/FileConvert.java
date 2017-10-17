package com.ebest.frame.baselib.okhttp.convert;

import android.os.Environment;
import android.text.TextUtils;

import com.ebest.frame.baselib.okhttp.callback.Callback;
import com.ebest.frame.baselib.okhttp.model.Progress;
import com.ebest.frame.baselib.okhttp.rx2.process.ProgressListener;
import com.ebest.frame.baselib.okhttp.rx2.process.ProgressResponseBody;
import com.ebest.frame.baselib.okhttp.utils.HttpUtils;
import com.ebest.frame.baselib.okhttp.utils.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * ================================================
 * 描    述：字符串的转换器
 * ================================================
 */
public class FileConvert implements Converter<File> {

    public static final String DM_TARGET_FOLDER = File.separator + "download" + File.separator; //下载目标文件夹

    private final String TAG = "FileConvert";

    private String folder;                  //目标文件存储的文件夹路径
    private String fileName;                //目标文件存储的文件名
    private Callback<File> callback;        //下载回调

    public FileConvert() {
        this(null);
    }

    public FileConvert(String fileName) {
        this(Environment.getExternalStorageDirectory() + DM_TARGET_FOLDER, fileName);
    }

    public FileConvert(String folder, String fileName) {
        this.folder = folder;
        this.fileName = fileName;
    }

    public void setCallback(Callback<File> callback) {
        this.callback = callback;
    }

    @Override
    public File convertResponse(Response response) throws Throwable {
        final String url = response.request().url().toString();
        if (TextUtils.isEmpty(folder))
            folder = Environment.getExternalStorageDirectory() + DM_TARGET_FOLDER;
        if (TextUtils.isEmpty(fileName)) fileName = HttpUtils.getNetFileName(response, url);

        File dir = new File(folder);
        IOUtils.createFolder(dir);
        final File file = new File(dir, fileName);
        IOUtils.delFileOrFolder(file);

        InputStream bodyStream = null;
        byte[] buffer = new byte[8192];
        FileOutputStream fileOutputStream = null;
        final Progress progress = new Progress();
        try {
            ResponseBody body = new ProgressResponseBody(response.body(), new ProgressListener() {
                @Override
                public void onProgressChanged(long readByte, long totalBytes) {
                    if (callback != null) {
                        Progress.changeProgress(progress, readByte, new Progress.Action() {
                            @Override
                            public void call(Progress progress) {
                                onProgress(progress);
                            }
                        });
                    }
                }
            });
            bodyStream = body.byteStream();
            progress.totalSize = body.contentLength();
            progress.fileName = fileName;
            progress.filePath = file.getAbsolutePath();
            progress.status = Progress.LOADING;
            progress.url = url;
            progress.tag = url;
            if (body == null) return null;
            bodyStream = body.byteStream();
            int len;
            fileOutputStream = new FileOutputStream(file);
            while ((len = bodyStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.flush();
            return file;
        } finally {
            IOUtils.closeQuietly(bodyStream);
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    private void onProgress(final Progress progress) {
        HttpUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callback.downloadProgress(progress);   //进度回调的方法
            }
        });
    }
}
