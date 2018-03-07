package org.wuxy.crawler.dao;

import java.util.List;

import org.wuxy.crawler.model.Song;
import org.wuxy.crawler.model.WebPage;

public interface CrawlerDao {
	//返回未爬取页面
	public WebPage getUnCrawlPage();
	//保存爬去到的页面信息
	public void savePage(WebPage webPage);
	//更新页面信息	
	public void updatePage(WebPage webPage);
	//保存歌曲信息	
	public void saveSong(Song song);
	//查询歌曲信息列表
	public List<Song> querySongsList(Integer pageNow, Integer pageNum);
	//查询歌曲总数
	public Long getSongsCount();
}
