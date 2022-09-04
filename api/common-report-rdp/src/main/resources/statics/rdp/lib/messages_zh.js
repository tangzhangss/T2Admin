(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define( ["jquery", "jquery.validate"], factory );
	} else if (typeof module === "object" && module.exports) {
		module.exports = factory( require( "jquery" ) );
	} else {
		factory( jQuery );
	}
}(function( $ ) {
	//手机号码验证
	jQuery.validator.addMethod("phone", function(value, element) {
	 var length = value.length;
	 var phone =  /^((1)+\d{10})$/
	 return this.optional(element) || (length == 11 && phone.test(value));
	}, "手机号码格式错误");
	// 身份证号码验证
		jQuery.validator.addMethod("idcard", function(value, element) {
			return this.optional(element) || isIdCardNo(value);
		}, "请正确输入您的身份证号码");
	//手机号码验证
	jQuery.validator.addMethod("regexp", function(value, element) {
	var regexp =  eval($(element).attr("regexp"));
	return this.optional(element)||regexp=="" || (regexp.test(value));
	}, "格式验证失败");
/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
 */
$.extend( $.validator.messages, {
	required: "当前字段不能为空",
	remote: "请修正此字段",
	email: "请输入有效的电子邮件地址",
	url: "请输入有效的网址",
	date: "请输入有效的日期",
	dateISO: "请输入有效的日期 (YYYY-MM-DD)",
	number: "请输入有效的数字",
	digits: "只能输入数字",
	creditcard: "请输入有效的信用卡号码",
	equalTo: "你的输入不相同",
	extension: "请输入有效的后缀",
	maxlength: $.validator.format( "最多可以输入 {0} 个字符" ),
	minlength: $.validator.format( "最少要输入 {0} 个字符" ),
	rangelength: $.validator.format( "请输入长度在 {0} 到 {1} 之间的字符串" ),
	range: $.validator.format( "请输入范围在 {0} 到 {1} 之间的数值" ),
	max: $.validator.format( "请输入不大于 {0} 的数值" ),
	min: $.validator.format( "请输入不小于 {0} 的数值" )
} );
return $;
}));
//身份证验证 引入的方法
function isIdCardNo(num) {
    var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,8, 4, 2, 1);
    var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4","3", "2");
    var varArray= new Array();
    var intValue;
    var lngProduct = 0;
    var intCheckDigit;
    var intStrLen = num.length;
    var idNumber= num;
    //initialize
    if((intStrLen != 15) && (intStrLen !=18)) {
       return false;
    }
    // check andset value
    for (i = 0;i < intStrLen; i++) {
       varArray[i] = idNumber.charAt(i);
       if ((varArray[i] < '0' || varArray[i]> '9') && (i != 17)){
           return false;
       } else if (i < 17) {
           varArray[i] = varArray[i] * factorArr[i];
       }
    }


    if(intStrLen == 18) {
       //check date
       var date8 = idNumber.substring(6, 14);
       if (isDate8(date8) == false) {
           return false;
       }
       // calculate the sum of the products
       for (i = 0; i < 17; i++) {
           lngProduct = lngProduct + varArray[i];
       }
       // calculate the check digit
       intCheckDigit = parityBit[lngProduct % 11];
       // check last digit
       if (varArray[17] != intCheckDigit) {
           return false;
       }
    }
    else{       //length is 15
       //check date
       var date6 = idNumber.substring(6, 12);
       if (isDate6(date6) == false) {
           return false;
       }
    }
    return true;
}
function isDate6(sDate) {
    if(!/^[0-9]{6}$/.test(sDate)) {
       return false;
    }
    var year,month, day;
    year =sDate.substring(0, 4);
    month =sDate.substring(4, 6);
    if (year< 1700 || year > 2500) returnfalse
    if (month< 1 || month > 12) return false
    return true
}


function isDate8(sDate) {
    if(!/^[0-9]{8}$/.test(sDate)) {
       return false;
    }
    var year,month, day;
    year =sDate.substring(0, 4);
    month =sDate.substring(4, 6);
    day =sDate.substring(6, 8);
    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,31]
    if (year< 1700 || year > 2500) return false
    if (((year %4 == 0) && (year % 100 != 0)) ||(year % 400 == 0)) iaMonthDays[1] = 29;
    if (month< 1 || month > 12) return false
    if (day< 1 || day > iaMonthDays[month - 1])return false
    return true
}
