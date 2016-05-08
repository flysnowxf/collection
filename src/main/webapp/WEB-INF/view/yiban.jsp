<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<title>第一版人民币 - PMG评级币数量查询</title>
<meta name="keywords" content="PMG,评级,评级币,评级钞,人民币,纸币,工农,工厂,织布,水牛,骆驼队,新华门,蒙古包,数量查询" />
<meta name="description" content="提供对新中国第一套人民币的PMG各评级分数的数量查询、统计报告和行情报价。PMG评级纸币数量查询，就在PMG666.com。" />
<%@ include file="/common/header.jsp" %>
<style>
.body {margin-top: 30px }
.query {
	margin-top: 30px;
	margin-left: 30px;
}
.body .container {width: 95% }
.footer {margin-top: 30px; margin-bottom: 30px }
.name-th {width: 8% }
.catalog-th {width: 5% }
.total-th {width: 3% }
.pmg-th {width: 3% }
.c-hidden {
	display: none;
}
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
.form-control {
	display: inline;
	width: 60px;
}
</style>
</head>
<body>
<div class="header">
	<div class="container">
		<div class="row">
			<div class="col-md-12 text-center"><h1>第一版PMG数量查询 <small>PMG666.com</small></h1></div>
			<div class="col-md-12 text-center">数据不含补号和样票，更新时间：${updateDate }</div>
		</div>
	</div>
</div>
<div class="query">
	<div class="container">
		<div class="row">
			<div class="col-md-12 text-center">
				<input class="btn btn-primary" type="button" value="显示低分" id="more">
			</div>
		</div>
	</div>
</div>
<div class="body">
	<div class="container">
	<div class="row">
	<div class="col-md-12" style="margin-bottom: 10px;"><a href="<%=path %>/"><code>返回首页查询其他版式</code></a></div>
	<c:forEach var="pmgItem" items="${pmgListMap }">
		<div class="col-md-12">
		<table class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th class="name-th">${pmgItem.key }</th>
			<th class="catalog-th">面值</th>
			<th class="catalog-th">编号</th>
			<th class="total-th">总数</th>
			<c:forEach var="title" items="${titleList }" varStatus="status">
			<c:if test="${title < '50' }">
			<th class="pmg-th c-hidden">${title }</th>
			</c:if>
			<c:if test="${title >= '50' }">
			<th class="pmg-th">${title }</th>
			</c:if>
			</c:forEach>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="pmg" items="${pmgItem.value }">
		<tr>
			<th>${pmg.name }</th>
			<td>${pmg.value }</td>
			<td>${pmg.catalog }</td>
			<td class="info">${pmg.total }</td>
			<c:forEach var="gradeCount" items="${pmg.gradeCountList }" varStatus="status">
			<c:if test="${status.index == 20 || status.index == 26 }">
			<td class="warning">${gradeCount.count }</td>
			</c:if>
			<c:if test="${status.index >= 13 && status.index != 20 && status.index != 26 && status.index != 29 }">
			<td>${gradeCount.count }</td>
			</c:if>
			<c:if test="${status.index < 13 }">
			<td class="c-hidden">${gradeCount.count }</td>
			</c:if>
			<c:if test="${status.index == 29 }">
			<td class="danger">${gradeCount.count }</td>
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
			<p>2.感谢现代钱币网 <code>www.coin001.com</code>对PMG编号的总结奉献</p>
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

<script>
$(function() {
	var flag = 0;
	$("#more").click(function() {
		if (flag == 0) {
			flag = 1;
			$(".c-hidden").show();
			$(this).val("隐藏低分");
		}
		else {
			flag = 0;
			$(".c-hidden").hide();
			$(this).val("显示低分");
		}
	});
});
</script>
<%@ include file="/common/stat.jsp" %>
</body>
</html>