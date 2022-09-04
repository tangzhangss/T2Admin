
var tagId, propId;
$(document).ready(() => {



    $("#saveBtn").bind("click", function () {
        saveTagProp();
    });
    $("#saveItemBtn").bind("click", function () {
        saveItem();
    });
    $("#addTagBtn").bind("click", function () {
        addTagItem();
    });
    $("#propType").bind("change", function () {
        changeItemsType($(this).val());
    });

    getTags();
    getProps();
});

function saveBD() {
    // var data = db.export();
    // var buffer = new Buffer(data);
    // fs.writeFileSync(path.join(__dirname, '../bddp.db'), buffer);
    console.log("save");
}

function getProps() {
    let res = executeSQL("SELECT * FROM propitems");
    var zNodes = [];
    let tableData = res[0];
    let columns = tableData.columns;
    let values = tableData.values;
    let len = columns.length;
    $.each(values, function (i, node) {
        let temp = {};
        for (let j = 0; j < len; j++) {
            temp[columns[j]] = node[j];
        }
        zNodes.push(temp);

    });
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };
    $.fn.zTree.init($("#propTree"), setting, zNodes);
}

function getTags() {
    let res = executeSQL("SELECT * FROM tags");
    let tableData = res[0];
    let index = tableData.columns.indexOf("type");
    let values = tableData.values;
    let tags = {};
    $.each(values, function (i, tag) {
        if (tags[tag[index]]) {
            tags[tag[index]].push(tag);
        } else {
            tags[tag[index]] = [];
            tags[tag[index]].push(tag);
        }
    });
    let div = $('#accordion');
    $.each(tags, function (k, v) {
        let accordionName = $('<h3>' + k + '</h3>');
        let accordionContent = $('<div></div>');
        div.append(accordionName).append(accordionContent);
        $.each(v, function (i, data) {
            let item = $('<div class="item-tag" data-id="' + data[0] + '">' + data[1] + '</div>');
            item.appendTo(accordionContent);
            item.bind("click", function () {
                let id = $(this).data("id");
                $(".item-tag").removeClass("on");
                $(this).addClass("on");
                getTagProp(id);
                tagId = id;
            });
        });
    });
    div.accordion({
        //heightStyle: "fill"
    });

}

function getTagProp(tagId) {
    let res = executeSQL("select propitems.*,a.tagId FROM propitems LEFT JOIN (SELECT * FROM tagprop WHERE tagId=" + tagId + ") a ON a.propId =propitems.id");
    var zNodes = [];
    let tableData = res[0];
    let columns = tableData.columns;
    let values = tableData.values;
    let len = columns.length;
    $.each(values, function (i, node) {
        let temp = {};
        for (let j = 0; j < len; j++) {
            temp[columns[j]] = node[j];
        }
        if (temp.pId == 0) {
            temp.halfCheck = (temp["tagId"] == tagId);
            temp.checked = (temp["tagId"] == tagId);
        } else {
            temp.checked = (temp["tagId"] == tagId);
        }
        zNodes.push(temp);

    });
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: zTreeOnClick
        }
    };
    $.fn.zTree.destroy("propTree");
    $.fn.zTree.init($("#propTree"), setting, zNodes);

}

function saveTagProp() {
    let id = $(".item-tag.on").data("id");
    var treeObj = $.fn.zTree.getZTreeObj("propTree");
    var nodes = treeObj.getCheckedNodes(true);
    var sqlStr = "INSERT INTO tagprop VALUES "
    var values = [];
    let idObj = {};
    $.each(nodes, function (i, node) {
        if (!idObj[node.id]) {
            sqlStr += "("+id+","+node.id+"),";
             values.push(id);
             values.push(node.id);
             idObj[node.id] = true;
        }

    });
    sqlStr = sqlStr.substring(0, sqlStr.length - 1);
    if (values.length > 0) {
        executeUpdateSQL(["DELETE FROM tagprop WHERE tagId=" + id]);
        executeInsertSQL(sqlStr);

    }
   // saveBD();

}

function delTagProp(tagId) {
    executeUpdateSQL(["DELETE FROM tagprop WHERE tagId=" + tagId]);
}

function zTreeOnClick(event, treeId, treeNode) {
    if (treeNode.level == 1) {
        propId = treeNode.id;
        getItems();
    }
    $("#itemform").hide();
    //console.log(treeNode);
};


function addTagItem() {
    let main = $("#propitems");
    let li = $('<li class="tag-items" >新建属性</li>');
    li.data("propId", propId);
    main.append(li);
    li.bind("click", function () {
        $("#itemform").show();
        $(".tag-items").removeClass("on");
        $(this).addClass("on");
        let type = $(this).data("propType");
        changeItemsType(type);
        if (type) {
            $("#propType").val(li.data("propType"));
            $("#propTitle").val(li.data("propTitle"));
            $("#propName").val(li.data("propName"));
            $("#propTag").val(li.data("propTag"));
            $("#propItems").val(li.data("propItems"));
        } else {
            $("#propType").val("title");
            $("#propTitle").val("");
            $("#propName").val("");
            $("#propTag").val("");
            $("#propItems").val("");
        }
    });
}

function changeItemsType(type) {
    $(".propTitle").hide();
    $(".propName").hide();
    $(".propItems").hide();
    $(".propTag").hide();
    if (type == "title" || !type) {
        $(".propTitle").show();
    } else if (type == "driver") {

    } else if (type == "tag") {
        $(".propItems").show();
        $(".propTag").show();
        $(".propTitle").show();
        $(".propName").show();

    } else if (type == "btn") {
        $(".propItems").show();
        //  $(".propTag").show();
        $(".propTitle").show();
        $(".propName").show();

    }
}

function saveItem() {
    // tagId, propId;
    let div = $(".tag-items.on");


    let values = [];
    let propType = $("#propType").val();
    let propTitle = $("#propTitle").val();
    let propName = $("#propName").val();
    let propTag = $("#propTag").val();
    let propItems = $("#propItems").val();
    let order = div.index();
    values.push(propId);
    values.push(propType);
    values.push(propTitle);
    values.push(propTag);
    values.push(propItems);
    values.push(propName);
    values.push(order);
    if (propType == "driver") {
        div.html("分割线");
        values[2] = "分割线";
    } else {
        div.html(propTitle);
    }
    div.data("propType", propType);
    div.data("propTitle", propTitle);
    div.data("propName", propName);
    div.data("propTag", propTag);
    div.data("propItems", propItems);
    if (div.data("itemId")) {
        let updateSqlStr = "UPDATE propitems SET type='" + propType + "', name='" + propTitle + "', tag='" + propTag + "', items='" + propItems + "',keyName='" + propName + "','sqen'='" + order + "' WHERE (id='" + div.data("itemId") + "')";
        executeUpdateSQLStr(updateSqlStr);
    } else {
        let sqlStr = 'INSERT INTO propitems ("pId", "type", "name", "tag", "items","keyName","sqen") VALUES ("'+propId+'", "'+propType+'", "'+propTitle+'", "'+propTag+'", "'+propItems+'", "'+propName+'", "'+order+'")';
        executeInsertSQL(sqlStr);
        getItems();
    }
  //  saveBD();

}

function getItems() {
    var stmt = executeSQL("SELECT * FROM propitems WHERE pId=" + propId + " order by sqen asc");
    let main = $("#propitems");
    main.empty();
    var list = stmt[0].values;
    $.each(list, function (i, res) {
        let li = $('<li class="tag-items" >新建属性</li>');
        li.data("propId", propId);

        li.data("propType", res[2]);
        li.data("propTitle", res[3]);
        li.data("propName", res[9]);
        li.data("propTag", res[4]);
        li.data("propItems", res[5]);
        li.data("itemId", res[0]);
        li.data("value", res[7]);

        li.html(res[3]);
        main.append(li);
        li.bind("click", function () {
            $("#itemform").show();
            $(".tag-items").removeClass("on");
            $(this).addClass("on");
            let type = $(this).data("propType");
            changeItemsType(type);
            if (type) {
                $("#propType").val(li.data("propType"));
                $("#propTitle").val(li.data("propTitle"));
                $("#propName").val(li.data("propName"));
                $("#propTag").val(li.data("propTag"));
                $("#propItems").val(li.data("propItems"));
            } else {
                $("#propTitle").val("");
                $("#propName").val("");
                $("#propTag").val("");
                $("#propItems").val("");
            }
        });
    })
    main.sortable({
        change: function (event, ui) {
            console.log("change", ui);
        },
        update: function (event, ui) {
            console.log("update", ui);
            var sqlStrArr = [];
            $(".tag-items ").each(function () {
                let updateSqlStr = "UPDATE propitems SET sqen='" + $(this).index() + "' WHERE id='" + $(this).data("itemId") + "'";
                sqlStrArr.push(updateSqlStr);
            });
            executeUpdateSQL(sqlStrArr);
            console.log(sqlStrArr);
        }
    });
}