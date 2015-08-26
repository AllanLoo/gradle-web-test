$(function(){
	frameResize();
});
function frameResize() {
    resizeForFrame();
    
    function resizeForFrame() {
        var winHeight = $(window).height();
       
        $("#mainArea").height(winHeight - 98);
        $("#navigation-sidebar").height(winHeight - 118);

        var winWidth = $(window).width();
        $("#contentArea").width(winWidth-209);
    }
}