layui.use(['element','util','table','laypage','upload'], function() {
    table = layui.table;
    var $ = layui.jquery,
        element = layui.element,
        tree = layui.tree,
        layer = layui.layer,
        util = layui.util,
        laypage = layui.laypage,
        upload = layui.upload;

    var width = ($('.xjlbTabe').width())  / 5;
    var tatleHeight = $(".xjlbTabe").height()
    // console.log(width)

    // 巡检记录
    table.render({
        elem: '#xjlbTabe'
        ,height: tatleHeight
        ,skin:'nob'
        // ,size: 'sm' //小尺寸的表格
        // ,url: '/demo/table/user/' //数据接口
        ,cols: [[ //标题栏
            {field: 'serial', title: '序号',align: 'center',width:width},
            {field: 'time', title: '用时', align: 'center'},
            {field: 'number', title: '异常数量', align: 'center',}
        ]]
        ,data: [{
            "serial": "1"
            ,"time": "阀门井1"
            ,"number": '10'
        }]
        ,page: {
            layout: ['prev', 'page', 'next'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            ,groups: 1 //只显示 1 个连续页码
            ,first: false //不显示首页
            ,last: false //不显示尾页
        }
    });

    // 异常详情分页
    laypage.render({
        elem: 'ycxqNum',
        count: 7,
        limit: 4,
        cuur: 1,
        layout: ['prev', 'next'],
        jump: function(obj, first){
            //  if(!first){
            // layer.msg('第 '+ obj.curr +' 页');
            //  }

        }
    });

    //  上传巡检状况--现场影像上传
    upload.render({
        elem: '#scxjzkUploadBtn'
        ,url: '/upload/'
        ,multiple: true
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                var $img = '<div class="imgBox pr">' +
                    '<i class="pa iconfont icon-guanbi1 imgCloseBtn" onclick="imgCloseBtn(this)"></i>'+
                    '<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">' +
                    '</div>'
                $('#scxjzkUploadList').append($img)
            });
        }
        ,done: function(res){
            //上传完毕
        }
    });

})

// 巡检任务--任务操作
var number = false

// 点击任务操作列表
function listActive(i){
    var txt = $(i).find('span').text();
    number = true;
    $(i).addClass('active').siblings('li').removeClass('active')
}
// 先选中列表再点击按钮
$('.taskOperation').on('click', 'button', function(){
    if(number){
        $(this).addClass('active').siblings('button').removeClass('active')
        rightBoxShow()
    }
})
// 上传巡检状况弹框--现场状况按钮选中状态
$(".scxjzkCheck").on('click','button', function(){
    $(this).addClass('active').siblings('button').removeClass('active');
    var txt = $(this).text();
    console.log(txt)
})

// 多张图片上传后可删除图片
function imgCloseBtn(i){
    $(i).parent('.imgBox').remove()
}

// 上传巡检状况弹框显示与隐藏
function layerBtn(type,txt){
    if(type == "show"){
        $("#" + txt ).removeClass('hide');
        console.log(type,txt)
    } else if(type == "hide"){
        $("#" + txt ).addClass('hide');
        console.log(type,txt)
    }
}