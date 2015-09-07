/**
 * Created by AllanLoo on 2015/9/7.
 * 系统用户操作js
 */
(function($){
    /**
     * http根路径
     * @type {string}
     */
    var G_ACCESS_ROOT_PATH = ctx + "/user";

    /********************************************************************
     *
     *                         用户管理模块
     *                         数据列表显示
     *
     ********************************************************************/
    function UserMgrModule(){
        var grid,userMgr = this;
        /**
         * 初始化用户数据列表
         */
       this.renderDataGrid = function(){
           grid = $.commonUtils.createGridWithPager({
                  url:G_ACCESS_ROOT_PATH+"/listByPage",
                  param:$("#searchForm").serialize(),
                  colModel:[
                      {name:"姓名",index:"realName",sortable:false,with:100},
                      {name:"登录名",index:"userName",sortable:false,with:100},
                      {name:"邮箱",index:"userEmail",sortable:false,with:100},
                      {name:"手机",index:"phone",sortable:false,with:100},
                      {name:"在职状态",index:"userStatus",with:80,formatter:function(cellvalue, options, rowObject){
                          if(cellvalue == 0){
                              return "离职";
                          }
                          if(cellvalue == 1){
                              return "在职";
                          }
                      }},
                      {name:"账号状态",index:"accountStatus",with:80,formatter:function(cellvalue, options, rowObject){
                          if(cellvalue == 0){
                              return "离线";
                          }
                          if(cellvalue == 1){
                              return "在线";
                          }
                          if(cellvalue == 2){
                              return "受限";
                          }
                      }}
                  ]
           });
       };

        /**
         * 数据检索
         */
        this.doSearch = function(){
            grid.loadData($("#searchForm").serialize());
        };

        /**
         * 初始化
         */
        this.init = function(){
            userMgr.renderDataGrid();
            $("#btnSearch").click(function(){
                userMgr.doSearch();
            });
            return userMgr;
        };
    }

    /******************************************************************
     *
     *                    用户新增操作模块
     *
     ******************************************************************/
    function UserAddModule(){

    }

    /******************************************************************
     *
     *                   用户修改操作模块
     *
     ******************************************************************/
    function UserModifyModule(){

    }

    $.extend({
        user:{
            mgrModule:new UserMgrModule(),
            addModule:new UserAddModule(),
            modifyModule:new UserMgrModule()
        }
    });
})(jQuery);
