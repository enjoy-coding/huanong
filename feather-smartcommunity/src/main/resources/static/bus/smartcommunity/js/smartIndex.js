featherCmsScript.register({
	element : "",
	onLoad : function(cmsOptions) {
		$.ajax({
			url:featherCmsScript.ctx + 'zhzl/api/getTree',
			ascnc:false,
			success:function(res) {
				$.fn.zTree.init($("#zTree"), setting, res.data);
			}
		});
	}
});
			
// 居民信息
featherCmsScript.register({
	element : "",
	onLoad : function(cmsOptions) {
        getPersonInfo("SQ000001","");
	}
});


// 人员巡检
featherCmsScript.register({
    element : "",
    onLoad : function(cmsOptions) {
        getRwzsCount("SQ000001","");
    }
});

// 投诉建议   通知公告
featherCmsScript.register({
    element : "",
    onLoad : function(cmsOptions) {
      getTsjy("SQ000001","");
    }
});

// 房屋信息
featherCmsScript.register({
	element: "",
	onLoad: function (cmsOptions) {
        getHouse("SQ000001","")
	}
});
