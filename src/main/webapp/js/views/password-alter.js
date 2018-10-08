!
function(t) {
    "use strict";
    var n = function() {};
    n.prototype.init = function() {
        t("#chaining-alert").click(function() {
            swal.setDefaults({
                input: "text",
                confirmButtonText: "下一步 &rarr;",
                cancelButtonText:"取消",
                showCancelButton: !0,
                animation: !1,
                progressSteps: ["1", "2", "3"],
                confirmButtonClass: "btn btn-confirm mt-2",
                cancelButtonClass: "btn btn-cancel ml-2 mt-2"
            });
            swal.queue([{
                title: "修改密码",
                text: "请输入原密码"
            },
            "新密码", "确认密码"]).then(function(t) {
            	//{"value":["aaaa","aaaa","aaa"]}   JSON.stringify(t)
                //swal.resetDefaults()
            	$.getJSON("modifyPass",
            			{ pw: t.value[0], pw1: t.value[1],pw2:t.value[2] }
            			,function (data,stutas){
            				if(data.result==0){
            					toastr["success"]("修改成功","成功");
            				}else{
            					toastr["error"]("修改失败"+data.result,"失败");
            				}
            	})
            },
            function() {
                //swal.resetDefaults()
            })
        }),
        t("#ajax-alert").click(function() {
            swal({
                title: "请输入视频管理密码",
                input: "number",
                showCancelButton: !0,
                confirmButtonText: "确认",
                cancelButtonText: "取消",
                showLoaderOnConfirm: !0,
                confirmButtonClass: "btn btn-confirm mt-2",
                cancelButtonClass: "btn btn-cancel ml-2 mt-2",
                preConfirm: function(t) {
                    return new Promise(function(n, e) {
                        setTimeout(function() {
                            "3494490@qq.com" === t ? e("这封电子邮件已经收到了。") : n()
                        },
                        2e3)
                    })
                },
                allowOutsideClick: !1
            }).then(function(t) {
                swal({
                    type: "成功",
                    title: "AJAX请求完成！",
                    html: "提交电子邮件： " + t,
                    confirmButtonClass: "btn btn-confirm mt-2"
                })
            })
        }),
        t("#contack_us,#about_us,#help").on("click",
                function() {
                    swal({
                        title: "陕西孜晟新能源技术服务有限公司<br>王工：029-89551651<br>邮箱：sxzsxny@seasonts.com",
                        confirmButtonClass: "btn btn-confirm mt-2"
                    }).
                    catch(swal.noop)
                })
    },
    t.SweetAlert = new n,
    t.SweetAlert.Constructor = n
} (window.jQuery),
function(t) {
    "使用严格";
    t.SweetAlert.init()
} (window.jQuery);