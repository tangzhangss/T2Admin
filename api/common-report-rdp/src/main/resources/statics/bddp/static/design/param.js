
$(function () {
    $("#add-param").bind("click", function () {
        addParamInput();
    });
    $("#save-param").bind("click", function () {
        saveParams();
    });
});
function addParamInput(name, defval) {
    var div = $('<div class="param-group"></div>');
    var input = $('<input type="text" class="param-inputs input-val" placeholder="参数名">');
    var def = $('<input type="text" class="param-inputs input-def" placeholder="默认值">');
    var delbtn = $('<button type="button" class="param-btn-del"><i class="fa fa-minus-circle" aria-hidden="true"></i></button>');
    if (name) {
        input.val(name);
    }
    if (defval) {
        def.val(defval);
    }
    div.append(input).append(def).append(delbtn);
    $(delbtn).bind("click", function (e) {
        $(this).parent().remove();
    });
    div.appendTo("#param-panel");
}

function saveParams() {
    var params = {};
    $("#param-panel").find(".input-val").each(function (index, d) {
        if (d.value) {
            var def = $(this).next(".input-def").val();
            params[d.value] = def;
        }
    })
    var box = currBox;
    var prop = box.data("prop");
    prop.params = params;
    initTagData($(".box-selected"));
    layx.msg("保存成功",{dialogIcon:'success'});
}
function bulidParams(prop) {
    $("#param-panel").empty();
    var params = prop.params;
    if (params) {
        $.each(params, function (k, v) {
            addParamInput(k, v);
        });
    }

}