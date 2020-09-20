var ydxj = {

    id: '', //当前任务ID
    addPicNum: 0,//设定起始上传图片编码
    taskName: '',
    inspectWorker: '', //巡检人员
    inspectWorkerId: '', //巡检人员ID
    inspectAddress: '', // 巡检地点
    serviceStatusJson: {}, //设备状态json
    serviceTypeJson: {},   //设备类型json
    xjjlId: '', //巡检记录ID
    ydxjSbPicStatus: 0,//是否新上传图片状态 0没， 1上传
    ydxjSbid: "",  //移动巡检记录ID
    ydxjSbidOpenType: '',//新增还是修改状态
    ydxjSbServiceType: '',//修改巡检设备的服务回显选项
    ydxjSbServiceStatus: '',//修改巡检设备的服务状态回显选项
    ydxjSbRemark: '',//修改巡检设备的备注回显
    HistorySbNum: '',//移动巡检修改记录的历史设备数量
    picSbid: '', //上传图片路径

    //发起任务打开
    xfrw: function () {
        $("#detail").html("");
        $("#detail").load(ctx + "aupipes/inspect/appAddTask", function (data) {
            $("#detail").css("display", "block");
        });
    },

    //关闭发起任务页面
    closeAddTask: function () {
        $("#detail").html("");
        $("#detail").css("display", "none");
    },


    //移动巡检页面1 返回
    ydxj1Back: function () {
        //刷新巡查列表数据
        /* page = 1, xjArr = [];
         var res = {status: '', startdate: '', enddate: '', pageNum: page, pageSize: pageSize};
         searchXjResult(res);*/
        //置空修改选项的设备数据
        ydxj.ydxjSbid = "";
        ydxj.ydxjSbidOpenType = "";
        ydxj.ydxjSbServiceType = "";
        ydxj.ydxjSbServiceStatus = "";
        $("#result").css("display", "block");
        $("#detail").css("display", "none");
        $("#detail").html("");
    },

    //查看详情返回按钮
    xjlbDetailBack : function (){
        $("#result").css("display", "block");
        $("#detail").css("display", "none");
        $("#detail").html("");
    },

    //移动巡检页面1 新增设备
    ydxj1Add: function () {
        ydxj.ydxj1AddEditPage("add", "");
        ydxj.ydxjSbidOpenType = "add";
    },

    //
    ydxj1Edit: function (sbId, serviceTypeId, serviceStatus, remark) {
        ydxj.ydxjSbid = sbId;//巡检记录id
        ydxj.ydxjSbidOpenType = "edit";
        ydxj.ydxjSbServiceType = serviceTypeId;
        ydxj.ydxjSbServiceStatus = serviceStatus;
        ydxj.ydxjSbRemark = remark;
        ydxj.ydxj1AddEditPage("edit", sbId);
        ydxj.picListInit();
    },

    //
    ydxj1AddEditPage: function (openType, sbId) {
        if (openType == "add") {
            ydxj.HistorySbNum = '';
        } else {
            ydxj.ydxjSbid = sbId;
        }
        $("#tapInfo").html("");
        $("#tapInfo").load(ctx + "aupipes/detail/ydxjAdd", function (data) {
            var str = "";
            if (ydxj.ydxjSbRemark == "null") {
                ydxj.ydxjSbRemark = "";
            }
            str += `<div class="layui-row layui-col-space5">
			<div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
				<div class="layui-form-item">
					<label class="layui-form-label">巡检人：</label>
					<div class="layui-input-block">
					    <input type="hidden" class="layui-input" name="inspectWorkerId" id="inspectWorkerId"  value="` + ydxj.inspectWorkerId + `" >
						<input type="text" class="layui-input" value="` + ydxj.inspectWorker + `" name="inspectWorker" id="inspectWorker" readonly="readonly">
					</div>
				</div>
			</div>
			<div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
				<div class="layui-form-item">
					<label class="layui-form-label">地点类型：</label>
					<div class="layui-input-block">
						<select name="serviceType" id="serviceType" lay-filter="aihaoSblx">
							<option value=""></option>
							<option value="0">配电房</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
				<div class="layui-form-item">
					<label class="layui-form-label">巡检地点：</label>
					<div class="layui-input-block">
						<input type="text" class="layui-input" name="serviceAddress" id="serviceAddress" value="` + ydxj.inspectAddress + `" readonly="readonly">
					</div>
				</div>
			</div>
			<div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
				<div class="layui-form-item">
					<label class="layui-form-label">设备状态：</label>
					<div class="layui-input-block">
						<select name="serviceStatus" id="serviceStatus"  lay-filter="aihaoStatus">
							<option value=""></option>
							<option value="0">无异常</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
				<div class="layui-form-item">
					<label class="layui-form-label">异常记录：</label>
					<div class="layui-input-block">
						<button type="button" class="layui-btn bg_green fl" style="width:18%" onclick="ydxj.ydxj2Gl();">关联</button>
					</div>
				</div>
			</div>
			<div class="exceptionRecord layui-row">
				
			</div>`;
            if (openType == "add") {
                str += `<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
                            <div class="layui-form-item">
                                <label class="layui-form-label">备注：</label>
                                <div class="layui-input-block">
                                    <textarea name="description" id="description" required lay-verify="required" placeholder="请输入内容" class="layui-textarea"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
                            <div class="layui-form-item">
                                <label class="layui-form-label">多媒体：</label>
                                <div class="layui-input-block">
                                    <button type="button" class="layui-btn bg_green upload" id="test1">选择图片</button>
                                </div>
                            </div>
                        </div>
                        <div class="layui-upload newSwiper" id="newSwiper" >
                            <div class="layui-upload-list uploadList" id="picList">
                            </div>
                        </div>
                        <div class="bottomBtnGrounp pa">
                                <button id="fileUpload" type="button" class="layui-btn layui-btn btn_orange" style="display: none">选择图片</button>
                        </div>
                    </div>`;
            } else if (openType == "edit") {
                str += `<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
                            <div class="layui-form-item">
                                <label class="layui-form-label">备注：</label>
                                <div class="layui-input-block">
                                    <textarea name="description" id="description" required lay-verify="required" placeholder="请输入内容" class="layui-textarea">` + ydxj.ydxjSbRemark + `</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="oldPicDiv">
                                <label class="layui-form-label">历史图片：</label>
                                <div class="swiper-container oldSwiper" style="padding-left: 1px;padding-top: 20px">
                                    <div class="layui-upload-list uploadList" id="oldPicList">
                                    </div>
                    
                                    <!-- 如果需要滚动条 -->
                                    <div id="swiper-scrollbar-oldPicList" class="swiper-scrollbar"></div>
                                </div>
                          </div>
                        <div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">多媒体：</label>
                                    <div class="layui-input-block">
                                        <button type="button" class="layui-btn bg_green upload" id="test1">选择图片</button>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-upload newSwiper" id="newSwiper" >
                                <div class="layui-upload-list uploadList" id="picList">
                                </div>
                            </div>
                            <div class="bottomBtnGrounp pa">
                                    <button id="fileUpload" type="button" class="layui-btn layui-btn btn_orange" style="display: none">上传图片</button>
                            </div>
                        </div>`;
            }
            ;
            //添加巡检记录页面
            $("#ydxj2XjlbForm").html(str);
            ydxj.addPicNum = 0;
            ydxj.ydxjSbPicStatus = 0;
            layui.use(['form', 'upload'], function () {
                var form = layui.form;
                //加载设备类型下拉选择
                $.get(ctx + "system/dict/data/api/list", {
                    dictType: "aup_service_type",
                    pageSize: 30
                }, function (data) {
                    var html = '';
                    for (var i = 0; i < data.data.length; i++) {
                        html += '<option value="' + data.data[i].dictValue + '">' + data.data[i].dictLabel + '</option>';
                    }
                    $("select[name='serviceType']").html(html);
                    form.render('select');
                    //默认勾选
                    var select = document.getElementById("serviceType");
                    for (var i = 0; i < select.options.length; i++) {
                        if (select.options[i].value == (ydxj.ydxjSbServiceType + "")) {
                            select.options[i].selected = true;
                            break;
                        }
                    }
                    form.render('select');
                });
                //加载设备状态下拉选择
                $.get(ctx + "system/dict/data/api/list", {
                    dictType: "aup_service_status",
                    pageSize: 30
                }, function (data) {
                    var html = '';
                    var obj = {}, obj1 = {};
                    for (var i = 0; i < data.data.length; i++) {
                        obj1[data.data[i].dictValue] = data.data[i].dictLabel;
                        obj = Object.assign(obj, obj1);
                        html += '<option value="' + data.data[i].dictValue + '">' + data.data[i].dictLabel + '</option>';
                    }
                    console.log(obj);
                    $("select[name='serviceStatus']").html(html);
                    form.render('select');
                    //默认选中
                    var select = document.getElementById("serviceStatus");
                    for (var i = 0; i < select.options.length; i++) {
                        if (select.options[i].value == (ydxj.ydxjSbServiceStatus + "")) {
                            select.options[i].selected = true;
                            break;
                        }
                    }
                    form.render('select');
                });
                //添加图片方法
                layui.upload.render({
                    elem: '#test1', //绑定元素
                    url: ctx + "aupipes/info/add", //上传接口
                    data: {
                        file_code: function () {
                            if (ydxj.ydxjSbidOpenType == "edit") {
                                return ydxj.ydxjSbid;
                            } else if (ydxj.ydxjSbidOpenType == "add") {
                                return ydxj.picSbid;
                            }
                        }
                    },
                    auto: false,
                    choose: function (obj) {
                        //将每次选择的文件追加到文件队列
                        var files = obj.pushFile();
                        ydxj.ydxjSbPicStatus = 1;

                        //$("#fileUpload").show(); //不显示图片提交框
                        // $("#picList").empty();
                        //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
                        obj.preview(function (index, file, result) {
                            ydxj.addImg(result);
                            //obj.resetFile(index, file, '123.jpg'); //重命名文件名，layui 2.3.0 开始新增
                            //这里还可以做一些 append 文件列表 DOM 的操作
                            //obj.upload(index, file); //对上传失败的单个文件重新上传，一般在某个事件中使用
                            //delete files[index]; //删除列表中对应的文件，一般在某个事件中使用
                        });
                    },
                    before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                    },
                    allDone: function (obj) { //当文件全部被提交后，才触发
                        if (ydxj.ydxjSbidOpenType == "edit") {
                            ydxj.ydxj2SaveInfo(ydxj.ydxjSbidOpenType, ydxj.ydxjSbid);
                            ydxj.picListInit();
                        } else if (ydxj.ydxjSbidOpenType == "add") {
                            //ydxj.ydxj2SaveInfo(ydxj.ydxjSbidOpenType,"");
                            $("#detail").css("display", "block");
                            $("#tapInfo").css("display", "none");
                            $("#tapInfo").html("");
                            startLook(ydxj.id);
                            parent.layer.msg("添加成功");
                        }
                        $("#picList").empty();
                        $("#fileUpload").hide();
                    },
                    error: function () {
                        //请求异常回调
                        $.modal.closeLoading();
                        $.modal.enable();
                    },
                    multiple: true,
                    number: 9,
                    accept: 'file', //允许上传的文件类型
                    acceptMime: 'image/jpg, image/jpeg, image/bmp, image/png',
                    exts: 'jpg|png|bmp|jpeg',
                    size: 20480, //最大允许上传的文件大小20MB
                    //bindAction: '#ydxj2Save'
                    bindAction: '#fileUpload'
                });
            });
            //若是修改页面读取巡检设备
            if (openType == "edit") {
                $.post(ctx + "aupipes/inspectservice/list", {
                    "taskId": ydxj.id,
                    "detailId": ydxj.ydxjSbid
                }, function (result) {
                    if (result.code == 0) {
                        ydxj.HistorySbNum = "";
                        if (result.rows.length > 0) {
                            ydxj.HistorySbNum = result.rows.length;
                        }
                        var str = "";
                        var dictStr = "";
                        //添加设备列表
                        for (var i = 0; i < result.rows.length; i++) {
                            if (result.rows[i].serviceSituation == null) {
                                result.rows[i].serviceSituation = "";
                            }
                            dictStr = "";
                            $.ajax({
                                url: ctx + "aupipes/serviceinfo/list?id=" + result.rows[i].serviceId,
                                type: "POST",
                                async: false,
                                dataType: "json",
                                success: function (resultData) {
                                    if (resultData.rows[0].serviceStatus != null && resultData.rows[0].serviceStatus != "") {
                                        var statusArr = resultData.rows[0].serviceStatus.split("|");
                                        for (var k = 0; k < statusArr.length; k++) {
                                            dictStr += '<option value="' + statusArr[k] + '">' + statusArr[k] + '</option>';
                                        }
                                    }
                                    str += `<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 aaa" id="` + result.rows[i].id + `">
                                    <input type="hidden" id="service` + result.rows[i].id + `" value="` + result.rows[i].serviceId + `">
                                    <div class="layui-form-item">
                                        <label class="layui-form-label">` + (i + 1) + `.名称：</label>
                                        <div class="layui-input-block">
                                            <span class="formeTxt" name="serviceName` + result.rows[i].id + `" id="serviceName` + result.rows[i].id + `">` + result.rows[i].serviceName + `</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
                                    <div class="layui-form-item">
                                        <label class="layui-form-label">状态：</label>
                                        <div class="layui-input-block">
                                            <select name="exceptionType` + result.rows[i].id + `" id="exceptionType` + result.rows[i].id + `" lay-filter="exceptionType` + result.rows[i].id + `">
                                                ` + dictStr + `
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
                                    <div class="layui-form-item">
                                        <label class="layui-form-label">情况说明</label>
                                        <div class="layui-input-block">
                                            <textarea name="description` + result.rows[i].id + `" id="description` + result.rows[i].id + `" required lay-verify="required" placeholder="请输入内容" class="layui-textarea" style="min-height:40px;">` + result.rows[i].serviceSituation + `</textarea>
                                        </div>
                                    </div>
                                </div>`;
                                }
                            })
                        }
                        $(".exceptionRecord").html("");
                        $(".exceptionRecord").html(str);
                        for (var key in result.rows) {
                            var id = result.rows[key].id;
                            var select = document.getElementById("exceptionType" + id);
                            for (var p = 0; p < select.options.length; p++) {
                                if (select.options[p].value == (result.rows[key].serviceStatus)) {
                                    select.options[p].selected = true;
                                    break;
                                }
                            }
                            form.render();
                        }
                        /*$.get(ctx + "system/dict/data/api/list", {
                            dictType: "aup_exception_type",
                            pageSize: 30
                        }, function (data) {
                            dictStr = '<option value=""></option>';
                            var obj = {}, obj1 = {};
                            for (var i = 0; i < data.data.length; i++) {
                                obj1[data.data[i].dictValue] = data.data[i].dictLabel;
                                obj = Object.assign(obj, obj1);
                                dictStr += '<option value="' + data.data[i].dictValue + '">' + data.data[i].dictLabel + '</option>';
                            }
                            //添加设备列表
                            for(var i =0;i<result.rows.length;i++){
                                if(result.rows[i].serviceSituation==null){
                                    result.rows[i].serviceSituation="";
                                }
                                str+=`<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 aaa" id="`+result.rows[i].id+`">
                                <input type="hidden" id="service`+result.rows[i].id+`" value="`+result.rows[i].serviceId+`">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">`+(i+1)+`.名称：</label>
                                    <div class="layui-input-block">
                                        <span class="formeTxt" name="serviceName`+result.rows[i].id+`" id="serviceName`+result.rows[i].id+`">`+result.rows[i].serviceName+`</span>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">状态：</label>
                                    <div class="layui-input-block">
                                        <select name="exceptionType`+result.rows[i].id+`" id="exceptionType`+result.rows[i].id+`" lay-filter="exceptionType`+result.rows[i].id+`">
                                            `+dictStr+`
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">情况说明</label>
                                    <div class="layui-input-block">
                                        <textarea name="description`+result.rows[i].id+`" id="description`+result.rows[i].id+`" required lay-verify="required" placeholder="请输入内容" class="layui-textarea" style="min-height:40px;">`+result.rows[i].serviceSituation+`</textarea>
                                    </div>
                                </div>
                            </div>`;
                            }
                            $(".exceptionRecord").html("");
                            $(".exceptionRecord").html(str);
                            form.render();
                            for(var key in result.rows){
                                var id = result.rows[key].id;
                                var select = document.getElementById("exceptionType"+id);
                                for (var i = 0; i < select.options.length; i++){
                                    if (select.options[i].value == (result.rows[key].serviceStatus)){
                                        select.options[i].selected = true;
                                        break;
                                    }
                                }
                                form.render();
                            }
                        });*/
                    }
                })
            }
            //隐藏数据
            $("#detail").css("display", "none");
            $("#tapInfo").css("display", "block");
        });
    },

    //加载之前上传的图片
    picListInit: function () {
        //获取多媒体内容
        $.ajax({
            type: "POST",
            url: ctx + "aupipes/info/list", //上传接口
            data: {"fileCode": ydxj.ydxjSbid},
            contentType: 'application/x-www-form-urlencoded',
            dataType: "json",
            success: function (result) {
                if (result.code == 0) {
                    var picList = result.rows;
                    if (picList.length == 0) {
                        $("#oldPicDiv").hide();
                        return;
                    }
                    $("#oldPicDiv").show();
                    $("#oldPicList").html("");
                    //$("#oldPicList").empty();
                    for (i = 0; i < picList.length; i++) {
                        var item = picList[i];
                        ydxj.addInternalImg(item.fileId, item.filePath)
                    }
                }
            }
        });
    },
    //添加图片
    addInternalImg: function (id, filePath) {
        //var url = ctx + filePath + "?text=图片加载中";
        var url = "";
        if (ctx == "/") {
            url = filePath + "?text=图片加载中";
        } else {
            url = ctx + filePath + "?text=图片加载中";
        }
        var ulDiv = $("#oldPicList");
        // ulDiv.append("<div class=\"swiper-slide\"><img src=\"../../../img/1.png\"></div>");
        /*ulDiv.append("<div class=\"swiper-slide\"><img onclick=\"showImg("+"#picList"+")\" src="+url+"></div>");*/
        ulDiv.append("<span class=\"swiper-slide\"><img onclick=\"ydxj.showImg('#oldPicList')\" layer-src=" + url + " src=" + url + " alt=\"\">" +
            "<i class=\"iconfont iconguanbi icon del\" onclick=\"ydxj.delOldFile(" + "'" + id + "')\"></i>" +
            "</span>");

        ydxj.swiperInit(".oldSwiper");
    },

    //添加图片
    addImg: function (base64) {
        var ulDiv = $("#picList");
        ulDiv.append("<span id=" + "'span" + ydxj.addPicNum + "'><img src=" + base64 + "><i class=\"iconfont iconguanbi icon del\" onclick=\"ydxj.delFile(" + "'span" + ydxj.addPicNum + "')\"></i></span>");
        ydxj.swiperInit(".newSwiper");
    },
    //删除图片
    delOldFile: function (id) {
        layer.confirm('确定删除么？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                type: "POST",
                url: ctx + "aupipes/info/remove", //上传接口
                data: {"ids": id},
                contentType: 'application/x-www-form-urlencoded',
                dataType: "json",
                success: function (result) {
                    layer.msg(result.msg)
                    if (result.code == 0) {
                        ydxj.picListInit();
                    }
                }
            });
        });
    },
    delFile: function (id) {
        layer.confirm('确定删除么？', {}, function () {
            var c = document.getElementById(id);
            c.parentNode.removeChild(c);
            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(index);
            parent.layer.msg("删除成功");
        });
    },
    showImg: function (id) {
        layer.photos({
            photos: id,
            move: false,
            anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
        });
    },
    //swiper初始化
    swiperInit: function (id) {
        var mySwiper = new Swiper('.swiper-container', {
            loop: false, // 循环模式选项
            // 如果需要滚动条
            scrollbar: {
                el: '.swiper-scrollbar',
            },
            spaceBetween: 8,
            slidesPerView: 'auto',
        })
    },

    //移动巡检页面1 结束任务
    ydxj1Stop: function () {
        $.post(ctx + "aupipes/inspect/edit", {"id": ydxj.id, "inspectStatus": 2}, function (data) {
            if (data.code == 0) {
                $("#result").load(ctx + "mobile/xjlb", function (data) {
                    xjlbResultHeight();
                    //渲染开始和结束时间
                    laydate.render({elem: '#test3'});
                    laydate.render({elem: '#test4'});
                    form.render();
                    //默认加载初始数据
                    page = 1, xjArr = [];
                    var res = {status: '', startdate: '', enddate: '', pageNum: page, pageSize: pageSize};
                    searchXjResult(res);
                    //监听提交
                    form.on('submit(xjFormSearch)', function (data) {
                        $("#xjListBox").html("");
                        $(".listMore").css("display", "block");
                        xjArr = [];
                        page = 1;
                        var test1 = $("#test3").val(),
                            test2 = $("#test4").val();

                        res = {
                            status: data.field.status,
                            startdate: test1,
                            enddate: test2,
                            pageNum: page,
                            pageSize: pageSize
                        };
                        searchXjResult(res);
                    });

                    $(".listMore").on("click", function () {
                        res.pageNum = page + 1;
                        if (total > xjArr.length) {
                            searchXjResult(res);
                        } else {
                            $(".listMore").css("display", "none");
                        }
                    });
                });
                $("#detail").css("display", "none");
                $("#detail").html("");
                parent.layer.msg("成功结束任务！");
            }
        });
    },

    //移动巡检页面2 返回
    ydxj2Back: function () {
        startLook(ydxj.id);
        $("#detail").css("display", "block");
        $("#tapInfo").css("display", "none");
        $("#tapInfo").html("");
    },

    //移动巡检页面2 新增设备保存
    ydxj2Save: function () {
        //保存时模拟点击上传图片
        //$("#fileUpload").click();
        /*if(ydxj.ydxjSbPicStatus==0){
            ydxj.ydxj2SaveInfo(ydxj.ydxjSbidOpenType,ydxj.ydxjSbid);
        }*/
        ydxj.ydxj2SaveInfo(ydxj.ydxjSbidOpenType, ydxj.ydxjSbid);
    },
    ydxj2SaveInfo: function (openType, sbId) {

        //存储巡检记录的巡检设备
        var ids = "";
        var serviceName = "";
        var serviceStatus = "";
        var serviceSituation = "";
        var serviceId = "";
        var list = $('.aaa');
        if (list.length > 0) {
            for (var i = 0; i < list.length; i++) {
                var id = list[i].id;
                var sub = id.substring(0, 9);
                var newId = id.substring(9);
                if (i == 0) {
                    if (sub == "sbService") {
                        ids += "null";
                        serviceName += ($("#serviceName" + newId + "").text());
                        //serviceStatus += ($("#exceptionType" + id + "").text());
                        serviceStatus += ($("#exceptionType" + newId + " option:selected").val());
                        if (($("#description" + newId + "").val()) == "") {
                            serviceSituation += (null);
                        } else {
                            serviceSituation += ($("#description" + newId + "").val());
                        }
                        serviceId += (newId);
                    } else {
                        ids += id;
                        serviceName += ($("#serviceName" + id + "").text());
                        serviceStatus += ($("#exceptionType" + id + " option:selected").val());
                        if (($("#description" + id + "").val()) == "") {
                            serviceSituation += (null);
                        } else {
                            serviceSituation += ($("#description" + id + "").val());
                        }
                        serviceId += ($("#service" + id + "").val());
                    }
                } else {
                    if (sub == "sbService") {
                        ids += ",null";
                        serviceName += ("," + ($("#serviceName" + newId + "").text()));
                        //serviceStatus += (","+($("#exceptionType" + newId + "").text()));
                        serviceStatus += ("," + ($("#exceptionType" + newId + " option:selected").val()));
                        if (($("#description" + newId + "").val()) == "") {
                            serviceSituation += ("," + (null));
                        } else {
                            serviceSituation += ("," + ($("#description" + newId + "").val()));
                        }
                        serviceId += ("," + newId);
                    } else {
                        ids += ("," + id);
                        serviceName += ("," + ($("#serviceName" + id + "").text()));
                        serviceStatus += ("," + ($("#exceptionType" + id + " option:selected").val()));
                        if (($("#description" + id + "").val()) == "") {
                            serviceSituation += ("," + (null));
                        } else {
                            serviceSituation += ("," + ($("#description" + id + "").val()));
                        }
                        serviceId += ("," + ($("#service" + id + "").val()));
                    }
                }
            }
            //保存巡检记录
            var ul = "";
            if (openType == "add") {
                ul = "aupipes/detail/addNew";

                var xjjl = {
                    "inspectWorker": $("#inspectWorker").val()
                    , "inspectWorkerId": ydxj.inspectWorkerId
                    , "serviceTypeId": $("#serviceType option:selected").val()
                    , "serviceName": $("#serviceName").val()
                    , "taskName": ydxj.taskName
                    , "serviceAddress": $("#serviceAddress").val()
                    , "serviceStatus": $("#serviceStatus option:selected").val()
                    , "description": $("#description").val()
                    , "taskId": ydxj.id
                };
                var xjsb = {
                    "ids": ids,
                    "serviceName": serviceName,
                    "serviceStatus": serviceStatus,
                    "serviceSituation": serviceSituation,
                    "taskId": ydxj.id,
                    "detailId": ydxj.ydxjSbid,
                    "serviceIds": serviceId
                };
                xjjl = JSON.stringify(xjjl);
                xjsb = JSON.stringify(xjsb);

                $.ajax({
                    url: ctx + ul,
                    type: "post",
                    async: false,
                    data: {
                        "xjjl": xjjl,
                        "xjsb": xjsb
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 0) {
                            ydxj.picSbid = data.data;
                            //保存巡检人员坐标
                            wxJsSdk.getLocation(function (res) {
                                lat = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                                long = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                                $.ajax({
                                    url: ctx + "aupipes/coordinate/add",
                                    data: {"taskId":ydxj.id,"longitude": long, "latitude": lat,"altitude":""},
                                    type: "POST",
                                    dataType: "json",
                                    async: false,
                                    success: function (xyData) {
                                        if(xyData==0){
                                            console.log("保存坐标成功！")
                                        }
                                    },
                                })
                            });
                            if (typeof(($("#picList img")[0])) == "undefined") {
                                $("#detail").css("display", "block");
                                $("#tapInfo").css("display", "none");
                                $("#tapInfo").html("");
                                startLook(ydxj.id);
                                parent.layer.msg("保存成功");
                            } else {
                                $("#fileUpload").click();
                            }
                        }
                    }
                })
            } else if (openType == "edit") {
                ul = "aupipes/detail/edit?id=" + sbId;

                //修改设备
                $.post(ctx + "aupipes/inspectservice/appEdit",
                    {
                        "ids": ids,
                        "serviceName": serviceName,
                        "serviceStatus": serviceStatus,
                        "serviceSituation": serviceSituation,
                        "taskId": ydxj.id,
                        "detailId": ydxj.ydxjSbid,
                        "serviceIds": serviceId
                    }, function (data) {
                    });

                //保存记录信息
                $.ajax({
                    url: ctx + ul,
                    type: "post",
                    async: false,
                    data: {
                        "inspectWorker": $("#inspectWorker").val()
                        , "inspectWorkerId": ydxj.inspectWorkerId
                        , "serviceTypeId": $("#serviceType option:selected").val()
                        , "serviceName": $("#serviceName").val()
                        , "taskName": ydxj.taskName
                        , "serviceAddress": $("#serviceAddress").val()
                        , "serviceStatus": $("#serviceStatus option:selected").val()
                        , "description": $("#description").val()
                        , "taskId": ydxj.id
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 0) {
                            //保存巡检人员坐标
                            wxJsSdk.getLocation(function (res) {
                                lat = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                                long = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                                $.ajax({
                                    url: ctx + "aupipes/coordinate/add",
                                    data: {"taskId":ydxj.id,"longitude": long, "latitude": lat,"altitude":""},
                                    type: "POST",
                                    dataType: "json",
                                    async: false,
                                    success: function (xyData) {
                                        if(xyData==0){
                                            console.log("保存坐标成功！")
                                        }
                                    },
                                })
                            });
                            $("#fileUpload").click();
                            startLook(ydxj.id);
                            $("#detail").css("display", "block");
                            $("#tapInfo").css("display", "none");
                            $("#tapInfo").html("");
                            parent.layer.msg("保存成功");
                        }
                    }
                })
            }
        }
    },
    //移动巡检页面2 关联设备页面
    ydxj2Gl: function () {
        $("#ydxjSbglInfo").load(ctx + "aupipes/detail/ydxjSbgl", function (data) {
            $.post(ctx + "aupipes/serviceinfo/list", function (data) {
                if (data.code == 0) {
                    var str = '';
                    for (var i = 0; i < data.rows.length; i++) {
                        str += `<div class="layui-row layui-col-space5">
                                    <input type="hidden" value="1">
                                    <div class="layui-col-xs2 layui-col-md2 layui-col-md2">
                                        <input type="checkbox" name="ydxjsb" value="` + data.rows[i].id + `"/>
                                    </div>
                                    <div class="layui-col-xs4 layui-col-md4 layui-col-md4">
                                        ` + data.rows[i].serviceName + `
                                    </div>
                                    <div class="layui-col-xs3 layui-col-md3 layui-col-md3">
                                        ` + data.rows[i].serviceModelNum + `
                                    </div>
                                    <div class="layui-col-xs3 layui-col-md3 layui-col-md3">
                                        ` + data.rows[i].serviceAddress + `
                                    </div>
                                    <div style="display: none;" class="layui-col-xs3 layui-col-md3 layui-col-md3">
                                         <input value="` + data.rows[i].serviceStatus + `"/>
                                    </div>
                                </div>`;
                    }
                    $(".wxdaTableBody").html("");
                    $(".wxdaTableBody").html(str);
                }
            });
            $("#ydxjSbglInfo").css("display", "block");
            $("#tapInfo").css("display", "none");
        })
    },

    //移动巡检页面3 返回
    ydxj3Back: function () {
        $("#ydxjSbglInfo").css("display", "none");
        $("#tapInfo").css("display", "block");

    },

    //移动巡检页面3  保存
    ydxj3Save: function () {
        var count = 1;
        if (ydxj.HistorySbNum != '') {
            count = (ydxj.HistorySbNum + 1);
        }
        var str = "";
        $("input[name='ydxjsb']:checked").each(function (i) {
            var ydxjsbVal = $(this).val();
            var ydxjsbName = $(this).parents("div").parents('div').children("div").eq(1)[0].innerText; //设备名称
            var ydxjsbXh = $(this).parents("div").parents('div').children("div").eq(2)[0].innerText; //设备型号
            var ydxjsbAddress = $(this).parents("div").parents('div').children("div").eq(3)[0].innerText; //设备地址
            var ydxjsbStatus = $(this).parents("div").parents('div').children("div").eq(4).children("input").eq(0)[0].value; //设备地址
            var ydxjsbValue = $(this).parents("div").parents('div').children("input").eq(0).context.value;
            str += `<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 aaa" id="sbService` + ydxjsbValue + `">
                        <input type="hidden" value="` + ydxjsbValue + `" >
                        <div class="layui-form-item">
                            <label class="layui-form-label">` + (i + count) + `.名称：</label>
                            <div class="layui-input-block">
                                <span class="formeTxt" name="serviceName` + ydxjsbValue + `" id="serviceName` + ydxjsbValue + `"  >` + ydxjsbName + `</span>
                                <input type="hidden" value="` + ydxjsbXh + `"  name="serviceStatus">
                                <input type="hidden" value="` + ydxjsbAddress + `"  name="serviceSituation">
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
                        <div class="layui-form-item">
                            <label class="layui-form-label">状态：</label>
                            <div class="layui-input-block">
                                <select name="exceptionType" id="exceptionType` + ydxjsbValue + `" lay-filter="aihao">`
            if (ydxjsbStatus != null && ydxjsbStatus != "") {
                var sbStatusArr = ydxjsbStatus.split("|");
                for (var i = 0; i < sbStatusArr.length; i++) {
                    str += '<option value="' + sbStatusArr[i] + '">' + sbStatusArr[i] + '</option>';
                }
            }
            str += `</select>
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
                        <div class="layui-form-item">
                            <label class="layui-form-label">情况说明</label>
                            <div class="layui-input-block">
                                <textarea name="description` + ydxjsbValue + `" id="description` + ydxjsbValue + `" required lay-verify="required" placeholder="请输入内容" class="layui-textarea" style="min-height:40px;"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-xs12 layui-col-sm12 layui-col-md12" style="display:none">
                        <div class="layui-form-item">
                            <label class="layui-form-label">设备状态</label>
                            <div class="layui-input-block">
                                <input id="sbStatus` + ydxjsbValue + `"  name="sbStatus` + ydxjsbValue + `" value="` + ydxjsbStatus + `" >
                            </div>
                        </div>
                    </div>`;
        });
        $(".exceptionRecord").append(str);
        //加载巡检设备状态下拉选择
        /*$.get(ctx + "system/dict/data/api/list", {
            dictType: "aup_exception_type",
            pageSize: 30
        }, function (data) {
            var html = '';
            var obj = {}, obj1 = {};
            for (var i = 0; i < data.data.length; i++) {
                obj1[data.data[i].dictValue] = data.data[i].dictLabel;
                obj = Object.assign(obj, obj1);
                html += '<option value="' + data.data[i].dictValue + '">' + data.data[i].dictLabel + '</option>';
            }
            $("select[name='exceptionType']").html(html);
            form.render('select');
        });
        $("select[name='exceptionType']").html(html);
        form.render('select');*/
        form.render('select')
        $("#ydxjSbglInfo").css("display", "none");
        $("#tapInfo").css("display", "block");
    },
    deleteNum: function (id) {
        layer.confirm('确定删除么？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.post(ctx + "aupipes/detail/remove", {
                ids: id
            }, function (data) {
                if (data.code == 0) {
                    $("#detail").css("display", "none");
                    $("#tapInfo").css("display", "block");
                    startLook(ydxj.id)
                    parent.layer.msg("删除成功");
                }
            })
        });
    },

    //查看详情页面的查看记录详情
    ydxjLookXjjl: function (sbId, serviceTypeId, serviceStatus, remark) {
        ydxj.ydxjSbid = sbId;//巡检记录id
        ydxj.ydxjSbidOpenType = "edit";
        ydxj.ydxjSbServiceType = serviceTypeId;
        ydxj.ydxjSbServiceStatus = serviceStatus;
        ydxj.ydxjSbRemark = remark;
        ydxj.ydxjLookXjjl_("edit", sbId);
        ydxj.lookServicePicList();
    },

    ydxjLookXjjl_: function (openType, sbId) {
        ydxj.ydxjSbid = sbId;
        $("#tapInfo").html("");
        $("#tapInfo").load(ctx + "aupipes/detail/ydxjLook", function (data) {
            var str = "";
            if (ydxj.ydxjSbRemark == "null") {
                ydxj.ydxjSbRemark = "";
            }
            str += `<div class="layui-row layui-col-space5">
        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
            <div class="layui-form-item">
                <label class="layui-form-label">巡检人：</label>
                <div class="layui-input-block">
                    <input type="hidden" class="layui-input" name="inspectWorkerId" id="inspectWorkerId"  value="` + ydxj.inspectWorkerId + `" >
                    <input type="text" class="layui-input" value="` + ydxj.inspectWorker + `" name="inspectWorker" id="inspectWorker" readonly="readonly">
                </div>
            </div>
        </div>
        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
            <div class="layui-form-item">
                <label class="layui-form-label">地点类型：</label>
                <div class="layui-input-block">
                    <select name="serviceType" id="serviceType" lay-filter="aihaoSblx" readonly="readonly">
                        <option value=""></option>
                        <option value="0">配电房</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
            <div class="layui-form-item">
                <label class="layui-form-label">巡检地点：</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input" name="serviceAddress" id="serviceAddress" value="` + ydxj.inspectAddress + `" readonly="readonly">
                </div>
            </div>
        </div>
        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
            <div class="layui-form-item">
                <label class="layui-form-label">设备状态：</label>
                <div class="layui-input-block">
                    <select name="serviceStatus" id="serviceStatus"  lay-filter="aihaoStatus" readonly="readonly">
                        <option value=""></option>
                        <option value="0">无异常</option>
                    </select>
                </div>
            </div>
        </div>
        <!--<div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
            <div class="layui-form-item">
                <label class="layui-form-label">异常记录：</label>
                <div class="layui-input-block">
                    <button type="button" class="layui-btn bg_green fl" style="width:18%" onclick="ydxj.ydxj2Gl();">关联</button>
                </div>
            </div>
        </div>-->
        <div class="exceptionRecordLook layui-row"></div>
        <div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
                    <div class="layui-form-item">
                        <label class="layui-form-label">备注：</label>
                        <div class="layui-input-block">
                            <textarea name="description" id="description" required lay-verify="required" placeholder="请输入内容" class="layui-textarea">` + ydxj.ydxjSbRemark + `</textarea>
                        </div>
                    </div>
                </div>
                <div class="form-group" id="oldPicDiv">
                        <label class="layui-form-label">历史图片：</label>
                        <div class="swiper-container oldSwiper" style="padding-left: 1px;padding-top: 20px">
                            <div class="layui-upload-list uploadList" id="oldPicList">
                            </div>
            
                            <!-- 如果需要滚动条 -->
                            <div id="swiper-scrollbar-oldPicList" class="swiper-scrollbar"></div>
                        </div>
                  </div>
                </div>`;
            //添加巡检记录页面
            $("#ydxj2XjlbForm").html(str);
            ydxj.addPicNum = 0;
            ydxj.ydxjSbPicStatus = 0;
            layui.use(['form', 'upload'], function () {
                var form = layui.form;
                //加载设备类型下拉选择
                $.get(ctx + "system/dict/data/api/list", {
                    dictType: "aup_service_type",
                    pageSize: 30
                }, function (data) {
                    var html = '';
                    for (var i = 0; i < data.data.length; i++) {
                        html += '<option value="' + data.data[i].dictValue + '">' + data.data[i].dictLabel + '</option>';
                    }
                    $("select[name='serviceType']").html(html);
                    form.render('select');
                    //默认勾选
                    var select = document.getElementById("serviceType");
                    for (var i = 0; i < select.options.length; i++) {
                        if (select.options[i].value == (ydxj.ydxjSbServiceType + "")) {
                            select.options[i].selected = true;
                            break;
                        }
                    }
                    form.render('select');
                });
                //加载设备状态下拉选择
                $.get(ctx + "system/dict/data/api/list", {
                    dictType: "aup_service_status",
                    pageSize: 30
                }, function (data) {
                    var html = '';
                    var obj = {}, obj1 = {};
                    for (var i = 0; i < data.data.length; i++) {
                        obj1[data.data[i].dictValue] = data.data[i].dictLabel;
                        obj = Object.assign(obj, obj1);
                        html += '<option value="' + data.data[i].dictValue + '">' + data.data[i].dictLabel + '</option>';
                    }
                    console.log(obj);
                    $("select[name='serviceStatus']").html(html);
                    form.render('select');
                    //默认选中
                    var select = document.getElementById("serviceStatus");
                    for (var i = 0; i < select.options.length; i++) {
                        if (select.options[i].value == (ydxj.ydxjSbServiceStatus + "")) {
                            select.options[i].selected = true;
                            break;
                        }
                    }
                    form.render('select');
                });
            });
            //若是修改页面读取巡检设备
            $.post(ctx + "aupipes/inspectservice/list", {
                "taskId": ydxj.id,
                "detailId": ydxj.ydxjSbid
            }, function (result) {
                if (result.code == 0) {
                    ydxj.HistorySbNum = "";
                    if (result.rows.length > 0) {
                        ydxj.HistorySbNum = result.rows.length;
                    }
                    var str = "";
                    var dictStr = "";
                    $.get(ctx + "system/dict/data/api/list", {
                        dictType: "aup_exception_type",
                        pageSize: 30
                    }, function (data) {
                        dictStr = '<option value=""></option>';
                        var obj = {}, obj1 = {};
                        for (var i = 0; i < data.data.length; i++) {
                            obj1[data.data[i].dictValue] = data.data[i].dictLabel;
                            obj = Object.assign(obj, obj1);
                            dictStr += '<option value="' + data.data[i].dictValue + '">' + data.data[i].dictLabel + '</option>';
                        }
                        //添加设备列表
                        for (var i = 0; i < result.rows.length; i++) {
                            if (result.rows[i].serviceSituation == null) {
                                result.rows[i].serviceSituation = "";
                            }
                            str += `<div class="layui-col-xs6 layui-col-sm6 layui-col-md6 aaa" id="` + result.rows[i].id + `">
                        <input type="hidden" id="service` + result.rows[i].id + `" value="` + result.rows[i].serviceId + `">
                        <div class="layui-form-item">
                            <label class="layui-form-label">` + (i + 1) + `.名称：</label>
                            <div class="layui-input-block">
                                <span class="formeTxt" name="serviceName` + result.rows[i].id + `" id="serviceName` + result.rows[i].id + `">` + result.rows[i].serviceName + `</span>
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
                        <div class="layui-form-item">
                            <label class="layui-form-label">状态：</label>
                            <div class="layui-input-block">
                                <select name="exceptionType` + result.rows[i].id + `" id="exceptionType` + result.rows[i].id + `" lay-filter="exceptionType` + result.rows[i].id + `">
                                    ` + dictStr + `
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
                        <div class="layui-form-item">
                            <label class="layui-form-label">情况说明</label>
                            <div class="layui-input-block">
                                <textarea name="description` + result.rows[i].id + `" id="description` + result.rows[i].id + `" required lay-verify="required" placeholder="请输入内容" class="layui-textarea" style="min-height:40px;">` + result.rows[i].serviceSituation + `</textarea>
                            </div>
                        </div>
                    </div>`;
                        }
                        $(".exceptionRecordLook").html("");
                        $(".exceptionRecordLook").html(str);
                        form.render();
                        for (var key in result.rows) {
                            var id = result.rows[key].id;
                            var select = document.getElementById("exceptionType" + id);
                            for (var i = 0; i < select.options.length; i++) {
                                if (select.options[i].value == (result.rows[key].serviceStatus)) {
                                    select.options[i].selected = true;
                                    break;
                                }
                            }
                            form.render();
                        }
                    });
                }
            })
            //隐藏数据
            $("#detail").css("display", "none");
            $("#tapInfo").css("display", "block");
        });
    },
    //查看巡检记录返回
    ydxjLookBack: function () {
        $("#detail").css("display", "block");
        $("#tapInfo").css("display", "none");
    },

    //加载之前上传的图片
    lookServicePicList: function () {
        //获取多媒体内容
        $.ajax({
            type: "POST",
            url: ctx + "aupipes/info/list", //上传接口
            data: {"fileCode": ydxj.ydxjSbid},
            contentType: 'application/x-www-form-urlencoded',
            dataType: "json",
            success: function (result) {
                if (result.code == 0) {
                    var picList = result.rows;
                    if (picList.length == 0) {
                        $("#oldPicDiv").hide();
                        return;
                    }
                    $("#oldPicDiv").show();
                    $("#oldPicList").html("");
                    //$("#oldPicList").empty();
                    for (i = 0; i < picList.length; i++) {
                        var item = picList[i];
                        ydxj.lookServiceAddInternalImg(item.fileId, item.filePath)
                    }
                }
            }
        });
    },
    //查看详情添加图片
    lookServiceAddInternalImg: function (id, filePath) {
        var url = "";
        //var url = ctx + filePath + "?text=图片加载中";
        if (ctx == "/") {
            url = filePath + "?text=图片加载中";
        } else {
            url = ctx + filePath + "?text=图片加载中";
        }
        var ulDiv = $("#oldPicList");
        ulDiv.append("<span class=\"swiper-slide\"><img onclick=\"ydxj.showImg('#oldPicList')\" layer-src=" + url + " src=" + url + " alt=\"\"></span>");
        ydxj.swiperInit(".oldSwiper");
    },

};
