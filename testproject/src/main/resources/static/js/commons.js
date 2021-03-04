$(function() {

	var hostIndex = location.href.indexOf(location.host) + location.host.length;
	var CONTEXT_PATH = location.href.substring(hostIndex, location.href.indexOf('/', hostIndex + 1));
	console.log("CONTEXT_PATH = " + CONTEXT_PATH);

	var viewIndex = hostIndex + CONTEXT_PATH.length;
	if (location.href.indexOf('/', viewIndex + 1) == -1) {
		var VIEW_PATH = location.href.substring(viewIndex);
	}
	else {
		var VIEW_PATH = location.href.substring(viewIndex, location.href.indexOf('/', viewIndex + 1));
	}
	console.log("VIEW_PATH = " + VIEW_PATH);

	sessionStorage.setItem("viewPath", VIEW_PATH);
	sessionStorage.setItem("contextPath", CONTEXT_PATH);
	//sessionStorage.setItem("currentView");


	var v = sessionStorage.getItem("viewPath");
	switch (v) {
		case '/dvc': $("#menu_dvc").addClass("active"); break;
		case '/mus': $("#menu_mus").addClass("active"); break;
		case '/cat': $("#menu_cat").addClass("active"); break;
		case '/ex': $("#menu_ex").addClass("active"); break;
	}


	$('input[type="text"]').keydown(function(event) {
		if (event.keyCode === 13) {
			console.log("엔터불가능");
			event.preventDefault();
		};
	});


	if (sessionStorage.getItem("viewPath") != '/dvc') { // 디바이스가 아닐때만 테이블 불러오기
		if (sessionStorage.getItem("currentpage") != -1) {
			$('.ui.dropdown').dropdown('set selected', sessionStorage.getItem("currentpage"));
		} if (sessionStorage.getItem("currentpage") == null || sessionStorage.getItem("currentpage") == -1) {
			$('.ui.dropdown').dropdown();
			fngetListHTML();
		}
	}

}).ajaxStart(function() {
	$("#loadingpage").show();
})
	.ajaxStop(function() {
		$("#loadingpage").hide();
		$('.ui.accordion').accordion();

	});