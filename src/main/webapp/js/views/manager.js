 
toastr.options = {
  "closeButton": true,
  "debug": false,
  "newestOnTop": false,
  "progressBar": false,
  "positionClass": "toast-bottom-right",
  "preventDuplicates": false,
  "onclick": null,
  "showDuration": "300",
  "hideDuration": "1000",
  "timeOut": "5000",
  "extendedTimeOut": "1000",
  "showEasing": "swing",
  "hideEasing": "linear",
  "showMethod": "fadeIn",
  "hideMethod": "fadeOut"
}

var time = true;

/*陕西孜晟新能源技术服务有限公司，王工：029-89551651，邮箱：邮箱：sxzsxny@seasonts.com。*/
$(document).ready(
		  	show()
        );

  setInterval("show()","10000");
		function show() {
		$.getJSON("getDeviceData",function (data,status){
			$('#glide').html('');
			$('bgMusic').attr("src","");
			console.dir(data);
			$.each(data.result,function(idx,item){ 
				console.dir(item);
			  if(item.devStatus){
			  if(item.tempStatus=="1"&&time){
					toastr["error"](item.devName+",温度超过上限："+item.devTempValue+"°","警告");
					$('bgMusic').attr("src","sound/alarm.mp3");
					div=	'<div class="front" style="background:red ; ">'
				}else if(item.tempStatus == "2"&&time){ 
					toastr["error"](item.devName+",温度超过下线："+item.devTempValue+"°","警告");
					$('bgMusic').attr("src","sound/alarm.mp3");
							div= '<div class="front" style="background:red ;">'	
				}else if(item.somgStatus == "1"&&time){ 
					toastr["error"](item.devName+",烟感报警","警告");
					$('bgMusic').attr("src","sound/alarm.mp3");
							div= '<div class="front" style="background:red ;">'	
				}else if(item.humiStatus=="1"&&time)
				{
					toastr["error"](item.devName+",湿度超过上限："+item.devHumiValue+"°","警告");
					$('bgMusic').attr("src","sound/alarm.mp3");
							div= '<div class="front" style="background:red ;">'	
				}else if(item.humiStatus=="2"&&time){
					toastr["error"](item.devName+",湿度超过上限："+item.devHumiValue+"°","警告");
					$('bgMusic').attr("src","sound/alarm.mp3");
					div= '<div class="front" style="background:red ;">'	
				}else{
							div= '<div class="front" style="background:#00a2e8 ;">'	
				};
			  }else{
				  div= '<div class="front" style="background:black;">'	
			  }
			  if(item.type==0){
				  a= '<a class="card" href="detail.html?id='+item.devKey+'" style="width:auto">';
				  p= '<p style="font-size:18px;"><span>'+item.devName+'</span><br><br>'+item.devTempName+':'+item.devTempValue+'<br><br>'+item.devHumiName+':'+item.devHumiValue+'</p>'
			  }else{
				  a= '<a class="card"  style="width:auto">';
				  if(item.somgStatus == "1"){
					  p='<p style="font-size:18px;"><span>'+item.devName+'</span><br><br><br>状态:报警<br><br></p>';
				  }else{
					  p='<p style="font-size:18px;"><span>'+item.devName+'</span><br><br><br>状态:正常<br><br></p>';
				  }
			  }
			 $('#glide').append(
					'<div class="col-3">'
					+ a
					+ div
					+ p
					+'</div>'
					+'<div class="back">'
					+'<div>'
					+'<button class="btn btn-danger" >查看更多</button>'
					+'</div>'
					+'</div>'
						+'</a>'
						+'</div>'
			 		);
			  }); 
			}); 
	  };
		
		function tz(item){
			window.location.href="detail.html";
		}
		
		$("#cleartoasts").click(function() {
	        toastr.clear();
        	var i=900;
	        var code = setInterval(count,1000);
	        function count(){
	        	if(i>=1){
	        		time=false;
	        		i--;
	        		$('#cleartoasts').text(i+"秒后恢复报警");
	        		$('#cleartoasts').attr('disabled', 'true');
	        	}
	        	else{
	        		time=true;
	        		clearInterval(code);
	        		$('#cleartoasts').text("15分钟内不再报警");
	        		//$('#cleartoasts').text(i+"秒后恢复报警");
	        		$('#cleartoasts').attr('disabled', 'false');
	        	}
	        };
	    })
		
		
		