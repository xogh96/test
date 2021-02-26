/**
 * id의 값을 2개 넣으면 그 값을 비교 해주는 function
 * @param string
 * @param string
 * @return boolean
 */
function th_compare(first, second) {

	firstval = $("#" + first).val();
	secondval = $("#" + second).val();

	if (firstval == null || secondval == null) return false;
	if (firstval == secondval) return true;
	else return false;
}



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
			reader.onload = function(e) {
				
				
				let ddv = '<div >';
				let img = '<img src="'+e.target.result+'" width="'+width+'" height="'+height+'" alt="동영상 미리보기는 아직 구현되지 않았습니다">';
				ddv+=img;				
				let str = '</br><h2 class="ui header" style="display:inline;">작품명 : '+text+'</h2>'
					+'&nbsp;&nbsp;&nbsp;&nbsp;<button class="ui primary basic button" value="'+contentarray.length+'" onclick="delcon(this)">'
					+'<i class="trash icon"></i></button></div>';
				ddv+=str;
				$('#' + thumbnailId).append(ddv);
			}

			// 파일을 읽는다
			//reader.readAsDataURL(f.files[i]);
		}
	}
	
}

/**
 * 	parameter
 * id : 적용될 div의 id
 * object list : type , label , placeholder , name 등등 -> list length만큼 반복해서 만든다
 * 
 * 생성된 form 의 id 는 Form이다
 * 생성된 버튼의 id 는 btnSubmit이며 form 데이터 가져와서 넣어주기
 */
function fnRegisterForm(id, objlist) {
	let dom = $('<div class="ui grid" style="padding-top: 30px;">');
	let str =
		'<div class="three column row">' +
		'<div class="right floated column" style="background-color:antiquewhite; padding: 30px;">' +
		'<form class="ui form" id="Form">';
	for (i = 0; i < objlist.length; i++) {
		str +=
			'<div class="field">' +
			'<label>' + objlist[i].label + '</label>' +
			'<input ';

		for (prop in objlist[i]) {
			str += prop + "=" + "'" + objlist[i][prop] + "' ";
		}

		str += '>' +
			'</div>';
	}
	str +=
		'</form>' +
		'<div id="thumbnailMul" style="padding-top:5px;"></div>' +
		'<div id="thumbnail" style="padding-top:5px;"></div>' +
		'<div class="ui right aligned container" style="padding-top: 30px;">' +
		'<button class="ui button" id="btnSubmit" onclick="fnSubmit()">저장하기</button>' +
		'<button class="ui button" id="btnInvisible" onclick="regpage(' + "'" + id + "'" + ')">취소하기</button>' +
		'</div>' +
		'</div>' +
		'</div>';
	$(str).appendTo(dom);
	$("#" + id).html(dom);
	$("#btnReg").hide();

}
function regpage(id) {
	$("#btnReg").show();
	console.log(id);
	$("#" + id).html("");
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
				location.replace(contextPath);
			}
		).fail(function() {
			alert("삭제실패 , 다시 시도해주세요");
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

function showRegisterPop() {
	$("#registerPop").show();
}

function hideRegisterPop() {
	$("#registerPop").hide();
}
//list를 post방식으로 get해오는 함수
function fngetList(e) {
	
	let contextPath = sessionStorage.getItem("contextPath");
	let viewPath = sessionStorage.getItem("viewPath");
	if(e=='onload'){
		$.post(contextPath + viewPath + '/getlist/' + -1,
		function(result) {
			fncreateTable(result);
		}
	).fail(
		function(error) {
			alert(fnGetErrorMessage(error));
		});
	}else{
	let data = e.childNodes[1].value
	if (data == 'all' || e=='all') {
		data = -1;
	}
	$.post(contextPath + viewPath + '/getlist/' + data,
		function(result) {
			fncreateTable(result);
		}
	).fail(
		function(error) {
			alert(fnGetErrorMessage(error));
		});
		}
}



					



//table을 만들어 주는 함수 html을 받아온다
function fngetListHTML(e) {
	let contextPath = sessionStorage.getItem("contextPath");
	let viewPath = sessionStorage.getItem("viewPath");
	if(e=='onload'){
		$.post(contextPath + viewPath + '/getlist/' + -1,
		function(result) {
			$("#gettable").html(result);
		}
	).fail(
		function(error) {
			alert(fnGetErrorMessage(error));
		});
	}else{
	let data = e.childNodes[1].value
	if (data == 'all') {
		data = -1;
	}
	$.post(contextPath + viewPath + '/getlist/' + data,
		function(result) {
			$("#gettable").html(result);
		}
	).fail(
		function(error) {
			alert(fnGetErrorMessage(error));
		});
		}
		
	
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
	
