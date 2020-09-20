// 登录请求
function check(form) {
	var userName = $("#userName").val(),
		userPassword = $("#userPassword").val()
	if(!userName || userName == "") {
		showMsg("请输入用户名")
		form.userName.focus();
		return false;
	} else if(!userPassword || userPassword == "") {
		showMsg("请输入密码")
		form.userPassword.focus();
		return false;
	} else {
		console.log("登录请求前",url)
		$.ajax({
			type: "POST",
			url: url + "/api/login",
			data: {
				"password": userPassword,
				"username": userName
			},
			dataType: "json",
			success: function(data) {
				if(data.code === 0) { //判断返回值，这里根据的业务内容可做调整
					setTimeout(function() { //做延时以便显示登录状态值
						showMsg("正在登录中...");
						$(".dimmer,.loginModal").removeClass("show")
						// 登录后点击报名变成会议管理
						userName = "";
						userPassword = "";
						localStorage.setItem("token",data.token);
						localStorage.setItem("userName",data.data.userName);
						window.location.href = 'index.html'
						//console.log(data);
					}, 100)
				} else {
					showMsg(data.msg); //显示登录失败的原因
					console.log(data,"500")
					return false;
				}
			},
			error: function(data) {
				showMsg(data.msg);
				//console.log("其它",data)
				return false;
			}
		})
	}
}

//错误信息提醒
function showMsg(msg) {
	$("#CheckMsg").text(msg);
}

// 点击退出
function dropOut(){
	$(".dimmer,.loginModal").removeClass("show");// 弹框关闭
	localStorage.removeItem("token") // 清除缓存
	localStorage.removeItem("userName") // 清除缓存
	window.location.href = 'index.html' // 刷新页面
}
