'use strict';
var chartTypes = ["line", "bar", "pie", "radar", "tree", "treemap", "sunburst", "funnel", "gauge", "boxplot", "heatmap", "graph", "parallel", "sankey", "scatter", "map", "candlestick"];
var exportPath = "";

function exportbd(selectPath) {
    let text = fs.readFileSync(path.join(__dirname, './static/export/Temp.html')).toString();
    var bdData = getBddpData();
    exportPath = selectPath + "/" + bdData.content.sceneName;
    if (!fs.existsSync(exportPath)) {
        fs.mkdirSync(exportPath);
    }
    text = text.replace(/{tempTitle}/g, bdData.content.sceneName);
    text = text.replace(/{tempContentWidth}/g, bdData.content.width);
    text = text.replace(/{tempContentHeight}/g, bdData.content.height);
    text = text.replace(/{tempContentColor}/g, bdData.content.backgroundColor);
    text = text.replace(/{tempContentImage}/g, addBgImage(bdData, exportPath));

    let cssText = fs.readFileSync(path.join(__dirname, './static/export/Temp.css')).toString();

    text = text.replace(/{tempStyle}/g, cssText);
    let boxsHtml = createBoxsHtml(bdData);

    text = text.replace(/{tempContent}/g, boxsHtml);
    let scriptStr = "$.ajaxSettings.async = false;\n\r" + addGlobalDataScript(bdData.content, exportPath) + addChartThemeScript(bdData) + createChartScript(bdData, exportPath) + createTagsScript(bdData, exportPath) + createTableScript(bdData, exportPath) + createTimeScript(bdData, exportPath) + createSwiperScript(bdData, exportPath);


    text = text.replace(/{tempScript}/g, scriptStr);
    // console.log(text);

    fs.writeFile(exportPath + "/" + bdData.content.sceneName + ".html", text, function (err) {
        if (err) throw err;
        console.log("Export Account Success!");
    });
    addCssFiles(exportPath);
    addJsFiles(exportPath);
}

function getBddpData(flag) {
    var bdData = {};
    bdData.content = {
        width: $("#content").data("width"),
        height: $("#content").data("height"),
        backgroundColor: $("#content").data("backgroundColor"),
        backgroundImage: $("#content").data("backgroundImage"),
        url: $("#content").data("url"),
        dataFrom: $("#content").data("dataFrom"),
        globalChartTheme: globalChartTheme
    };
    if (!flag) {
        if ($("#content").data("sceneName") && $("#content").data("sceneName") != "undefined") {
            bdData.content.sceneName = $("#content").data("sceneName");
        } else {
            let sitemap = $("#scene");
            sitemap.addClass("on");
            getSceneConfig();
            layx.msg('场景名称不能为空！', {
                dialogIcon: 'warn'
            });
            return false;
        }
    }

    bdData.boxs = [];
    $("#content").children(".box").each(function () {
        let prop = $(this).data("prop");
        let box = {
            options: prop.options,
            optionsText: prop.optionsText,
            other: prop.other,
            rectP: prop.rectP,
            data: prop.data,
            type: prop.type,
            parts: prop.parts,
            effect: prop.effect,
            filter: prop.filter,
            id: prop.id,
            gmOptions: prop.gmOptions,
            swiper: prop.swiper,
            slides: prop.slides
        };
        if (prop.type == "swiper") {
            box.slides = [];
            $(this).find(".box").each(function () {
                let slideProp = $(this).data("prop");
                let slide = {
                    options: slideProp.options,
                    optionsText: slideProp.optionsText,
                    other: slideProp.other,
                    rectP: slideProp.rectP,
                    data: slideProp.data,
                    type: slideProp.type,
                    parts: slideProp.parts,
                    effect: slideProp.effect,
                    filter: slideProp.filter,
                    id: slideProp.id,
                    bigType: slideProp.bigType,
                    gmOptions: slideProp.gmOptions,
                    slide: slideProp.slide
                };
                box.slides.push(slide);
            });
        }
        bdData.boxs.push(box);
    });
    return bdData;
}

function addImage(bdData, exportPath) {
    let boxs = bdData.boxs;
    $.each(boxs, function (index, prop) {
        if (prop.type == "image") {

        }
    });
}

function addMapFiles(exportPath, prop) {
    let mappath = prop.other.mappath;
    let mapName = prop.options.geo.map;
    if (!fs.existsSync(exportPath + "/map")) {
        fs.mkdirSync(exportPath + "/map");
    }
    let map = fs.readFileSync(mappath);
    fs.writeFile(exportPath + "/map/" + mapName + ".json", map, function (err) {
        if (err) throw err;
        console.log("Export MAP Success!");
    });

}

function addCssFiles(exportPath) {
    var cssFiles = ["./static/common/autoscroll/autoscroll.css", "./static/common/reset.css", "./static/common/ledtime/digital-clock.css", "./static/common/swiper/swiper.min.css"];
    if (!fs.existsSync(exportPath + "/css")) {
        fs.mkdirSync(exportPath + "/css");
    }
    $.each(cssFiles, function (i, filenode) {
        let filename = getFileName(filenode);
        console.log(filename, filename);
        let css = fs.readFileSync(path.join(__dirname, filenode));
        fs.writeFile(exportPath + "/css/" + filename, css, function (err) {
            if (err) throw err;
            console.log("Export CSS Success!");
        });
    })

}

function addJsFiles(exportPath) {
    var jsFiles = ["./static/design/effect.js", "./static/common/autoscroll/autoscroll.js", "./static/common/jquery/jquery-1.11.0.js", "./static/common/echarts/echarts.min.js", "./static/common/ledtime/digital-clock.js", "./static/export/bulidCharts.min.js", "./static/common/swiper/swiper.min.js"];
    if (!fs.existsSync(exportPath + "/js")) {
        fs.mkdirSync(exportPath + "/js");
    }
    $.each(jsFiles, function (i, filenode) {
        let filename = getFileName(filenode);
        let js = fs.readFileSync(path.join(__dirname, filenode));
        fs.writeFile(exportPath + "/js/" + filename, js, function (err) {
            if (err) throw err;
            console.log("Export JS Success!");
        });
    });
}

function addBgImage(bdData, exportPath) {
    var backgroundImage = bdData.content.backgroundImage;
    if (backgroundImage) {
        try {
            let imagePath = "";
            if (backgroundImage.indexOf('url(".') > -1) {

                imagePath = getInnerString(backgroundImage, "url(\"", "\")")[0];
                imagePath = path.join(__dirname, imagePath);
            } else if (backgroundImage.indexOf('url("file') > -1) {

                imagePath = getInnerString(backgroundImage, "url(\"file:///", "\")")[0];
            } else {
                imagePath = backgroundImage;
            }
            let postfix = getdir(imagePath);
            let imageName = hex_md5(imagePath);

            if (fs.existsSync(imagePath)) {
                let dstpath = exportPath + "/img/" + imageName + "." + postfix;
                if (!fs.existsSync(exportPath + "/img")) {
                    fs.mkdirSync(exportPath + "/img");
                }
                var readStream = fs.createReadStream(imagePath);
                var writeStream = fs.createWriteStream(dstpath);
                readStream.pipe(writeStream);

            } else {
                let dstpath = exportPath + "/img/" + imageName + "." + postfix;
                if (!fs.existsSync(exportPath + "/img")) {
                    fs.mkdirSync(exportPath + "/img");
                }
                request(imagePath).pipe(fs.createWriteStream(dstpath));
            }
            console.log("Export BgImage Success!");
            return "background-image: url(img/" + imageName + "." + postfix + ");";
        } catch (error) {
            return "";
        }

    } else {
        return "";
    }

}
// 提取固定字符之间的字符串
function getInnerString(source, prefix, postfix) {
    var regexp = new RegExp(encodeReg(prefix) + '.+' + encodeReg(postfix), 'gi');
    var matches = String(source).match(regexp);
    var formatedMatches = $.map(matches, value => {
        return value.replace(prefix, '').replace(postfix, '');
    });
    return formatedMatches;
}

//转义影响正则的字符
function encodeReg(source) {
    return String(source).replace(/([.*+?^=!:${}()|[\]/\\])/g, '\\$1');
}

function createBoxsHtml(bdData) {
    let boxs = bdData.boxs;
    let html = "";
    $.each(boxs, function (index, prop) {
        let rectP = prop.rectP;
        let tempHtml = $('<div class="box"></div>');
        tempHtml.css({
            width: rectP.width + 'px',
            height: rectP.height + 'px',
            left: rectP.x + 'px',
            top: rectP.y + "px",
            transform: 'rotate(' + rectP.rotate + 'deg)',
            "z-index": rectP.zIndex
        });
        tempHtml.attr("id", prop.id);
        createTagsHtml(prop, tempHtml);
        html += tempHtml.prop("outerHTML") + "\n\r";
        tempHtml.remove();
    });
    return html;
}

function addGlobalDataScript(content, exportPath) {
    let url = content.url;
    if (content.dataFrom == 2) {
        if (url) {
            let filename = getFileName(url);
            exportJsonDataFile(url, exportPath);
            return "getgGlobalData('jsondata/" + filename + "');\n\r";
        } else {
            return "";
        }
    } else {
        if (url) {
            return "getgGlobalData('" + url + "');\n\r";
        } else {
            return "";

        }
    }

}

function addChartThemeScript(bdData) {
    let scriptStr = "";
    let boxs = bdData.boxs;
    let themes = [];
    $.each(boxs, function (index, prop) {
        if (prop.bigType == "chart" || $.inArray(prop.type, chartTypes) >= 0) {
            if (prop.other.theme && $.inArray(prop.other.theme, themes) < 0) {
                themes.push(prop.other.theme);
                let data = fs.readFileSync(path.join(__dirname, './static/charts-theme/' + prop.other.theme + '.json'));
                data = JSON.parse(data);
                data = JSON.stringify(data);
                scriptStr += "echarts.registerTheme('" + prop.other.theme + "', JSON.parse('" + data + "'));\n\r";
            }


            //obj = JSON.parse(data) ;
            //echarts.registerTheme('customed', obj)
        } else if (prop.type == "swiper" && prop.slides.length > 0) {
            $.each(prop.slides, function (i, slide) {
                if (slide.other.theme && $.inArray(slide.other.theme, themes) < 0) {
                    themes.push(slide.other.theme);
                    let data = fs.readFileSync(path.join(__dirname, './static/charts-theme/' + slide.other.theme + '.json'));
                    data = JSON.parse(data);
                    data = JSON.stringify(data);
                    scriptStr += "echarts.registerTheme('" + slide.other.theme + "', JSON.parse('" + data + "'));\n\r";
                }
            });
        }
    });
    return scriptStr;
}

function createChartScript(bdData, exportPath) {
    let scriptStr = "";
    let boxs = bdData.boxs;
    $.each(boxs, function (index, prop) {
        if (prop.bigType == "chart" || $.inArray(prop.type, chartTypes) >= 0) {
            let tempHtml = "";
            if (prop.other.dataFrom == 1) {

            } else if (prop.other.dataFrom == 2) {} else if (prop.other.dataFrom == 3) {
                exportJsonDataFile(prop.data.link, exportPath);
            }

            if (prop.type == "map") {
                addMapFiles(exportPath, prop);
                tempHtml += createMapChartScript(prop);
            }
            tempHtml += "var tag" + prop.id + "Chart = echarts.init(document.getElementById('tag-" + prop.id + "'),'" + prop.other.theme + "');" + "\n\r";
            tempHtml += "var tag" + prop.id + "Prop = " + JSON.stringify(prop) + ";" + "\n\r";
            if (prop.optionsText) {
                prop.options = "";
                tempHtml += "var tag" + prop.id + "Option = eval('('+tag" + prop.id + "Prop.optionsText+')')" + ";\n\r";
            } else {
                tempHtml += "var tag" + prop.id + "Option = tag" + prop.id + "Prop.options" + ";\n\r";
            }
            tempHtml += "createTagsBox(tag" + prop.id + "Prop,tag" + prop.id + "Option,tag" + prop.id + "Chart);" + "\n\r";
            //  tempHtml += "tag" + prop.id + "Chart.setOption(tag" + prop.id + "Option);" + "\n\r";
            scriptStr += tempHtml + "\n\r";
        } else if (prop.type == "swiper" && prop.slides.length > 0) {
            $.each(prop.slides, function (i, slide) {
                let tempHtml = "";
                if (slide.other.dataFrom == 1) {

                } else if (slide.other.dataFrom == 2) {} else if (slide.other.dataFrom == 3) {
                    exportJsonDataFile(slide.data.link, exportPath);
                }

                if (slide.type == "map") {
                    addMapFiles(exportPath, slide);
                    tempHtml += createMapChartScript(slide);
                }
                tempHtml += "var tag" + slide.id + "Chart = echarts.init(document.getElementById('tag-" + slide.id + "'),'" + slide.other.theme + "');" + "\n\r";
                tempHtml += "var tag" + slide.id + "Prop = " + JSON.stringify(slide) + ";" + "\n\r";
                if (slide.optionsText) {
                    tempHtml += "var tag" + slide.id + "Option = eval('('+tag" + slide.id + "Prop.optionsText+')')" + ";\n\r";
                    slide.options = "";
                } else {
                    tempHtml += "var tag" + slide.id + "Option = tag" + slide.id + "Prop.options" + ";\n\r";
                }
                tempHtml += "createTagsBox(tag" + slide.id + "Prop,tag" + slide.id + "Option,tag" + slide.id + "Chart);" + "\n\r";
                //  tempHtml += "tag" + prop.id + "Chart.setOption(tag" + prop.id + "Option);" + "\n\r";
                scriptStr += tempHtml + "\n\r";
            });
        }
    });
    return scriptStr;
}

function createTagsScript(bdData, exportPath) {
    let scriptStr = "";
    let boxs = bdData.boxs;
    $.each(boxs, function (index, prop) {
        if (prop.type == "text") {
            let tempHtml = "";
            if (prop.other && prop.other.dataFrom == 1) {

            } else if (prop.other && prop.other.dataFrom == 2) {} else if (prop.other && prop.other.dataFrom == 3) {
                exportJsonDataFile(prop.data.link, exportPath);
            }
            tempHtml += "var tag" + prop.id + "Prop = " + JSON.stringify(prop) + ";" + "\n\r";
            tempHtml += "createTagsBox(tag" + prop.id + "Prop,'#tag-" + prop.id + "');" + "\n\r";
            scriptStr += tempHtml + "\n\r";
        }
    });
    return scriptStr;
}

function createMapChartScript(prop) {
    return "$.getJSON('map/" + prop.options.geo.map + ".json', function(data){ echarts.registerMap('" + prop.options.geo.map + "', data);});\n\r";
}

function createTableScript(bdData, exportPath) {
    let scriptStr = "";
    let boxs = bdData.boxs;
    $.each(boxs, function (index, prop) {
        if (prop.type == "table") {
            let tempHtml = "";
            prop.effect = prop.effect || {};
            tempHtml += "var tag" + prop.id + "Table = document.getElementById('tag-" + prop.id + "');" + "\n\r";
            tempHtml += "var tag" + prop.id + "Option = " + JSON.stringify(prop.gmOptions) + ";" + "\n\r";
            tempHtml += "var tag" + prop.id + "Autoscroll = " + prop.effect.autoscroll + ";" + "\n\r";
            tempHtml += "$(tag" + prop.id + "Table).initScroll(tag" + prop.id + "Option);" + "\n\r";
            tempHtml += "if(tag" + prop.id + "Autoscroll){$(tag" + prop.id + "Table).autoScroll();}" + "\n\r";
            scriptStr += tempHtml + "\n\r";
        }
    });
    return scriptStr;
}

function createSwiperScript(bdData, exportPath) {
    let scriptStr = "";
    let boxs = bdData.boxs;
    $.each(boxs, function (index, prop) {
        if (prop.type == "swiper") {
            let tempHtml = "";
            tempHtml += "var tag" + prop.id + "Option = " + JSON.stringify(prop.swiper) + ";" + "\n\r";
            tempHtml += "var swiper" + prop.id + " = new Swiper('#tag-" + prop.id + "',tag" + prop.id + "Option);" + "\n\r";
            scriptStr += tempHtml + "\n\r";
        }
    });
    return scriptStr;
}

function createTimeScript(bdData, exportPath) {
    let scriptStr = "";
    let boxs = bdData.boxs;
    $.each(boxs, function (index, prop) {
        if (prop.type == "time") {
            let tempHtml = "$('#tag-" + prop.id + "').ledTime();" + "\n\r";
            scriptStr += tempHtml + "\n\r";
        }
    });
    return scriptStr;
}

function exportJsonDataFile(url, exportPath) {
    let filename = getFileName(url);
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',
        async: false,
        timeout: 3000,
        success: function (data, status) {
            if (!fs.existsSync(exportPath + "/jsondata")) {
                fs.mkdirSync(exportPath + "/jsondata");
            }
            fs.writeFile(exportPath + "/jsondata/" + filename, JSON.stringify(data, null, 4), function (err) {
                if (err) throw err;
                console.log("Export MAP Success!");
            });
        },
        complete: function (err, status) {
            //console.log(err);
        },
        error: function (xhr, status, error) {
            console.log("导出JSON失败：" + url);
            layx.msg('JSON数据文件导出失败', {
                dialogIcon: 'error'
            });
        }
    });
}

function exportImageFile(path, exportPath) {
    let respath = "";
    if (fs.existsSync(path)) {
        let last = path.lastIndexOf('\\') > 0 ? path.lastIndexOf('\\') : path.lastIndexOf('/');
        if (last > 0) {
            let name = path.substr(last + 1);
            name =hex_md5(name);
            let dstpath = exportPath + "/img/" + name;
            if (!fs.existsSync(exportPath + "/img")) {
                fs.mkdirSync(exportPath + "/img");
            }
            var readStream = fs.createReadStream(path);
            var writeStream = fs.createWriteStream(dstpath);
            readStream.pipe(writeStream);
            respath = "img/" + name;
        }
    } else {
        let last = path.lastIndexOf('/');
        if (last > 0) {
            let name = path.substr(last + 1);
            name = hex_md5(name);
            let dstpath = exportPath + "/img/" + name;
            if (!fs.existsSync(exportPath + "/img")) {
                fs.mkdirSync(exportPath + "/img");
            }
            request(path).pipe(fs.createWriteStream(dstpath));
            respath = "img/" + name;
        }
    }
    return respath;
}

function createTagsHtml(prop, box) {
    switch (prop.type) {
        case "line":
            craeteHtmlLineChart(prop, box);
            break;
        case "bar":
            craeteHtmlBarChart(prop, box);
            break;
        case "pie":
            craeteHtmlPieChart(prop, box);
            break;
        case "radar":
            craeteHtmlRadarChart(prop, box);
            break;
        case "tree":
            craeteHtmlTreeChart(prop, box);
            break;
        case "treemap":
            craeteHtmlTreemapChart(prop, box);
            break;
        case "sunburst":
            craeteHtmlSunburstChart(prop, box);
            break;
        case "funnel":
            craeteHtmlFunnelChart(prop, box);
            break;
        case "gauge":
            craeteHtmlGaugeChart(prop, box);
            break;
        case "boxplot":
            craeteHtmlBoxplotChart(prop, box);
            break;
        case "heatmap":
            craeteHtmlHeatmapChart(prop, box);
            break;
        case "graph":
            craeteHtmlGraphChart(prop, box);
            break;
        case "parallel":
            craeteHtmlParallelChart(prop, box);
            break;
        case "sankey":
            craeteHtmlsankeyChart(prop, box);
            break;
        case "scatter":
            craeteHtmlScatterChart(prop, box);
            break;
        case "map":
            craeteHtmlMapChart(prop, box);
            break;
        case "candlestick":
            craeteHtmlCandkestickChart(prop, box);
            break;
        case "text":
            craeteHtmlText(prop, box);
            break;
        case "rect":
            craeteHtmlRect(prop, box);
            break;
        case "circle":
            craeteHtmlCircle(prop, box);
            break;
        case "image":
            craeteHtmlImage(prop, box);
            break;
        case "table":
            craeteHtmlTable(prop, box);
            break;
        case "time":
            craeteHtmlTime(prop, box);
            break;
        case "triangle":
            craeteHtmlTriangle(prop, box);
            break;
        case "iframe":
            craeteHtmlIframe(prop, box);
            break;
        case "swiper":
            craeteHtmlSwiper(prop, box);
            break;
        default:
            break;
    }
}

function craeteChartHtml(prop, box) {
    let temp = $('<div class="tag-charts"  style="height:100%;width:100%"></div>');
    temp.attr("id", "tag-" + prop.id);
    return temp;
}

function craeteHtmlLineChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlBarChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlPieChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlRadarChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlTreeChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlTreemapChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlSunburstChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlFunnelChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlGaugeChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlBoxplotChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlHeatmapChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlGraphChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlParallelChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlsankeyChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlScatterChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlMapChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}

function craeteHtmlCandkestickChart(prop, box) {
    let chart = craeteChartHtml(prop, box);
    box.append(chart);
}


function setTagsHtmlParts(tag, prop) {
    let parts = prop.parts;
    tag.attr("id", "tag-" + prop.id);
    if (parts) {
        $.each(parts, function (k, val) {
            switch (k) {
                case "fontSize":
                    tag.css("font-size", val + "px");
                    break;
                case "fontWeight":
                    tag.css("font-weight", val);
                    break;
                case "textShadow":
                    tag.css("text-shadow", val);
                    break;
                case "color":
                    tag.css("color", val);
                    break;
                case "backgroundImage":
                    tag.css({
                        "background-image": "url('" + exportImageFile(val, exportPath) + "')",
                        "background-repeat": "no-repeat",
                        "background-size": "100% 100%"
                    });
                    break;
                case "imgUrl":
                    tag.attr("src", exportImageFile(val, exportPath));
                    break;
                case "backgroundColor":
                    tag.css({
                        "background-color": val
                    });
                    break;
                case "text":
                    tag.html(val);
                    break;
                case "borderRadius":
                    tag.css({
                        "border-radius": val + "px"
                    });
                    break;
                case "iframeUrl":
                    tag.attr("src", val);
                    break;
                case "fontFamily":
                    tag.css("font-family", val);
                    break;
                case "borderWidth":
                    tag.css("border-width", val);
                    break;
                case "borderStyle":
                    tag.css("border-style", val);
                    break;
                case "borderColor":
                    tag.css("border-color", val);
                    break;
                case "textAlign":
                    tag.css("text-align", val);
                    break;
                case "lineHeight":
                    tag.css("line-height", val + "px");
                    break;
                default:
                    break;
            }
        })
    }

}

function craeteHtmlText(prop, box) {
    let tagHtml = $('<div class="tag-text" style="width: 100%;height: 100%;"></div>');
    setTagsHtmlParts(tagHtml, prop);
    box.append(tagHtml);
}

function craeteHtmlRect(prop, box) {
    let tagHtml = $('<div class="tag-rect" style="width:100%;height:100%;background-color:#ffffff;"></div>');
    setTagsHtmlParts(tagHtml, prop);
    box.append(tagHtml);
}

function craeteHtmlCircle(prop, box) {
    let tagHtml = $('<div class="tag-circle" style="width:100%;height:100%;border-radius: 100%;background-color:#ffffff"></div>');
    setTagsHtmlParts(tagHtml, prop);
    box.append(tagHtml);
}

function craeteHtmlImage(prop, box) {
    let tagHtml = $('<img class="tag-image" width="100%" height="100%" src="static/img/default.jpg" />');
    setTagsHtmlParts(tagHtml, prop);
    box.append(tagHtml);
}

function craeteHtmlTable(prop, box) {
    let tagHtml = $('<div class="tag-table kgo-scroll-sty " style="width:100%;height:100%;"><div class="kgo-scroll-head"></div><div class="kgo-scroll-body"><ul class="kgo-scroll-body-ul"></ul></div></div>');
    setTagsHtmlParts(tagHtml, prop);
    box.append(tagHtml);
}

function craeteHtmlTime(prop, box) {
    let tagHtml = $('<div class="tag-time clock"></div>');
    setTagsHtmlParts(tagHtml, prop);
    box.append(tagHtml);
}

function craeteHtmlTriangle(prop, box) {
    let tagHtml = $('<div class="tag-triangle"></div>');
    setTagsHtmlParts(tagHtml, prop);
    box.append(tagHtml);
}

function craeteHtmlIframe(prop, box) {
    let tagHtml = $('<iframe class="tag-iframe" style="width:100%;height:100%;"></iframe>');
    setTagsHtmlParts(tagHtml, prop);
    box.append(tagHtml);
}

function craeteHtmlSwiper(prop, box) {
    let tagHtml = $('<div class="tag-swiper swiper-container"><div class="swiper-wrapper"></div><div class="swiper-pagination"></div></div>');
    if (prop.slides.length > 0) {
        $.each(prop.slides, function (i, slide) {
            let slideDiv = $('<div class="swiper-slide"></div>');
            slideDiv.attr("id", "slide-" + slide.id);
            slideDiv.appendTo(tagHtml.find(".swiper-wrapper"));
            slideDiv.append(craeteChartHtml(slide))
        });
    }
    setTagsHtmlParts(tagHtml, prop);
    box.append(tagHtml);
}