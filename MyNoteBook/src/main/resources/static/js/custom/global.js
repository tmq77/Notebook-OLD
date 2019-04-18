/**
 * 全局js
 */

// 禁用F12和右键
/*document.oncontextmenu = function() {
	return false;
};
document.onkeydown = function() {
	if (window.event && window.event.keyCode == 123) {
		event.keyCode = 0;
		event.returnValue = false;
		return false;
	}
};*/

/**
 * 消息框配置js
 */
toastr.options = {
	closeButton : false,
	debug : false,
	progressBar : true,
	positionClass : "toast-top-full-width",
	onclick : null,
	showDuration : "300",
	hideDuration : "1000",
	timeOut : "2000",
	extendedTimeOut : "1000",
	showEasing : "swing",
	hideEasing : "linear",
	showMethod : "fadeIn",
	hideMethod : "fadeOut"
};

// 验证电话号码
function isPhoneAvailable(str) {
	var myreg = /^[1][3,4,5,7,8][0-9]{9}$/g;
	return myreg.test(str);
}

//验证半角数字
function isAlphaNum(str) {
	// 年份由2-9开头
	// id最少18位
	// ^[2-9] : 开头 2-9  -> 1位
	// [0-9]{17,}: 中间 0-9 -> 最少17位(后面跟上终止符代表中间的数字至少要匹配17位才能结束)
	// $终止符，此处可以不加终止符
	var myreg = /^[2-9][0-9]{17,}$/g;
	return myreg.test(str);
}

// 验证邮箱
function isMailAvailable(str) {
	var myreg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
	return myreg.test(str);
}

$(function() {
	$("input[name='password'], input[name='checkPassword']").on("change", function(){
		var hash = hex_md5($(this).val());
		$(this).val(hash);
	});
})