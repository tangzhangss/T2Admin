/**
 * 免费版插件
 */
function doPrint(how) {
	if (isIE()) {
		//加载打印控件
		if ($("#jatoolsPrinter").length == 0) {
			var $printer = "<OBJECT  ID='jatoolsPrinter' CLASSID='CLSID:B43D3361-D075-4BE2-87FE-057188254255' codebase='"+content_path+"/statics/print/jatoolsPrinter.cab#version=8,6,0,0'></OBJECT>";
			$("body").append($printer);
		}

		myDoc = {
			printBackground : true, //打印背景颜色
			settings : {
				topMargin : 100,
				leftMargin : 80,
				bottomMargin : 100,
				rightMargin : 80,
				orientation : $('input[name="printfx"]:checked').val(),
				paperName : 'a4'
			}, // 配置页边距（单位1/10mm），orientation:1为纵向,2为横向，选择a4纸张进行打印
			documents : window.frames["prefrm"].document,
			copyrights : '杰创软件拥有版权  www.jatools.com' // 版权声明,必须   
		};
		if (how == '打印预览...')
			jatoolsPrinter.printPreview(myDoc); // 打印预览
		else if (how == '打印...')
			jatoolsPrinter.print(myDoc, true); // 打印前弹出打印设置对话框
		else
			jatoolsPrinter.print(myDoc, false); // 不弹出对话框打印
	} else {
		$("#prefrm")[0].contentWindow.print();
	}
}

function isIE() {
	if (!!window.ActiveXObject || "ActiveXObject" in window)
		return true;
	else
		return false;
}