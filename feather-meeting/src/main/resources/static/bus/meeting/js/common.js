//编写代码
var url = window.location.protocol + "//" + window.location.host;
var token = window.localStorage.token; // token
var userName = localStorage.getItem("userName"); // 用户名
var $txtList = ''; // 展示列表
var meetingID = sessionStorage.getItem("meetingID"); // 缓存会议ID
var participationId = sessionStorage.getItem("participationId"); // 参会会议ID
var info = sessionStorage.getItem("info"); // 缓存会议信息
var openId = getOpenId();

$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: url + '/api/meeting/getNewestMeeting',
        data: {
            "uniqueId": openId
        },
        dataType: 'json',
        success: function (data) {
            if (data.code === 0) {
                var data = data.data;
                console.log(data)
                if (!token) {
                    $("#loginBefore").removeClass("hide")
                    $("#loginAfter").addClass("hide")
                    if (data.meetingInfo == undefined) {
                        $txtList = '<dl>' + 
							'<dt>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX会议</dt>' + 
							'<dd class="time"><i></i>时间：暂无</dd>' + 
							'<dd>' + 
								'<a class="btn btnOrange">敬请期待</a>' + 
							'</dd>'+
						'</dl>'
                    } else {
                        var meetingInfo = data.meetingInfo;
                        sessionStorage.setItem("meetingID", data.meetingInfo.id)
                        //console.log(meetingID)
                        if (!data.hasJoin) {
                            $txtList = '<dl>' +
									'<dt>' + meetingInfo.title + '</dt>' +
									'<dd class="time"><i></i>时间：' + meetingInfo.beginTimeStr + '</dd>' +
									'<dd>' +
										'<a href="javascript:void(0)" class="btn btnOrange" onclick="checkHasGz(this)">点击报名</a>' +
									'</dd>' +
								'</dl>'
                        } else {
                            sessionStorage.setItem("participationId", data.meetingPersonnel.id)
                            //console.log(participationId)
                            $txtList = '<dl>' + 
									'<dt>' + meetingInfo.title + '</dt>' +
									'<dd class="time"><i></i>时间：'+ meetingInfo.beginTimeStr + '</dd>' +
									'<dd>' +
										'<a href="success.html" class="btn btnOrange">查看会议</a>' +
									'</dd>' +
								'</dl>'
                        }
                    }
                } else {
                    $("#loginBefore").addClass("hide")
                    $("#loginAfter").removeClass("hide")
                    var $loginInfo = userName + '<i class="iconfont icon-gerenzhongxin"></i>'
                    $("#loginAfter").append($loginInfo)
                    if (data.meetingInfo == undefined) {
                        $txtList = '<dl>' +
								'<dt>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX会议</dt>' +
								'<dd class="time"><i></i>时间：暂无</dd>' + 
								'<dd>' +
									'<a href="hygl.html?add" class="btn btnOrange btnBlue">添加会议</a>' +
								'</dd>' +
							'</dl>'
                    } else {
                        var meetingInfo = data.meetingInfo;
                        sessionStorage.setItem("meetingID", data.meetingInfo.id)
                        //console.log(meetingID)
                        sessionStorage.setItem("info", JSON.stringify(meetingInfo))
                        var read = JSON.parse(localStorage.getItem('info'));
                        $txtList = '<dl>' +
								'<dt> '+ meetingInfo.title + '</dt>' +
								'<dd class="time"><i></i>时间：'+ meetingInfo.beginTimeStr + '</dd>' +
								'<dd>' +
									'<a href="hygl.html?edit" class="btn btnOrange btnBlue">会议管理</a>' + 
									'<a href="hygl.html?add" class="btn btnOrange btnBlue">新增会议</a>' +
								'</dd>' +
							'</dl>'
                    }
                }
                $("#txtList").append($txtList)
            }

        },
        error: function (xhr, type) {
            // 请求失败
            console.log("请求失败")
        }
    })

})

//弹框调用及弹框消失 

$formAlert = '<i class="iconfont icon-bukaixin txtColorRed"></i><span id="formAlertTxt">必须项不能为空</span>'
$("#formAlert").html($formAlert)

function close() {
    $("#formAlert").addClass("show");
    setTimeout(function () {
        $("#formAlert").removeClass("show")
    }, 1000)
}


function getCookie(name) {
    var strCookie = document.cookie;//获取cookie字符串
    var arrCookie = strCookie.split("; ");//分割
    //遍历匹配
    for (var i = 0; i < arrCookie.length; i++) {
        var arr = arrCookie[i].split("=");
        if (arr[0] == name) {
            return arr[1];
        }
    }
    return "";
}

/**
 * 设置cookie
 * @param key
 * @param value
 * @param expiredays
 */
function setCookie(key, value, expiredays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + expiredays);
    document.cookie = key + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
}

function trim(value) {
    if (value == null) {
        return "";
    }
    return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
}

function isEmpty(value) {
    if (value == null || value == undefined || this.trim(value) == "") {
        return true;
    }
    return false;
}

function getOpenId() {
	return sessionStorage.getItem("openId");
    //return getCookie("openId");
}

/**
 * 检测是否已经关注了
 */
function checkHasGz(_this) {
    var data = {openId: openId};
    $.get(url + '/wx/getSubscrible', data, function (result) {
        if (result.code == 0) {
            var wxUser = result.data.wxUser;
            if (!isEmpty(wxUser) && wxUser.subscrible == 1) {
                window.location.href = "signUp.html";
            } else {
                alert("请首先关注公众号!")
            }
        } else {
            alert(result.msg);
        }
    });
}