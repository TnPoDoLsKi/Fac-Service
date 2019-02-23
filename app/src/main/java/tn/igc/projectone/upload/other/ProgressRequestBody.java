package tn.igc.projectone.upload.other;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {
    private MediaType mContentType;
    private File mFile;
    private UploadCallbacks mListener;

    private static final int DEFAULT_BUFFER_SIZE = 2048;

    public interface UploadCallbacks {
        void onProgressUpdate(int percentage,long uploaded, long total);

        void onError();

        void onFinish();

        void uploadStart();
    }

    public ProgressRequestBody(final MediaType contentType,final File file, final UploadCallbacks listener) {
        mContentType = contentType;

        mFile = file;
        mListener = listener;
        mListener.uploadStart();
    }

    @Override
    public MediaType contentType() {
        // Returns the Content-Type header for this body.
        return mContentType;
    }

    @Override
    public long contentLength() throws IOException {
        //Returns the number of bytes that will be written to sink in a call to writeTo(okio.BufferedSink), or -1 if that count is unknown.
        return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        //Writes the content of this request to sink.
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;

        try {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            while ((read = in.read(buffer)) != -1) {
                uploaded += read;
                sink.write(buffer, 0, read);
                handler.post(new ProgressUpdater(uploaded, fileLength));
            }
        } finally {
            in.close();
        }
    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;

        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            Log.e("onProgressUpdate", " ->  " + "onProgressUpdate ");

            try {

                int progress = (int) (100 * mUploaded / mTotal);

                if (progress == 100)
                    mListener.onFinish();

                else
                    mListener.onProgressUpdate(progress,mUploaded,mTotal);
            } catch (ArithmeticException e) {
                mListener.onError();
                e.printStackTrace();
            }
        }
    }
}
