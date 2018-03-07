var loginInfoText=$(".login-info-text")

var loginInfo=$(".login-info")
var loginText=$("#login-text")
var loginPasswd=$("#login-passwd")
var loginButton=$(".login-login-button")

loginInfo.hide()

loginText.focus(function(){
	loginInfo.hide()
	loginButton.parent().css("margin-top","20px")
})
loginPasswd.focus(function(){
	loginInfo.hide()
	loginButton.parent().css("margin-top","20px")
})
loginButton.on('click',function(){
	if(loginText.val().length==""){
		loginButton.parent().css("margin-top","0")
		infoText("请输入用户名")
	}else{
		infoText("登录中...")
		$.ajax({
			type:"POST",
			url:"userLoginer",
			data:{loginAccount:loginText.val(),loginPassword:loginPasswd.val()},
			dataType:"text",
			success:function(data){
				if(data==1){
					infoText("用户名或密码错误")
				}else{
					window.open("index.html","_self")
				}
			},
			error:function(data){
				infoText("用户名或密码错误")
			}
			
		})
	}
})

function infoText(info){
	loginButton.parent().css("margin-top","0")
	loginInfo.show()
	loginInfoText.text(info)
}