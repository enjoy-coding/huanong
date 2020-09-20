function uploadFile(file, obj) {
    debugger
    var data = new FormData();
    if (file instanceof FileList) {
        if ($.common.isNotEmpty(file) && file.length > 0) {
            for (var i = 0; i < file.length; i++) {
                data.append("file", file[i], file[i].name);
            }
        }
    } else {
        data.append("file", file);
    }
    $.ajax({
        type: "POST",
        url: ctx + "np/api/upload",
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        dataType: 'json',
        success: function (result) {
            if (result.code == web_status.SUCCESS) {
                var item = result.data[0];
                if (typeof obj == "function") {
                    obj(result);
                } else if (typeof obj == "string") {
                    $(obj).summernote('editor.insertImage', ctx + item.filePath, item.fileName);
                }
            } else {
                $.modal.alertError(result.msg);
            }
        },
        error: function (error) {
            $.modal.alertWarning("图片上传失败。");
        }
    });
}

/**
 * 上传封面
 * @param fileBtn 上传按钮
 * @param img 显示封面的元素id
 */
function uploadCoverImage(fileBtn, imgId, coverImage) {
    var file = $("#" + fileBtn).get(0).files[0];
    uploadFile(file, callback);
    function callback(result) {
        if ($.common.isNotEmpty(result.data) && result.data.length > 0) {
            var img = result.data[0];
            $("#" + imgId).attr("src", ctx + img.filePath);
            $("#" + coverImage).val(img.filePath);
        }
    }
}

function uploadZip(fileBtn, linkVal){
    var file = $("#" + fileBtn).get(0).files[0];
    uploadFile(file, callback);
    function callback(result) {
        if ($.common.isNotEmpty(result.data) && result.data.length > 0) {
            if(result.code == 0) {
                $.modal.msgSuccess(result.msg);
                var zip = result.data[0];
                $("#" + linkVal).val(zip.filePath);
            }else{
                $.modal.msgError(result.msg);
            }
        }
    }
}

function uploadAttaches(options) {
    var defaults = {
        uploadBtnId: "attachesBtn",
        showId: "zbt-table",
        hideText: "attaches",
        ctx: "",
        titles: [
            {
                title: "附件名称",
                width: 500,
                keyName: "fileName"
            }, {
                title: "附件大小(b)",
                width: 200,
                keyName: "fileSize"
            }]
    };
    options = $.extend(defaults, options);

    var file = $("#" + options.uploadBtnId).get(0).files;
    var titles = options.titles;
    var showId = options.showId;
    var hideText = options.hideText;
    var hideTextVal = $("#" + hideText).val();
    //上传文件
    uploadFile(file, callback);

    function callback(result) {
        var code = result.code;
        if (code == 0) {
            var data = result.data;
            //说明上传成功
            if ($.common.isNotEmpty(data) && data.length > 0) {
                if ($("#" + showId).find("table").length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        $.fn.ZBTable.addRow(showId, titles, data[i], options.ctx);
                    }
                } else {
                    $('#' + options.showId).ZBTable({
                        titles: titles,
                        data: data
                    });
                }
                for (var i = 0; i < data.length; i++) {
                    hideTextVal += JSON.stringify(data[i]) + ";";
                }
                $("#" + hideText).val(hideTextVal);
            }
        }
    }
}

/**
 *
 * @param showId 包裹表格的divId
 * @param dataStr 数据字符串
 * @param showOpt 显示删除按钮
 */
function initAttachesTable(showId, dataStr, showOpt) {
    var data = [];
    if ($.common.isNotEmpty(dataStr)) {
        var dataStr = dataStr.split(";");
        if (dataStr.length > 0) {
            for (var i = 0; i < dataStr.length; i++) {
                var dataI = dataStr[i];
                if (dataI != "") {
                    data.push(JSON.parse(dataI));
                }

            }
            var titles = [
                {
                    title: "附件名称",
                    width: 300,
                    keyName: "fileName"
                }, {
                    title: "附件大小(b)",
                    width: 150,
                    keyName: "fileSize"
                }]
            $('#' + showId).ZBTable({
                ctx: ctx,
                titles: titles,
                data: data,
                showOpt: showOpt
            });
        }
    }
}

/**
 * 初始化编辑器
 * @param height 高度
 * @param domValId 编辑器值的domId,为编辑器填充内容
 */
function initSummernote(height, domValId) {
    if ($.common.isEmpty(height)) {
        height = 300;
    }
    $('.summernote').summernote({
        placeholder: '请输入内容',
        height: height,
        lang: 'zh-CN',
        followingToolbar: false,
        callbacks: {
            onImageUpload: function (files) {
                uploadFile(files[0], ".summernote");
            },
            onPaste: function (e) {
                var bufferText = ((e.originalEvent || e).clipboardData || window.clipboardData).getData('Text');
                e.preventDefault();
                document.execCommand('insertText', false, bufferText);
            }
        }
    });
    var content = $("#" + domValId).val();
    //设置内容
    $('#editor').summernote('code', content);
}


function addAttachesDelEvent(domId) {
    $(document).on("click", "a", function () {
        var str = $("#" + domId).val();
        $tr = $(this).parents("tr");
        $tr.children("td").each(function (index, item) {
            if (index == 0) {
                var id = $(item).attr("id");
                var result = delElementFromStr(str, id);
                $("#" + domId).val(result);
            }
        })

        $tr.remove();

    });
}


function delElementFromStr(str, searchStr) {
    var strArr = str.split(";");
    for (var i = 0; i < strArr.length; i++) {
        if ($.common.isNotEmpty(strArr[i])) {
            var obj = JSON.parse(strArr[i])
            if (obj.fileId == searchStr) {
                strArr.splice(i, 1);
            }
        }
    }
    return strArr.join(";")
}

function imageView(_this){
    console.log(_this);
   /* var url = $(_this).attr("href");
    var url = "/napo/profile/upload/2020/06/23/30726f402bfd23fb95050bbb7f046ec1.jpg";
    $.getJSON(url, function(json){
        layer.photos({
            photos: json,
            anim: 0,
            closeBtn:1,
            shade:0.3
        });
    });*/
}
