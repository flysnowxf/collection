<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<title>PMG评级币数量查询-枣红、背绿、车工和碳黑等各版评级分数统计</title>
<meta name="keywords" content="PMG,评级,评级币,评级钞,人民币,纸币,纪念钞,行情,冠号,豹子号,尾8,枣红,背绿,车工,绿3,大黑拾,数量查询" />
<meta name="description" content="网站提供对新中国第一套、第二套人民币、第三套人民币、第四套人民币和纪念钞的PMG各评级分数的数量查询、统计报告和行情报价，以及对冠号的区分归类和真伪鉴别。PMG评级纸币数量查询，就在PMG666.com。" />
<%@ include file="/common/header.jsp" %>
<style>
.body {margin-top: 30px }
.query {
	margin-top: 30px;
	margin-left: 30px;
}
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
			<div class="col-md-12 text-center"><h1>PMG评级币数量查询 <small>PMG666.com</small></h1></div>
			<div class="col-md-12 text-center">数据不含补号和样票，更新时间：${updateDate }</div>
		</div>
	</div>
</div>
<div class="query">
	<div class="container">
		<div class="row">
			<div class="col-md-12 text-center">
				<label style="margin-left: 10px">第三版人民币：</label>
				<select class="form-control" style="width: 80px" id="noteId">
					<option value="1">壹角</option>
					<option value="3">伍角</option>
					<option value="4">壹圆</option>
					<option value="5">贰圆</option>
					<option value="6">伍圆</option>
					<option value="7">拾圆</option>
				</select>
				<label style="margin-left: 10px">冠号：</label>
				<input type="text" class="form-control" id="blockA" placeholder="1">
				<input type="text" class="form-control" id="blockB" placeholder="X">
				<input type="text" class="form-control" id="blockC" placeholder="VIII">
				<input class="btn btn-primary btn-lg" type="button" value="查询品种" id="queryBlock" style="margin-left: 10px">
				<label id="blockResult" style="margin-left: 10px"></label>
			</div>
		</div>
	</div>
</div>
<div class="body">
	<div class="container">
	<div class="row">
	<div class="col-md-12" style="margin-bottom: 10px;"><a href="<%=path %>/yiban" target="_blank"><code>查询第一版</code></a></div>
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
			<th>${pmg.name }
			<c:if test="${fn:length(pmg.blockList) > 0 }">
			<button type="button" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#${pmg.id }Modal">?</button>
			<!-- Modal -->
			<div class="modal fade" id="${pmg.id }Modal" tabindex="-1" role="dialog" aria-labelledby="${pmg.id }ModalLabel">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h3 class="modal-title" id="${pmg.id }ModalLabel">${pmg.name }冠号</h3>
						</div>
						<div class="modal-body">
							<c:forEach var="block" items="${pmg.blockList }">
								<h3>${block.name }</h3>
								<c:forEach var="group" items="${block.blockGroupList }">
									<h4>${group.name }</h4>
									<p>${group.value }</p>
								</c:forEach>
								<c:if test="${!empty block.remarkNum }">
									<h4>补号</h4>
									<p>${block.remarkNum }</p>
								</c:if>
								<hr>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
			</c:if>
			</th>
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
			<p>3.感谢现代钱币网的 <code>薛总</code> 对冠号的总结奉献</p>
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

<script>
$(function() {
	$("#queryBlock").click(function() {
		var noteId = $("#noteId").val();
		var blockA = $("#blockA").val();
		var blockB = $("#blockB").val();
		var blockC = $("#blockC").val();
		
		$.ajax({
			url: "<%=path %>/utils/queryBlock",
			data: {noteId: noteId, blockA: blockA, blockB: blockB, blockC: blockC },
			success: function(data) {
				var text = data;
				if (data == "") {
					text = "无查询结果！请确认输入的冠号是否有误！";
				}
				$("#blockResult").html(text);
			}
		});
	});
});
</script>
<%@ include file="/common/stat.jsp" %>
</body>
</html>