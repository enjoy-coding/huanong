var featherCmsScript = {
	ctx : "/",
	register : function(options) {
		if (options) {
			options.element = options.element || featherCmsScript.defaultElement;
			if (featherCmsScript.optionsElements.indexOf(options.element) == -1) {
				featherCmsScript.optionsElements.push(options.element);
			}
			var link = featherCmsScript.optionsMapper[options.element];
			if (!link) {
				link = [];
				featherCmsScript.optionsMapper[options.element] = link;
			}
			link.push(options);
		}
	},
	trigger : function(eventType, filter) {
		switch (eventType) {
		case "load": {
			var inBuilding = window.featherCmsBuilder ? true : false;
			featherCmsScript.optionsElements.forEach(function(element, index, array) {
				var link = featherCmsScript.optionsMapper[element];
				if (link) {
					link.forEach(function(options, index, array) {
						if (index == 0 && options.onLoad) {
							var enable = featherCmsScript.filtTrigger(options, filter);
							if (enable) {
								options.onLoad(options);
							}
						}
					});
				}
				// bind click event
				if (element != featherCmsScript.defaultElement) {
					var $ele = $(element);
					if ($ele.length > 0) {
						$ele.attr(featherCmsScript.attachedName, 'click').click(function() {
							var link = featherCmsScript.optionsMapper[element];
							if (link) {
								var eventThat = this;
								link.forEach(function(options, index, array) {
									if (inBuilding) {
										featherCmsBuilder.design(options, eventThat);
									} else if (!inBuilding && options.onClick) {
										options.onClick(options, eventThat);
									}
								});
							}
						});
					}
				}
			});
			featherCmsScript.optionsElements.forEach(function(element, index, array) {
				var link = featherCmsScript.optionsMapper[element];
				if (link) {
					link.forEach(function(options, index, array) {
						if (index > 0 && options.onLoad) {
							options.onLoad(options);
						}
					});
				}
			});
			break;
		}
		case "resize": {
			featherCmsScript.optionsElements.forEach(function(element, index, array) {
				var link = featherCmsScript.optionsMapper[element];
				if (link) {
					link.forEach(function(options, index, array) {
						if (options.onResize) {
							options.onResize(options);
						}
					});
				}
			});
			break;
		}
		}
	},
	get : function(opt) {
		/*
		 opt pattern: { key:"", widget:"" }
		 */
		//var link = featherCmsScript.optionsMapper[key];
		//return link ? link[0] : null;
		//TODO
		return null;
	},
	filtTrigger : function(options, filter) {
		var elementFlag = true;
		var widgetFlag = true;
		var groupFlag = true;
		if  (filter) {
			if (filter.elements && filter.elements.length) {
				elementFlag = false;
				for (var i = 0; i < filter.elements.length; i++) {
					var ele = filter.elements[i];
					if (options.element == ele) {
						elementFlag = true;
						break;
					}
				}
			}
			if (filter.widgets && filter.widgets.length) {
				widgetFlag = false;
				for (var i = 0; i < filter.widgets.length; i++) {
					var wdt = filter.widgets[i];
					if (options.widget == wdt) {
						widgetFlag = true;
						break;
					}
				}
			}
			if (filter.groups && filter.groups.length) {
				groupFlag = false;
				for (var i = 0; i < filter.groups.length; i++) {
					var grp = filter.groups[i];
					if (options.group == grp) {
						groupFlag = true;
						break;
					}
				}
			}
		}
		return elementFlag && widgetFlag && groupFlag;
	},
	setVersion : function() {
		if (featherCmsScript.version) {
			ignore = ignore || featherCmsScript.versiongIgnore;
			function inIgnore(str) {
				//var lcStr = str.toLocaleLowerCase();
				for (var item of ignore) {
					if (str.indexOf(item) != -1) {
						return true;
					}
				}
				return false;
			}
			function appendVersion(str) {
				return str + (str.indexOf("?") == -1 ? "?" : "&") + "_=" + version;
			}
			var link = document.getElementsByTagName('link');
			for (var item of link) {
				var href = item.href;
				if (!inIgnore(href)) {
					item.href = appendVersion(href);
				}
			}
			var script = document.getElementsByTagName('script');
			for (var item of script) {
				var src = item.src;
				if (src && !inIgnore(src)) {
					item.src = appendVersion(src);
				}
			}
		}
	},
	versiong : "",
	versiongIgnore : [ ".min.", "jquery", "font", "echarts", "layui", "Cesium" ],
	defaultElement : "*",
	attachedName : "fcms-atta",
	optionsElements : [],
	optionsMapper : []
};

/*
// options pattern
var options = {
    element : "", // "#id" or ".class" or empty
    widget : "", // component, eg: "map", "charts-line", "link", "nav" ....
    group : "", // same or together
    onLoad : function (opt) { },
    onResize : function (opt) { },
    onClick : function (opt, _this) { }
};*/

featherCmsScript.setVersion();

$(function() {
	featherCmsScript.trigger("load");

	$(window).resize(function() {
		featherCmsScript.trigger("resize");
	});
// scan element
});


ah.proxy({
    onRequest: (config, handler) => {
        // console.log(config.url)
        handler.next(config);
    },
    onResponse: (response, handler) => {
        var cttType = response.headers["content-type"];
        if (cttType && cttType.toLocaleLowerCase().indexOf("json") !== -1 && response.config.xhr.responseType != "arraybuffer" && (typeof response.response === "string")) {
            var text = response.response;
            if (text.indexOf("未登录或登录超时。请重新登录") !== -1) {
                top.location.href = featherCmsScript.ctx + "/login";
                handler.reject({
                    config: response.config,
                    type: 'error'
                });
                return;
            }
            if (text.indexOf('"code":500') !== -1) {
                console.log(text);
                var vj = JSON.parse(response.response);
                if (vj.msg) {
                    setTimeout(function () {
                        layer.open({
                            type: 0,
                            title: false,
                            content: vj.msg,
                            offset: 'b',
                            icon: 5,
                            shade: 0,
                            resize: false,
                            time: 6000
                        });
                    }, 100);
                }
                //throw new SyntaxError("后端错误"+ vj.code);
            }
        }
        handler.next(response)
    }
});