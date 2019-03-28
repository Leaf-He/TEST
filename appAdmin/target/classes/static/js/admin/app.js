$(function() {

    window.onresize=function(){
        changeFrameHeight();
    }

    //动态获取菜单
    $.ajax({
        type : "POST",  //提交方式
        url : contextPath+"admin/sideMenu",//路径
        data : {},
        success : function(data) {//返回数据根据结果进行相应的处理
            var html = template('admin_side_menu_template', data.dataMap);
            document.getElementById('side_menu_list').innerHTML = html;
            $('.menu').click(function () {
                //如果不是父节点
                if(!$(this).hasClass('parent')){
                    $('.menu').removeClass('active');
                    $(this).addClass('active');
                }
            });
        }
    });
    //导航栏隐藏
    $(".navbar-expand-toggle").click(function() {
        $(".app-container").toggleClass("expanded");
        return $(".navbar-expand-toggle").toggleClass("fa-rotate-90");
      });

    $(".navbar-right-expand-toggle").click(function() {
        $(".navbar-right").toggleClass("expanded");
        return $(".navbar-right-expand-toggle").toggleClass("fa-rotate-90");
      });

    $(".side-menu .nav .dropdown").on('show.bs.collapse', function() {
        return $(".side-menu .nav .dropdown .collapse").collapse('hide');
    });

});

//右边菜单点击事件
function sideMenuClick(menuUrl, menuId,isParent,obj) {
    $('.menu').removeClass('active');
    $('#'+menuId).parents('li').addClass('active');//设置父节点高亮
    $('.menu').filter(".parent").find('.child').css('color','#FFF');
    $('#acive_menu_title').empty();
    if(!isParent) { //非父节点
        $(obj).css('color', '#22A7F0');
        $("#acive_menu_title").append("<li>" + $('#'+menuId).parents('.menu.parent').find('span.title').html() +"</li>");
        $("#acive_menu_title").append("<li>" + $(obj).html() +"</li>");
    }else{
        $("#acive_menu_title").append("<li>" + $(obj).children('span.title').html() +"</li>");
    }
    if(!$.isEmptyObject(menuUrl)){
        $('#main_frame').attr('src',contextPath+menuUrl);
    }
}

//改变iframe高度
function changeFrameHeight(){
    var ifm= document.getElementById("main_frame");
    ifm.height=document.documentElement.scrollHeight;
    /*
    if (ifm) {
        var iframeWin = ifm.contentWindow ;
        if (iframeWin.document.body) {
            ifm.height = iframeWin.document.documentElement.scrollHeight;
            alert(ifm.height);
        }
    }
    */
}



