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
function previewImageMul(f, thumbnailId, width, height, text) {
	let image = null;
	if (f.files && f.files[0]) {
		// FileReader 객체 사용
		for (i = 0; i < f.files.length; i++) {
			var reader = new FileReader();

			// 파일을 읽는다
			reader.readAsDataURL(f.files[i]);
			// 파일 읽기가 완료되었을때 실행

			if (!f.files[i].type.startsWith('video')) {
				image = true;
			} else {
				image = false;
			}

			reader.onload = function(e) {
				let ddv = `
						<div class="three wide column" >
							<div class="ui fade reveal">`;

				if (image) {
					ddv += `<div class="visible content">
								<img src="${e.target.result}" width="${width}" height="${height}"
								onmouseover="fnmouseover(this)" 
											onmouseleave="fnmouseleave(this)"
								alt="이미지 또는 동영상만 올려주세요" style="color:white"/>
							</div>
							
							`;

				}
				else {
					ddv += `<div class="visible content">
								<video width="${width}" height="${height}"
								onmouseover="fnmouseover(this)" 
											onmouseleave="fnmouseleave(this)"
										  autoplay muted >
											<source src="${e.target.result}">
										</video>
							</div>`;
				}

				ddv += `
							<div class="hidden content" >
								<img data-src="holder.js/270x180?bg=2a2025&amp;text=삭제하기"
								/>
								
							</div>
							<button id="btndelcon" class="circular red ui icon button" value="${contentarray.length}" onclick="delcon(this)" 
							style="display:none ; position:absolute; top:10px; right:10px; z-index:999 " 
							onmouseover="fnmouseover(this)" 
							>
								<i class="x icon"></i>
							</button>
						</div>
						
						<div class="ui labeled input" style="width:270px" >
	
					 <div class="ui blue label" style="width:25%">
					    작품명
					  </div>
						
						
						<div class="ui input" style="width:270px" >
  							<input type="text" class="cname" placeholder="작품이름" name="text" data-val = "${contentarray.length}" value= "${text}"
							onkeydown="testchange(this)"
							onchange="testchange(this)"/>
						</div>
								
								</div>
				</div>
				`;
				$('#' + thumbnailId).append(ddv);
				Holder.run({});

			}
			// 파일을 읽는다
			//reader.readAsDataURL(f.files[i]);
		}
	}
}
function fnmouseover(evt){
	$(evt).parent().parent().find(".circular.button").css("display","inline-block");
}
function fnmouseleave(evt){
	$(evt).parent().parent().find(".circular.button").css("display","none");
}

function testchange(evt) {
	contentarray[evt.dataset.val - 1] = evt.value;
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
	sessionStorage.setItem("currentpage", $("#getlistdata").attr("value"));

	if (sessionStorage.getItem("currentpage") == 'undefined') {
		sessionStorage.setItem("currentpage", -1);
	}
	$.post(contextPath + viewPath + '/getlist/' + sessionStorage.getItem("currentpage"),
		function(result) {
			$("#gettable").html(result);
			sessionStorage.setItem("currentpage", sessionStorage.getItem("currentpage"));
			console.log(`CURRENT_PAGE = ${sessionStorage.getItem("currentpage")}`);
			Holder.run({});
		}
	).fail(
		function(error) {
			alert(fnGetErrorMessage(error));
		});

}

//contentarray비었는지 확인하는 function
function fnemptycontentarray(){
	for(i=0;i<contentarray.length ; i++){
		if(contentarray[i] == ''){
			alert("작품명이 비어있는 작품이 존재합니다 다시 확인해주세요");
			$("#thumbnailMul").find("input")[i].focus();
			return true;
		} 
	}
}
//전시작품 전용 submit
function fnExhibitSubmit() {
	if(fnemptycontentarray()){
		return false;
	}
	let contextPath = sessionStorage.getItem("contextPath");
	let viewPath = sessionStorage.getItem("viewPath");
	let datas = new FormData($("#Form")[0]);

	for (i = 0; i < filearray.length; i++) {
		datas.append("files", filearray[i]);
	}

	datas.append("content", contentarray);

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
	if(fnemptycontentarray()){
		return false;
	}
	let contextPath = sessionStorage.getItem("contextPath");
	let viewPath = sessionStorage.getItem("viewPath");
	let datas = new FormData($("#Form")[0]);

	for (i = 0; i < filearray.length; i++) {
		datas.append("files", filearray[i]);
	}
	if (deletearray.length > 0) {
		if (!confirm("기존 데이터에서 변경된 사항이 있습니다 정말 삭제 하시겠습니까?")) {
			alert("변경 취소되었습니다");
			return false;
		}
	}
	datas.append("content", contentarray);
	datas.append("delete", deletearray);

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
		dataType: "html",
		success: function(jqXHR) {
			location.href = e.value;
		},
		error: function(jqXHR) {
			alert(JSON.parse(jqXHR.responseText).message);
		},
	});
}

function showRegisterPopModal() {
	$('.ui.modal').modal({
		inverted: true
	}).modal('show').modal('setting', 'closable', false);
}

function currentpageSessionSetDefault() {
	sessionStorage.setItem("currentpage", -1);
}