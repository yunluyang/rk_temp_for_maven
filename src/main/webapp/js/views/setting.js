  toastr.options = {
        		  "closeButton": false,
        		  "debug": false,
        		  "newestOnTop": false,
        		  "progressBar": false,
        		  "positionClass": "toast-bottom-center",
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
  		$(document).ready(function() {
  			$.getJSON("querySetting",function(data,status){
  				console.dir(data);
  							$('#DevTempValueMax').val(data.DevTempValueMax);
			    			$('#DevTempValueMin').val(data.DevTempValueMin);
			    			$('#DevHumiValueMax').val(data.DevHumiValueMax);
			    			$('#DevHumiValueMin').val(data.DevHumiValueMin);
			    			$('#AlarmTemMax').val(data.AlarmTemMax);
			    			$('#AlarmTemMin').val(data.AlarmTemMin);
			    			$('#AlarmHumMax').val(data.AlarmHumMax);
			    			$('#AlarmHumMin').val(data.AlarmHumMin);
			    			$('#phone1').val(data.phone1);
			    			$('#phone2').val(data.phone2);
			    			$('#phone3').val(data.phone3);
			    	});
  		 
	  		$('#ajxaForm').ajaxForm(function(data) { 
			 if(data.result==2){
				  //
				 toastr["error"]("温度或湿度设置失败，请查看是否最高值低于最低值","失败");
			  }else if(data.result==3){
				  //手机号格式错误
				toastr["error"]("手机号格式错误，请检查","失败");
			  }else if(data.result==4){
				  //手机号格式错误
					toastr["error"]("温度或湿度报警设置不正确，请检查","失败");
				  }else{
				  //成功
					toastr["success"]("提交成功","成功");
			  }
			});  
  		}); 
  		
  		 