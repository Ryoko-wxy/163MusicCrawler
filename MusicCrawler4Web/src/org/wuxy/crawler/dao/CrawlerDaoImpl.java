package org.wuxy.crawler.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.wuxy.crawler.model.Song;
import org.wuxy.crawler.model.WebPage;
import org.wuxy.crawler.model.WebPage.PageType;
import org.wuxy.crawler.model.WebPage.Status;
import org.wuxy.crawler.util.JdbcUtil;

public class CrawlerDaoImpl implements CrawlerDao{
	//返回未爬取页面
	public synchronized WebPage getUnCrawlPage(){
		
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		try {
			WebPage unCrawlPage = (WebPage)qr.query("SELECT * FROM webpage WHERE STATUS = 'uncrawl' ORDER BY RAND() LIMIT 1"
					,new MyWebPage());
			return unCrawlPage;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	//保存爬取到的页面信息
	public void savePage(WebPage webPage){
		
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		try {
			int i = qr.update("insert into webpage (url,title,type,status,html) values(?,?,?,?,?)",
					new Object[]{
							webPage.getUrl(),
							webPage.getTitle(),
							webPage.getType().toString(),
							webPage.getStatus().toString(),
							webPage.getHtml()
					});
			System.out.println("保存了"+i+"条歌单页面");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//更新页面信息
	public void updatePage(WebPage webPage){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		try {
			int i = qr.update("update webpage set status = ? where id = ?",
					new Object[]{
						webPage.getStatus().toString(),
						webPage.getId()
			});
			System.out.println("更新了"+i+"条页面信息");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//保存歌曲信息
	public void saveSong(Song song){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		try {
			int i = qr.update("insert into song (url,title,commentcount) values(?,?,?)",
					new Object[]{
						song.getUrl(),
						song.getTitle(),
						song.getCommentCount()
			});
			System.out.println("保存了"+ i +"首歌曲");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//查询歌曲信息列表 
	@Override
	public List<Song> querySongsList(Integer pageNow, Integer pageNum) {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		try {
			List<Song> songList = (List<Song>)qr.query("select * from song where commentcount > 10000 limit "+pageNum*(pageNow-1)+","+pageNum,
					new MySongList());
			return songList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	//查询歌曲总数
	public Long getSongsCount(){
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
		
		try {
			Long total = (Long) qr.query("select count(*) from song where commentcount > 10000",new ScalarHandler(1));
			return total;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	//自行封装结果集
	class MyWebPage implements ResultSetHandler{
		@Override
		public WebPage handle(ResultSet rs) throws SQLException {
			rs.next();
			WebPage webPage = new WebPage();
			webPage.setId(rs.getInt("id"));
			webPage.setUrl(rs.getString("url"));
			webPage.setTitle(rs.getString("title"));
			webPage.setType(PageType.valueOf(rs.getString("type")));
			webPage.setStatus(Status.valueOf(rs.getString("status")));
			return webPage;
		}
	}
	
	class MySongList implements ResultSetHandler{

		@Override
		public List<Song> handle(ResultSet rs) throws SQLException {
			ArrayList<Song> list = new ArrayList<>();
			while(rs.next()){
				Song song = new Song();
				song.setId(Integer.parseInt(rs.getString("id")));
				song.setUrl(rs.getString("url"));
				song.setTitle(rs.getString("title"));
				song.setCommentCount(Integer.parseInt(rs.getString("commentcount")));
				list.add(song);
			}
			return list;
		}
	}

}
