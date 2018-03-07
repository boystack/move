$.ajax({
//	type:"POST",
	url:"userManager",
	dataType:"text",
	success:function(data){
		console.log(1)
        var arr= data;
		json = eval("(" +arr +")");
		usermanager(json)
	},
	error:function(data){
		console.log(data)
	}
	
})

function usermanager(json){
	for(var i=0;i < json.length;i++){
		var usermanager='<tr><td><input type="checkbox" /></td><td>'+json[i].id+'</td><td>'+json[i].account+'</td><td>'+json[i].name+'</td><td>'+json[i].sex+'</td><td>'+json[i].permision+'</td>'+
		'<td><div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs">'
//		+'<div class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span> 删除</div></div></div></td></tr>'
		$("tbody").append(usermanager) 
	}
}
              
