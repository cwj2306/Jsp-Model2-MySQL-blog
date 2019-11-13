<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/include/nav.jsp"%>

<!--================Blog Area =================-->
<section class="blog_area single-post-area">
	<div class="container">
		<div class="row">
			<div class="col-lg-2"></div>
			<div class="col-lg-8">
				<div class="main_blog_details">
					<a href="#"><h4>${board.title}</h4></a>
					<div class="user_details">
						<div class="float-left">

							<c:if test="${board.userId eq sessionScope.user.id}">
								<a href="/blog/board?cmd=updateForm&id=${board.id}">Update</a>
								<a href="/blog/board?cmd=delete&id=${board.id}">Delete</a>
							</c:if>

						</div>
						<div class="float-right">
							<div class="media">
								<div class="media-body">
									<h5>${board.user.username}</h5>
									<p>${board.createDate}</p>
								</div>
								<div class="d-flex">
									<img src = "${board.user.userProfile}" width="40px" height="40px" style="border-radius: 50%;"/>
								</div>
							</div>
						</div>
					</div>

					<!-- 본문 -->
					<p>${board.content}</p>
					<!-- 본문 끝-->
					<hr />
				</div>

				<!-- 댓글 시작 -->
				<div class="comments-area" id="comments-area">

					<c:forEach var="comment" items="${comments}">

						<!-- 댓글 아이템 시작 -->
						<div class="comment-list" id="comment-id-${comment.id}">
							<div class="single-comment justify-content-between d-flex">
							
								<div class="user justify-content-between d-flex">
									<div class="thumb">
										<img src="${comment.user.userProfile}" width="40px" height="40px" style="border-radius: 50%;"/>
									</div>
									<div class="desc">
										<h5>
											<a href="#">${comment.user.username}</a>
										</h5>
										<p class="date">${comment.createDate}</p>
										<p class="comment" style="word-break:break-all;">${comment.content}</p>
									</div>
								</div>
								
								<div class="reply-btn">
									<c:if test='${comment.userId==sessionScope.user.id}'>
										<button type="button" class="btn-reply text-uppercase" onclick="commentDelete(${comment.id})" style="display:inline-block; float:left; margin-right:10px;">삭제</button>
									</c:if>
									
									<button type="button" class="btn-reply text-uppercase" onclick="replyListShow(this,${comment.id})" style="display:inline-block; float:left; margin-right:10px;">보기</button>
									
									<c:if test="${not empty sessionScope.user}">
										<button type="button" class="btn-reply text-uppercase" onclick="replyFormShow(this,${comment.id})" >쓰기</button>
									</c:if>
								</div>
								
							</div>
						</div>
						<!-- 댓글 아이템 끝 -->

					</c:forEach>

					<!-- 대 댓글 아이템 시작 -->
					<!-- <div class="comment-list left-padding">
						<div class="single-comment justify-content-between d-flex">
							<div class="user justify-content-between d-flex">
								<div class="thumb">
									<img src="img/blog/c2.jpg" alt="">
								</div>
								<div class="desc">
									<h5>
										<a href="#">Elsie Cunningham</a>
									</h5>
									<p class="date">December 4, 2017 at 3:12 pm</p>
									<p class="comment">Never say goodbye till the end comes!</p>
								</div>
							</div>
						</div>
					</div> -->
					<!-- 대 댓글 아이템 끝 -->
					
				</div>
				<!-- 댓글 끝 -->

				<!-- 댓글 쓰기 시작 -->
				<div class="comment-form" style="margin-top: 0px;">
					<h4 style="margin-bottom: 20px">Leave a Comment</h4>
					<form id="comment-submit">
						<input type="hidden" name="userId" value="${sessionScope.user.id}" />
						<input type="hidden" name="boardId" value="${board.id}" />
						<div class="form-group">
							<textarea id="content" style="height: 60px" class="form-control mb-10" rows="2" name="content" placeholder="content" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Messege'" required="required"></textarea>
						</div>
						<!-- 버튼 타입 submit을해주면 부조건 새로고침되서 onClick를 쓴다. -->
						<!-- 버튼 타입을 button을해주면 자바스크립트에서 바로 submit 되는걸 막아줌 -->
						<c:if test="${not empty sessionScope.user}">
							<button type="button" onClick="commentWrite()" class="primary-btn submit_btn">Post Comment</button>						
						</c:if>
					</form>
				</div>
				<!-- 댓글 쓰기 끝 -->

			</div>
			<div class="col-lg-2"></div>
		</div>
	</div>
</section>
<!--================Blog Area =================-->

<!-- ==============Comment Script============== -->
<script>
	function commentWriteForm(id, username, content, createDate, userProfile){
	    var comment_list = "<div class='comment-list' id='comment-id-"+id+"'> ";
	    comment_list += "<div class='single-comment justify-content-between d-flex'> ";
	    comment_list += "<div class='user justify-content-between d-flex'> ";
	    comment_list += "<div class='thumb'> <img src='"+userProfile+"' width='40px' height='40px' style='border-radius: 50%;'> </div> ";
	    comment_list += "<div class='desc'><h5><a href='#'>"+username+"</a></h5> ";
	    comment_list += "<p class='date'>"+createDate+"</p><p class='comment' style='word-break:break-all;'>"+content+"</p></div></div> ";
	    comment_list += "<div class='reply-btn'>";
	    comment_list += "<button type='button' onClick='commentDelete("+id+")' class='btn-reply text-uppercase' style='display:inline-block; float:left; margin-right:10px;'>삭제</button>";
	    comment_list += "<button type='button' onClick='replyListShow(this,"+id+")' class='btn-reply text-uppercase'  style='display:inline-block; float:left; margin-right:10px;'>보기</button>";
	    comment_list += "<button type='button' onClick='replyFormShow(this,"+id+")' class='btn-reply text-uppercase'>쓰기</button></div></div></div>";
	    
	    return comment_list;
	}

	//comment 쓰기
	function commentWrite(){
		
		var comment_submit_string = $("#comment-submit").serialize();
		$.ajax({
			method: "POST",
			url: "/blog/api/comment?cmd=write",
			data: comment_submit_string,
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			dataType: "json", //받은 데이터를 자바스크립트 객체로 자동으로 변환해줌 따라서 아래의 comment는 문자열이 아니라 객체임
			
			success: function(comment){
				//화면에 적용
				var comment_et = commentWriteForm(comment.id, comment.user.username, comment.content, comment.createDate, comment.user.userProfile);
				$("#comments-area").append(comment_et);
				//입력폼 초기화하기
				$("#content").val("");
			},
			error: function(xhr){
				console.log(xhr);
			}
		});
		
	}


	//comment 삭제
	function commentDelete(comment_id){
		$.ajax({
			url: "/blog/api/comment?cmd=delete",
			method: "POST",
			contentType: "text/plain; charset=utf-8", //MIME 타입
			data: comment_id+"",
			
			success: function(result){
				if(result === "ok"){
					$("#comment-id-"+comment_id).remove();
					$("#comment-replys-"+comment_id).remove();
				}
			},
			error: function(xhr){
				
			}
		});
	}
	
	function replyItemForm(id, username, content, createDate, userProfile, userId){
		var replyItem = "<div id='reply-id-"+id+"' class='comment-list left-padding'>";
		replyItem+= "<div class='single-comment justify-content-between d-flex'>";
		replyItem+= "<div class='user justify-content-between d-flex'>";
		replyItem+= "<div class='thumb'><img src='"+userProfile+"' width='40px' height='40px' style='border-radius: 50%;'> </div> ";
		replyItem+= "<div class='desc'><h5><a href='#'>"+username+"</a></h5>";
		replyItem+= "<p class='date'>"+createDate+"</p>";
		replyItem+= "<p class='comment' style='word-break:break-all;'>"+content+"</p>";
		replyItem+= "</div></div>";
		var sessionUserId = ${sessionScope.user.id}+"";
		if(sessionUserId==userId){
			replyItem+= "<div class='reply-btn'><button type='button' onClick='replyDelete("+id+")' class='btn-reply text-uppercase'>삭제</button></div>";	
		}
		replyItem+= "</div></div>";
		
		return replyItem;
	}

	//reply 보기
	function replyListShow(et, comment_id){
		if(et.innerHTML==="보기"){
			
			et.innerHTML = "닫기";
			
			$.ajax({
				method: "POST",
				url: "/blog/api/reply?cmd=list",
				data: comment_id+"",
				contentType: "text/plain; charset=utf-8",
				dataType: "json",
				
				success: function(replys){
					if(replys.length > 0){
						//div 생성
						//<div id="comment-replys-comment_id" class="comment-list"></div>
						var comment_replys = document.createElement("div");
						comment_replys.id = "comment-replys-"+comment_id;
						comment_replys.className = "comment-list";
						
						$("#comment-id-"+comment_id).after(comment_replys);
						
						for(reply of replys){
							var reply_et = replyItemForm(reply.id, reply.user.username, reply.content, reply.createDate, reply.user.userProfile, reply.userId);
							$("#comment-replys-"+comment_id).append(reply_et);
						}
					}
				},
				error: function(xhr){
					console.log(xhr);
				}
			});
			
		}else{
			
			et.innerHTML = "보기";
			$("#comment-replys-"+comment_id).remove();
			
		}

	}
	
	//reply 삭제
	function replyDelete(reply_id){
		$.ajax({
			method: "POST",
			url: "/blog/api/reply?cmd=delete",
			data: reply_id+"",
			contentType: "text/plain; charset=utf-8",
			
			success: function(result){
				if(result ==="ok"){
					$("#reply-id-"+reply_id).remove();
				}
			},
			error: function(xhr){
				console.log(xhr);
			}
		});
	}
	
	//reply 쓰는 form 보여주기
	function replyFormShow(et, comment_id){
		if(et.innerHTML==="쓰기"){
			
			et.innerHTML = "닫기";
			
			var comment_form_inner = "<form id='reply-submit'><input type='hidden' name='userId' value='${sessionScope.user.id}'/><input type='hidden' name='commentId' value='"+comment_id+"'/><div class='form-group'><textarea style='height:60px' class='form-control mb-10' rows='2' name='content' placeholder='Messege' required=''></textarea></div><button type='button' onclick='replyWrite()' class='primary-btn submit_btn'>Post Reply</button></form>";
			
			//div 생성
			//<div id="reply-form-comment_id" class="comment-form" style="margin-top: 0px;"></div>
			var comment_form = document.createElement("div");
			comment_form.id = "reply-form-"+comment_id;
			comment_form.className = "comment-form";
			comment_form.style = "margin-top: 0px";
			
			comment_form.innerHTML = comment_form_inner;
			
			var comment_list = document.querySelector("#comment-id-"+comment_id);
			comment_list.append(comment_form); //append, prepend / after, before
			
		}else{
			
			et.innerHTML = "쓰기";
			$("#reply-form-"+comment_id).remove();
			
		}
		 
	}
	
	
	//reply 쓰기
	function replyWrite(){
		
		var reply_submit_string = $("#reply-submit").serialize();
		
		$.ajax({
			method: "POST",
			url: "/blog/api/reply?cmd=write",
			data: reply_submit_string,
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			dataType: "json", //받은 데이터를 자바스크립트 객체로 자동으로 변환해줌 따라서 아래의 comment는 문자열이 아니라 객체임
			
			success: function(replys){
				
				if(replys.length > 0){
					
					var comment_id = replys[0].commentId;
					
					var btns = $("#comment-id-"+comment_id+" .reply-btn button");
					
					btns[btns.length-2].textContent = "닫기";
					btns[btns.length-1].textContent = "쓰기";	
					
					$("#reply-form-"+comment_id).remove();
					$("#comment-replys-"+comment_id).remove();
					
					//div 생성
					//<div id="comment-replys-comment_id" class="comment-list"></div>
					var comment_replys = document.createElement("div");
					comment_replys.id = "comment-replys-"+comment_id;
					comment_replys.className = "comment-list";
					
					$("#comment-id-"+comment_id).after(comment_replys);
					
					for(reply of replys){
						var reply_et = replyItemForm(reply.id, reply.user.username, reply.content, reply.createDate, reply.user.userProfile, reply.userId);
						$("#comment-replys-"+comment_id).append(reply_et);
					}
				}
				
			},
			error: function(xhr){
				console.log(xhr);
			}
		});

	}
	
	
</script>

<%@ include file="/include/footer.jsp"%>