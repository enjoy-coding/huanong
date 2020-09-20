// 上传文件开始
var falg = false; // 判断二维码是否上传成功
var qrCode = document.forms["myForm"]["qrCode"].value; // 二维码

info = JSON.parse(info); // 将缓存的会议信息转为对象
var btn = window.location.href.split("?")[1]; // 判断点击的是新增还是编辑
//console.log(btn)
// 如果是编辑就将信息附值,如果是新增就不附值
if (btn == "edit") {
    $("#title").val(info.title);
    $("#beginTimeStr").val(info.beginTimeStr);
    $("#place").val(info.place);
    $("#img").attr("src", url + info.qrCode);
    $('#downhz,#downtxl').removeClass("hide")
    qrCode = info.qrCode;
    var postLink = url + '/api/meeting/editMeeting';
} else {
    var postLink = url + '/api/meeting/addMeeting';
}
// 点击按钮触发文件主传事件
$('#my-img').click(function (e) {
    $('#img-upload').click();
});
var inputBox = document.getElementById("img-upload");
inputBox.addEventListener("change", function () {
    var data = new FormData();
    data.append("file", inputBox.files[0]);
    $.ajax({
        type: "POST",
        url: url + '/prd/attachment/api/upload?thumb=true',
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        dataType: 'json',
        success: function (result) {
            //console.log(result)
            if (result.code == 0) {
                var item = result.data[0];
                $("#img").attr('src', url + item.filePath)
                falg = true;
                qrCode = item.filePath
            } else {
                console.log("上传失败")
            }
        },
        error: function (error) {
            console.log("上传失败2")
        }
    });
})
// 上传文件结束
// 表单提交
function validateForm(form) {
    var title = document.forms["myForm"]["title"].value, // 标题
        beginTimeStr = document.forms["myForm"]["beginTimeStr"].value, // 时间
        place = document.forms["myForm"]["place"].value // 地址

    var data = {
        "title": title,
        "beginTimeStr": beginTimeStr + ":00",
        "place": place,
        "qrCode": qrCode,
        "id": meetingID
    }
    if (title == "") {
        close()
        form.title.focus();
        return false
    } else if (beginTimeStr == "") {
        close()
        form.beginTimeStr.focus();
        return false
    } else if (place == "") {
        close()
        form.place.focus();
        return false
    } else if (qrCode == "") {
        close()
        $("#formAlertTxt").html("二维码不能为空")
        form.qrCode.focus();
        return false
    }
    //console.log(data,postLink)
    $.ajax({
        type: "POST",
        dataType: "json",
        url: postLink, //url
        data: data, //这个是form表单中的id   jQuery的serialize()方法通过序列化表单值
        success: function (result) {
            //console.log(result)
            if (result.code == 0) {
                console.log("编辑会议")
                window.location.href = 'index.html' // 返回首页
            }
        },
        error: function (err) {
            console.log(err)
        }
    });
}

$(function () {
    if (info && info.id && info.title) {
        //var xlsUrl = url + "/api/meeting/exportExcel?mp.weixin.qq.com&id=" + info.id;
        //var xlsUrl = url + "/api/meeting/exportExcel?id=" + info.id;
        //var docUrl = url + "/api/meeting/exportWord?id=" + info.id;
        //$("#downhz").attr("download", xlsUrl).attr("href", xlsUrl);
        //$("#downtxl").attr("download", docUrl).attr("href", docUrl);
        $("#downbox").show();
    }
    $("#downhz").click(function () {
        window.open(url + "/api/meeting/exportExcel?id=" + info.id);
    });
    $("#downtxl").click(function () {
        window.open(url + "/api/meeting/exportWord?id=" + info.id);
    });

});

