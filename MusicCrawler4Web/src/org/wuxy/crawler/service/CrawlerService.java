package org.wuxy.crawler.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.wuxy.crawler.dao.CrawlerDao;
import org.wuxy.crawler.dao.CrawlerDaoImpl;
import org.wuxy.crawler.model.Song;
import org.wuxy.crawler.model.WebPage;
import org.wuxy.crawler.model.WebPage.PageType;

public class CrawlerService {
	
	private static final int MAX_THREADS = 5;
	
	private CrawlerDao crawlerDao = new CrawlerDaoImpl();
	//返回未爬取页面
	public WebPage getUnCrawlPage(){
		return crawlerDao.getUnCrawlPage();
	}
	
	//保存爬去到的页面信息
	public void savePage(List<WebPage> webPageList){
		for (WebPage webPage : webPageList) {			
			crawlerDao.savePage(webPage);
		}
	}
	public void savePage(WebPage webPage){			
			crawlerDao.savePage(webPage);
		}
	
	//更新页面信息
	public void updatePage(WebPage webPage){
		crawlerDao.updatePage(webPage);
	}
	//保存歌曲信息
	public void saveSong(Song song){
		crawlerDao.saveSong(song);
	}
	//查询歌曲心理列表
	public List<Song> querySongsList(Integer pageNow,Integer pageNum){
		return crawlerDao.querySongsList(pageNow,pageNum);
	}
	//查询歌曲总数
	public Long getSongsCount(){
		return crawlerDao.getSongsCount();
	}
	
	//初始化页面
	private void init(String catalog){
	    List<WebPage> webPages = new ArrayList<WebPage>();
	    for(int i = 0; i < 43; i++) {
	        webPages.add(new WebPage("http://music.163.com/discover/playlist/?order=hot&cat=" + catalog + "&limit=35&offset=" + (i * 35), PageType.playlists));
	    }
	    for (WebPage webPage : webPages) {
	    	crawlerDao.savePage(webPage);
		}
	}
	public void init(){
        init("全部");            
        init("华语");
        init("欧美");
        init("日语");
        init("韩语");
        init("粤语");
        init("小语种");
        init("流行");
        init("摇滚");
        init("民谣");
        init("电子");
        init("舞曲");
        init("说唱");
        init("轻音乐");
        init("爵士");
        init("乡村");
        init("R&B/Soul");
        init("古典");
        init("民族");
        init("英伦");
        init("金属");
        init("朋克");
        init("蓝调");
        init("雷鬼");
        init("世界音乐");
        init("拉丁");
        init("另类/独立");
        init("New Age");
        init("古风");
        init("后摇");
        init("Bossa Nova");
        init("清晨");
        init("夜晚");
        init("学习");
        init("工作");
        init("午休");
        init("下午茶");
        init("地铁");
        init("驾车");
        init("运动");
        init("旅行");
        init("散步");
        init("酒吧");
        init("怀旧");
        init("清新");
        init("浪漫");
        init("性感");
        init("伤感");
        init("治愈");
        init("放松");
        init("孤独");
        init("感动");
        init("兴奋");
        init("快乐");
        init("安静");
        init("思念");
        init("影视原声");
        init("ACG");
        init("校园");
        init("游戏");
        init("70后");
        init("80后");
        init("90后");
        init("网络歌曲");
        init("KTV");
        init("经典");
        init("翻唱");
        init("吉他");
        init("钢琴");
        init("器乐");
        init("儿童");
        init("榜单");
        init("00后");
	 }
	//爬行入口,启动线程。
	public void crawl(String str) throws InterruptedException{
		ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
		if (str.equals("open")) {
			System.out.println("开启");
			for (int i = 0; i < MAX_THREADS; i++) {
				executorService.execute(new CrawlerThread());
			}
			executorService.shutdown();
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		}
	}

}
