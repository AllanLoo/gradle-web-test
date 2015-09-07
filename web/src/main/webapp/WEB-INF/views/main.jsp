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
  <link href="${ctxStatic}/js/treeview/jquery.treeview.css" rel="stylesheet">
  <script src="${ctxStatic}/js/layout.js"></script>
  <script src="${ctxStatic}/js/tab.js"></script>
  <script src="${ctxStatic}/js/treeview/jquery.treeview.js"></script>
  <script src="${ctxStatic}/js/sysmenu.js"></script>
  <script>
    var tabObj;
    $(function(){
      initIndex();
      tabObj = $.tab({
        fixTabItems:[{"tabId":"000","tabText":"系统主页","iframeUrl":"${ctx}/sysIndex"}]
      });
      $.sysMenuRender({targetId:"navigation-sidebar",data:[
        {name:"系统管理",id:"2",pid:1,level:2,url:''},
        {name:"评估管理",id:"3",pid:1,level:2,url:''},
        {name:"客户管理",id:"4",pid:1,level:2,url:''},
        {name:"借贷管理",id:"5",pid:1,level:2,url:''},
        {name:"预警管理",id:"6",pid:1,level:2,url:''},
        {name:"用户管理",id:"7",pid:2,level:3,url:'${ctx}/user/mgr'},
        {name:"部门管理",id:"8",pid:2,level:3,url:''},
        {name:"岗位管理",id:"9",pid:2,level:3,url:''},
        {name:"权限管理",id:"10",pid:2,level:3,url:''},
        {name:"角色管理",id:"11",pid:2,level:3,url:''},
        {name:"车辆评估",id:"12",pid:3,level:3,url:''},
        {name:"房屋评估",id:"13",pid:3,level:3,url:''},
        {name:"客户信息录入",id:"14",pid:4,level:3,url:''},
        {name:"客户信息初审",id:"15",pid:4,level:3,url:''},
        {name:"客户信息终审",id:"16",pid:4,level:3,url:''},
        {name:"车抵押借款",id:"17",pid:5,level:3,url:''},
        {name:"房抵押借款",id:"18",pid:5,level:3,url:''},
        {name:"借款审批",id:"19",pid:5,level:3,url:''},
        {name:"还款管理",id:"20",pid:5,level:3,url:''},
        {name:"车险预警",id:"21",pid:6,level:3,url:''},
        {name:"还款预警",id:"22",pid:6,level:3,url:''},
        {name:"借贷初审",id:"23",pid:19,level:4,url:''},
        {name:"借贷终审",id:"24",pid:19,level:4,url:''}
      ]
      });
    });
    /**
     * 去首页
     */
    function openSysIndexTab(){
      tabObj.open("000","系统主页","....");
    }

    function refresh(){
      tabObj.refreshActiveTab();
    }
  </script>
</head>
<body style="padding-top:41px;overflow:hidden">
<div class="navbar navbar-master navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
            <span class="navbar-brand navbar-brand-master">
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
        <li><a  href="javascript:openSysIndexTab()" title="系统主页"><i class="glyphicon glyphicon glyphicon-home" style="color:#fff"></i></a></li>
        <li><a  href="#" title="修改密码"><i class="glyphicon glyphicon-lock" style="color:#fff"></i></a></li>
        <li ><a href="${ctx}/logout" title="安全退出"><i class="glyphicon glyphicon-off" style="color:#fff"></i></a></li>
      </ul>
    </div>
  </div>
</div>

<!-- tab标签位置-->
<div id="tabArea">
  <div class="timetip">
    <span id="datetime">2014年09月06日 20:44:02</span>
  </div>
  <!--tab左移按钮-->
  <div id="menu-ctrl-left"><a href="javascript:void(0);" id="tab-left-btn"><i class="glyphicon glyphicon-chevron-left"></i></a></div>
  <!--tab项-->
  <div class="tabs">
    <ul id="tabs-container">
      <!--等待js插入tab标签项-->

    </ul>

  </div>
  <!--tab右移按钮-->
  <div id="menu-ctrl-right"><a href="javascript:void(0)" id="tab-right-btn"><i class="glyphicon glyphicon-chevron-right"></i></a></div>
</div>

<!--主体区域-->
<div id="mainArea">
  <!--导航菜单区域-->
  <div class="navigation">
    <div class="navigation-title">
      <i class="glyphicon glyphicon-th-list" style="color:#337ab7;font-size:13px;margin-right:5px"></i>主功能菜单导航
    </div>
    <div id="navigation-sidebar" style="overflow:hidden">
      <!--等待js插入菜单项-->
    </div>
  </div>
  <!--菜单的隐藏和显示-->
  <div id="menu-ctrl-bar" class="ico-left"><a href="javascript:void(0);" onclick="toggleSidebar();"></a></div>
  <!--内容区域-->
  <div id="contentArea">
    <!--等待js插入iframe项-->
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
