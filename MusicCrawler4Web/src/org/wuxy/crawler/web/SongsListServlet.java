package org.wuxy.crawler.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wuxy.crawler.model.Song;
import org.wuxy.crawler.service.CrawlerService;
import org.wuxy.crawler.util.BaseServlet;




public class SongsListServlet extends BaseServlet{

	/**
	 * 歌曲展示Servlet
	 */
	private static final long serialVersionUID = 1L;
	private CrawlerService crawlerService = new CrawlerService();
	
	 public void showMusicList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		 int pageNum = 80;
		 int pageNow = 0;			
		 String pre = req.getParameter("pre");
		 String next = req.getParameter("next");
		 Long total = crawlerService.getSongsCount();
		 if (pre==null||next==null) {
			 pageNow = 1;
		 }
		 if (pre!=null && Integer.parseInt(pre)>0) {
			pageNow = Integer.parseInt(pre);
		}
		 if (next!=null && Integer.parseInt(next)<=(1+total/pageNum)) {
			pageNow = Integer.parseInt(next);
		}
		 List<Song> list = crawlerService.querySongsList(pageNow, pageNum);
		 req.setAttribute("list",list);
		 req.setAttribute("total", total);
		 req.setAttribute("pageNow", pageNow);
		 req.getRequestDispatcher("/jsp/songs.jsp").forward(req, resp);
	 }

	 
	 public void switchCrawler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, InterruptedException {

		 String str = req.getParameter("switch");
		 crawlerService.crawl(str);
	 }
}
