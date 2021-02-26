$(function(){

	var hostIndex = location.href.indexOf( location.host ) + location.host.length;
    var CONTEXT_PATH = location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
	console.log("CONTEXT_PATH = " + CONTEXT_PATH);
	
	var viewIndex = hostIndex+CONTEXT_PATH.length;
	if(location.href.indexOf('/', viewIndex + 1) == -1){
		var VIEW_PATH = location.href.substring(viewIndex);
	}
	else{
		var VIEW_PATH = location.href.substring(viewIndex , location.href.indexOf('/', viewIndex + 1) );
	}
	console.log("VIEW_PATH = "+ VIEW_PATH);
	sessionStorage.setItem("viewPath" , VIEW_PATH);
	sessionStorage.setItem("contextPath",CONTEXT_PATH);
	//sessionStorage.setItem("currentView");
	
	
	var v = sessionStorage.getItem("viewPath");
	switch(v){
		case '/dvc' : $("#menu_dvc").addClass("active"); break;
		case '/mus' : $("#menu_mus").addClass("active"); break;
		case '/cat' : $("#menu_cat").addClass("active"); break;
		case '/ex' : $("#menu_ex").addClass("active"); break;
	}


	$('input[type="text"]').keydown(function(event) {
		if (event.keyCode === 13) {
		  event.preventDefault();
		};
	  });
	 



	  
}).ajaxStart(function(){
	$("#loadingpage").show();
})
.ajaxStop(function(){
	$("#loadingpage").hide();
	$('.ui.accordion').accordion();
	
});