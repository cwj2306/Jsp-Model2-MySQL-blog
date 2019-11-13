<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/include/nav.jsp" %>

	<section class="contact_area">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<form class="row contact_form" action="/blog/user?cmd=update" method="post" onsubmit="return validateCheck()">
						<input type="hidden" name="id" value="${sessionScope.user.id}">
						
						<div class="col-md-12">
							<div class="form-group">
								<input type="text" class="form-control" value="${sessionScope.user.username}" name="username" placeholder="아이디" readonly="readonly">
							</div>
						</div>
						
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
								<input type="email" class="form-control" value="${sessionScope.user.email}" name="email" placeholder="이메일" maxlength="40" readonly="readonly">
							</div>
						</div>
						
						<div class="col-md-12">
							<div class="form-group float-right">
								<a style="cursor:pointer;" class="blog_btn" href="#" onclick="goPopup()">주소 찾기</a>
							</div>
						</div>
						
						<div class="col-md-12">
							<div class="form-group">
								<input type="text" class="form-control" value="${sessionScope.user.address}"name="address" id="roadFullAddr" placeholder="도로명 주소" required="required" readonly="readonly">
							</div>
						</div>
						
						<div class="col-md-12 text-right">
							<button type="submit" value="submit" class="btn submit_btn">Save</button>
						</div>
						
					</form>
				</div>
			</div>
		</div>
	</section>
	
	<br/><br/>
	
	<script>
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

<%@ include file = "/include/footer.jsp" %>