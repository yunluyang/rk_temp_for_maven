


			var Request = new Object(); 
			Request = GetRequest();

			
		
			window.onload = function () {
				 //1.初始化Table
			    var oTable = new TableInit();
			    oTable.Init();
				getData(0);
				quertyCd();
			}

		function getData(type){
			var temData = [],humiData = [],recTiem=[];
			 // 基于准备好的dom，初始化echarts实例
	        var myChart = echarts.init(document.getElementById('tempContainer'));

	        // 指定图表的配置项和数据
	        option = {
	        	    tooltip: {
	        	        trigger: 'axis'
	        	    },
	        	    legend: {
	        	        data:['温度数据','湿度数据']
	        	    },
	        	    grid: {
	        	        left: '3%',
	        	        right: '4%',
	        	        bottom: '3%',
	        	        containLabel: true
	        	    },
	        	    toolbox: {
	        	        feature: {
	        	            saveAsImage: {}
	        	        }
	        	    },
	        	    xAxis: {
	        	        type: 'category',
	        	        boundaryGap: false,
	        	        data: recTiem
	        	    },
	        	    yAxis: {
	        	        type: 'value'
	        	    },
	        	    series: [
	        	        {
	        	            name:'温度数据',
	        	            type:'line',
	        	            stack: '总量',
	        	            data:temData
	        	        },
	        	        {
	        	            name:'湿度数据',
	        	            type:'line',
	        	            stack: '总量',
	        	            data:humiData
	        	        }
	        	    ]
	        	};

	        myChart.showLoading();
			$.getJSON("getWenduData", {
			    id:Request['id'],
			    type:type
			  },function (data,status){
				$.each(data.list, function(index, item) {
					var date = new Date(item.RecordTime);
			        var y = date.getFullYear();  
			        var m = date.getMonth();
			        var d = date.getDate();
					temData.push( item.Tem);
					humiData.push(item.Hum);
					recTiem.push(item.RecordTime);
					console.dir(item.RecordTime+":"+item.Tem+":"+item.Hum);
				});
				myChart.hideLoading();
		        // 使用刚指定的配置项和数据显示图表。
		        myChart.setOption(option);
			});
		}
		
		function GetRequest() { 
			var url = location.search; //获取url中"?"符后的字串 
			var theRequest = new Object(); 
			if (url.indexOf("?") != -1) { 
			var str = url.substr(1); 
			strs = str.split("&"); 
			for(var i = 0; i < strs.length; i ++) { 
			theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]); 
			} 
			} 
			return theRequest; 
			} 
		
	
		
		
		function saveKT(){
			var comm = $('#com option:selected').text();//选中的文本
			var add = $('#adds option:selected').text();//选中的文本
			var baud = $('#baud option:selected').text();//选中的文本
			var info = $('#condtinfo').val();
			var deviceKey = Request['id'];
			$.getJSON("saveConditioner", {
				comm:comm,
				add:add,
				baud:baud,
				deviceKey:deviceKey,
				info:info
			  },function (data,status){
				  swal({
		                title: "成功",
		                text: "绑定空调成功",
		                type: "success",
		                confirmButtonClass: "btn btn-confirm mt-2"
		            })
			});
		}
		
		function quertyCd(){
			$.getJSON("queryConditioner", {
				deviceKey:Request['id']
			  },function (data,status){
				  if(data!=null){
					  $('#com option:selected').text(data.COM);
					  $('#adds option:selected').text(data.SlaveId);
					  $('#baud option:selected').text(data.Baud);
					  $('#condtinfo').val(data.ConditionerName);
					  $('#save').text("重置");
				  }
			});
		}
		
		var TableInit = function () {
		    var oTableInit = new Object();
		    //初始化Table
		    oTableInit.Init = function () {
		        $('#tb_departments').bootstrapTable({
		            url: 'queryAlarm',         //请求后台的URL（*）
		            method: 'get',                      //请求方式（*）
		            striped: true,                      //是否显示行间隔色
		            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		            pagination: true,                   //是否显示分页（*）
		            sortable: true,                     //是否启用排序
		            sortOrder: "asc",                   //排序方式
		            queryParams: oTableInit.queryParams,//传递参数（*）
		            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
		            pageNumber:1,                       //初始化加载第一页，默认第一页
		            pageSize: 10,                       //每页的记录行数（*）
		            pageList: [10, 25, 50, 100,500],        //可供选择的每页的行数（*）
		            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
		            strictSearch: false,
		            showColumns: false,                  //是否显示所有的列
		            showRefresh: false,                  //是否显示刷新按钮
		            minimumCountColumns: 2,             //最少允许的列数
		            clickToSelect: true,                //是否启用点击选中行
		       //     height: 650,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		            uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
		            showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
		            cardView: false,                    //是否显示详细视图
		            detailView: false,                   //是否显示父子表
		            exportDataType:'all',               //导出
		            showExport: false,  //是否显示导出按钮
		            buttonsAlign:"right",  //按钮位置
		            exportTypes:['excel'],  //导出文件类型
		            Icons:'glyphicon-export',
		            columns: [{
		                checkbox: false,
		                visible: false
		            },  {
		                field: 'ID',
		                title: '报警序号',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            },{
		                field: 'AlarmType',
		                title: '报警类型',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            },{
		                field: 'AlarmMessage',
		                title: '报警信息',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            },{
		                field: 'AlarmRange',
		                title: '设定范围',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            },{
		                field: 'DataValue',
		                title: '报警值',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            },{
		                field: 'RecordTime',
		                title: '报警时间',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true,
		            }],
		            onLoadSuccess: function () {
		            	 },
		            onLoadError: function () {
		            	},
		            onDblClickRow: function (row, $element) {
		            	var id = row.id;
		            	alert(id);
		            	},
		            rowStyle: function (row, index) { //设置行的特殊样式
		                    //这里有5个取值颜色['active', 'success', 'info', 'warning', 'danger'];
		            	 var strclass = "";
		                    if (row.isPassCS == 2) {
		                        strclass = "warning";
		                    }else if(row.isPassCS == 1||row.isPassCS == 3){
		                    	strclass = "success";
		                    }else{
		                    	strclass = "danger";
		                    }
		                    return { classes: strclass }
		                }
		        });
		    };
		    //得到查询的参数
		    oTableInit.queryParams = function (params) {
		        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		        		  rows: params.limit,                         //页面大小
	                      page: (params.offset / params.limit) + 1,   //页码
	                      deviceKey : Request['id']
		        };
		        return temp;
		    };
		    return oTableInit;
		};
		
		
		