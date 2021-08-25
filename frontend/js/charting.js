const makeChart = ()=>{
    // setup
    const labels = [
        'January',
        'February',
        'March',
        'April',
        'May',
        'June',
    ];
    const data = {
        labels: labels,
        datasets: [{
            label: 'My Data A',
            backgroundColor: 'rgb(255, 99, 132)',
            borderColor: 'rgb(99, 132, 255)',
            data: [0, 10, 5, 2, 20, 30, 45],
        }, {
            label: 'My Data B',
            backgroundColor: 'rgb(99, 132, 255)',
            borderColor: 'rgb(255, 99, 132)',
            data: [10, 5, 2, 20, 30, 45, 0],
        }]
    };
    // config
    const config = {
        type: 'line',
        data,
        options: {}
    };
    
    // and here is the chart
    let myChart = new Chart('myChart', config);
}

// get some API static data
let results={}
// fetch('http://localhost:8080/api/static_data.json')
//         .then(response => response.json())
//         .then(data => {
//             results = data
//             console.log(results['data'])
//             makeChart()
//         })
makeChart()