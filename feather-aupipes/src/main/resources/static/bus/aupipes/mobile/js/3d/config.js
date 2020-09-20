var getLanguagePackageByLang = {
    returnPackageByLang: function () {
        var lang = this.getLocationRequestParam('lang') || 'zh-CN';
        return this.getLanguageFileByLang(lang);
    },
    getLocationRequestParam: function (name) {
        var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)')
        var r = window.location.search.substr(1).match(reg)
        if (r != null)
            return unescape(r[2])
        return undefined
    },
    getLanguageFileByLang: function (lang) {
        var url = ctx + 'bus/aupipes/Cesium/Languages/Configs/' + lang + '.json';
        var promise = new Promise(function (resolve, reject) {
            if (!lang) {
                resolve({});
            } else {
                $.ajax({
                    url: url,
                    type: 'GET',
                    dataType: 'json',
                    success: function (data) {
                        resolve(data);
                    },
                    error: function () {
                        reject({});
                    }
                });
            }
        });
        return promise;
    },

    translationLangByKey: function (lang, data) {
        if (!lang || !data) {
            return
        } else {
            var htmls = $('.' + lang);
            if (htmls) {
                for (var i = 0; i < htmls.length; i++) {
                    var html = htmls[i];
                    var hType = html.localName;;
                    var id = html.getAttribute("lang-key");
                    switch (hType) {
                        case 'button':
                            this.changeButtonTextByLang(html, id, data)
                            break;
                        case 'label':
                            this.changLabelTextByLang(html, id, data);
                            break;
                        case 'span':
                            this.changeSpanTextByLang(html, id, data);
                            break;
                        case 'select':
                            this.changeOptionsTextByLangClass(html, id, data);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    },

    changeSpanTextByLang: function (html, id, lang) {
        if (html && lang[id]) {
            $(html).html(lang[id]);
        }
    },
    changLabelTextByLang: function (html, id, lang) {
        if (html && lang[id]) {
            $(html).text(lang[id]);
        }
    },
    changeButtonTextByLang: function (html, id, lang) {
        if (html && lang[id]) {
            $(html).text(lang[id]);
        }
    },
    changeLabelTextByLangClass: function (html, id, lang) {
        if (html && lang[id]) {
            $(html).text(lang[id]);
        }
    },

    /**
     * 翻译select标签下的options选项
     * {@Param} data:select标签className  String
     * {@Param} option:select标签下option标签id Array
     * {@Param} lang:语言包集合 Object
     */
    changeOptionsTextByLangClass: function (html, id, lang) {
        //$(html).text(lang[id]);
        if (html) {
            var length = $(html).get(0).options.length;
            for (var i = 0; i < length; i++) {
                var id = $(html).get(0).options[i].getAttribute("lang-key");
                if (id && lang[id])
                    $(html).get(0).options[i].text = lang[id]
            }
        }
    }
}
