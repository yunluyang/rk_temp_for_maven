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

			window.onload = function () {
				 //1.初始化Table
			    var oTable = new TableInit();
			    oTable.Init();
			    var table = TableInit2();
			    table.Init();
			    var temtable = TableInit3();
			    temtable.Init();
			    var equipment = TableInit4();
			    equipment.Init();
			}
		
		var TableInit = function () {
		    var oTableInit = new Object();
		    //初始化Table
		    oTableInit.Init = function () {
		        $('#tb_departments').bootstrapTable({
		            url: 'queryCdtHistory',         //请求后台的URL（*）
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
		            pageList: [10,50,100,500,5000],        //可供选择的每页的行数（*）
		            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
		            strictSearch: false,
		            showColumns: false,                  //是否显示所有的列
		            showRefresh: false,                  //是否显示刷新按钮
		            minimumCountColumns: 2,             //最少允许的列数
		            clickToSelect: true,                //是否启用点击选中行
		       //     height: 650,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
		            showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
		            cardView: false,                    //是否显示详细视图
		            detailView: false,                   //是否显示父子表
		            columns: [{
		                checkbox: false,
		                visible: false
		            },  {
		                field: 'id',
		                title: '报警序号',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            },{
		                field: 'DevName',
		                title: '温湿度设备',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            },{
		                field: 'ConditionerName',
		                title: '空调名称',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            },{
		                field: 'type',
		                title: '动作',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            },{
		                field: 'info',
		                title: '说明',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            },{
		                field: 'RecordTime',
		                title: '操作时间',
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
		                    if (row.info == "成功") {
		                        strclass = "success";
		                    }else{
		                    	strclass = "danger";
		                    }
		                    return { classes: strclass }
		                },
		            showExport: false,  //是否显示导出按钮
		            buttonsAlign:"right",  //按钮位置
		            exportTypes:['excel'],  //导出文件类型
		            exportDataType: "all",   
		            Icons:'glyphicon-export',
		            exportOptions:{
		                    fileName: '运行报告',  //文件名称设置
		                    worksheetName: 'sheet1',  //表格工作区名称
		                    tableName: '运行报告表',
		                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
		                },



		            
		        });
		    };
		    //得到查询的参数
		    oTableInit.queryParams = function (params) {
		        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		        		  rows: params.limit,                         //页面大小
	                      page: (params.offset / params.limit) + 1   //页码
		        };
		        return temp;
		    };
		    return oTableInit;
		};
		
		
		var TableInit2 = function () {
		    var oTableInit = new Object();
		    //初始化Table
		    oTableInit.Init = function () {
		        $('#tb_alarms').bootstrapTable({
		            url: 'queryAllAlarm',         //请求后台的URL（*）
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
		            pageList: [10,50,100,500,5000],        //可供选择的每页的行数（*）
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
		            } ,{
		                field: 'DeviceName',
		                title: '报警设备',
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
		        };
		        return temp;
		    };
		    return oTableInit;
		};
		
		
		var TableInit3 = function () {
		    var oTableInit = new Object();
		    //初始化Table
		    oTableInit.Init = function () {
		        $('#tb_temp_humi').bootstrapTable({
		            url: 'queryAllTemHumi',         //请求后台的URL（*）
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
		            pageList: [10,100,500,5000,10000],        //可供选择的每页的行数（*）
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
		                field: 'id',
		                title: '报警序号',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            } ,{
		                field: 'DeviceName',
		                title: '报警设备',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            },{
		                field: 'Tem',
		                title: '温度',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            },{
		                field: 'Hum',
		                title: '湿度',
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
		        };
		        return temp;
		    };
		    return oTableInit;
		};
		
		var TableInit4 = function () {
		    var oTableInit = new Object();
		    //初始化Table
		    oTableInit.Init = function () {
		        $('#tb_equipment').bootstrapTable({
		            url: 'queryEquipment',         //请求后台的URL（*）
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
		            pageList: [10,100,500,5000,10000],        //可供选择的每页的行数（*）
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
		                field: 'DeviceKey',
		                title: 'id',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            } ,{
		                field: 'DevName',
		                title: '设备名称',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            },{
		                field: 'SlaveId',
		                title: '地址',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            },{
		                field: 'Comm',
		                title: '端口',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true
		            },{
		                title: '操作',
		                align: 'center',
		             	valign: 'middle',
		                sortable: true,
		                formatter: function (value,row,index){
		                	return [
		                	       '<a href="/deletEquipment?deviceKey='+row.DeviceKey+'" style="color: red">删除</a>'
			                	        		].join();
		                }
		            }],
		            onLoadSuccess: function () {
		            	 },
		            onLoadError: function () {
		            	},
		            onDblClickRow: function (row, $element) {
		            	var id = row.id;
		            	alert(id);
		            	}
		        });
		    };
		    //得到查询的参数
		    oTableInit.queryParams = function (params) {
		        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		        		  rows: params.limit,                         //页面大小
	                      page: (params.offset / params.limit) + 1,   //页码
		        };
		        return temp;
		    };
		    return oTableInit;
		};
		
	