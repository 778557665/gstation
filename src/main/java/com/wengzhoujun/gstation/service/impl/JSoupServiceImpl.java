package com.wengzhoujun.gstation.service.impl;

import com.wengzhoujun.gstation.entity.Video;
import com.wengzhoujun.gstation.service.JSoupService;
import com.wengzhoujun.gstation.service.VideoService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 2019/6/26.
 *
 * @author WengZhoujun
 */
@Service
public class JSoupServiceImpl implements JSoupService {

    @Autowired
    private VideoService videoService;

    @Test
    @Override
    public void crawlingPearVideo() throws IOException {
        Document doc = Jsoup.connect("https://www.pearvideo.com/").userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0").get();
        Elements elements = doc.getElementsByClass("vervideo-lilink actplay");
        for (Element element : elements) {
            String url = element.attr("href");
            String furl = "https://www.pearvideo.com/" + url;
                try {
                    child(furl);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("---- 出错了1 ----");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("---- 出错了2 ----");
                }
        }
    }

    public void child(String childUrl) throws IOException, InterruptedException {
        System.out.println("---- 开始 ----");
        Document child = Jsoup.connect(childUrl).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0").get();
        Elements video = child.getElementsByTag("script");
        String videoUrl = getVideoPath(video);

        Element element2 = child.getElementById("poster");
        Elements img = element2.getElementsByTag("img");
        Elements tags = child.getElementsByClass("tags");

        String imgUrl = img.get(0).attr("src");
        String title = img.get(0).attr("alt");

        Video video1 = new Video();
        video1.setCreateTime(new Date());
        video1.setTitle(title);
        video1.setVideoCoverPath(imgUrl);
        video1.setVideoPath(videoUrl);
        String tag1 = tags.get(0).getElementsByClass("tag").text();
        video1.setVideoType(tag1);

        videoService.save(video1);
        Thread.sleep(5000L);
        System.out.println("---- 成功 ----");
    }

    private String getVideoPath(Elements video){
        for (Element element : video) {
            if(element.data().contains("https://video.pearvideo.com/")){
                String[] data = element.data().split(",",14);
                String path = data[12];
                return path.substring(path.indexOf("http"), path.length() - 1);
            }
        }
        return null;
    }

}
