$(function() {
	$(".btn-login").on("click", () => {
		$.ajax({
			url : "/login",
			type : "post",
			dataType : "json",
			cache : false,
			data:{
				account: $("input[name='account']").val(),
				password: $("input[name='password']").val(),
			},
			success: (data) => {
				if (data.error == "0") {
					toastr.success(data.message);
					setTimeout(() => {
						window.location.href="/notes"; 
					}, 500);
				} else {
					toastr.warning(data.message);
				}
			}
		});
	})
})