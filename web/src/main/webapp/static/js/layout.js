/**
 * 首页初始化
 */
function initIndex(){
    frameResize();
    writeDateInfo();
    tabsContainerResize();
}
/**
 * 框架长宽重计算
 */
function frameResize() {
    resizeForFrame();

    function resizeForFrame() {
        var winHeight = $(window).height();
        var topHeight = $("div.navbar-fixed-top:eq(0)").outerHeight()+$("#tabArea").outerHeight();
        var footerHeight = $("div.navbar-fixed-bottom:eq(0)").outerHeight(true);
        var middleHeight = winHeight - topHeight - footerHeight;
        $("#mainArea").height(middleHeight);
        $("#navigation-sidebar").height(middleHeight-$(".navigation-title").outerHeight());
        $("#menu-ctrl-bar").height(middleHeight);
        $("#menu-ctrl-bar a").css("margin-top",middleHeight*0.5-33);

        var winWidth = $(window).width();
        var leftWidth = $(".navigation").outerWidth()+$("#menu-ctrl-bar").outerWidth();
        $("#contentArea").width(winWidth-leftWidth-2);
    }
}
/**
 * tab容器长度重计算
 */
function tabsContainerResize(){
    var w=$("#tabArea").outerWidth()-$("#datetime").parent(".timetip").outerWidth(true)-
        $("#menu-ctrl-left").outerWidth(true)-$("#menu-ctrl-right").outerWidth(true);
    $("#tabs-container").parent(".tabs").width(w-5);
}
//当前日期
function writeDateInfo() {
    var now = new Date();
    var year = now.getFullYear(); //getFullYear getYear
    var month = now.getMonth();
    var date = now.getDate();
    var day = now.getDay();
    var hour = now.getHours();
    var minu = now.getMinutes();
    var sec = now.getSeconds();
    var week;
    month = month + 1;
    if (month < 10) month = "0" + month;
    if (date < 10) date = "0" + date;
    if (hour < 10) hour = "0" + hour;
    if (minu < 10) minu = "0" + minu;
    if (sec < 10) sec = "0" + sec;
    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    week = arr_week[day];
    var time = "";
    time = year + "年" + month + "月" + date + "日" + " " + hour + ":" + minu + ":" + sec;

    $("#datetime").text(time);
    var timer = setTimeout("writeDateInfo()", 1000);
}

/**
 * 隐藏或显示菜单栏
 */
function toggleSidebar(){
    var winWidth = $(window).width();
    if($(".navigation").is(":hidden")){
        var leftWidth = $(".navigation").outerWidth()+$("#menu-ctrl-bar").outerWidth();
        $("#contentArea").width(winWidth-leftWidth-2);
        $(".navigation").show();
        $("#menu-ctrl-bar").removeClass("ico-right").addClass("ico-left");
    }else{
        $(".navigation").hide();
        var leftWidth = $("#menu-ctrl-bar").outerWidth();
        $("#contentArea").width(winWidth-leftWidth-2);
        $("#menu-ctrl-bar").removeClass("ico-left").addClass("ico-right");
    }

}