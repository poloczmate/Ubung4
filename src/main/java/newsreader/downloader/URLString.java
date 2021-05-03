package newsreader.downloader;

import java.util.concurrent.Callable;

public class URLString implements Callable<String>{
    private String url;
    private ParallelDownloader pd;

    public URLString(String url, ParallelDownloader pd) {
        this.url = url;
        this.pd = pd;
    }

    @Override
    public String call() throws Exception {
        return pd.saveUrl2File(url);
    }
}
