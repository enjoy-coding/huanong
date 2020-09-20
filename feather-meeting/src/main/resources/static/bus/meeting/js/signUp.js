$('#my-img').click(function () {
    $('#img-upload').click();
});
var flag = false;
var photo = document.forms["signApForm"]["photo"].value; // 电话	
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
            console.log(result)
            if (result.code == 0) {
                var item = result.data[0];
                flag = true;
                $("#photoImg").attr('src', url + item.filePath)
                //console.log(item.filePath)
                photo = item.filePath  // 照片路径
            } else {
                console.log("上传失败")
            }
        },
        error: function (error) {
            console.log("上传失败2")
            //console.log(error)
        }
    });
})

function signupform(form) {
    var userName = document.forms["signApForm"]["userName"].value, // 姓名
        school = document.forms["signApForm"]["school"].value, // 学校
        unit = document.forms["signApForm"]["unit"].value, // 工作单位
        job = document.forms["signApForm"]["job"].value, // 职务
        address = document.forms["signApForm"]["address"].value, // 地址
        code = document.forms["signApForm"]["code"].value, // 邮编
        mobile = document.forms["signApForm"]["mobile"].value; // 手机号
        telephone = document.forms["signApForm"]["telephone"].value; // 固定电话
    if (userName == "") {
        close()
        form.userName.focus();
        return false
    } else if (school == "") {
        close()
        form.school.focus();
        return false
    } else if (unit == "") {
        close()
        form.unit.focus();
        return false
    } else if (job == "") {
        close()
        form.job.focus();
        return false
    } else if (address == "") {
        close()
        form.address.focus();
        return false
    } else if (code == "") {
        close()
        form.code.focus();
        return false
    } else if (mobile == "") {
        close()
        form.mobile.focus();
        return false
    } else if (photo == "" || flag == false) {
        close()
        $("#formAlertTxt").html("照片不能为空")
        form.photo.focus();
        return false
    }

    var openId = getOpenId();
    data = {
        "uniqueId": openId,
        "meetingId": meetingID,
        "photo": photo,
        "userName": userName,
        "school": school,
        "unit": unit,
        "job": job,
        "address": address,
        "code": code,
        "mobile": mobile,
        "telephone": telephone,
        "openId": openId
    }
    $.ajax({
        type: "POST",
        dataType: "json",
        url: url + "/api/meeting/addMeetingPersonnel",
        data: data,
        success: function (data) {
            var data = data.data;
            sessionStorage.setItem("participationId", data.meetingPersonnel.id)
            link();
        },
        error: function (data) {
            alert(data);
        }
    });
}

function link() {
    window.location.href = "success.html"; //在原有窗口打开
}

// 2寸照片的高
imgHeight()
window.onresize = function () {
    imgHeight()
};

function imgHeight() {
    var imgHeight = ($("#fileImg").width()) / 0.66;
    $("#fileImg").height(imgHeight);
    $("#fileTxt").height(imgHeight);
    //	console.log(imgHeight)
}
