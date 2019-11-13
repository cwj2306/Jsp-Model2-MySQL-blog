<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/nav.jsp"%>

<section class="contact_area">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<form class="row contact_form" action="/blog/user?cmd=join" method="post" onsubmit="return validateCheck()">

					<div class="col-md-10">
						<div class="form-group">
							<input type="text" class="form-control" id="username" name="username" placeholder="아이디" required="required">
						</div>
					</div>

					<div class="col-md-2">
						<div class="form-group float-right">
							<a style="cursor: pointer;" class="blog_btn" href="#" onclick="usernameCheck()">중복확인</a>
						</div>
					</div>
					<span id="username_input" style="font-size:12px; "></span>

					<div class="col-md-12">
						<div class="form-group">
							<input type="password" class="form-control" id="password" name="password" placeholder="비밀번호" required="required" maxlength="20">
						</div>
					</div>

					<div class="col-md-12">
						<div class="form-group">
							<input type="password" class="form-control" id="passwordCheck" name="passwordCheck" placeholder="비밀번호 확인" required="required" maxlength="20">
						</div>
					</div>

					<div class="col-md-12">
						<div class="form-group">
							<input type="email" class="form-control" name="email" placeholder="이메일" required="required" maxlength="40">
						</div>
					</div>

					<div class="col-md-10">
						<div class="form-group">
							<input type="text" class="form-control" name="address" id="roadFullAddr" placeholder="도로명 주소" required="required" readonly="readonly">
						</div>
					</div>

					<div class="col-md-2">
						<div class="form-group float-right">
							<a style="cursor: pointer;" class="blog_btn" href="#" onclick="goPopup()">주소찾기</a>
						</div>
					</div>

					<div class="col-md-12 text-right">
						<button type="submit" value="submit" class="btn submit_btn">Join</button>
					</div>

				</form>
			</div>
		</div>
	</div>
</section>

<br />
<br />

	<script>
		
		var usernameOverlapCheck = false;
		//아이디 중복 확인
		function usernameCheck(){
			var username = document.querySelector("#username").value;
			
			fetch("/blog/api/user?username="+username).then(function(r){
				return r.text();
			}).then(function(r){
				var status = r; //ok 중복되지 않음.
				var et = document.querySelector("#username_input");
	
				if(status === "ok"){
					et.style.color="green";
					et.innerHTML = "사용 가능한 아이디 입니다.";
					usernameOverlapCheck = true;
				}else{
					et.style.color="red";
					et.innerHTML = "사용할 수 없는 아이디 입니다.";
					usernameOverlapCheck = false;
				}
			});
			
		}

		function validateCheck(){
			var password = document.querySelector('#password').value;
			var passwordCheck = document.querySelector('#passwordCheck').value;
			var address = document.querySelector('#roadFullAddr').value;
			
			if(address == ''){
				alert('주소를 입력하세요.');
				return false;
			}
			
			if (password === passwordCheck) {
				return true;
			}else{
				alert('비밀번호를 정확히 입력해주세요');
				return false;
			}
		}
		
		function goPopup(){
			// 주소검색을 수행할 팝업 페이지를 호출합니다.
			// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
			var pop = window.open("/blog/juso/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
			
			// 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
		    //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
		}

		//주소입력 버튼 누르면 콜백됨
		function jusoCallBack(roadFullAddr){
			// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
			var juso = document.querySelector('#roadFullAddr');
			juso.value = roadFullAddr;
		}
	</script>

<%@ include file="/include/footer.jsp"%>