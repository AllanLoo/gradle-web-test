/**
 * 系统菜单js
 * 需要TreeView插件，为了达到无限极菜单
 * @author AllanLoo
 */
;(function($){
    var _defaultSetting = {
        /**
         *上下文，用来访问服务器资源
         * @default ''
         * @example http://localhost:8080
         */
        context:'',
        /**
         * 目标元素id，菜单将在该元素中渲染
         *  @default ''
         */
        targetId:'',
        /**
         * tab实例化对象
         * @default ''
         */
        tabInstance:'',
        /**
         *  是否是嵌套式的数据源
         *  @default false
         *  @example
         *  当为true时，数据源的格式为
         *     [
         *         {name:'系统管理',ico:'',id:1,level:1,pid:-1,children:[{name:'用户管理',id:11,level:2,url:'...',pid:1}]},
         *         {name:'客户管理',ico:'',id:2,level:1,pid:-1,children:[{name:'客户信息',id:21,level:2,url:'...',pid:2}]}
         *     ]
         *   当为false时，数据源格式为
         *   [
         *     {name:'系统管理',ico:'',id:1,level:1,pid:-1},
         *     {name:'用户管理',id:11,level:2,url:'...',pid:1},
         *     {name:'客户管理',ico:'',id:2,level:1,pid:-1},
         *     {name:'客户信息',id:21,level:2,url:'...',pid:2}
         *   ]
         */
        nested:false,
        /**
         * 菜单的数据源，注意当非嵌套形式时，数据源可以是无序的
         * @default [] 数组元素为json对象
         * @example [
         *              {name:'系统管理',ico:'',id:1,level:1,pid:-1,children:[{name:'用户管理',id:11,level:2,url:'...',pid:1}]},
         *               {name:'客户管理',ico:'',id:2,level:1,pid:-1,children:[{name:'客户信息',id:21,level:2,url:'...',pid:2}]}
         *           ]
         *    或者[
         *     {name:'系统管理',ico:'',id:1,level:1,pid:-1},
         *     {name:'用户管理',id:11,level:2,url:'...',pid:1},
         *     {name:'客户管理',ico:'',id:2,level:1,pid:-1},
         *     {name:'客户信息',id:21,level:2,url:'...',pid:2}
         *   ]
         */
        data:[]
    }

    /**
     * 菜单渲染
     *  @modifier private
     */
    var _menuRender = function(){
        $("#"+_defaultSetting.targetId).html('<ul id="navigation-menu"></ul>');
        var menuContainer = $("#"+_defaultSetting.targetId).find("#navigation-menu");

        for (var i = 0,l = _defaultSetting.data.length; i < l; i++) {
            var menuItem = _defaultSetting.data[i];
            var liEle = $("<li>");
            liEle.html('<div class="navigation-menu-item" id="menu-'+menuItem.id+'"><span>'+menuItem.name+'</span><i class="menu-item-ico"></i><div>');
            //绑定单击事件
            liEle.find(".navigation-menu-item").click(function(){
                _expendSubmenu(this);
            });
            menuContainer.append(liEle);
            if(!$.commonUtils.isEmpty(menuItem.children)){
                var scDiv = $("<div>",{id:menuItem.id+"-submenu-container",class:"submenu-container"}).css("display","none");
                liEle.append(scDiv);
                _submenuRender(menuItem.children,menuItem.id,scDiv);
            }
        };
        //展开第一个菜单

        $("#menu-"+_defaultSetting.data[0].id).click();
    };


    /**
     *  递归创建子菜单
     * @modifier private
     * @param submenuData 子菜单的数据源
     * @param pid 父级的id数值
     * @param pele 父元素
     */
    var _submenuRender = function(submenuData,pid,pele){
        var container = $(pid+"-submenu");
        if(container.length == 0){
            container = $("<ul>",{id:pid+"-submenu"});
            //父元素可以先添加容器，然后容器再加元素，这个是没有关系的
            pele.append(container);
        }

        for (var i = 0,l = submenuData.length; i < l; i++) {
            var submenuItemData = submenuData[i];
            var submenuItem = $("<li>",{id:"submenu-item-"+submenuItemData.id});
            var submenuIco = $("<span>",{class:"submenu-ico"}).html("&nbsp;");
            var submenuText = $("<span>",{class:"submenu-txt",text:submenuItemData.name});
            //创建超链接
            var subMenuLink;
            subMenuLink = $("<a>",{class:"submenu-link", href:"javascript:void(0);"});
            subMenuLink.data("id",submenuItemData.id);
            subMenuLink.data("name",submenuItemData.name);
            subMenuLink.data("url",submenuItemData.url);
            //注册事件
            subMenuLink.on("click",function(){
                $("a.selected").removeClass("selected");
                $(this).addClass("selected");
                if($.commonUtils.isEmpty(submenuItemData.url)){
                    // return;
                }
                _linkRight($(this).data("id"),$(this).data("name"),_defaultSetting.context+$(this).data("url"));
            });
            if(!$.commonUtils.isEmpty(submenuItemData.children)){
                subMenuLink = $("<span>");
            }else{
                submenuIco.css({"background-position":"0px -20px"});
            }
            subMenuLink.append(submenuIco)
                .append(submenuText)
                .appendTo(submenuItem);
            //如果存在子菜单，那么创建子菜单
            if(!$.commonUtils.isEmpty(submenuItemData.children)){
                _submenuRender(submenuItemData.children,submenuItemData.id,submenuItem);
            }
            //将菜单项添加到菜单容器中去
            container.append(submenuItem);
        };
    };

    /**
     * 点击菜单时关联右边
     * @modifier private
     * @param menuId 菜单id
     * @param menuName 菜单名称
     * @param url 菜单链接
     */
    var _linkRight = function(menuId,menuName,url){
        var tabObj = _defaultSetting.tabInstance;
        if($.commonUtils.isEmpty(tabObj)){
            tabObj =  $.tab();
        }
        tabObj.open(menuId,menuName,url);
    };
    /**
     * 展开或合并子菜单
     *  @modifier private
     *  @param obj HTML元素对象
     */
    var _expendSubmenu = function(obj){
        if(!$(obj).hasClass("expended")){
            var lastExpendedMenu = $("div.expended");
            if(lastExpendedMenu.length > 0){
                _collapseSubmenu(lastExpendedMenu);
            }

            //重算高度
            var menuHeight = $("#mainArea").height() - $("#mainArea .navigation-title").outerHeight();
            var h = menuHeight-$(".navigation-menu-item").outerHeight()*_defaultSetting.data.length-1;
            $(obj).next().height(h);
            $(obj).next().slideToggle();

            //加载treeview
            var submenuId = obj.id.replace("menu-","")+"-submenu";
            if(!$("#"+submenuId).hasClass("treeview")){
                $("#"+submenuId).treeview({
                    animated: "fast",
                    collapsed: true,
                    unique: true
                });
            }


            $(obj).find(".menu-item-ico").css("background-position-y","97%");
            $(obj).addClass("expended");

        }else{
            _collapseSubmenu($(obj));
        }
    };

    /**
     * 合并子菜单
     *  @modifier private
     *  @param obj jquery元素对象
     */
    var _collapseSubmenu = function($obj){
        $obj.next().slideToggle();
        $obj.find(".menu-item-ico").css("background-position-y","0");
        $obj.removeClass("expended");
    };

    /**
     *  格式化数据源,将扁平化数据格式化成层级化
     *  排除数据库默认的根菜单，默认菜单id为1
     *  @modifier private
     *
     */
    var _formatData = function(){
        _defaultSetting.data = _getChildren(1);
    };

    /**
     * 获取子元素
     *  @modifier private
     *  @param 父元素id
     */
    var _getChildren = function(pid){
        var children = [];
        for (var i = 0,l = _defaultSetting.data.length; i < l; i++) {
            var item = _defaultSetting.data[i];
            if(item.pid == pid){
                children.push(item);
                var itemChildren = _getChildren(item.id);
                if(itemChildren.length > 0){
                    item.children = itemChildren;
                }
            }
        }
        return children;
    };

    /**
     *  组件初始化方法
     *  @modifier private
     */
    var _init = function(opt){
        if(!opt.data || opt.data.length == 0){
            alert("没有找到菜单数据源，无法渲染系统菜单");
            return;
        }
        $.extend(_defaultSetting,opt);
        if(!_defaultSetting.nested){
            _formatData();
        }
        _menuRender();
    };
    $.extend({sysMenuRender:function(opt){
        _init(opt);
    }});
})(jQuery);