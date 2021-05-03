package newsreader.downloader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ParallelDownloader extends Downloader{
    @Override
    public int process(List<String> urls) {
        //TODO Download URLs with Future
        List<URLString> urls2 = new ArrayList<>();
        for (String s : urls){
            urls2.add(new URLString(s, this));
        }
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<String>> urlsList = null;
        try{
            urlsList = executor.invokeAll(urls2);
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        executor.shutdown();

        for (Future<String> f : urlsList){
            try {
                f.get();
            }catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return urls.size();
    }
}
