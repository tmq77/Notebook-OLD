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
	var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
	if (!myreg.test(str)) {
		return false;
	} else {
		return true;
	}
}

// 验证邮箱
function isMailAvailable(str) {
	var myreg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
	if (!myreg.test(str)) {
		return false;
	} else {
		return true;
	}
}

$(function() {
	$("input[name='password'], input[name='checkPassword']").on("change", function(){
		var hash = hex_md5($(this).val());
		$(this).val(hash);
	});
})