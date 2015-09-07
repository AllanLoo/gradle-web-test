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
     * 消息提示
     * @param level 1-安全提示：操作成功 2-警告提示：友情提示，比如输入不合法 3-错误提示:当程序出现错误时选这个选项
     * @param msg
     * @param callback
     */
    CommonUtils.prototype.tip = function(level,msg,callback){
        alert(msg);
    };

    /**
     * 渲染表格html元素
     * @param gridContainerTarget
     * @param gridTableTarget
     * @param gridPagerTarget
     */
    function renderGridHtml(gridContainerTarget,gridTableTarget,gridPagerTarget){
        var $gridTable = $("<table>",{id:this.removeSelectorSign(gridTableTarget)});
        $(gridContainerTarget).append($gridTable);
        if(gridPagerTarget){
           var $gridPager = $("<div>",{id:this.removeSelectorSign(gridPagerTarget)});
            $(gridContainerTarget).append($gridPager);
        }
    }

    /**
     * 检测grid配置参数
     * @param setting
     * @returns {boolean}
     */
    function cheekGridOpt(setting){
        if($(setting.gridContainerTarget).length > 1){
            this.tip(3,"存在多个选择器为"+setting.gridContainerTarget+"的元素");
            return false;
        }
        if($(setting.gridContainerTarget).length == 0){
            return false;
        }
        return true;
    }
    /**
     * 去除选择器的标识 如#grid变成gird,.grid变成grid
     * @param selector
     */
    CommonUtils.prototype.removeSelectorSign = function(selector){
        if(selector.charAt(0)=="."||selector.charAt(0)=="#"){
            var res = selector.substring(1);
            return res;
        }
        return selector;
    };

    /**
     * 创建Grid
     * @param opt
     * @returns {*}
     */
    CommonUtils.prototype.createGridWithPager = function(opt){
       var setting = {
           gridContainerTarget:"#dataGrid",
           gridTableTarget:"#gridTable",
           gridPagerTarget:"#gridPager",
           method:"GET",
           datatype:"json",
           height:$(window).height() - 175,
           rownumbers:true,
           sortable:true,
           sortorder:"desc",
           sortname:"id",
           param:{},
           pageSize:15,
           delay:true//延迟加载
       },msg="";
        $.extend(setting,opt);
        //检查配置条件
        if(!cheekGridOpt.call(this,setting)){
            return;
        }
        //创建grid的html元素
        renderGridHtml.call(this,setting.gridContainerTarget,setting.gridTableTarget,setting.gridPagerTarget);
       if(setting.delay){
           msg = "请点击查询按钮进行数据检索";
           setting.datatype = "local";
       }

       var gridHandler = $(setting.gridContainerTarget).find(setting.gridTableTarget).jqGrid({
            url:setting.url,
            mtype:setting.method,
            datatype: setting.datatype,
            height: setting.height,
            autowidth: true,
            colModel:setting.colModel,
            pager: $(setting.gridContainerTarget).find(setting.gridPagerTarget),
            postData:setting.param,
            rowNum: setting.pageSize,
            sortname: setting.sortname,
            sortorder: setting.sortorder,
            rownumbers: setting.rownumbers,
            shrinkToFit: false,
           rowList:[15,20,50,100],
           delay:true,
           jsonReader : {
               root:"datas",
               page: "pageNo",
               rows:"pageSize",
               total: "totalPages",
               records: "count"
           },
           loadError:function(xhr,status,error){
              alert("出错了");
           },
           loadComplete:function(xhr){

           }
      });
        var grid = {
            handler:gridHandler,
            loadData:function(param){
                if(setting.delay){
                    gridHandler.setGridParam({datatype:'json',delay:false});//将延迟关闭，该状态控制了查询结果的提示消息，这里状态需要改变一下
                }
                if(param){
                    gridHandler.setGridParam({postData:param});
                }
            }
        }
        return grid;
    };

    /**
     * 通用ajax
     * @param opt
     */
    CommonUtils.prototype.ajax = function(opt){
        //弹出数据加载提示框
        var setting = {
          url:'',
          type:"post",
          success:null,
          error:null,
          async:true,
          data:null,
          dataType:'json'
        };
        $.extend(setting,opt);
        $.ajax({
            url:setting.url,
            type:setting.type,
            async:setting.async,
            data:setting.data,
            dataType:setting.dataType,
            success:setting.success,
            error:setting.error
        });
    };

    var cu = new CommonUtils();
    $.extend({commonUtils:cu});
})(jQuery);