package org.wuxy.crawler.model;
public class WebPage {

	public enum PageType {
		song, playlist, playlists;
		}
	    
	public enum Status {
		crawled, uncrawl;
		}
	private Integer id;
    private String url;
    private String title;
    private PageType type;
    private Status status;
    private String html;
    
    
    public WebPage() {
		super();
	}

	public WebPage(String url, PageType type) {
        super();
        this.url = url;
        this.type = type;
        this.status = Status.uncrawl;
    }
    
    public WebPage(String url, PageType type, String title) {
        super();
        this.url = url;
        this.type = type;
        this.title = title;
        this.status = Status.uncrawl;
    }
    
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public PageType getType() {
		return type;
	}
	public void setType(PageType type) {
		this.type = type;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	@Override
	public String toString() {
		return "WebPage [url=" + url + ", title=" + title + ", type=" + type + ", status=" + status + ", html=" + html
				+ "]";
	}
    
}
