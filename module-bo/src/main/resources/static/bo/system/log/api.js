/* API 로그 차트 */
$.ajax({
    url: "/bo/system/log/api/list",
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
                // 라벨숨김
                plugins: {
                    legend: {
                        display: false
                    },
                }
            }
        });
    }
});