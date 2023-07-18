/* 관리자 로그 차트 */
$.ajax({
    url: "/bo/system/log/admin/logList",
    type: 'get',
    dataType: 'json',
    success: function (data) {

        let urlList = [];
        let cntList = [];
        let colorList = [];

        for (let i = 0; i < data.data.length; i++) {
            urlList.push(data.data[i].url);
            cntList.push(data.data[i].count);
            colorList.push(colorize());
        }

        function colorize() {
            let r = Math.floor(Math.random() * 200);
            let g = Math.floor(Math.random() * 200);
            let b = Math.floor(Math.random() * 200);
            return 'rgba(' + r + ', ' + g + ', ' + b + ', 0.7)';
        }

        let ctx = document.getElementById('myChart').getContext('2d');

        let myChart = new Chart(ctx, {

            type: 'doughnut',
            data: {
                labels: urlList,
                datasets: [{
                    label: urlList,
                    data: cntList,
                    backgroundColor: colorList
                }],
            },
            options: {
                plugins: {
                    legend: {
                        display: false
                    },
                }
            }
        });
    }
});

/* [SEARCH] 버튼 클릭 시 */
$("button[data-role=search]").click(function () {
    let id = $("#adminId").val().trim();
    console.log(id);

    let param = {
        adminId: id
    }
    $.ajax({
        type: "get",
        url: "/bo/system/log/admin/search",
        data: param,
        success: function (html) {
            $("#loginLogList").empty();
            $("#adminList").empty();
            $("#adminList").append(html);
        },
        error: function () {

        }
    });
});

/* admin-list 테이블 클릭 시 */
$(document).on("click", "tr[data-role=loginLog]", function () {

    let id = $(this).data("adminId");
    console.log("clicked ID >>>> " + id);

    let param = {
        adminId: id
    }
    $.ajax({
        type: "get",
        url: "/bo/system/log/admin/loginLog",
        data: param,
        success: function (html) {
            $("#loginLogList").empty();
            $("#loginLogList").append(html);
        },
    });
});