$(function () {
    $(window).scroll(function () {
        //若滚动条离顶部大于100元素
        if ($(window).scrollTop() > 100)
            $("#go-top").fadeIn(1000);
        // $("#go-top").show();
        else
            $("#go-top").fadeOut(1000);
        // $("#go-top").hide();
    });

    $("#go-top").click(function (e) {
        e.preventDefault();
        $('body,html').animate({scrollTop: 0}, 1000);
    });

    toastr.options = {
        "closeButton": true,
        "debug": false,
        "newestOnTop": true,
        "progressBar": true,
        "positionClass": "toast-top-center",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "2000",
        "hideDuration": "2000",
        "timeOut": "2000",
        "extendedTimeOut": "2000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };

    let clipboard = new ClipboardJS('.btn-success');
    //优雅降级:safari 版本号>=10,提示复制成功;否则提示需在文字选中后，手动选择“拷贝”进行复制
    clipboard.on('success', function (e) {
        e.clearSelection();
        if ($("#json-textarea").val()) {
            toastr.success("复制成功！");
        } else {
            toastr.info("json字符串为空!");
        }
        console.info('Action:', e.action);
        console.info('Text:', e.text);
        console.info('Trigger:', e.trigger);
    });
    clipboard.on('error', function (e) {
        toastr.info('请选择"拷贝"进行复制!');
        console.error('Action:', e.action);
        console.error('Trigger:', e.trigger);
    });

    $('#format-btn').on('click', function () {
        formatJson($('#json-textarea').val());
    });
    $('#compress-btn').on('click', function () {
        compressJson($('#json-textarea').val())
    });
    $('#save-btn').on('click', function () {
        let text = $("#json-textarea").val();
        if (text) {
            saveFile(text);
        } else {
            toastr.info("文本为空!");
        }
    });
    $('#clear-btn').on('click', function () {
        clearJson();
    });

    let collapseExpandBtn = $('#collapse-expand-btn');
    collapseExpandBtn.html("折叠");
    collapseExpandBtn.on('click', function () {
        let btnText = $(this).html();
        if (btnText === "折叠") {
            $('#json').JSONView('collapse');
            $(this).html("展开");
        } else {
            $('#json').JSONView('expand');
            $(this).html("折叠");
        }
    });

    let fullScreenBtn = $("#full-screen-btn");
    fullScreenBtn.html("全屏");
    fullScreenBtn.on('click', function () {
        let btnText = $(this).html();
        if (btnText === "全屏") {
            fullScreen();
        } else {
            exitFullScreen();
        }
    });

    let fullscreenState = document.getElementById("full-screen-btn");
    document.addEventListener("fullscreenchange", function () {
            fullscreenState.innerHTML = (document.fullscreen) ? "退出" : "全屏";
        }, false
    );

    document.addEventListener("mozfullscreenchange", function () {
            fullscreenState.innerHTML = (document.mozFullScreen) ? "退出" : "全屏";
        }, false
    );

    document.addEventListener("webkitfullscreenchange", function () {
            fullscreenState.innerHTML = (document.webkitIsFullScreen) ? "退出" : "全屏";
        }, false
    );
    document.addEventListener("msfullscreenchange", function () {
            fullscreenState.innerHTML = (document.msFullscreenElement) ? "退出" : "全屏";
        }, false
    );
});

let result;

function clearJson() {
    $("#json-textarea").val("");
}

function compressJson(jsonString) {
    try {
        eval("(" + jsonString + ")");
        let compressJson = JSON.stringify(JSON.parse(jsonString));
        $("#json-textarea").val(compressJson);
    } catch (e) {
        toastr.error("json字符串格式不正确!");
    }
}

function formatJson(jsonString) {
    try {
        if (!jsonString) {
            toastr.info("json字符串为空!");
            return;
        }
        let jsonObject = eval("(" + jsonString + ")");
        let json = JSON.stringify(jsonObject, undefined, 4);
        $("#json-textarea").val(json);
        result = json;
        $("#json").JSONView(jsonObject);

    } catch (e) {
        toastr.error("json字符串格式不正确!");
    }
}

//全屏
function fullScreen() {
    // let el = document.documentElement;
    let el = document.getElementById("format-json-result");
    let rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullscreen;
    if (typeof rfs !== "undefined" && rfs) {
        rfs.call(el);
        // $("#json").css({"min-width":$(document.body).width(),"max-width":$(document.body).width()});
        // $("#json").css({"min-height":$(document.body).height(),"max-height":$(document.body).height()});
        console.log($(window).height()); //浏览器当前窗口可视区域高度
        console.log($(document).height()); //浏览器当前窗口文档的高度
    }
}

//退出全屏
function exitFullScreen() {
    if (document.exitFullscreen) {
        document.exitFullscreen();
    }
    else if (document.mozCancelFullScreen) {
        document.mozCancelFullScreen();
    }
    else if (document.webkitCancelFullScreen) {
        document.webkitCancelFullScreen();
    }
    else if (document.msExitFullscreen) {
        document.msExitFullscreen();
    }
    if (typeof cfs !== "undefined" && cfs) {
        cfs.call(el);
    }
}

function fakeClick(obj) {
    let ev = document.createEvent("MouseEvents");
    ev.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
    obj.dispatchEvent(ev);
}

function exportRaw(name, data) {
    let urlObject = window.URL || window.webkitURL || window;
    let export_blob = new Blob([data]);
    let save_link = document.createElementNS("http://www.w3.org/1999/xhtml", "a")
    save_link.href = urlObject.createObjectURL(export_blob);
    save_link.download = name;
    fakeClick(save_link);
}

function saveFile(text){
    exportRaw('json-msj.txt', text);
}