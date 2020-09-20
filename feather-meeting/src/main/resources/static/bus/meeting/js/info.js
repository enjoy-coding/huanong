function uploadFile(ctx, file, obj) {
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
		type : "POST",
		url : ctx + "prd/attachment/api/upload",
		data : data,
		cache : false,
		contentType : false,
		processData : false,
		dataType : 'json',
		success : function(result) {
			if (result.code == web_status.SUCCESS) {
				var item = result.data[0];
				if (typeof obj == "function") {
					obj(result);
				} else if (typeof obj == "string") {
					$(obj).summernote('editor.insertImage', item.filePath, item.fileName);
				}
			} else {
				$.modal.alertError(result.msg);
			}
		},
		error : function(error) {
			$.modal.alertWarning("图片上传失败。");
		}
	});
}
function selectDeptTree(ctx, treeId, treeName) {
	var deptId = $("#" + treeId).val();
	if ($.common.isEmpty(deptId)) {
		deptId = 0;
	}
	var url = ctx + "system/dept/selectDeptTree/" + deptId;
	var options = {
		title : '选择部门',
		width : "380",
		url : url,
		callBack : doSubmit
	};
	$.modal.openOptions(options);

	function doSubmit(index, layero) {
		var body = layer.getChildFrame('body', index);
		$("#" + treeId).val(body.find('#treeId').val());
		$("#" + treeName).val(body.find('#treeName').val());
		layer.close(index);
	}
}
function selectListDialog(options) {
	var defaults = {
		url : '',
		title : '',
		width : "600",
		height : "500",
		id : '',
		name : ''
	};
	var options = $.extend(defaults, options);

	var url = options.url;
	var title = options.title;
	var width = options.width;
	var height = options.height;
	var id = options.id;
	var name = options.name;
	var hideEle = options.hideEle

	var opts = {
		title : title,
		width : width,
		height : height,
		url : url,
		callBack : doSubmit
	}
	$.modal.openOptions(opts);

	function doSubmit(index, layero) {
		var body = layer.getChildFrame('body', index);
		$("#" + id).val(body.find('#' + id).val());
		$("#" + name).val(body.find('#' + name).val());
		if ($.common.isNotEmpty(hideEle) && hideEle.length > 0) {
			for (var i = 0; i < hideEle.length; i++) {
				$("#" + hideEle[i]).parents(".form-group").hide();
			}
		}
		layer.close(index);
	}
}
function selectTplInst(ctx, tplInstId, tplInstName, siteId, hideEle) {
	var url = ctx + 'cms/templateInst/dialogList?id=' + $("#" + tplInstId).val() + "&siteId=" + $("#" + siteId).val();
	var options = {
		url : url,
		title : '选择模板实例',
		id : tplInstId,
		name : tplInstName,
		hideEle : hideEle
	}
	selectListDialog(options);
}
function selectArticleColumnTree(ctx, articleColumnId, articleColumnName) {
	var url = ctx + "cms/articlecolumn/selectArticleColumnTree/" + ($("#" + articleColumnId).val() || '0');
	var options = {
		title : '选择栏目',
		width : "380",
		height : "500",
		url : url,
		callBack : doSubmit
	};
	$.modal.openOptions(options);

	function doSubmit(index, layero) {
		var tree = layero.find("iframe")[0].contentWindow.$._tree;
		//if ($.tree.notAllowParents(tree)) {
		var node = window["layui-layer-iframe" + index].selectedTreeNode;
		$("#" + articleColumnId).val(node.id);
		$("#" + articleColumnName).val(node.name);
		layer.close(index);
	}
}

/**
 * 上传封面
 * @param fileBtn 上传按钮
 * @param img 显示封面的元素id
 */
function uploadCoverImage(fileBtn, imgId, coverImage) {
	var file = $("#" + fileBtn).get(0).files[0];
	uploadFile(ctx, file, callback);

	function callback(result) {
		if ($.common.isNotEmpty(result.data) && result.data.length > 0) {
			var img = result.data[0];
			$("#" + imgId).attr("src", img.filePath);
			$("#" + coverImage).val(img.filePath);
		}
	}
}

function initDatePicker(options) {
	var defaults = {
		language : 'zh-CN',
		domId : "",
		pickerPosition : "bottom-left",
		timeFormat : "yyyy-mm-dd hh:ii:ss",
		fromSeverTime : true
	};
	var options = $.extend(defaults, options);
	$("#" + options.domId).datetimepicker({
		autoclose : true,
		language : options.language,
		pickerPosition : options.pickerPosition,
		format : options.timeFormat
	});
}

//选择会议
function selectMeetingInfo(ctx, meetingId, meetingTitle) {
	var url = ctx + "meeting/info/dialogList";
	var options = {
		title : '选择会议',
		width : "600",
		height : "500",
		url : url,
		callBack : doSubmit
	};
	$.modal.openOptions(options);

	function doSubmit(index, layero) {
		var body = layer.getChildFrame('body', index);
		$("#" + meetingId).val(body.find('#meetingId').val());
		$("#" + meetingTitle).val(body.find('#meetingTitle').val());
		layer.close(index);
	}
}

function selectSite(ctx, siteId, siteName) {
	var siteIdVal = $("#" + siteId).val();
	if ($.common.isEmpty(siteIdVal)) {
		siteIdVal = 0;
	}
	var url = ctx + "cms/site/dialogList/" + siteIdVal;
	var options = {
		title : '选择站点',
		width : "600",
		height : "500",
		url : url,
		callBack : doSubmit
	};
	$.modal.openOptions(options);

	function doSubmit(index, layero) {
		var body = layer.getChildFrame('body', index);
		$("#" + siteId).val(body.find('#siteId').val());
		$("#" + siteName).val(body.find('#siteName').val());
		layer.close(index);
	}
}

function selectTemplate(ctx, tplId, tplName) {
	var tplIdVal = $("#" + tplId).val();
	if ($.common.isEmpty(tplIdVal)) {
		tplIdVal = 0;
	}
	var url = ctx + "cms/template/dialogList/" + tplIdVal;
	var options = {
		title : '选择模板',
		width : "600",
		height : "500",
		url : url,
		callBack : doSubmit
	};
	$.modal.openOptions(options);

	function doSubmit(index, layero) {
		var body = layer.getChildFrame('body', index);
		$("#" + tplId).val(body.find('#tplId').val());
		$("#" + tplName).val(body.find('#tplName').val());
		layer.close(index);
	}
}

function uploadAttaches(options) {
	var defaults = {
		uploadBtnId : "attachesBtn",
		showId : "zbt-table",
		hideText : "attaches",
		ctx : "",
		titles : [
			{
				title : "附件名称",
				width : 500,
				keyName : "fileName"
			}, {
				title : "附件大小(b)",
				width : 200,
				keyName : "fileSize"
			} ]
	};
	options = $.extend(defaults, options);

	var file = $("#" + options.uploadBtnId).get(0).files;
	var titles = options.titles;
	var showId = options.showId;
	var hideText = options.hideText;
	var hideTextVal = $("#" + hideText).val();
	//上传文件
	uploadFile(options.ctx, file, callback);

	function callback(result) {
		var code = result.code;
		if (code == 0) {
			var data = result.data;
			//说明上传成功
			if ($.common.isNotEmpty(data) && data.length > 0) {
				if ($("#" + showId).find("table").length > 0) {
					for (var i = 0; i < data.length; i++) {
						$.fn.ZBTable.addRow(showId, titles, data[i]);
					}
				} else {
					$('#' + options.showId).ZBTable({
						titles : titles,
						data : data
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

function initAttachesTable(showId, str) {
	var data = [];
	if ($.common.isNotEmpty(str)) {
		var dataStr = str.split(";");
		if (dataStr.length > 0) {
			for (var i = 0; i < dataStr.length; i++) {
				var dataI = dataStr[i];
				if (dataI != "") {
					data.push(JSON.parse(dataI));
				}

			}
			var titles = [
				{
					title : "附件名称",
					width : 500,
					keyName : "fileName"
				}, {
					title : "附件大小(b)",
					width : 200,
					keyName : "fileSize"
				} ]
			$('#' + showId).ZBTable({
				titles : titles,
				data : data
			});
		}
	}
}

/**
 * 初始化编辑器
 * @param ctx 路径
 * @param domValId 编辑器值的domId,为编辑器填充内容
 */
function initSummernote(ctx, height, domValId) {
	if ($.common.isEmpty(height)) {
		height = 300;
	}
	$('.summernote').summernote({
		placeholder : '请输入内容',
		height : height,
		lang : 'zh-CN',
		followingToolbar : false,
		callbacks : {
			onImageUpload : function(files) {
				uploadFile(ctx, files[0], this);
			}
		}
	});
	var content = $("#" + domValId).val();
	$('#editor').summernote('code', content);
}


function addAttachesDelEvent(domId) {
	$(document).on("click", "a", function() {
		var str = $("#" + domId).val();
		$tr = $(this).parents("tr");
		$tr.children("td").each(function(index, item) {
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