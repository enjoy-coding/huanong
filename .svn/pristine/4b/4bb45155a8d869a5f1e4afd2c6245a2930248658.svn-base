var userAgent = navigator.userAgent; //用于判断浏览器类型
var fileNum = 10; //文件数量
var fileSize = 1 * 1024 * 1024; //文件大小
var imgWidth = "160px";
var imgHeight = "130px";

/**
 * 添加图片并预览
 * @param {*} fileSaveList 
 */
function addImg(file, fileSaveList, fileSize, fileNum) {
    $("" + file).change(function () {
        //获取选择图片的对象
        var docObj = $(this)[0];
        var picDiv = $(this).parents(".picDiv");
        //得到所有的图片文件
        var fileList = docObj.files;
        var fileLength = $(".imageDiv").length + fileList.length;
        if (!controlFileNum(fileLength, fileNum)) {
            $.modal.msg("图片上传上限");
            return;
        }
        controlFileAddDisplay(fileLength,fileNum,1)
        //循环遍历
        for (var i = 0; i < fileList.length; i++) {
            if (!controlFileSize(fileList[i].size, fileSize)) {
                $.modal.msg("上传失败，图片大小不符合，请重新选择！");
                return;
            }
            //动态添加html元素
            var picHtml = "<div class='imageDiv' > <img id='img_" + fileList[i].name + "' /> <div class='cover'><i class='delbtn'  id='delbtn_" + i + "'>删除</i></div></div>";
            console.log(picHtml);
            picDiv.prepend(picHtml);
            //获取图片imgi的对象
            var imgObjPreview = document.getElementById("img_" + fileList[i].name);
            if (fileList && fileList[i]) {
                //图片属性
                imgObjPreview.style.display = 'block';
                imgObjPreview.style.width = imgWidth;
                imgObjPreview.style.height = imgHeight;

                if (userAgent.indexOf('MSIE') == -1) {
                    //IE以外浏览器
                    imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]); //获取上传图片文件的物理路径;
                } else {
                    //IE浏览器
                    if (docObj.value.indexOf(",") != -1) {
                        var srcArr = docObj.value.split(",");
                        imgObjPreview.src = srcArr[i];
                    } else {
                        imgObjPreview.src = docObj.value;
                    }
                }
                fileSaveList.push(fileList[i]);//添加进集合中
                //如果达到指定数量，隐藏增加按钮
            }
        }

        $(".delbtn").click(function(){addImgDelete(this,imgList)});
    });
}
/**
 * 添加视频
 */
function addSingleVideo(video, videoList, fileSize) {
    $(""+video).change(function (e) {
        var file = this.files[0]; //获取文件
        var fileType = file.type; //获取文件类型
        var videoFile = $(""+video);
        //判断是否是视频文件 0视频 -1图片；
        if (fileType.search("video") == 0) {
            if (!controlFileSize(file.size,fileSize)) {
                $.modal.msg("上传失败，视频太大，请重新选择！");
                return;
            }
            var createObjectURL = function (file) {
                return window[window.webkitURL ? 'webkitURL' : 'URL']['createObjectURL'](file);
            };
            var videoSrc = createObjectURL(file);
            var videoNew = "<li><video id='videos'  controls='controls'><source src='" + videoSrc + "' ></source></video><em onclick='delete_video(this,0)'>-</em></li>";
            var video = document.getElementsByTagName("video");
            if (video[0] != undefined) {
                //如果有视频 新上传的替换掉
                $('video').closest("li").html("<video controls='controls'><source src='" + videoSrc + "' ></source></video><em onclick='delete_video(this)'>-</em>");
            } else {
                //如果没有上传视频 追加上去
                $(".addVideo").prepend(videoNew);
            }

            videoList.push(videoSrc);
        } else {
            $.modal.msg("请上传视频！");
        }
    });
}

/**
 * 删除单图
 * @param {*} obj 
 */
function editSingleImgDelete(obj, url, fn) {
    $.modal.confirm("确定删除该图片吗？", function () {
        // 获取自己点击的按钮的id
        var btn_id = obj.id;
        $.ajax({
            url: file_prefix + "/remove",
            data: { ids: btn_id },
            type: "post",
            success: fn
        })
    });
}


/**
 * 编辑状态下删除图片
 * @param {*} obj 
 */
function editImgDelete(obj, url) {
    $.modal.confirm("确定删除该图片吗？", function () {
        // 获取自己点击的按钮的id
        var btn_id = obj.id;
        $.ajax({
            url: url,
            data: { ids: btn_id },
            type: "post",
            success: function (result) {
                //获取该id按钮的父节点
                $.modal.msg("删除成功");
                $("#" + btn_id).parent().parent().remove();
            }
        })
    });
}
/**
 * 新增状态下删除图片
 * @param {*} obj 
 */
function addImgDelete(obj, fileSaveList,num) {
    $.modal.confirm("确定删除该图片吗？", function () {
        var g = $("#" + obj.id).parent();
        for (var i = 0; i < fileSaveList.length; i++) {
            var img = g.prev()[0].id;
            img = img.substring(img.indexOf("_") + 1, img.length);
            if (img == fileSaveList[i].name) {
                fileSaveList.splice(i);
                break;
            }
        }
        $("#" + obj.id).parent().parent().remove();
        $.modal.msg("删除成功");
        var imgNum = $(".imageDiv").length;
        controlFileAddDisplay(imgNum,num,0)
        
    });
}

/**
 * 
 * @param {*} imgNum 图片列表
 * @param {*} num  控制数量
 * @param {*} e 增加还是删除
 */
function controlFileAddDisplay(imgNum,size,e){
   
    var num = size == null || size == "" || size == undefined ? fileSize : size;
    if(e==1){
        if(imgNum == num ){
            $(".addImages").hide();
        }
    }else{
        if(imgNum < num ){
            $(".addImages").show();
        }
    }
}
/**
 * 控制大小
 */
function controlFileSize(paramsSize, size) {
    var size1 = size == null || size == "" || size == undefined ? fileSize : size;
    if (paramsSize > size1) {
        return false;
    }
    return true;
}
/**
 * 控制数量
 * @param {*} fileList 
 */
function controlFileNum(paramsNum, num) {
    //获取已存在的图片张数和file要上传的张数
    var num1 = num == null || num == "" || num == undefined ? fileNum : num;
    //var fileNum = $(".imageDiv").length + fileList.length;
    if (paramsNum > num1) {
        return false;
    }
    return true;
}
/**
 * 图片上传
 * @param {*} url 上传路径
 * @param {*} id 源id
 * @param {*} imgList 图片集合 
 * @param {*} code 所属类型
 */
function imgUploadAjax(url, id, imgList, code, fn) {
    var formdata = new FormData();
    for (var i = 0; i < imgList.length; i++) {
        formdata.append("files", imgList[i]);
    }
    formdata.append("file_code", code);
    formdata.append("file_target", id);
    $.ajax({
        url: url,
        data: formdata,
        type: "post",
        cache: false,
        processData: false,
        contentType: false,
        success: fn
    });
}

Array.prototype.remove = function (val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

/**
     * 多图预览
     * @param e
     */
    function imageView(e,url){
        // var url = $.table._option.auditDetaiPiclUrl+"?parkrentId="+$("#parkrentId").val();
        $.getJSON(url, function(json){
            layer.photos({
                photos: json,
                anim: 0,
                closeBtn:1,
                shade:0.3
            });
        });
    }

/**
    * 上传文件
     */
function sendFile(file, obj) {
    var data = new FormData();
    data.append("file", file);
    $.ajax({
        type: "POST",
        url: ctx + "prd/attachment/api/upload",
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        dataType: 'json',
        success: function (result) {
            if (result.code == web_status.SUCCESS) {
                var item = result.data[0];
                $(obj).summernote('editor.insertImage', item.filePath, item.fileName);
            } else {
                $.modal.alertError(result.msg);

            }
        },
        error: function (error) {
            $.modal.alertWarning("图片上传失败。");
        }
    });
}

// 详细信息
function detailFull(id, ext) {
    var _url = detaiFulllUrl(id, ext);
    var index = layer.open({
        type: 2,
        area: ['100%','100%'],
        fix: true,
        //不固定
        maxmin: false,
        shade: 0.3,
        title: $.table._option.modalName + "详细",
        content: detaiFulllUrl(id, ext),
        btn: ['关闭'],
        // 弹层外区域关闭
        shadeClose: true,
        cancel: function(index, layero) {
            return true;
        },
    });
    layer.full(index);
}

// 详细访问地址
function detaiFulllUrl(id, ext) {
    var url = "/404.html";
    if ($.common.isNotEmpty(id)) {
        url = $.table._option.auditDetailUrl.replace("{id}", id);
    } else {
        var id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        if (id.length == 0) {
            $.modal.alertWarning("请至少选择一条记录");
            return;
        }
        url = $.table._option.auditDetailUrl.replace("{id}", id);
    }
    url = url.replace("{ext}", ext||"");
    return url;
}


function detail(url,title,width, height, callback){
    //如果是移动端，就使用自适应大小弹窗
    if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
        width = 'auto';
        height = 'auto';
    }
    if ($.common.isEmpty(title)) {
        title = false;
    }
    if ($.common.isEmpty(url)) {
        url = "/404.html";
    }
    if ($.common.isEmpty(width)) {
        width = 800;
    }
    if ($.common.isEmpty(height)) {
        height = ($(window).height() - 50);
    }
    if ($.common.isEmpty(callback)) {
        callback = function (index, layero) {
            var iframeWin = layero.find('iframe')[0];
            iframeWin.contentWindow.submitHandler(index, layero);
        }
    }
    var index = layer.open({
        type:2,
        area: [width + 'px', height + 'px'],
        fix: false,
        //不固定
        maxmin: true,
        shade: 0.3,
        title: title,
        content: url,
        btn: ['关闭'],
        // 弹层外区域关闭
        shadeClose: true,
        cancel: function(index, layero) {
            return true;
        },
    });
}