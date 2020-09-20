function checkWxOauth() {
    var getQueryValue = function (name) {
        var query = decodeURI(window.location.search.substring(1));
        var vars = query.split("&");
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] == name) {
                return pair[1];
            }
        }
        return null;
    };
    
    sessionStorage.removeItem("openId");
    
    var openId = getQueryValue("openId");
    var code = getQueryValue("code");
    if (openId) {
    	$.ajax({
            url: "http://bfgl.hzau.edu.cn/wx/checkOpenId",
            data: {
            	openId: openId,
            	code: code
            },
            dataType: "json",
            async: false,
            error: function (request) {
                alert("系统错误");
            },
            success: function (data) {
            	sessionStorage.setItem("openId", openId);
            }
    	});
    } else {
        var callback = "http://bfgl.hzau.edu.cn/bus/meeting/index.html";
        if (code) {
            location.href = "http://bfgl.hzau.edu.cn/wx/getOpenId?code=" + code + "&url=" + callback;
        } else {
            location.href = "http://bfgl.hzau.edu.cn/wx/getCode?url=" + callback;
        }
    }
}

checkWxOauth();