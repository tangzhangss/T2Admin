function fill_insert(obj) {
    $(obj).parent().parent().after(function () {
        var str = $(obj).parent().parent().clone();
        if (foreignArr.length > 0) {
            $(str).find('input').each(function () {
                if (foreignArr.indexOf($(this).attr('name')) == -1 && $(this).attr('type') != 'button') {
                    $(this).val('');
                }
            });
        }
        str.find('input[name=_fill_row_sign]').val('1');
        str.attr('flag', 'add');
        return str;
    });
}

function fill_delete(obj) {
    var record = {};
    if ($(obj).parent().parent().attr('flag') == undefined) {
        $(obj).parent().parent().each(function (index, element) {
            parseobj(record, this);
        });
        if (!$.isEmptyObject(record))
            fillData.rmrecords.push(record);
    }
    $(obj).parent().parent().detach();
}

function fill_save() {
    if ($('#rdp_fill_form').valid()) {
        getrecord();
        getrecords();
        //debugger;
        $.ajax({
            type: 'post',
            url: fillUrl,
            contentType: 'application/json',
            data: JSON.stringify(fillData),
            success: function (data) {
                if (data.code == 0) {
                    alert('保存成功');
                } else {
                    alert(data.msg)
                }
            },
            error: function () {
                alert('请求失败')
            }
        })
    }
}

function getrecord() {
    var record = {};
    $('#rdp_fill_form thead tr').each(function (index, element) {
        parseobj(record, this);
    });
    fillData.record = record;
    //console.log(JSON.stringify(record))
}

function getrecords() {
    fillData.records = [];
    var record = {};
    $('#rdp_fill_form tbody tr').each(function (index, element) {
        if ($(this).attr('loop') != undefined) {
            if (!$.isEmptyObject(record)) {
                fillData.records.push(record);
            }
            record = {};
        }
        parseobj(record, this);
        if ((index + 1) == $('#rdp_fill_form tbody tr').size() && !$.isEmptyObject(record))
            fillData.records.push(record);
    });
    //console.log(JSON.stringify(records))
}

function parseobj(record, obj) {
    $(obj).find('input[type=text]').each(function (ind, el) {
        record[el.name] = $(el).val();
    });
    $(obj).find('input[type=password]').each(function (ind, el) {
        record[el.name] = $(el).val();
    });
    $(obj).find('input[type=hidden]').each(function (ind, el) {
        record[el.name] = $(el).val();
    });
    $(obj).find('select').each(function (ind, el) {
        record[el.name] = $(el).val();
    });
    $(obj).find('textarea').each(function (ind, el) {
        record[el.name] = $(el).text();
    });
    $(obj).find('input[type=radio]').each(function (ind, el) {
        if ($(el).is(":checked")) {
            record[el.name] = $(el).val();
        }
    });
    $(obj).find('input[type=checkbox]').each(function (ind, el) {
        if ($(el).is(":checked")) {
            if (record[el.name] == undefined) {
                record[el.name] = $(el).val();
            } else {
                record[el.name] = record[el.name] + "," + $(el).val();
            }
        }
    });
}

$(function () {
    $('#rdp_fill_form').validate({
        errorLabelContainer: $('#rdp_fill_form div.error')
    });
});