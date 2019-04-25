/**
 * home.html 2019年3月8日 23点22分
 */

/**
 * 删除笔记操作:由于生成笔记列表时该函数是拼接而成，所以必须定义在js上下文中(window作用域) 使用jquery的事件绑定亦可
 * 
 * @param _this
 *            当前点击的对象
 */
/*
 * let deleteNote = (_this) => { let url = $(_this).parent().prop("href");
 * $.ajax({ url: url, type: "put", dataType : "json", cache : false, data : { id :
 * $("#title").val(), _method : "put", }, success: () => { }, error: () => {
 * toastr.error("通信异常"); } }); // return false; 此处return false
 * 链接还是会跳转，需要在外部定义onclick事件处加上return false; etc: onclick="func();return false;" }
 */



$(() => {
	// 富文本编辑器变量(新建)
	let textEditorCreate;
	// 富文本编辑器变量(修改)
	let textEditorUpdate;
	
	/**
	 * 分页函数
	 * 
	 * @param pageCount
	 *            总页数
	 */
	let pagination = (pageCount) => {
		let cur = parseInt($("#page-index").val());
		let pre = cur - 1;
		let next = cur + 1;
		// 上一页
		let preHref = "/notes?page=" + pre;
		// 下一页
		let nextHref = "/notes?page=" + next;
		
		if (cur <= 1) {
			// 已经是第一页,上一页按钮的href指向#
			preHref = "#";
		}
		
		if (cur >= pageCount) {
			// 已经是最后一页，下一页按钮的href指向#
			nextHref = "#";
		}
		// 上一页按钮
		let li = '<li><a href="' + preHref + '">&laquo;上一页</a></li>';
		for (let i = 1; i <= pageCount; i++) {
			if (cur == i) {
				// 当前页按钮添加active类
				li += "<li class='active'><a href='/notes?page=" + i +"'>" + i + "</a></li>";
			} else {
				li += "<li><a href='/notes?page=" + i +"'>" + i + "</a></li>";
			}
			
		}
		// 下一页按钮
		li += '<li><a href="' + nextHref + '">下一页&raquo;</a></li>';
		$(".pagination").append(li);
	};
	
	// 全局配置属性，如果第一页数据大于等于10或者有第二页数据则显示分页工具
	if ($(".note-list").length >= 10 || $("#page-index").val() > 1) {
		$(".footer").show();
		// 根据页数拼接页数按钮
		let count = parseInt($("#page-count").text());
		pagination(count);
	}
	
	// -------------------------------------------------------------------------
	
	// 导航条点击事件:添加背景色
	$("li").on("click", () => {
		$(".active").removeClass("active");
		$(this).addClass("active");
	});
	
	// 钩子函数: 当模态框对用户可见时触发（将等待 CSS 过渡效果完成）:加载编辑器,第一次加载时会设置模态框高度
	$("#modal-new-note").on("shown.bs.modal", () => {
		if (!textEditorCreate) {
			textEditorCreate = editormd("my-editormd", {
				placeholder : "记录精彩瞬间~",
				emoji : true,
				// width : "90%", // 编辑器控件的宽度
				height : "100%", // height : "100%", 如果指定px 例如 height :
									// 640，那么不需要加引号也不用加px(默认px)，指定百分比则需要加引号
				editorTheme : "3024-day", // 设置主题颜色 具体见源码
				theme : "default", // 设置工具栏主题颜色,default | dark
				previewTheme : "default", // 设置预览颜色 default | dark
				
				taskList : true,
				tex : true,
				flowChart : true,
				sequenceDiagram : true,
				codeFold : true,
				syncScrolling : "single",
				path : "/editor.md/lib/",  // 控件的依赖包,因为springmvc的拦截器设置,"/"代表资源根目录，即static目录下
				saveHTMLToTextarea : true, // / 保存 HTML 到 Textarea
											// 会自动生成一个隐藏的Textarea保存HTML代码 控件名为
											// $-html-code
											// $代表创建编辑器的div的id值,这里是my-editormd

				toolbarIcons : function() {
					// Or return editormd.toolbarModes[name]; //
					// full, simple, mini
					// Using "||" set icons align right.
					// 自定义按钮, "|"分隔符为一组
					return [ /* "submit", "|", */ "undo", "redo", "|",
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
				 * imageUpload : true, imageFormats : [ "jpg", "jpeg", "gif",
				 * "png", "bmp", "webp" ], imageUploadURL : "${proPath
				 * }/base/blog/upFile",
				 */
			});
		}
		// 设置编辑器高度
		$("#my-editormd").height($(window).height() * 0.7);
	})
	
	// 钩子函数: 在调用 show 方法后触发
	$("#modal-new-note").on("show.bs.modal", () => {
		
	})
	
	// 修改笔记的模态框显示后钩子事件
	$("#modal-update-note").on("shown.bs.modal", () => {
		
	})
	
	// 新建笔记: clear按钮事件:清空输入
	$("#btn-clear").on("click", () => {
		$("#txt-title").val("");
	})
	
	// 修改笔记: clear按钮事件:清空输入
	$("#btn-clear-update").on("click", () => {
		$("#txt-title-update").val("");
	})
	
	// 新建笔记：保存按钮事件:保存文档
	$("#btn-save").on("click", () => {
		
		if (!$.trim($("#txt-title").val())) {
			toastr.warning("请输入标题");
			return;
		}
		if (!$.trim($("textarea[name='my-editormd-html-code']").val())) {
			toastr.warning("内容不能为空");
			return;
		}
		
		$.ajax({
			type:"post",
			url: "/note",
			dataType: "json",
			cache: false,
			data: {
				// ajax参数中如果不加引号直接使用 如txt-title的形式，则js解析会报错，带连接符的参数名需要加引号
				"txt-title": $("#txt-title").val(),
				"my-editormd-markdown-doc": $("#my-editormd-markdown-doc").val(),
				"my-editormd-html-code": $("textarea[name='my-editormd-html-code']").val(),
				_method : "post"
			},
			success: (data) => {
				if (data.error == "0") {
					toastr.success(data.message);
					$("#modal-new-note").hide();
					setTimeout(() => {
						window.location.href="/notes"; 
					}, 700);
				} else {
					toastr.warning(data.message);
				}
			},
			error: () => { 
				toastr.error("通信异常"); 
			}
		});
	})
	
	// 删除笔记操作
	// 注意 如果需要用到当前操作对象，
	// 匿名函数的$(this)指window,所以有操作对象的时候,需要使用function()而不是es6语法的箭头函数
	$(".delete").on("click", function(){
		// http://www.jq22.com/demo/jqueryConfirm20160413/ 在线演示各种提示框
		$.confirm({
	        title: '确认删除?',
	        content: '确认删除该笔记?',
	        type: 'red',
	        icon: 'glyphicon glyphicon-question-sign',
	        buttons: {
	            ok: {
	                text: '确认',
	                btnClass: 'btn-danger',
	                action: () => {
	                	try {
	            			let url = $(this).parent().prop("href");
	            			// 找到最后出现的“/”,则后续的就是笔记的id
	            			let index = url.lastIndexOf("/");
	            			let id = url.substr(index + 1);
	            			// 正确的id应该是18位以上的纯数字，并且以2开头
	            			if (!isAlphaNum(id)) {
	            				// toastr.warn("找不到该笔记"); // 这行代码会报异常，会进入catch
	            				toastr.warning("找不到该笔记");
	            			} else {
	            				// 进入后台处理，完成处理后页面不刷新，清除当前点击删除的一条数据
	            				// 必要时刻作隐藏分页以及切换到上一页处理
	            				$.ajax({ 
	            					url: url, 
	            					type: "delete", 
	            					dataType : "json", 
	            					cache :false, 
	            					data : { 
	            						id : id, 
	            						_method : "delete"
	            					},
	            					success: (data) => {
	            						if (data.error == "0") {
	            							toastr.info(data.message);
	            							setTimeout(() => {
	            								window.location.href="/notes"; 
	            							}, 700);
	            						} else {
	            							toastr.warning(data.message);
	            						}
	            					},
	            					error: () => { 
	            						toastr.error("通信异常"); 
	            					}
	            				});
	            		   }
	            		} catch (e) {
	            			toastr.error("javascript异常：" + e);
	            		}
	                }
	            },
	            cancel: {
	                text: '取消',
	                btnClass: 'btn-default'
	            }
	        }
	    });
		return false;
	})
	
	// 修改笔记：保存按钮事件:保存文档
	$("#btn-save-update").on("click", () => {
		
		if (!$.trim($("#txt-title-update").val())) {
			toastr.warning("请输入标题");
			return;
		}
		if (!$.trim($("textarea[name='my-editormd-update-html-code']").val())) {
			toastr.warning("内容不能为空");
			return;
		}
		
		try {
			let url = $(this).parent().prop("href");
			// 找到最后出现的“/”,则后续的就是笔记的id
			let index = url.lastIndexOf("/");
			let id = url.substr(index + 1);
			// 正确的id应该是18位以上的纯数字，并且以2开头
			if (!isAlphaNum(id)) {
				// toastr.warn("找不到该笔记"); // 这行代码会报异常，会进入catch
				toastr.warning("找不到该笔记");
			} else {
				$.ajax({ 
					url: url, 
					type: "put", 
					dataType : "json", 
					cache :false, 
					data : { 
						// ajax参数中如果不加引号直接使用 如txt-title的形式，则js解析会报错，带连接符的参数名需要加引号
						"note-id" : id, 
						"txt-title-update": $("#txt-title-update").val(),
						"my-editormd-update-markdown-doc": $("#my-editormd-update-markdown-doc").val(),
						"my-editormd-update-html-code": $("textarea[name='my-editormd-update-html-code']").val(),
						_method : "put"
					},
					success: (data) => {
						if (data.error == "0") {
							toastr.info(data.message);
							$("#modal-update-note").hide();
							setTimeout(() => {
								window.location.href="/notes"; 
							}, 700);
						} else {
							toastr.warning(data.message);
						}
					},
					error: () => { 
						toastr.error("通信异常"); 
					}
				});
		   }
		} catch (e) {
			toastr.error("javascript异常：" + e);
		}
	})
	
	// 更新笔记操作:打开更新模态框并且加载数据
	// 注意 如果需要用到当前操作对象，
	// 匿名函数的$(this)指window,所以有操作对象的时候,需要使用function()而不是es6语法的箭头函数
	$(".update").on("click", function(){
		$.confirm({
	        title: '确认修改?',
	        content: '确认修改该笔记?',
	        type: 'blue',
	        icon: 'glyphicon glyphicon-question-sign',
	        buttons: {
	            ok: {
	                text: '确认',
	                btnClass: 'btn-info',
	                action: () => {
	                	$("#modal-update-note").modal("show");
	                }
	            },
	            cancel: {
	                text: '取消',
	                btnClass: 'btn-default'
	            }
	        }
	    });
		return false;
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
