/**
 * home.html 2019年3月8日 23点22分
 */
$(() => {
	// 富文本编辑器变量
	let textEditor;
	// 分页控件生成 pageCount : 总页数
	let pagination = (pageCount) => {
		let cur = parseInt($("#page-index").val());
		let pre = cur - 1;
		let next = cur + 1;
		// 上一页
		let preHref = "/notes?page=" + pre;
		// 下一页
		let nextHref = "/notes?page=" + next;
		
		if (pre <= 0) {
			// 已经是第一页,上一页按钮的href指向#
			preHref = "#";
		}
		
		if (next >= pageCount) {
			// 已经是最后一页，下一页按钮的href指向#
			nextHref = "#";
		}
		
		let li = '<li><a th:href="' + preHref + '">&laquo;上一页</a></li>';
		for (let i = 1; i <= pageCount; i++) {
			li += "<li><a href='/notes?page=" + i +"'>" + i + "</a></li>";
		}
		li += '<li><a href="' + nextHref + '">下一页&raquo;</a></li>';
		$(".pagination").append(li);
	};
	
	if ($(".note-list").length >= 10) {
		$(".footer").show();
		// 根据页数拼接页数按钮
		let count = parseInt($("#page-count").text());
		pagination(count);
	}
	
	// 导航条点击事件:添加背景色
	$("li").on("click", () => {
		$(".active").removeClass("active");
		$(this).addClass("active");
	});
	
	// 钩子函数: 当模态框对用户可见时触发（将等待 CSS 过渡效果完成）:加载编辑器,第一次加载时会设置模态框高度
	$("#modal-new-note").on("shown.bs.modal", () => {
		if (!textEditor) {
			textEditor = editormd("my-editormd", {
				placeholder : "记录精彩瞬间~",
				emoji : true,
				// width : "90%",  // 编辑器控件的宽度
				height : "100%", // height : "100%",  如果指定px 例如 height : 640，那么不需要加引号也不用加px(默认px)，指定百分比则需要加引号
				editorTheme : "3024-day", // 设置主题颜色 具体见源码
				theme : "default", // 设置工具栏主题颜色,default | dark
				previewTheme : "default", // 设置预览颜色  default | dark
				
				taskList : true,
				tex : true,
				flowChart : true,
				sequenceDiagram : true,
				codeFold : true,
				syncScrolling : "single",
				path : "/editor.md/lib/",  // 控件的依赖包,因为springmvc的拦截器设置,"/"代表资源根目录，即static目录下
				saveHTMLToTextarea : true, // / 保存 HTML 到 Textarea  会自动生成一个隐藏的Textarea保存HTML代码 控件名为 $-html-code  $代表创建编辑器的div的id值,这里是my-editormd

				toolbarIcons : function() {
					// Or return editormd.toolbarModes[name]; //
					// full, simple, mini
					// Using "||" set icons align right.
					// 自定义按钮, "|"分隔符为一组
					return [ /*"submit", "|",*/ "undo", "redo", "|",
							"bold", "hr", "del", "|", "h1", "h2",
							"h3", "h4", "h5", "h6", "|", "list-ul",
							"list-ol", "|", "image", "code",
							"code-block", "||", "watch",
							"fullscreen", "preview", "help", "|",
							"clear"]
				},
				// 自定义图标类
				toolbarIconsClass : {
					// submit : "fa-save", // 指定一个FontAawsome的图标类
					close : "fa-close",
				},
				// 自定义工具栏按钮的事件处理
				toolbarHandlers : {
					submit : function(cm, icon, cursor, selection) {
						// var cursor = cm.getCursor();
						// //获取当前光标对象，同cursor参数
						// var selection = cm.getSelection();
						// //获取当前选中的文本，同selection参数
						// this == 当前editormd实例
						save();
					},
					close : function(cm, icon, cursor, selection) {
						// [关闭]回调函数
						// TODO SOMETHINE...
					},
				},
				lang : {
					toolbar : {
						submit : "保存笔记(将会提交到数据库)", // 自定义按钮的提示文本，即title属性
						close : "关闭(不会清空当前编辑内容)",
					}
				},
				/* 上传图片配置 */
				/*
				 * imageUpload : true, imageFormats : [ "jpg",
				 * "jpeg", "gif", "png", "bmp", "webp" ],
				 * imageUploadURL : "${proPath }/base/blog/upFile",
				 */
			});
		}
		$("#my-editormd").height($(window).height() * 0.7);
	})
	
	// 钩子函数: 在调用 show 方法后触发
	$("#modal-new-note").on("show.bs.modal", () => {
		
	})
	
	// clear按钮事件:清空输入
	$("#btn-clear").on("click", () => {
		$("#txt-title").val("");
	})
	
	// 保存按钮事件:保存文档
	$("#btn-save").on("click", () => {
		if (!$.trim($("#txt-title").val())) {
			toastr.warning("请输入标题");
			return;
		}
		if (!$.trim($("textarea[name='my-editormd-html-code']").val())) {
			toastr.warning("内容不能为空");
			return;
		}
		$("#frm-create").submit();
	})
});

/**
 * 保存笔记
 */
function save() {
	// TODO 输入验证没有做，后台的也没做
	$.ajax({
		url : "/save",
		type : "post",
		dataType : "json",
		cache : false,
		data : {
			title : $("#title").val(),
			mdText : $("#my-editormd-markdown-doc").val(),
			mdHtml : $('[name="my-editormd-html-code"]').val(),
			_method : "post",
		},
		success : function(data) {
			var divStr = "<div class='well text-justify'><a href='notes/"
					+ data.key.result + "'> " + data.key.htmlText + "</a>"
			$("#content-list").append(divStr);
			toastr.success("保存成功。。");
			$("#shadow").hide(500);
			setTimeout(function() {
				window.location.reload();
			}, 100);
		},
		error : function(xhr, textStatus) {
			if (xhr.status == 200) {
				toastr.warning("请先登录。。");
			}
			alert(xhr.status);
			alert(JSON.parse(xhr.responseText).message);
		}
	});
}
