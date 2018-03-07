<%@ page language="java"  pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>歌曲列表</title>
 <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css"></link>
  <link rel="stylesheet" href="/static/css/footer.css"></link>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <a class="navbar-brand">网易云音乐评论过万的歌曲(共${total}首歌曲)</a>
    </div>
  </div>
</nav>

 <div class="container" style="margin-top:50px">
   <div class="row">
     <div class="col-sm-8">
      <div class="blog-post">
        <span class="col-sm-8"></span>
      </div>
      
      <table class="table"> 
        <thead>
            <tr>
                <th>歌曲</th>
                <th>评论数</th>
            </tr>
            <tr>
<%--                 <th><a href="${pageContext.request.contextPath }/Index?action=switchCrawler&switch=open">开启爬行</th>
 --%>            </tr>
        </thead>
        <c:forEach items="${ requestScope.list}" var="Song">
  		<tbody class="div" onmousemove="divin(this);" onmouseout="divout(this);">
  			<tr>
  				<td>
  					<a href="${ Song.url}">
  						${ Song.title}
  					</a>
  				</td>
  				<td><font style="color:red;">评论数${ Song.commentCount}</font></td>
  			</tr>
  		</tbody>
		</c:forEach>
       <tbody>
      	 <tr>
      	 	<td><a href="${pageContext.request.contextPath }/Index?action=showMusicList&pre=${ pageNow-1}">上一页</a>&nbsp;</td>
			<td><a href="${pageContext.request.contextPath }/Index?action=showMusicList&next=${ pageNow+1}" >下一页</a>&nbsp;</td>
		</tr>
      </tbody>
    </div>
   </div>
</body>
</html>