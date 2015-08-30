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
  <script src="${ctxStatic}/js/layout.js"></script>
</head>
<body style="padding-top:41px">
<div class="navbar navbar-master navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
            <span class="navbar-brand navbar-brand-master" href="javascript:void(0)">
               Y的三次方企业管理系统
            </span>
      <!--下面这一段比较神奇，用于媒体查询时自适应响应，当浏览器缩小时，可看到效果-->
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
              data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
        <li><a  href="#" title="系统主页"><i class="glyphicon glyphicon glyphicon-home" style="color:#fff"></i></a></li>
        <li><a  href="#" title="修改密码"><i class="glyphicon glyphicon-lock" style="color:#fff"></i></a></li>
        <li ><a href="${ctx}/logout" title="安全退出"><i class="glyphicon glyphicon-off" style="color:#fff"></i></a></li>
      </ul>
    </div>
  </div>
</div>
<div>
  <!-- tab标签位置-->
  <div id="tabArea">
    <div class="timetip">
      <span id="datetime">2014年09月06日 20:44:02</span>
    </div>
    <!--tab左移按钮-->
    <div id="menu-ctrl-left"><a href="javascript:void(0)"><i class="glyphicon glyphicon-chevron-left"></i></a></div>
    <!--tab项-->
    <div class="tabs" style="float:left;padding-top:4px;padding-left:5px;">
      <ul id="tabs-container">
        <li class="tab-active"><a>系统主页</a></li>
        <li><a>部门管理</a><a class="tab-close"><i class="glyphicon glyphicon-remove" ></i></a></li>
        <li><a>系统管理</a><a class="tab-close"><i class="glyphicon glyphicon-remove" ></i></a></li>
        <li><a>系统管理</a><a class="tab-close"><i class="glyphicon glyphicon-remove" ></i></a></li>
        <li><a>系统管理</a><a class="tab-close"><i class="glyphicon glyphicon-remove" ></i></a></li>
        <li><a>系统管理</a><a class="tab-close"><i class="glyphicon glyphicon-remove" ></i></a></li>
        <li><a>系统管理</a><a class="tab-close"><i class="glyphicon glyphicon-remove" ></i></a></li>
        <li><a>系统管理</a><a class="tab-close"><i class="glyphicon glyphicon-remove" ></i></a></li>
        <li><a>系统管理</a><a class="tab-close"><i class="glyphicon glyphicon-remove" ></i></a></li>
        <li><a>系统管理</a><a class="tab-close"><i class="glyphicon glyphicon-remove" ></i></a></li>
        <li><a>系统管理</a><a class="tab-close"><i class="glyphicon glyphicon-remove" ></i></a></li>
        <li><a>系统管理</a><a class="tab-close"><i class="glyphicon glyphicon-remove" ></i></a></li>
        <li><a>系统管理</a><a class="tab-close"><i class="glyphicon glyphicon-remove" ></i></a></li>
      </ul>
    </div>
    <!--tab右移按钮-->
    <div id="menu-ctrl-right"><a href="javascript:void(0)"><i class="glyphicon glyphicon-chevron-right"></i></a></div>
  </div>
  <div>
    <!--主体区域-->
    <div id="mainArea">
      <!--导航菜单区域-->
      <div class="navigation">
        <div class="navigation-title">
          <i class="glyphicon glyphicon-th-list" style="color:#337ab7;font-size:13px;margin-right:5px"></i>主功能菜单导航
        </div>
        <div id="navigation-sidebar" >
          <ul id="navigation-menu">
            <li>
              <div class="navigation-menu-item"><span>系统管理</span><i class="menu-item-ico"></i></div>
              <div></div>
            </li>
            <li>
              <div class="navigation-menu-item"><span>客户管理</span><i class="menu-item-ico"></i></div>
              <div></div>
            </li>
            <li>
              <div class="navigation-menu-item"><span>资料管理</span><i class="menu-item-ico" style="background-position-y:97%"></i></div>
              <div style="height:95px;background:#fff">sdf</div>
            </li>
          </ul>
        </div>
      </div>
      <div id="menu-ctrl-bar"></div>
      <!--内容区域-->
      <div id="contentArea">
        <iframe style="display: block;" id="tabs_iframe_0302" name="tabs_iframe_0302" src="user-mgr.html" frameborder="0" height="100%" width="100%"></iframe>
      </div>
    </div>
    <!--底部-->
    <div class="navbar footer-master navbar-fixed-bottom">
      <div class="container-fluid">
        <div class="footer-bar" style="width:42%;text-align:left;">
          技术支持：南京自由开发者AllanLoo
          <span class="south-separator"></span>
          内侧版 V1.0.1
          <span class="south-separator"></span>
        </div>
        <div class="footer-bar" style="width:16%">
          Copyright © 2015-2016 By AllanLoo
        </div>
        <div class="footer-bar" style="width:42%;text-align:right">
          <span class="south-separator"></span>
          角色名称：管理员
          <span class="south-separator"></span>
          操作员：管理员
        </div>

      </div>
    </div>
    <!--加载进度-->
    <div id="loading-bg"> </div>
    <div id="loading">
      <img src="../images/loading.gif" style="vertical-align: middle;"/>
      <span>正在拼了命为您加载…</span>
    </div>
</body>
</html>
