/**
 *  tab控件
 *  该元素主要组成部分是ul,且position是relative，通过改变left值来实现移动;
 *   并且通过tab与iframe联动
 *  @author AllanLoo
 *  @version 1.0.1
 */
;(function ($){
    var _defaultSetting = {
        /**
         * tab左移动控制按钮id
         * @default:tab-left-btn
         */
        prevBtnId:"tab-left-btn",
        /**
         * tab右移动控制按钮id
         * @default:tab-right-btn
         */
        nextBtnId:"tab-right-btn",
        /**
         * tab父元素的id，也就是ul的id
         */
        tabItemParentId:"tabs-container",
        /**
         * iframe父元素的id
         *  @example
         *  <div id='contentArea'>
         *    <iframe id="item1"/>
         *    <iframe id="item2"/>
         *   </div>
         *
         */
        iframeItemParentId:"contentArea",
        /**
         * 初始固定的tab,位于最左侧，当有多个时，第一个是激活状态
         * @type:object array
         * @default:[]
         * @example:[{tabId:'000',tabText:'系统主页',iframeUrl:'http://..'}]
         *
         */
        fixTabItems:[]
    };

    /**
     *  判断值是否为空
     *  @modifier: private
     *  @result :true-空
     */
    var _isEmpty = function(v){
        if(v==undefined || v==null || v=="" || v=="null" || v=="undefined"){
            return true;
        }
        return false;
    };

    /**
     *  初始化方法
     */
    var _init = function(opt){
        if(!_isEmpty(opt)){
            $.extend(_defaultSetting,opt);
        }

        $("#"+_defaultSetting.nextBtnId).on("click",function(){_moveToNextTabItem();});
        $("#"+_defaultSetting.prevBtnId).on("click",function(){_moveToPrevTabItem();});

        //初始化固定的tab
        if(_defaultSetting.fixTabItems&&_defaultSetting.fixTabItems.length>0){
            for (var i = 0; i < _defaultSetting.fixTabItems.length; i++) {
                var isActive = false;
                if(i==0){
                    isActive = true;
                }
                var tabItem = _defaultSetting.fixTabItems[i];
                _addTab(tabItem.tabId,tabItem.tabText,tabItem.iframeUrl,false,isActive);
            }
        }

    };


    /**
     * 添加一个tab标签
     * @modifier: private
     * @param tabId:标签id
     * @param tabText:标签文字
     * @param iframeUrl：关联iframe的src
     * @param canClose：是否能关闭
     * @param isActive：是否是激活状态
     */
    var _addTab = function(tabId,tabText,iframeUrl,canClose,isActive){
        if(!_tabIsExist(tabId)){
            var $tabEle = $("<li id='tab-"+tabId+"'><a>"+tabText+"</a><a class='tab-close'><i class='glyphicon glyphicon-remove'></i></a></li>");
            $("#"+_defaultSetting.tabItemParentId).append($tabEle);
            if(canClose){
                // 绑定关闭事件
                $tabEle.find("i").on("click",function(){
                    _closeTab(tabId);
                    _moveToPrevTabItem()&&_moveToNextTabItem(tabId);
                });
            }else{
                $tabEle.find("a.tab-close").remove();
            }
            //关联ifame
            _linkTabIframe(tabId,iframeUrl);

            //绑定单击事件
            $("li[id=tab-" + tabId + "]",$("#"+_defaultSetting.tabItemParentId)).on("click",function(){
                _makeTabActive(tabId);

            });

            if(isActive){
                //激活该标签
                _makeTabActive(tabId);
            }
            //绑定右键菜单
            _bindRightMenu(tabId);
        }else{
            //激活该标签
            _makeTabActive(tabId);
        }

    };

    /**
     * 创建右键菜单，存在就显示，不存在就创建
     * @modifier: private
     *
     **/
    var _bindRightMenu = function(tabId){
        var oMenu = $("#tab-right-menu");
        if(oMenu.length==0){
            //右键菜单的html
            oMenu = $('<div id="tab-right-menu" style="display:none">'
            + '<ul>'
            +  '<li id="close-other">关闭其他标签页</li>'
            +  '<li id="close-left">关闭左侧标签页</li>'
            +  '<li id="close-right">关闭右侧标签页</li>'
            +  '<li id="close-all">关闭所有标签页</li>'
            + '</ul>'
            +'</div>');
            $("#"+_defaultSetting.tabItemParentId).after(oMenu);

            oMenu.find("li").each(function(){
                //菜单项鼠标移上去的样式
                $(this).hover(function(){$(this).addClass("trm-item-hl");},
                    function(){$(this).removeClass("trm-item-hl");});
                //绑定单击事件
                $(this).click(function(){
                    _rightMenuClickEvent(this.id,tabId);
                });
                oMenu.find("li").each(function(){
                    //菜单项鼠标移上去的样式
                    $(this).hover(function(){$(this).addClass("trm-item-hl");},
                        function(){$(this).removeClass("trm-item-hl");});
                    //绑定单击事件
                    $(this).click(function(){
                        _rightMenuClickEvent(this.id,tabId);
                    });

                });
            });
            //隐藏菜单
            $(document).click(function () {
                oMenu.hide();
            });
            $(document).mousedown(function (e) {
                if (3 == e.which) {
                    oMenu.hide();
                }
            });

        }
        //自定义右键菜单
        var maxWidth = maxHeight = 0;
        var aDoc = [document.documentElement.offsetWidth, document.documentElement.offsetHeight];
        $("li[id=tab-" + tabId + "]",$("#"+_defaultSetting.tabItemParentId)).on("contextmenu",function () {
            var event = event || window.event;

            oMenu.css("left",event.clientX + "px").css("top",event.clientY + "px");
            oMenu.show();
            //最大显示范围
            maxWidth = aDoc[0] - oMenu.width();
            maxHeight = aDoc[1] - oMenu.height();
            //防止菜单溢出
            if (oMenu.offset().top > maxHeight) {
                oMenu.css('top', maxHeight + "px");
            }
            if (oMenu.offset().left > maxWidth) {
                oMenu.css('left', maxWidth + "px");
            }

            oMenu.find("li").each(function(){
                //绑定单击事件
                $(this).unbind("click").bind("click",function(){
                    _rightMenuClickEvent(this.id,tabId);
                });

            });

            return false;
        });

    };

    /**
     * 右键菜单项的单击事件
     * @modifier: private
     * @param id：菜单项的id
     * @param tabId：当前tab标签的id
     */
    var _rightMenuClickEvent = function(id,tabId){
        if(id=="close-other"){
            _closeOtherTab(tabId);
        }
        if(id=="close-left"){
            _closeTabLeft(tabId);
        }
        if(id=="close-right"){
            _closeTabRight(tabId);
        }
        if(id=="close-all"){
            _closeAllTab();
        }
    };

    /**
     * 关闭所有标签，除固定标签外，关闭后，将固定标签显示出来
     * @modifier: private
     */
    var _closeAllTab = function(){
        var tabItems = $("> li",$("#"+_defaultSetting.tabItemParentId));
        for (var i = 0, l = tabItems.length; i < l; i++){
            var tabItem = $(tabItems[i]);
            if(tabItem.find(".tab-close").length >0){
                _closeTab(tabItem.attr("id").replace("tab-",""));
            }
        }
        //将第一个tab移动到可视化区域
        _moveToPrevTabItem($(tabItems[0]).attr("id").replace("tab-",""));
    };

    /**
     * 关闭除自己外所有的标签，再将看不见的区域给显示出来
     * @modifier: private
     * @param tabId
     */
    var _closeOtherTab = function(tabId){
        var tabItems = $("> li",$("#"+_defaultSetting.tabItemParentId));
        for (var i = 0, l = tabItems.length; i < l; i++){
            var tabItem = $(tabItems[i]);
            if(tabItem.attr("id").replace("tab-","")==tabId){
                continue;
            }
            if(tabItem.find(".tab-close").length >0){
                _closeTab(tabItem.attr("id").replace("tab-",""));
            }
        }
        //将第一个tab移动到可视化区域
        _moveToPrevTabItem($(tabItems[0]).attr("id").replace("tab-",""));
    };
    /**
     * 关闭当前标签的左侧的所有标签，除固定标签外
     * @modifier: private
     * @param tabId:当前标签的id
     *
     */
    var _closeTabLeft = function(tabId){
        var tabItems = $("> li",$("#"+_defaultSetting.tabItemParentId));
        for (var i = 0, l = tabItems.length; i < l; i++){
            var tabItem = $(tabItems[i]);
            if(tabItem.attr("id").replace("tab-","")==tabId){
                break;
            }
            if(tabItem.find(".tab-close").length >0){
                _closeTab(tabItem.attr("id").replace("tab-",""));
            }
        }
        //将第一个tab移动到可视化区域
        _moveToPrevTabItem($(tabItems[0]).attr("id").replace("tab-",""));
    };

    /**
     * 关闭当前标签的右侧的所有标签
     * @modifier: private
     * @param tabId:当前标签的id
     *
     */
    var _closeTabRight = function(tabId){
        var tabItems = $("> li",$("#"+_defaultSetting.tabItemParentId));
        for (var i = tabItems.length - 1; i >= 0; i--) {
            var tabItem = $(tabItems[i]);
            if(tabItem.attr("id").replace("tab-","")==tabId){
                break;
            }
            if(tabItem.find(".tab-close").length >0){
                _closeTab(tabItem.attr("id").replace("tab-",""));
            }
        };
        //先将第一个显示到可视化区域，如果当前tab被右侧隐藏了，我们再移动到当前标签位置
        _moveToPrevTabItem($(tabItems[0]).attr("id").replace("tab-",""));
        _moveToNextTabItem(tabId);
    };
    /**
     *  判断tab是否已经存在
     *  @modifier: private
     *  @param tabId：标签id
     *  @result: true-存在
     */
    var _tabIsExist = function(tabId){
        if($("li[id=tab-" + tabId + "]",$("#"+_defaultSetting.tabItemParentId)).length>0)
            return true;
        return false;
    }
    /**
     * 单击一个标签时，激活此标签，同时显示与该标签相关联的ifame
     * 如果该标签被隐藏，则需要将其显示出来
     *  @modifier:private
     *  @param tabId:标签id
     */
    var _makeTabActive = function(tabId){
        $("li[id=tab-" + tabId + "]",$("#"+_defaultSetting.tabItemParentId)).addClass("tab-active")
            .siblings().removeClass("tab-active");
        $("iframe[id=tab-iframe-" + tabId + "]", $("#"+_defaultSetting.iframeItemParentId)).show().siblings().hide();
        !_moveToNextTabItem(tabId)&&_moveToPrevTabItem(tabId);
    };
    /**
     * tab移动下一个位置，也就是tab容器（ul）向左移动
     * @modifier: private
     * @result: true-移动成功
     */
    var _moveToNextTabItem = function(tabId){
        var tabItems = $("> li",$("#"+_defaultSetting.tabItemParentId)),
            nextBtn = $("#"+_defaultSetting.nextBtnId);

        if (!nextBtn.length)
            return false;

        var nextBtnOffset = nextBtn.offset(),moveToTabItem = null;
        //遍历所有tab项，找到右侧第一个被隐藏的tab项
        for (var i = 0, l = tabItems.length; i < l; i++){
            var tabitem = $(tabItems[i]);
            //当前tab的起始位置，和结束位置
            var startPosition = tabitem.offset().left, endPostion = tabitem.offset().left + tabitem.outerWidth(true);
            if(!_isEmpty(tabId)){
                if (endPostion > nextBtnOffset.left && tabitem.attr("id").replace("tab-","") == tabId){
                    moveToTabItem = tabitem;
                    break;
                }
            }else{
                //如果当前tab的结束位置超过了右移按钮的起始位置，那么该tab项就是要显示的
                if(endPostion > nextBtnOffset.left){
                    moveToTabItem = tabitem;
                    break;
                }
            }
        }
        //如果不存在被隐藏的tab，那么就不移动
        if (moveToTabItem == null)
            return false;

        var originalLeft = parseInt($("#"+_defaultSetting.tabItemParentId).css("left"));
        //设置的一个偏差值，当tab不是完全被遮盖的时候，没有必要移动整个tab，移动一部分就行了,
        //但是被遮罩的宽度太小，那么移动就看不出来,所以这里设置一个偏差值。
        var willMoveWidth = moveToTabItem.offset().left+moveToTabItem.outerWidth(true)-nextBtnOffset.left;
        if(willMoveWidth < 5){
            willMoveWidth = moveToTabItem.outerWidth(true);
        }

        //开始移动
        $("#"+_defaultSetting.tabItemParentId).animate({left:originalLeft-willMoveWidth});
        return true;
    };

    /**
     * tab移动上一个位置，也就是tab容器（ul）向右移动
     * @modifier: private
     * @result: true-移动成功
     */
    var _moveToPrevTabItem = function(tabId){
        var tabItems = $("> li",$("#"+_defaultSetting.tabItemParentId)),
            prevBtn = $("#"+_defaultSetting.prevBtnId);

        if (!prevBtn.length)
            return false;
        var prevBtnEndPosition = prevBtn.offset().left+prevBtn.outerWidth(),moveToTabItem = null,moveToTabItemNextIndex;
        //遍历所有tab项，找到左侧第一个被隐藏的tab项
        for (var i = 0, l = tabItems.length; i < l; i++){
            var tabitem = $(tabItems[i]);
            //当前tab的起始位置，和结束位置
            var startPosition = tabitem.offset().left;
            if(!_isEmpty(tabId)){
                if (startPosition < prevBtnEndPosition && tabitem.attr("id").replace("tab-","") == tabId){
                    moveToTabItem = tabitem;
                    break;
                }
            }else{
                //如果当前tab的结束位置超过了右移按钮的起始位置，那么该tab项就是要显示的
                if(startPosition >= prevBtnEndPosition){
                    moveToTabItemNextIndex = i;
                    break;
                }
            }
        }
        //如果移动tab的下一个tab的索引是0的话，说明左侧没有任何被隐藏的tab，不需要移动
        if(_isEmpty(tabId)){
            if(moveToTabItemNextIndex==null||moveToTabItemNextIndex==0){
                return false;
            }
            moveToTabItem = $(tabItems[moveToTabItemNextIndex-1]);
        }else{
            if(moveToTabItem==null){
                return false;
            }
        }

        var originalLeft = parseInt($("#"+_defaultSetting.tabItemParentId).css("left"));
        //设置的一个偏差值，当tab不是完全被遮盖的时候，没有必要移动整个tab，移动一部分就行了,
        //但是被遮罩的宽度太小，那么移动就看不出来,所以这里设置一个偏差值。

        var willMoveWidth = prevBtnEndPosition - moveToTabItem.offset().left;
        if(willMoveWidth < 5){
            willMoveWidth = moveToTabItem.outerWidth(true);
        }
        //开始移动,5为父容器的padding-left值
        $("#"+_defaultSetting.tabItemParentId).animate({left:originalLeft+willMoveWidth+5});
        return true;
    };

    /**
     * 联动tab的iframe，当创建或关闭或显示tab标签时，联动与之关联的iframe
     *  @modifier: private
     */
    var _linkTabIframe = function(tabId,iframeUrl){

        //iframe联动
        var $tabIframe =  $("#"+_defaultSetting.iframeItemParentId).find("#tab-iframe-"+tabId);
        //如果与该tab关联的iframe不存在，那么创建一个
        if($tabIframe.length==0){
            var $iframeEle = $("<iframe>",{id:'tab-iframe-'+tabId,
                class:'tab-iframe',
                src:iframeUrl,
                height:'100%',
                width:'100%',
                frameborder:'0'}).css("display","block");

            $("#"+_defaultSetting.iframeItemParentId).find(".tab-iframe").hide();
            $("#"+_defaultSetting.iframeItemParentId).append($iframeEle);
        }
        //如果存在那么就显示
        else{
            $("#"+_defaultSetting.iframeItemParentId).find(".tab-iframe").hide();
            $tabIframe.show();
        }
    };

    /**
     *  关闭标签，同时需要关闭与该标签关联的iframe，如果当前的标签是激活状态，那么前一个标签变成激活状态
     *  @modifier: private
     *  @param tabId:标签id
     */
    var _closeTab = function(tabId){
        var currentIsSelected = $("li[id=tab-" + tabId + "]", $("#"+_defaultSetting.tabItemParentId)).hasClass("tab-active");
        if(currentIsSelected){
            $("iframe[id=tab-iframe-" + tabId + "]", $("#"+_defaultSetting.iframeItemParentId)).prev().show();
            $("li[id=tab-" + tabId + "]",$("#"+_defaultSetting.tabItemParentId)).prev().addClass("tab-active");
        }
        var linkedIframe = $("iframe[id=tab-iframe-" + tabId + "]", $("#"+_defaultSetting.iframeItemParentId));
        if (linkedIframe.length)
        {
            var frame = linkedIframe[0];
            frame.src = "about:blank";
            try
            {
                frame.contentWindow.document.write('');
            } catch (e){
            }
            $.support.msie && CollectGarbage();
            linkedIframe.remove();
        }
        $("li[id=tab-" + tabId + "]",$("#"+_defaultSetting.tabItemParentId)).remove();
    };

    /**
     * 刷新当前tab
     * @param tabId
     * @private
     */
    var _refreshActiveTab = function(){
        var tabId = $("#"+_defaultSetting.tabItemParentId).find("li.tab-active").attr("id").replace("tab-","");
        var iframe = $("iframe[id=tab-iframe-" + tabId + "]", $("#"+_defaultSetting.iframeItemParentId));
        iframe.attr("src",iframe.attr("src"));
    }

    function Tab(){
        this.open = function(tabId,tabText,iframeUrl){
            _addTab(tabId,tabText,iframeUrl,true,true);
        };
        this.refreshActiveTab = function(){
            _refreshActiveTab();
        } ;
    }
    $.extend({tab:function(opt){
        _init(opt);
        return new Tab();
    }});
})(jQuery);