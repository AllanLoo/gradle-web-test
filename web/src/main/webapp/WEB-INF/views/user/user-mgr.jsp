<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/8/4
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <meta name="decorator" content="default"/>
    <title>YYY的三次方-系统主页</title>
  <!--jqGrid样式-->
  <link href="${ctxStatic}/js/jqGrid/css/jqgrid.css" rel="stylesheet">
  <script src="${ctxStatic}/js/jqGrid/js/jquery-ui-custom.min.js"></script>
  <script src="${ctxStatic}/js/jqGrid/js/jqGrid.js"></script>
  <script src="${ctxStatic}/js/modules/sys/user.js"></script>
  <script>
     $(function(){
       $.user.mgrModule.init();
     });

  </script>
</head>
<body>
<div class="container-fluid" style="padding:0 1px;">
  <!--蚂蚁线-->
  <div class="row navigation-title">
    <div class="col-md-5" style="padding-left:3px;">
      <i class="glyphicon glyphicon-map-marker" style="color:#E93131"></i>
      位置：系统设置 > 用户管理 > 数据列表
    </div>
  </div>
  <!--工具条-->
  <div class="tool-bar">
    <a class="tool-bar-btn" href="javascript:void(0);" onclick="parent.refresh()"><span>
      <b style="background: url('${ctxStatic}/images/refresh.png') 50% 4px no-repeat;">刷新</b>
    </span></a>
    <span class="tools_separator"></span>
    <a class="tool-bar-btn"><span><b style="background: url('${ctxStatic}/images/btn_add.png') 50% 4px no-repeat;">新增</b></span></a>
    <a class="tool-bar-btn"><span><b style="background: url('${ctxStatic}/images/btn_edit.png') 50% 4px no-repeat;">修改</b></span></a>
    <a class="tool-bar-btn"><span><b style="background: url('${ctxStatic}/images/btn_delete.png') 50% 4px no-repeat;">删除</b></span></a>
  </div>
  <!--分隔线
  <div class="parting-line"><span>查询条件</span></div>-->
  <!--查询条件表单-->
  <div class="search-area">
    <form class="form-inline" id="searchForm">
      <div class="search-form-row">
        <div class="form-group">
          <label for="exampleInputName2">用户名</label>
          <input type="text" class="form-control" id="exampleInputName2" placeholder="Jane Doe">
        </div>
        <div class="form-group">
          <label for="exampleInputName2">姓名</label>
          <input type="text" class="form-control" id="exampleInputName1" placeholder="Jane Doe">
        </div>
        <button class="btn btn-default btn-search" id="btnSearch" type="button">查询</button>
      </div>
    </form>
  </div>
  <!--数据列表-->
  <div id="dataGrid" class="data-grid">
  </div>
</div>

</body>
</html>
