<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/8/4
  Time: 15:14
  系统登录页。
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="default"/>
   <title>YYY的三次方-登录</title>
    <script>
        function onBeforeSubmit(){
            if($("#username").val().length==0){
                $("#msg").html("请填写用户名");
                return false;
            }
            if($("#password").val().length==0){
                $("#msg").html("请填写密码");
                return false;
            }
            if($("#validateCodeArea").css("visibility")=="visible"&&$("#validateCode").val().length==0){
                $("#msg").html("请填写验证码");
                return false;
            }
            return true;
        }
        function reloadValidateCode(img){
            $(img).attr("src","${ctx}/static/validateCode?_="+(new Date()));
        }
    </script>
</head>
<body style="padding-top:70px">
   <div class="navbar navbar-login navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand navbar-brand-login" href="javascript:void(0)">
                Y的三次方企业管理系统
            </a>
        </div>
      </div>
   </div>
    <!--主区域-->
    <div class="container-fluid login-container" style="height:645px">
        <div id="login-wraper">
            <form class="form-horizontal" method="post" action="${ctx}/login" onsubmit="return onBeforeSubmit()">
                <legend>
                    <span style="color:#08c;">系统登录</span>
                </legend>
                <div class="form-group">
                    <div class="col-md-8 col-md-offset-2">
                        <input type="text" class="form-control" name="username" id="username"
                               placeholder="用户名" maxlength="20" value="${username}"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-8 col-md-offset-2">
                        <input type="password" class="form-control" name="password" id="password" placeholder="密码" maxlength="20"/>
                    </div>
                </div>
                <div class="form-group" style="visibility:${needValidateCode==true?'visible':'hidden'}" id="validateCodeArea">
                    <div class="col-md-5 col-md-offset-2">
                        <input type="text" class="form-control" id="validateCode" name="validateCode" placeholder="验证码" maxlength="10"/>
                    </div>
                    <div class="col-md-3">
                        <img style="cursor:pointer" src="${ctx}/static/validateCode"
                             alt="验证码" title="看不清楚，单击重新获取" onclick="reloadValidateCode(this);"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-1 col-md-5">
                        <div class="checkbox">
                            <label style="color:#08c">
                                <input type="checkbox" name="rememberMe">记住我
                            </label>
                        </div>
                    </div>
                    <div class="col-md-5">
                        <button type="submit" class="btn btn-primary">登&nbsp;&nbsp;录</button>
                    </div>
                </div>
                <div id="msg" style="color: #E0F30F;">${message}</div>
            </form>
        </div>
    </div>

    <div class="navbar navbar-default navbar-fixed-bottom">
        <div class="container-fluid">
            <div class="navbar-header">
                <p class="footer-login"> Copyright © 2015-2016, 南京自由开发者AllanLoo, All Rights Reserved, 当前版本：v1.0.1</p>
            </div>
        </div>
    </div>
</body>
</html>
