var scn_data={
		alarm:{alarm:10,fault:10},
		dtu:{ on:150,off:150},
		plc:{on:10,off:10},
		industy:{v1:10,v2:11,v3:12,v3:14,v4:15,v5:17,v6:18},
		online:{v1:10,v2:11,v3:12,v3:14,v4:15,v5:17,v6:18},
		almMsg:[{msg:"输出电压 237.0V 输入电压 220.0V"},
				{msg:"输出频率 50Hz 输入频率 50Hz"},
				{msg:"电池正压 202.6V 电池负压 0V"},
				{msg:"当前温度 22.3° 备用时间 99999s"},
				{msg:"电池容量 100% 故障代码 fffffff"}
				],
		msgCnt:[{msg:100,alm:20},
			{msg:200,alm:40},
			{msg:300,alm:50},
			{msg:400,alm:35},
			{msg:400,alm:40},
			{msg:400,alm:11},
			{msg:400,alm:66},
			{msg:100,alm:77},
			{msg:200,alm:88},
			{msg:300,alm:22},
			{msg:400,alm:99},
			{msg:400,alm:100},
			{msg:400,alm:111},
			{msg:400,alm:222},
			{msg:100,alm:333},
			{msg:200,alm:11},
			{msg:300,alm:33},
			{msg:400,alm:55},
			{msg:400,alm:77},
			{msg:400,alm:90}
			],
		map:[{area:"山东",cnt:20},
			{area:"浙江",cnt:40},
			{area:"江苏",cnt:50},
			{area:"辽宁",cnt:50}
		],
		factoryHeader:[
	        {"categories":"设备名称"},
	        {"categories":"温度"},
	        {"categories":"湿度"},
	        {"categories":"状态"},
	        {"categories":"报警"},
	        {"categories":"操作"}
    	],
		factory:[
			{"company":"远动通信屏","dtuCnt": 200, "plcCnt": 400,"dataCnt": "在线","alarm": "-"},
	        {"company":"视频监控屏","dtuCnt": 3000,"plcCnt": 2000,"dataCnt": "在线","alarm": "-"},
	        {"company":"AGC及AVC控制服务器屏","dtuCnt": 1500,"plcCnt": 1000,"dataCnt": "在线","alarm": "-"},
	        {"company":"光功率预测系统屏","dtuCnt": 1500,"plcCnt": 300,"dataCnt": "在线","alarm": "温度上限报警>120"},
	        {"company":"公用测控屏","dtuCnt": 1000,"plcCnt": 800,"dataCnt": "离线","alarm": "-"},
	        {"company":"烟感设备1","dtuCnt": 1000,"plcCnt": 800,"dataCnt": "在线","alarm": "-"}]
	};
var vm = new Vue({
	el: '#content',
	data: scn_data,
	methods: {
		details: function() {
			
		}
	}
})