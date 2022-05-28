//展开收起
var bottom = "65px";
var right = "122px";
var bottomBf = "35%";
var rightBf = "184px";
var setting = {
		imageWidth:1680,
		imageHeight:1050
	};
var windowHeight = $(window).height();
var windowWidth = $(window).width();
/*var capsLockKey = e.keyCode ? e.keyCode : e.which;
var shifKey = e.shiftKey ? e.shiftKey:((capsLockKey == 16) ? true : false);
if(((capsLockKey >= 65 && capsLockKey <= 90) && !shifKey)||((capsLockKey >= 97 && capsLockKey <= 122) && shifKey)){
   alert("1");
}else{
	alert("2");
   
}*/

init = function(){
	var windowHeight = $(window).height();
	var windowWidth = $(window).width();
	$(".login_conatiner").height(windowHeight).width(windowWidth);
	$("#container_bg").height(windowHeight).width(windowWidth);
	$("#login_right_box").height(windowHeight);
	var imgW = setting.imageWidth;
	var imgH = setting.imageHeight;
	var ratio = imgH / imgW; //图片的高宽比

	imgW = windowWidth; //图片的宽度等于窗口宽度
	imgH = Math.round(windowWidth * ratio); //图片高度等于图片宽度 乘以 高宽比

	if(imgH < windowHeight){ //但如果图片高度小于窗口高度的话
		imgH = windowHeight; //让图片高度等于窗口高度
		imgW = Math.round(imgH / ratio); //图片宽度等于图片高度 除以 高宽比
	}
	$(".login_img_01").width(imgW).height(imgH);  //设置图片高度和宽度
	
	
	
	
};
openBtn = function(){
	
	if(1366 <= windowWidth && windowWidth <= 1440 ){
		rightBf = "10%";
	}
	if(1024 <= windowWidth && windowWidth <= 1250 ){
		right = "20px";
	}
	if(windowHeight <= 900 ){
		bottomBf = (windowHeight - 395) * 0.5;
	}
	if(windowHeight <= 680 ){
		bottomBf = (windowHeight - 395) * 0.3;
	}
};
var lqrcode;
$(function(){
	$(window).resize(function(){
		init();
		openBtn();
	});
	//加载账号密码登录
	password_login(true);
	
	$(".toggle_btn").hover(function(){
		if($(this).attr("data-shrink")=="0"){
			$(this).html("收起");

		}else{
			$(this).html("展开");

		}
	},function(){
		if($(this).attr("data-shrink")=="0"){
			$(this).html("<span class='minus'></span>");

		}else{
			$(this).html("<span class='plus'></span>");

		}
	});
	$(".toggle_btn").click(function(){
		if($(this).attr("data-shrink")=="0") {
			$(this).parent().animate({bottom: bottom, right: right, height: "41px"}, 400);
			$(this).parent().addClass("box-open");
			$(this).html("<span class='plus'></span>");
			$(this).attr("data-shrink", "1");
		}else {
			if(windowHeight <= 900 ){
				bottomBf = (windowHeight - 395) * 0.5;
			}
			if(windowHeight <= 680 ){
				bottomBf = (windowHeight - 395) * 0.3;
			}
			$(this).parent().animate({bottom: bottomBf, right: rightBf, height: "395px"}, 400);
			$(this).parent().removeClass("box-open");
			$(this).html("<span class='minus'></span>");
			$(this).attr("data-shrink", "0");
		}
	});
});
//加载账号登录功能
function password_login(firstclick){
	var passwordhtml = document.getElementById("password_template").innerHTML;
	$("#template_container").html(passwordhtml);
	init();
	openBtn();
	if(lqrcode!=null){
		lqrcode.clear();
	}
	//每次切换都先让登录框最大化
	if(!firstclick){
		$(".toggle_btn").parent().animate({bottom: bottomBf, right: rightBf, height: "395px"}, 400);
		$(".toggle_btn").parent().removeClass("box-open");
		$(".toggle_btn").html("<span class='minus'></span>");
		$(".toggle_btn").attr("data-shrink", "0");		
	}
	
	
	$("#index_login_btn").click(function(){
		/*var capsLockKey = e.keyCode ? e.keyCode : e.which;
		var shifKey = e.shiftKey ? e.shiftKey:((capsLockKey == 16) ? true : false);
		if(((capsLockKey >= 65 && capsLockKey <= 90) && !shifKey)||((capsLockKey >= 97 && capsLockKey <= 122) && shifKey)){
		   alert("1");
		}else{
			alert("2");
		   
		}*/
		login();
	}); 
	
	//点击账号登陆
	$("#password_login").click(function(){
		$("#password_login").addClass("active");
		$("#qrcode_login").removeClass("active");
		$("#mobile_login").removeClass("active");
		$("#login_content").html(passwordhtml);
	});
	//点击扫码登陆
	$("#qrcode_login").click(function(){
		$("#password_login").removeClass("active");
		$("#qrcode_login").addClass("active");
		$("#mobile_login").removeClass("active");
		$("#login_content").html(qrcodehtml);
	});
	//点击手机号
	$("#mobile_login").click(function(){
		$("#password_login").removeClass("active");
		$("#qrcode_login").removeClass("active");
		$("#mobile_login").addClass("active");
		$("#login_content").html(mobilehtml);
	});
	
	//点击记住用户名
	$("#rememberName").change(function(){
		if($(this).is(":checked")){
			var $u = $("#un").val() ;
			if($.trim($u)==''){
				$("#errormsg").text("账号不能为空。").show();
				$(this).removeAttr("checked");
			}else{
				//不等于空，写cookie
				setCookie('hdu_cas_un' , $u , 365);
			}
		}else{
			//反选之后清空cookie
			clearCookie('hdu_cas_un');
		}
	});
	
	//用户名文本域keyup事件
	$("#un").keyup(function(e){
		if(e.which == 13) {
			login();
	    }
	}).keydown(function(e){
		$("#errormsg").hide();
	}).focus();
	
	//密码文本域keyup事件
	$("#pd").keyup(function(e){
		if(e.which == 13) {
			login();
	    }
	}).keydown(function(e){
		$("#errormsg").hide();
	});
	//如果有错误信息，则显示
	if($("#errormsghide").text()){
		$("#errormsg").text($("#errormsghide").text()).show();
	}
	//获取cookie值
	var cookie = getCookie('hdu_cas_un');
	if(cookie){
		$("#un").val(cookie);
		$("#rememberName").attr("checked","checked");
	}
	//重新获取验证码
	$("#a_changeCode").click(function(){
    	$("#codeImage").attr("src", "code?"+Math.random()) ;
    });
	
	//360
	$("#open_360").mouseover(function(){
		$("#open_360_img").show();
	}).mouseout(function(){
		$("#open_360_img").hide();
	});
	
	//如果有错误信息，则显示
	if($("#errormsghide").text()){
		$("#errormsg").text($("#errormsghide").text()).show();
	}
};
//加载扫码登录功能
function qrcode_login(){
	var qrcodehtml = document.getElementById("qrcode_template").innerHTML;
	$("#template_container").html(qrcodehtml);
	init();
	openBtn();
	//每次切换都先让登录框最大化
	$(".toggle_btn").parent().animate({bottom: bottomBf, right: rightBf, height: "395px"}, 400);
	$(".toggle_btn").parent().removeClass("box-open");
	$(".toggle_btn").html("<span class='minus'></span>");
	$(".toggle_btn").attr("data-shrink", "0");
	
	lqrcode = new loginQRCode("qrcode",153,153);
	lqrcode.generateLoginQRCode(function(result){
		window.location.href = result.redirect_url;
	});
}

//加载手机号登录功能
function mobile_login() {
	var mobilehtml = document.getElementById("mobile_template").innerHTML;
	$("#template_container").html(mobilehtml);
	init();
	openBtn();
	//每次切换都先让登录框最大化
	$(".toggle_btn").parent().animate({bottom: bottomBf, right: rightBf, height: "395px"}, 400);
	$(".toggle_btn").parent().removeClass("box-open");
	$(".toggle_btn").html("<span class='minus'></span>");
	$(".toggle_btn").attr("data-shrink", "0");

	//确认发送手机验证码间隔的初始状态
	if (_intDiff != 0) {
		$("#send_mobile_code").hide();
		$("#send_mobile_code_wait").html(_intDiff + "s后重新获取").show();
	}

	var mobile_reg = /^1\d{10}$/;

	//点击获取验证码
	$("#send_mobile_code").click(function(){
		if($("#mobile").val()==""||!mobile_reg.test($("#mobile").val())) {
			$("#errormsg_mobile").text("请输入正确的手机号！").show();
		} else {
			var intDiff = parseInt(59);//倒计时总秒数量
			timer(intDiff);
			//做发送处理
			//console.log("send code to " + $("#mobile").val());
			$.post("mobileCodeLogin", {
					method: "sendMobileCode",
					mobile: $("#mobile").val(),
					skipWechat: "true"
				},
				function (data) {

				}
			);
		}
	});

	//点击下一步
	$("#mobile_next_btn").click(function(){
		var $mobile = $("#mobile");
		var $code = $("#mobile_code");

		//检查输入数据完整性
		var mobile = $mobile.val().trim();
		if(mobile==""||!mobile_reg.test(mobile)) {
			$("#errormsg_mobile").text("请输入正确的手机号！").show();
			$mobile.focus();
			$mobile.parent().addClass("login_error_border");
			return ;
		}

		var code = $code.val().trim();
		if(code==""){
			$("#errormsg_mobile").text("短信验证码不能为空！").show();
			$code.focus();
			$code.parent().addClass("login_error_border");
			return ;
		}

		//锁定输入框避免重复提交
		$mobile.attr("disabled","disabled");
		$code.attr("disabled","disabled");

		//校验短信验证码
		$.post("mobileCodeLogin", {
				method: "verifyMobileCode",
				mobile: mobile,
				mobile_code: code,
				skipWechat: "true"
			},
			function (data) {
				//console.log(data);
				if (data.success) {
					//获取到成功的校验结果，跳转到下一步填写身份信息
					mobile_login2(mobile);
				} else {
					//验证失败，提示错误信息，并解锁输入框
					if (data.msg == "code_error") {
						$("#errormsg_mobile").text("验证码错误！").show();
					} else if (data.msg == "code_expired") {
						$("#errormsg_mobile").text("验证码已过期！").show();
					} else {
						$("#errormsg_mobile").text("验证失败！请稍后再试。").show();
					}

					$mobile.removeAttr("disabled");
					$code.removeAttr("disabled");
				}
			}
		);
	});

	//手机号文本域keyup事件
	$("#mobile").keyup(function(e){
		if(e.which == 13) {
		}
	}).keydown(function(e){
		$("#errormsg_mobile").hide();
	}).focus();

	//验证码文本域keyup事件
	$("#mobile_code").keyup(function(e){
		if(e.which == 13) {
		}
	}).keydown(function(e){
		$("#errormsg_mobile").hide();
	});

}

//手机验证码登录页2：校验个人信息
function mobile_login2(mobile) {
	var mobilehtml2 = document.getElementById("mobile_template_2").innerHTML;
	$("#template_container").html(mobilehtml2);
	init();
	openBtn();
	//每次切换都先让登录框最大化
	$(".toggle_btn").parent().animate({bottom: bottomBf, right: rightBf, height: "395px"}, 400);
	$(".toggle_btn").parent().removeClass("box-open");
	$(".toggle_btn").html("<span class='minus'></span>");
	$(".toggle_btn").attr("data-shrink", "0");

	//点击登录
	$("#mobile_login_btn").click(function(){
		//console.log("login_btn1");
		var $id_number = $("#id_number");
		var $user_name = $("#user_name");
		var $card_id = $("#card_id");

		//检查输入数据完整性
		var id_number = $id_number.val().trim();
		if(id_number==""){
			$("#errormsg_mobile2").text("学号/工号不能为空！").show();
			$id_number.focus();
			$id_number.parent().addClass("login_error_border");
			return ;
		}
		var user_name = $user_name.val().trim();
		if(user_name==""){
			$("#errormsg_mobile2").text("姓名不能为空！").show();
			$user_name.focus();
			$user_name.parent().addClass("login_error_border");
			return ;
		}
		var card_id = $card_id.val().trim();
		if(card_id==""){
			$("#errormsg_mobile2").text("身份证号不能为空！").show();
			$card_id.focus();
			$card_id.parent().addClass("login_error_border");
			return ;
		}

		//锁定输入框避免重复提交
		$id_number.attr("disabled","disabled");
		$user_name.attr("disabled","disabled");
		$card_id.attr("disabled","disabled");

		var serviceurl = getQueryVariable("service");

		//校验用户身份并登录
		$.post("mobileCodeLogin", {
				method: "login",
				id_number: id_number,
				user_name: user_name,
				card_id: card_id,
				mobile: mobile,
				serviceurl: serviceurl,
				skipWechat: "true"
			},
			function (data) {
				//console.log(data);
				if (data.success) {
					//登录成功后跳转
					window.location.href = decodeURIComponent(data.redirect_url);
				} else {
					//登录失败，提示对应信息
					if (data.msg == "mobile_not_same") {
						$("#errormsg_mobile2").text("登录失败！手机号与身份不匹配！").show();
					} else if (data.msg == "user_not_found") {
						$("#errormsg_mobile2").text("登录失败！身份信息有误或未找到该用户！").show();
					} else if (data.msg == "user_not_active") {
						$("#errormsg_mobile2").text("登录失败！该用户已被冻结！请联系管理员处理。").show();
					} else {
						$("#errormsg_mobile2").text("登录失败！").show();
					}

					//解锁输入框
					$id_number.removeAttr("disabled");
					$user_name.removeAttr("disabled");
					$card_id.removeAttr("disabled");
				}
			}
		);


	});

	//学号文本域keyup事件
	$("#id_number").keyup(function(e){
		if(e.which == 13) {
		}
	}).keydown(function(e){
		$("#errormsg_mobile2").hide();
	});

	//姓名文本域keyup事件
	$("#user_name").keyup(function(e){
		if(e.which == 13) {
		}
	}).keydown(function(e){
		$("#errormsg_mobile2").hide();
	});

	//身份证号文本域keyup事件
	$("#card_id").keyup(function(e){
		if(e.which == 13) {
		}
	}).keydown(function(e){
		$("#errormsg_mobile2").hide();
	});
}

function login(){
	var $u = $("#un") , $p=$("#pd");
	
	var u = $u.val().trim();
	if(u==""){
		$u.focus();
		$u.parent().addClass("login_error_border");
		return ;
	}
	
	var p = $p.val().trim();
	if(p==""){
		$p.focus();
		$p.parent().addClass("login_error_border");
		return ;
	}
	
	$u.attr("disabled","disabled");
	$p.attr("disabled","disabled");
	
	var lt = $("#lt").val();
	
	$("#ul").val(u.length);
	$("#pl").val(p.length);
	$("#rsa").val(strEnc(u+p+lt , '1' , '2' , '3'));
	
	$("#loginForm")[0].submit();
	
}

//图片的轮播功能
jQuery(function() {

	jQuery('#camera_wrap_4').camera({

		height : 'auto',//高度

		hover : false,//鼠标经过幻灯片时暂停(true, false)

		//imagePath: 图片的目录

		loader : 'none',//加载图标(pie, bar, none)

		//loaderColor: 加载图标颜色( '颜色值,例如:#eee' )

		//loaderBgColor: 加载图标背景颜色

		loaderOpacity : '8',//加载图标的透明度( '.8'默认值, 其他的可以写 0, .1, .2, .3, .4, .5, .6, .7, .8, .9, 1 )

		loaderPadding : '2',//加载图标的大小( 填数字,默认为2 )

		navigation : false,//左右箭头显示/隐藏(true, false)

		navigationHover : false,//鼠标经过时左右箭头显示/隐藏(true, false)

		pagination : false,//是否显示分页(true, false)

		playPause : false,//暂停按钮显示/隐藏(true, false)

		pauseOnClick : false,//鼠标点击后是否暂停(true, false)

		portrait : false,//显示幻灯片里所有图片的实际大小(true, false)

		thumbnails : false,//是否显示缩略图(true, false)

		time : 500,// 幻灯片播放时间( 填数字 )

		//transPeriod: 4000,//动画速度( 填数字 )

		imagePath : '../images/',
		thumbnails : false

	});

	//触发如何使用360极速模式图片
	$("#open_360").mouseover(function() {
		$("#open_360_img").show();
	}).mouseout(function() {
		$("#open_360_img").hide();
	});

});

//设置cookie
function setCookie(cname, cvalue, exdays) {
  var d = new Date();
  d.setTime(d.getTime() + (exdays*24*60*60*1000));
  var expires = "expires="+d.toUTCString();
  document.cookie = cname + "=" + cvalue + "; " + expires;
}

//获取cookie
function getCookie(cname) {
  var name = cname + "=";
  var ca = document.cookie.split(';');
  for(var i=0; i<ca.length; i++) {
      var c = ca[i];
      while (c.charAt(0)==' ') c = c.substring(1);
      if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
  }
  return "";
}

//清除cookie  
function clearCookie(name) {  
  setCookie(name, "", -1);  
}

//获取url参数
function getQueryVariable(variable)
{
	var query = window.location.search.substring(1);
	var vars = query.split("&");
	for (var i=0;i<vars.length;i++) {
		var pair = vars[i].split("=");
		if(pair[0] == variable){return pair[1];}
	}
	return "";
}

var _intDiff = 0;//全局时间
function timer(intDiff){
	$("#send_mobile_code").hide();
	$("#send_mobile_code_wait").html("60s后重新获取").show();
	if(_intDiff == 0){

		var objTimer = window.setInterval(function(){
			_intDiff = Math.floor(intDiff);//设置全局时间
			var second=0;//时间默认值
			if(intDiff > 0){
				second = Math.floor(intDiff);
			}
//			if (second <= 99){
//				second = '0' + second;
//			}
// 			if (second <= 9){
// 				second = '0' + second;
// 			}
			$('#send_mobile_code_wait').html(second+'s后重新获取');
			if(second == 0){
				$("#send_mobile_code_wait").hide();
				$("#send_mobile_code").show();

				window.clearInterval(objTimer);
			}
			intDiff--;
		}, 1000);
	}else{

	}
}