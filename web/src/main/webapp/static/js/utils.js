/**
 * 常用工具类
 *  @author AllanLoo
 *
 */
;(function($){
    function CommonUtils(){
    }

    /**
     * 判断值是否为空
     * @modifier public
     * @param v 需要判断的值
     * @result true-空
     */
    CommonUtils.prototype.isEmpty = function(v){
        if(v==undefined || v=='undefined' || v==null || v=='null' || v.length==0){
            return true;
        }
        return false;
    };

    /**
     * 创建Grid
     * @param opt
     * @returns {*}
     */
    CommonUtils.prototype.createGridWithPager = function(opt){
       var setting = {
         gridId:"gridTable",
         gridPagerId:"gridPager",
           method:"GET",
           height:$(window).height() - 168,
           rownumbers:true,
           sortable:true,
           sortorder:"asc",
           sortname:"id",
           pageSize:15
       };
        $.extend(setting,opt);

       var grid = $("#"+setting.gridId).jqGrid({
            url:setting.url,
            mtype:setting.method,
            datatype: "json",
            height: setting.height,
            autowidth: true,
            colModel:setting.colModel,
            pager: "#"+setting.gridPagerId,
            data:rows,
            rowNum: setting.pageSize,
            sortname: setting.sortname,
            sortorder: setting.sortorder,
            rownumbers: setting.rownumbers,
            shrinkToFit: false,
           rowList:[15,20,50,100],
           jsonReader : {
               root:"datas",
               page: "pageNo",
               rows:"pageSize",
               total: "totalPages",
               records: "count"
           }
       });
        return grid;
    };

    var cu = new CommonUtils();
    $.extend({commonUtils:cu});
})(jQuery);