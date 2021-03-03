/**
 * ajax 하는 function
 * 
 */
function fnAjaxJsonSend(url, method, data, callOk, callErr, callComplete) {
	//let header = $("meta[name='_csrf_header']").attr("content");
	//let token = $("meta[name='_csrf']").attr("content");

	$.ajax({
		url: url,
		type: method,
		data: JSON.stringify(data),
		dataType: 'json',
		contentType: "application/json; charset=UTF-8",
		timeout: 300000,
		//beforeSend: function (xhr) {
		//  xhr.setRequestHeader(header, token);
		//},
		success: function(data, textStatus, jqXHR) {
			if (typeof (callOk) === 'function') {
				callOk(data);
			}
		},
		error: function(error) {
			if (typeof (callErr) === 'function') {
				callErr(error);
			}
		},
		complete: function() {
			if (typeof (callComplete) === 'function') {
				callComplete();
			}
		}
	})
}

function fnAjaxJsonSendFormFile(url, method, data, callOk, callErr, callComplete) {
	$.ajax({
		url: url,
		type: method,
		data: data,
		processData: false,  // 데이터 객체를 문자열로 바꿀지에 대한 값이다. true면 일반문자...
		contentType: false,  // 해당 타입을 true로 하면 일반 text로 구분되어 진다.
		timeout: 300000,
		success: function(data, textStatus, jqXHR) {
			if (typeof (callOk) === 'function') {
				callOk(data);
			}
		},
		error: function(error) {
			if (typeof (callErr) === 'function') {
				callErr(error);
			}
		},
		complete: function() {
			if (typeof (callComplete) === 'function') {
				callComplete();
			}
		}
	})
}

function fnGetErrorMessage(error) {
	let msg = '';
	try {
		msg = error.responseJSON.message;
	} catch (e) {
		// skip
	}
	return msg;
}

//form의 내용을 모두 채웠는지 확인하는 function
function fnFormEmpty(form) {
	let tf = true;
	$('#' + form).find('input[type!="hidden"]').not('input[name="file"]').each(function() {
		if (!$(this).val()) {
			tf = false;
		}
	});
	if (tf == false) {
		alert("모든정보를 입력해주세요");
		return false;
	}
	return true;
}


//form을 serializeArray해서 넣으면 json 으로 반환
function objectifyForm(formArray) {
	//serialize data function
	var returnArray = {};
	for (var i = 0; i < formArray.length; i++) {
		returnArray[formArray[i]['name']] = formArray[i]['value'];
	}
	return returnArray;
}
//파일하나 읽으면 미리보기 아이디가 thumbnail인 img에 src를 넣어주는 함수
//변수로 아이디랑 width , height 받아오기 
function previewImage(f, thumbnailId, width, height) {
	console.log('f length = ' + f.files.length)
	if (f.files && f.files[0]) {
		$('#' + thumbnailId).empty();

		// FileReader 객체 사용
		for (i = 0; i < f.files.length; i++) {
			var reader = new FileReader();
			// 파일 읽기가 완료되었을때 실행
			reader.onload = function(e) {

				let img = $("<img>");
				img.attr('src', e.target.result);
				img.attr('width', width);
				img.attr('height', height);

				$('#' + thumbnailId).append(img);
			}

			// 파일을 읽는다
			reader.readAsDataURL(f.files[i]);
		}
	}
}
//썸네일 여러개 넣기
function previewImageMul(f, thumbnailId, width, height , text) {
	
	if (f.files && f.files[0]) {
		// FileReader 객체 사용
		for (i = 0; i < f.files.length; i++) {
			var reader = new FileReader();
			
			// 파일을 읽는다
			reader.readAsDataURL(f.files[i]);
			// 파일 읽기가 완료되었을때 실행
			if(!f.files[i].type.startsWith('video')){
			reader.onload = function(e) {
					let ddv = `
				<div class="three wide column" >
						<div class="ui fade reveal">
							<div class="visible content">
								<img src="${e.target.result}" width="${width}" height="${height}"
								onmouseover="$('.circular.button').show();" 
								onmouseleave="$('.circular.button').hide();"/>
							</div>
							<div class="hidden content" >
								<img data-src="holder.js/200x180?bg=2a2025&amp;size=15&amp;text=${text}"/>
								
							</div>
							<button id="btndelcon" class="circular ui icon button" value="${contentarray.length}" onclick="delcon(this)" 
							style="display:none ; position:absolute; top:10px; left:155px; z-index:999 " 
							onmouseover="$('.circular.button').show();"
							>
								<i class="x icon"></i>
							</button>
						</div>
						
				</div>
				`;
				$('#' + thumbnailId).append(ddv);
				Holder.run({});
				}
			}else{
				reader.onload = function(e) {
					
					
										
					let ddv = `
				<div class="three wide column" >
						<div class="ui fade reveal">
							<div class="visible content">
								<video width="${width}" height="${height}"
								onmouseover="$('.circular.button').show();" 
								onmouseleave="$('.circular.button').hide();"
										 controls style="cursor: pointer;">
											<source src="${e.target.result}"
													
											 >
										</video>
							</div>
							<div class="hidden content" >
								<img data-src="holder.js/200x180?bg=2a2025&amp;size=15&amp;text=${text}"/>
								
							</div>
							<button id="btndelcon" class="circular ui icon button" value="${contentarray.length}" onclick="delcon(this)" 
							style="display:none ; position:absolute; top:10px; left:155px; z-index:999 " 
							onmouseover="$('.circular.button').show();"
							>
								<i class="x icon"></i>
							</button>
						</div>
						
				</div>
				`;
				$('#' + thumbnailId).append(ddv);
				Holder.run({});
				}
			}
			// 파일을 읽는다
			//reader.readAsDataURL(f.files[i]);
		}
	}
}
function fnSubmit() {
	let contextPath = sessionStorage.getItem("contextPath");
	let viewPath = sessionStorage.getItem("viewPath");
	let datas = new FormData($("#Form")[0]);
	//내용이 비었는지 확인하기
	if (fnFormEmpty('Form') == true) {
		fnAjaxJsonSendFormFile(contextPath + viewPath + '/add', 'post', datas,
			function(result) {
				console.log(result);
				
				location.replace(contextPath);
			},
			function(error) {
				alert(fnGetErrorMessage(error));
			}
		);
	}
}
function fnDelete(id) {
	let contextPath = sessionStorage.getItem("contextPath");
	let viewPath = sessionStorage.getItem("viewPath");
	if (confirm("정말 삭제 하시겠습니까?")) {
		$.post(contextPath + viewPath + "/remove/" + id.value,
			function(data) {
				console.log(data);
				location.replace(contextPath);
			}
		).fail(function(error) {
			alert(fnGetErrorMessage(error));
		});
	}
}

function fnModify(evt) {
	let contextPath = sessionStorage.getItem("contextPath");
	let viewPath = sessionStorage.getItem("viewPath");
	let datas = new FormData($("#Form")[0]);

	if (fnFormEmpty('Form') == true) {
		fnAjaxJsonSendFormFile(contextPath + viewPath +
			'/modify/' + $("#" + evt).val(), 'post', datas,
			function(result) {
				console.log(result);
				
				location.replace(contextPath);
			},
			function(error) {
				alert(fnGetErrorMessage(error));
			}
		);
	}
}
//table을 만들어 주는 함수 html을 받아온다
function fngetListHTML() {
	let contextPath = sessionStorage.getItem("contextPath");
	let viewPath = sessionStorage.getItem("viewPath");
	sessionStorage.setItem("currentpage",$("#getlistdata").attr("value"));
	
	if(sessionStorage.getItem("currentpage") == 'undefined'){
		sessionStorage.setItem("currentpage",-1);
	}
		$.post(contextPath + viewPath + '/getlist/' + sessionStorage.getItem("currentpage"),
		function(result) {
			$("#gettable").html(result);
			sessionStorage.setItem("currentpage",sessionStorage.getItem("currentpage"));
			console.log(`CURRENT_PAGE = ${sessionStorage.getItem("currentpage")}`);
			Holder.run({});
		}
	).fail(
		function(error) {
			alert(fnGetErrorMessage(error));
		});
		
	}

//전시작품 전용 submit
function fnExhibitSubmit() {
	let contextPath = sessionStorage.getItem("contextPath");
	let viewPath = sessionStorage.getItem("viewPath");
	let datas = new FormData($("#Form")[0]);
	
	for(i=0;i<filearray.length;i++){
		datas.append("files",filearray[i]);	
	}
	
	datas.append("content",contentarray);
	
	//내용이 비었는지 확인하기
	
		fnAjaxJsonSendFormFile(contextPath + viewPath + '/add', 'post', datas,
			function(result) {
				console.log(result);
				location.replace(contextPath);
			},
			function(error) {
				alert(fnGetErrorMessage(error));
			}
		);
	
}

function fnExhibitModify() {
	let contextPath = sessionStorage.getItem("contextPath");
	let viewPath = sessionStorage.getItem("viewPath");
	let datas = new FormData($("#Form")[0]);
	
	for(i=0;i<filearray.length;i++){
		datas.append("files",filearray[i]);	
	}
	if(deletearray.length>0)
	{
		if (!confirm("기존 데이터에서 변경된 사항이 있습니다 정말 삭제 하시겠습니까?")) {
			alert("변경 취소되었습니다");
			return false;
		}
	}
	
	datas.append("content",contentarray);
	datas.append("delete",deletearray);
	
	//내용이 비었는지 확인하기
	
		fnAjaxJsonSendFormFile(contextPath + viewPath + '/modify', 'post', datas,
			function(result) {
				console.log(result);
				location.replace(contextPath);
			},
			function(error) {
				alert(fnGetErrorMessage(error));
			}
		);
	
}

function fndownloadfile(e) {
	$.ajax({
		url: e.value,
		type: 'get', 
		dataType : "html",
		success:function(jqXHR){
			location.href = e.value;
		},
		error:function(jqXHR) {
		alert(JSON.parse(jqXHR.responseText).message);
	},
	});
}
	
function showRegisterPopModal() {
	$('.ui.modal').modal({
		inverted: true
	}).modal('show').modal('setting', 'closable', false);
}

function currentpageSessionSetDefault(){
	sessionStorage.setItem("currentpage",-1);
}