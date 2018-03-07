$.ajax({
//	type:"POST",
	url:"movepersonal",
	data:{id:0,loginPassword:8},
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
		var usermanager='<li>'+
        '<img class="am-img-thumbnail am-img-bdrs" src="static/img/img/'+json[i].photo+'" alt=""/>'+
        '<div class="gallery-title">'+json[i].name+'</div>'+
        '<div class="gallery-desc">'+json[i].filetype+'</div>'+
      '</li>'
		$("#movie").append(usermanager) 
	}
}            
