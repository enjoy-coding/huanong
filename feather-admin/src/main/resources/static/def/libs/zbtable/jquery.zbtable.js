;
(function ($) {
    $.fn.ZBTable = function (options) {
        var defaults = {
            ctx: '',
            evenRowClass: 'zbt-evenRow',
            oddRowClass: 'zbt-oddRow',
            curRowClass: 'zbt-curRow',
            titles: [],
            data: [],
            overflowHeight: "260px",
            showOpt: true
        }
        var opts = $.extend(defaults, options);
        var titles = opts.titles;
        var datas = opts.data;
        var showOpt = opts.showOpt;
        var init = function (dom) {
            var id = dom[0].id;
            $("#" + id).empty();
            var arrH = [];

            arrH.push("<table class='zbt-table'>");
            arrH.push("<thead id='" + id + "-thead'>");
            arrH.push("<tr>");
            $.each(titles, function (i, item) {
                var tdw = "";
                if (item.width) {
                    tdw = "width=\"" + item.width + "px\""
                }
                arrH.push("<th " + tdw + ">" + item.title + "</th>");

            });
            if(showOpt){
                arrH.push("<th>操作</th>");
            }
            arrH.push("</tr>");
            arrH.push("</thead>");
            arrH.push("<tbody id='" + id + "-tbody'>");
            $.each(datas, function (i, item) {
                arrH.push("<tr>");
                $.each(titles, function (i, item2) {
                    if (item[item2.keyName] != undefined) {
                        if (i == 0) {
                            var fullPath = opts.ctx + item["filePath"];
                            arrH.push("<td id=\"" + item.fileId + "\"><a onmousemove=\"imageView(this)\" target=\"_blank\" href=\"" + fullPath+ "\">" + item[item2.keyName] + "</td>");
                        } else {
                            arrH.push("<td>" + item[item2.keyName] + "</td>");
                        }

                    }
                });
                if(showOpt){
                    arrH.push("<td><a href=\"javascript:void(0)\">删除</a></td>");
                }
                arrH.push("</tr>")
            });
            arrH.push("</tbody></table>");
            dom.append(arrH.join(""));
        }

        var addRowColor = function (_id) {
            $("#" + _id + "-tbody tr:even").addClass(opts.evenRowClass);
            $("#" + _id + "-tbody tr:odd").addClass(opts.oddRowClass);
            $("#" + _id + "-tbody tr").hover(function () {
                $(this).addClass(opts.curRowClass)
            }, function () {
                $(this).removeClass(opts.curRowClass)
            })
        }
        var syncTableWidth = function (id) {
            var ths = $("#" + id + "-thead tr:eq(0) > th");
            var len = ths.length;
            ths.each(function (i) {
                if (i < len - 1) {
                    var tarTh = $(this);
                    var srcTd = $("#" + id + "-tbody tr:eq(0) > td:eq(" + i + ")");
                    if (srcTd.length == 0) {
                        return
                    }
                    var inW, outW, Width;
                    if (tarTh.attr("width")) {
                        inW = Math.ceil(tarTh.innerWidth());
                        outW = Math.ceil(tarTh.outerWidth());
                        Width = Math.ceil(tarTh.width())
                    } else {
                        inW = Math.ceil(srcTd.innerWidth());
                        outW = Math.ceil(srcTd.outerWidth());
                        Width = Math.ceil(srcTd.width())
                    }
                    srcTd.innerWidth(inW).outerWidth(outW).width(Width);
                    tarTh.innerWidth(inW).outerWidth(outW).width(Width)
                }
            })
        }
        var setOverflowHeight = function (id) {
            $("#" + id + "-zbTable-data").css({
                "max-height": opts.overflowHeight,
                "overflow": "auto"
            })
        }
        return this.each(function () {
            var _this = $(this);
            var _this_id = _this[0].id;
            init(_this);
            addRowColor(_this_id);
            syncTableWidth(_this_id);
            setOverflowHeight(_this_id);
        });
    }


    $.fn.ZBTable.addRow = function (id, titles, data, ctx) {
        var arrH = [];
        arrH.push("<tr>");
        var $tr = $("#" + id).find("tr:last");
        var $prev = $tr;
        $.each(titles, function (i, item2) {
            if (data[item2.keyName] != undefined) {
                if (i == 0) {
                    var fullPath = ctx + data["filePath"];
                    arrH.push("<td id=\"" + data.fileId + "\"><a target=\"_blank\" href=\"" + fullPath + "\">" + data[item2.keyName] + "</td>");
                } else {
                    arrH.push("<td>" + data[item2.keyName] + "</td>");
                }
            }
        });

        arrH.push("<td><a href=\"javascript:void(0)\">删除</a></td>");
        arrH.push("</tr>");

        $tr.after(arrH.join(""));

        if ($prev.hasClass("zbt-evenRow")) {
            $tr = $("#" + id).find("tr:last");
            $tr.addClass("zbt-oddRow");
        } else {
            $tr = $("#" + id).find("tr:last");
            $tr.addClass("zbt-evenRow");
        }

    }
})(jQuery);