!
function(o) {
    "use strict";
    var r = function() {};
    r.prototype.respChart = function(r, t, e, i) {
        var a = r.get(0).getContext("2d"),
        l = o(r).parent();
        function n() {
            r.attr("width", o(l).width());
            switch (t) {
            case "Doughnut":
                new Chart(a, {
                    type: "doughnut",
                    data: e,
                    options: i
                });
                break;
            case "Bar":
                new Chart(a, {
                    type: "bar",
                    data: e,
                    options: i
                })
            }
        }
        o(window).resize(n),
        n()
    },
    r.prototype.init = function() {
        this.respChart(o("#bar"), "Bar", {
            labels: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"],
            datasets: [{
                label: "Sales Analytics",
                backgroundColor: "#00acc1",
                borderColor: "#00acc1",
                borderWidth: 1,
                hoverBackgroundColor: "#d4570f",
                hoverBorderColor: "#d4570f",
                data: [65, 59, 80, 81, 56, 89, 40, 32, 65, 59, 80, 81, 56, 89, 40, 32, 65, 59, 80, 81, 56, 89, 40, 32, 65, 59, 80, 81, 56, 89, 40]
            }]
        },
        {
            legend: {
                display: !1
            },
            scales: {
                yAxes: [{
                    gridLines: {
                        display: !1
                    },
                    ticks: {
                        max: 100,
                        min: 20,
                        stepSize: 20
                    }
                }],
                xAxes: [{
                    barPercentage: .3,
                    gridLines: {
                        color: "rgba(0,0,0,0.05)"
                    }
                }]
            }
        });
        this.respChart(o("#doughnut"), "Doughnut", {
            labels: ["Bitcoin", "Ethereum", "Cardano"],
            datasets: [{
                data: [80, 50, 100],
                backgroundColor: ["#02a8b5", "#f1556c", "#e3eaef"],
                hoverBackgroundColor: ["#02a8b5", "#f1556c", "#e3eaef"],
                borderWidth: 3,
                hoverBorderColor: "#fff"
            }]
        },
        {
            cutoutPercentage: 80,
            legend: {
                position: "bottom",
                labels: {
                    padding: 30
                }
            }
        })
    },
    o.ChartJs = new r,
    o.ChartJs.Constructor = r
} (window.jQuery),
function(o) {
    "use strict";
    o.ChartJs.init()
} (window.jQuery),
$(document).ready(function() {
    var o, r = function() {
        $("#sparkline1").sparkline([25, 23, 26, 24, 25, 32, 30, 24, 19], {
            type: "line",
            width: "100%",
            height: "80",
            chartRangeMax: 35,
            lineColor: "#f1556c",
            fillColor: "rgba(229, 43, 76, 0.3)",
            highlightLineColor: "rgba(0,0,0,.1)",
            highlightSpotColor: "rgba(0,0,0,.2)",
            maxSpotColor: !1,
            minSpotColor: !1,
            spotColor: !1,
            lineWidth: 1
        }),
        $("#sparkline2").sparkline([0, 23, 43, 35, 44, 45, 56, 37, 40], {
            type: "line",
            width: "100%",
            height: "80",
            chartRangeMax: 50,
            lineColor: "#00acc1",
            fillColor: "rgba(0, 172, 193, 0.2)",
            highlightLineColor: "rgba(0,0,0,.1)",
            highlightSpotColor: "rgba(0,0,0,.2)",
            maxSpotColor: !1,
            minSpotColor: !1,
            spotColor: !1,
            lineWidth: 1
        }),
        $("#sparkline3").sparkline([25, 23, 26, 24, 25, 32, 30, 24, 19], {
            type: "line",
            width: "100%",
            height: "80",
            chartRangeMax: 35,
            lineColor: "#f1556c",
            fillColor: "rgba(229, 43, 76, 0.3)",
            highlightLineColor: "rgba(0,0,0,.1)",
            highlightSpotColor: "rgba(0,0,0,.2)",
            maxSpotColor: !1,
            minSpotColor: !1,
            spotColor: !1,
            lineWidth: 1
        }),
        $("#sparkline4").sparkline([0, 23, 43, 35, 44, 45, 56, 37, 40], {
            type: "line",
            width: "100%",
            height: "80",
            chartRangeMax: 50,
            lineColor: "#00acc1",
            fillColor: "rgba(0, 172, 193, 0.2)",
            highlightLineColor: "rgba(0,0,0,.1)",
            highlightSpotColor: "rgba(0,0,0,.2)",
            maxSpotColor: !1,
            minSpotColor: !1,
            spotColor: !1,
            lineWidth: 1
        })
    };
    r(),
    $(window).resize(function(t) {
        clearTimeout(o),
        o = setTimeout(function() {
            r(),
            DrawMouseSpeed()
        },
        300)
    })
});