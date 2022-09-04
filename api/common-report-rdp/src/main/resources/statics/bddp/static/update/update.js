var http = require('http');
var fs = require('fs');
//var mkdirp = require('mkdirp');
var serverUrl = 'http://bddp.wangcong.online/bddp/';
var layxUpdate;

function getVersion() {
    var url = serverUrl + 'version.json?r=' + Math.random();
    http.get(url, function (req, res) {
        var datas = '';
        req.on('data', function (data) {
            datas += data;
        });
        req.on('end', function () {
            var serverVers = JSON.parse(datas);
            var localhostVer = getCurrVersion();
            $.each(serverVers, function (s) {
                let serverVer = serverVers[s];
                var flag = serverVer.versuion == (Number(localhostVer.versuion) + 0.1).toFixed(2);
                if (flag) {
                    let files = serverVer.files;
                    layxUpdate = layx.iframe('update', '系统更新 <small>当前版本' + localhostVer.versuion + '</small>', './static/update/update.html', {
                        shadable: true,
                        icon: '<img src="./static/img/logo.ico" style="height:22px;display:block;" />',
                        alwaysOnTop: true,
                        border: false,
                        minMenu: false,
                        maxMenu: false,
                        //   closeMenu: false,
                        movable: false,
                        resizable: false,
                        event: {
                            onload: {
                                before: function (layxWindow, winform) {
                                    // alert("加载之前");
                                    // console.log(new Date() + "加载之前~")
                                    // console.log(layxWindow,winform);
                                    // console.log("=============分割线===============");
                                },
                                after: function (layxWindow, winform) {
                                    //  alert("加载之后");
                                    $(layxWindow).find("iframe")[0].contentWindow.createDesc(JSON.stringify(serverVer.desc), serverVer.versuion);
                                }
                            }
                        }
                    });
                    console.log("有新版本");
                }
            });
        });
    });
};

var updateSys = function () {
    var url = serverUrl + 'version.json?r=' + Math.random();
    http.get(url, function (req, res) {
        var datas = '';
        req.on('data', function (data) {
            datas += data;
        });
        req.on('end', function () {
            var serverVers = JSON.parse(datas);
            var localhostVer = getCurrVersion();
            $.each(serverVers, function (s) {
                var serverVer = serverVers[s];
                var flag = serverVer.versuion == (Number(localhostVer.versuion) + 0.1).toFixed(2);
                if (flag) {
                    var win = layx.getFrameContext('update');
                    win.showProgress();
                    let files = serverVer.files;
                    let len = files.length;
                    $.each(files, function (i, filename) {
                        updateFile(serverVer.versuion + "/" + filename, filename, len, i, serverVer.versuion);
                        win.setProgress(Math.floor((i + 1) / len * 100));
                    });
                }
            });


        });
    });
};
var updateSys2 = function () {
    var url = serverUrl + 'version.json?r=' + Math.random();
    http.get(url, function (req, res) {
        var datas = '';
        req.on('data', function (data) {
            datas += data;
        });
        req.on('end', function () {
            var serverVers = JSON.parse(datas);
            var serverVer = serverVers[0];
            var localhostVer = getCurrVersion();
            if (serverVer.versuion > localhostVer.versuion) {
                var win = layx.getFrameContext('update');
                win.showProgress();
                let files = serverVer.files;
                let len = files.length;
                $.each(files, function (i, filename) {
                    getHttpData(serverVer.versuion + "/" + filename, function (data) {
                        let filepath = './resources/app/' + filename;
                        let pos = filepath.lastIndexOf("/");
                        let dirpath = filepath.substr(0, pos);
                        mkdirs(dirpath, () => {
                            console.log('文件创建成功或已存在');
                            try {

                                fs.writeFileSync(filepath, data);
                                console.log('更新文件"' + filepath + '"成功！');
                                win.setProgress(Math.floor((i + 1) / len) * 100);
                                if ((i + 1) == len) {
                                    updateVersionFile(serverVer.versuion);
                                }
                            } catch (error) {
                                console.log('更新文件"' + filepath + '"失败！');
                                layx.msg('更新文件"' + filepath + '"失败！', {
                                    dialogIcon: 'warn',
                                    position: "ct"
                                });
                            }
                        })

                    });
                });
            }
        });
    });
};

var updateVersionFile = function (versionNumber) {
    var versionObj = fs.readFileSync(path.join(__dirname, "/version.json"));
    versionObj = JSON.parse(versionObj);
    versionObj.versuion = versionNumber;
    fs.writeFile(path.join(__dirname, "/version.json"), JSON.stringify(versionObj, null, 4), function (err) {
        if (err) throw err;
        console.log("Export Account Success!");
    });
}

var getCurrVersion = function () {
    var version = fs.readFileSync(path.join(__dirname, "/version.json"));
    version = JSON.parse(version);
    return version;
}


var updateFile = function (url, filename, len, i, version) {
    var win = layx.getFrameContext('update');
    let filepath = '../' + filename;
    let pos = filepath.lastIndexOf("/");
    let dirpath = filepath.substr(0, pos);
    // console.log(path.join(__dirname,dirpath));
    // mkdirp(path.join(__dirname, dirpath), function (err) {
    //     if (err) {
    //         console.error(err);
    //     } else {
    //         try {
    //             var file = fs.createWriteStream(path.join(__dirname, filepath));
    //             http.get(serverUrl + url, function (res) {
    //                 res.on('data', function (data) {
    //                     file.write(data);
    //                 }).on('end', function () {
    //                     file.end();
    //                     console.log('更新文件"' + path.join(__dirname, filepath) + '"成功！');
    //                     if ((i + 1) == len) {
    //                         updateVersionFile(version);
    //                     }
    //                 });
    //             });
    //         } catch (error) {
    //             console.log('更新文件"' + filepath + '"失败！');
    //             layx.msg('更新文件"' + filepath + '"失败！', {
    //                 dialogIcon: 'warn',
    //                 position: "ct"
    //             });
    //         }
    //     }
    // });


    mkdirs(path.join(__dirname, dirpath), function () {
        console.log('文件夹创建成功或已存在');
        try {
            var file = fs.createWriteStream(path.join(__dirname, filepath));
            http.get(serverUrl + url, function (res) {
                res.on('data', function (data) {
                    file.write(data);
                }).on('end', function () {
                    file.end();
                    console.log('更新文件"' + path.join(__dirname, filepath) + '"成功！');
                    if ((i + 1) == len) {
                        updateVersionFile(version);
                    }
                });
            });
        } catch (error) {
            console.log('更新文件"' + filepath + '"失败！');
            layx.msg('更新文件"' + filepath + '"失败！', {
                dialogIcon: 'warn',
                position: "ct"
            });
        }
    })




};

var getHttpData = function (filepath, success, error) {
    // 回调缺省时候的处理
    success = success || function () {};
    error = error || function () {};

    var url = serverUrl + filepath + '?r=' + Math.random();

    http.get(url, function (res) {
        var statusCode = res.statusCode;

        if (statusCode !== 200) {
            // 出错回调
            error();
            // 消耗响应数据以释放内存
            res.resume();
            return;
        }

        res.setEncoding('utf8');
        var rawData = '';
        res.on('data', function (chunk) {
            rawData += chunk;
        });

        // 请求结束
        res.on('end', function () {
            // 成功回调
            success(rawData);
        }).on('error', function (e) {
            // 出错回调
            error();
        });
    });
};



// getHttpsData('index.html', function (data) {
//     // 写入文件
//     fs.writeFileSync('index.html', data);
//     // 然后下一个文件获取并写入...
//   });

// 递归创建目录 异步方法  
function mkdirs(dirname, callback) {
    fs.exists(dirname, function (exists) {
        if (exists) {
            callback();
        } else {
            // console.log(path.dirname(dirname));  
            mkdirs(path.dirname(dirname), function () {
                fs.mkdir(dirname, callback);
                console.log(dirname);
                console.log('在' + path.dirname(dirname) + '目录创建好' + dirname + '目录');
            });
        }
    });
}
// 递归创建目录 同步方法
function mkdirsSync(dirname) {
    if (fs.existsSync(dirname)) {
        return true;
    } else {
        if (mkdirsSync(path.dirname(dirname))) {
            fs.mkdirSync(dirname);
            return true;
        }
    }
}

// mkdirs('hello/a/b/c',() => {
//     console.log('done');
// })