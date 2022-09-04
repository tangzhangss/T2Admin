function doPrint(how) {
	var myDoc = {
		settings : {
			paperName :'A4',
			portrait : true,
			marginLeft : 15,
			marginTop : 15,
			marginRight : 15,
			marginBottom : 15
		},
		documents : document, // 打印页面
		copyrights : '杰创软件拥有版权  www.jatools.com' // 版权声明必须
	};
	var jcp = getJCP();
	// 调用打印方法
	if (how == '打印预览')
		jcp.printPreview(myDoc, false);
	else if (how == '打印...') {
		jcp.print(myDoc, true);
	} else
		jcp.print(myDoc, false); // 不弹出对话框打印
}