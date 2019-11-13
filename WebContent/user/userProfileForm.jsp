<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/nav.jsp"%>

<section class="contact_area">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">

				<div class="col-md-12 text-center">
					<img id="img__wrap" width="200px" height="200px" style="border-radius: 50%;"/>
				</div><br/><br/>

				<!-- enctype는 mime타입에서 찾아봐서 써주자 -->
				<form action="/blog/user?cmd=uploadProfileImg" method="POST" enctype="multipart/form-data">
					<div class="col-md-12 text-center">
						<input id="img__input" type="file" name="userProfile" /><br/><br/>
						<input type="submit" class="btn submit_btn" value="프로필 사진 업로드" /><br/><br/>
					</div>
				</form>

				<script src="/blog/js/jquery-3.2.1.min.js"></script>
				<script>
					$("#img__input").on("change", handleImgFile);

					function handleImgFile(e) {

						var f = e.target.files[0];

						if (f.type.match("image.*")) {
							var reader = new FileReader();

							reader.onload = function(e) {
								console.log(e.target.result);
								$("#img__wrap").attr("src", e.target.result);
							}

							reader.readAsDataURL(f);
						} else {
							alert("이미지 파일이 아닙니다.");
							$("#img__wrap").attr("src", "");
						}

					}
				</script>

				</form>

			</div>
		</div>
	</div>
</section>

<%@ include file="/include/footer.jsp"%>