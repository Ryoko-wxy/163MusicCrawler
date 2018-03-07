package org.wuxy.crawler.service;

import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.wuxy.crawler.model.Song;
import org.wuxy.crawler.model.WebPage;
import org.wuxy.crawler.model.WebPage.PageType;
import org.wuxy.crawler.model.WebPage.Status;

public class CrawlerThread implements Runnable{
    public static final String BASE_URL = "http://music.163.com";
    public static final String text = "{\"username\": \"\", \"rememberLogin\": \"true\", \"password\": \"\" , \"limit\": \"20\", \"offset\": \"20\"}";
    public static final CrawlerThreadHelp help = new CrawlerThreadHelp();
    private static CrawlerService CrawlerService = new CrawlerService();
    
    
	@Override
	public void run() {
		while (true) {
			WebPage webPage = CrawlerService.getUnCrawlPage();
			if (webPage==null) {
				// 拿不到url，说明没有需要爬的url，直接退出
				return;
			}try {
				if (fetchHtml(webPage)) {
					System.out.println(webPage.getId());
					parse(webPage);
					webPage.setStatus(Status.crawled);
					CrawlerService.updatePage(webPage);
					System.out.println("======");
					Thread.sleep(15000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
    
    
    private void parse(WebPage webPage) throws Exception{
    	//根据type判断如果页面类型处理相应页面
    	if (PageType.playlists.equals(webPage.getType())) {
			List<WebPage> songLists = parsePlaylists(webPage);
			CrawlerService.savePage(songLists);
		}
    	if (PageType.playlist.equals(webPage.getType())) {
			List<WebPage> songList = parsePlaylist(webPage);
			CrawlerService.savePage(songList);
		}
    	if (PageType.song.equals(webPage.getType())) {
			Song song = parseSong(webPage);
			CrawlerService.saveSong(song);
		}
    }
	
	private boolean fetchHtml(WebPage webPage) throws Exception{
		Response response = Jsoup.connect(webPage.getUrl()).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
			      .referrer("http://www.google.com").timeout(3000).execute();
		webPage.setHtml(response.body());
		return response.statusCode() / 100 == 2 ? true : false;
	}
	//解析歌单列表页面
	private List<WebPage> parsePlaylists(WebPage webPage){
		Elements playList = Jsoup.parse(webPage.getHtml()).select("ul p.dec a");
		return playList.stream().map(e -> new WebPage(BASE_URL + e.attr("href"), PageType.playlist, e.html())).collect(Collectors.toList());
	}
	
	//解析歌单页面
	private List<WebPage> parsePlaylist(WebPage webPage){
		Elements songs = Jsoup.parse(webPage.getHtml()).select("ul.f-hide li a");
		return songs.stream().map(e -> new WebPage(BASE_URL + e.attr("href"), PageType.song, e.html())).collect(Collectors.toList());
	}
	//解析歌曲页面
	private Song parseSong(WebPage webPage) throws Exception {
		//取评论数
		Document document = help.commentAPI(text, webPage.getUrl().split("=")[1]);
		String[] split = document.text().split(",");
		String commentCount = split[split.length-2].split(":")[1];
		//取歌名
		Elements songName = Jsoup.parse(webPage.getHtml()).getElementsByClass("f-ff2");
		Song song = new Song();
		song.setCommentCount(Integer.parseInt(commentCount));
		song.setUrl(webPage.getUrl());
		song.setTitle(songName.text());
		return song;
	}
	
	public static void main(String[] args) throws Exception {
/*		//测试歌曲	    
 		WebPage playlist = new WebPage("http://music.163.com/song?id=185918", PageType.song);
	    CrawlerThread crawlerThread = new CrawlerThread();
		crawlerThread.fetchHtml(playlist);
	    crawlerThread.parse(playlist);
*/
	    
/*		//测试歌单
		WebPage playlist = new WebPage("http://music.163.com/playlist?id=876886895", PageType.playlist);
	    CrawlerThread crawlerThread = new CrawlerThread();
	    crawlerThread.fetchHtml(playlist);
	    List<WebPage> list = crawlerThread.parsePlaylist(playlist);
	    for (WebPage webPage : list) {
	    	CrawlerService.savePage(webPage);
	    	System.out.println(webPage.getUrl());
		}
*/		
/*		//测试歌单列表
		WebPage playlist = new WebPage();
	    CrawlerThread crawlerThread = new CrawlerThread();
			playlist.setUrl("http://music.163.com/discover/playlist/?order=hot&cat=%E5%85%A8%E9%83%A8&limit=35&offset=");
			playlist.setType(PageType.playlists);
	    	crawlerThread.fetchHtml(playlist);

	    	List<WebPage> list = crawlerThread.parsePlaylists(playlist);
	    	for (WebPage webPage : list) {
				CrawlerService.savePage(webPage);
			}
	    	System.out.println("-------");
	    	System.out.println(list.get(1).getUrl());*/
		
		
		CrawlerService.init();
		//CrawlerService.crawl("open");
		System.out.println("---");
	}
}
