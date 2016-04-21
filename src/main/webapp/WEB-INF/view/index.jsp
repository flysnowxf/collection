<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<title>PMG评级币数量查询</title>
<meta name="keywords" content="PMG,评级,评级币,评级钞,人民币,纸币,纪念钞,行情,冠号,豹子号,尾8,枣红,背绿,车工,大黑拾" />
<meta name="description" content="网站提供对新中国第二套人民币、第三套人民币和纪念钞的PMG各评级分数的数量查询和行情报价，以及对冠号的区分归类和真伪鉴别。" />
<%@ include file="/common/header.jsp" %>
<style>
.body {margin-top: 30px }
.body .container {width: 95% }
.footer {margin-top: 30px; margin-bottom: 30px }
.name-th {width: 10% }
.catalog-th {width: 8% }
.pmg-th {width: 6% }
.copyright {
    background: #111;
    font-size: 13px;
    text-align: center;
    color: #555;
    padding-top: 14px;
    padding-bottom: 20px;
    border-top: 1px solid #303030;
}
.bs-callout {
    padding: 20px;
    margin: 20px 0;
    border: 1px solid #eee;
    border-left-width: 5px;
    border-radius: 3px;
}
.bs-callout-info {
    border-left-color: #1b809e;
}
</style>
</head>
<body>
<div class="header">
	<div class="container">
		<div class="row">
			<div class="col-md-12 text-center"><h1>PMG评级币数量查询 <small>PMG666.com</small></h1></div>
			<div class="col-md-12 text-center">更新时间：${updateDate }</div>
		</div>
	</div>
</div>
<div class="body">
	<div class="container">
	<div class="row">
	<c:forEach var="pmgItem" items="${pmgListMap }">
		<div class="col-md-12">
		<table class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th class="name-th">${pmgItem.key }</th>
			<th class="catalog-th">编号</th>
			<th class="pmg-th">总数</th>
			<c:forEach var="title" items="${titleList }" varStatus="status">
			<th class="pmg-th">${title }</th>
			<c:if test="${status.index == 3 || status.index == 4 }">
			<th class="pmg-th">参考价</th>
			</c:if>
			</c:forEach>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="pmg" items="${pmgItem.value }">
		<tr>
			<th>${pmg.name }</th>
			<td>${pmg.catalog }</td>
			<td class="info">${pmg.total }</td>
			<c:forEach var="gradeCount" items="${pmg.gradeCountList }" varStatus="status">
			<c:if test="${status.index == 3 || status.index == 4 }">
			<td class="warning">${gradeCount.count }</td>
			<td class="warning"><c:if test="${gradeCount.price > 0 }">${gradeCount.price } 元</c:if></td>
			</c:if>
			<c:if test="${status.index != 3 && status.index != 4 && status.index != 7 }">
			<td>${gradeCount.count }</td>
			</c:if>
			<c:if test="${status.index == 7 }">
			<td class="danger">${gradeCount.count }</td>
			</c:if>
			</c:forEach>
			<c:forEach var="keyValue" items="${pmg.keyValueList }" varStatus="status">
			<c:if test="${keyValue.key == '高分难度' }">
			<td><c:forEach var="i" begin="1" end="${keyValue.value }" step="1"><span class="glyphicon glyphicon-star"></span></c:forEach></td>
			</c:if>
			<c:if test="${keyValue.key != '高分难度' }">
			<td>${keyValue.value }</td>
			</c:if>
			</c:forEach>
		</tr>
		</c:forEach>
		</tbody>
		</table>
		</div>
	</c:forEach>
	</div>
	</div>
</div>
<div class="info">
	<div class="container">
		<div class="row">
			<div class="bs-callout bs-callout-info" id="callout-type-dl-truncate">
			<h3>感谢</h3>
			<p>1.PMG评级币的数据取自于PMG官网 <code>www.pmgnotes.com</code></p>
			<p>2.参考价取自于现代钱币网 <code>www.coin001.com</code></p>
			<h3>友情链接</h3>
			<a href="http://www.bootcss.com" target="_blank"><code>Bootstrap</code></a>
			<a href="http://glyphicons.com" target="_blank"><code>Glyphicons</code></a>
			</div>
		</div>
	</div>
</div>
<div class="copyright">
	<div class="container">
		<div class="row">
			<div class="col-md-12 text-center">2016 Copyright © PMG666.com</div>
		</div>
	</div>
</div>

<%@ include file="/common/stat.jsp" %>
</body>
</html>