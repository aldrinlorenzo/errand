// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

// Pie Chart Example
var ctx = document.getElementById("myPieChart");
var pendingTasks = parseInt(document.getElementById("pendingTasks").textContent);
var ongoingTasks= parseInt(document.getElementById("ongoingTasks").textContent);
var completedTasks = parseInt(document.getElementById("completedTasks").textContent);
var cancelledTasks= parseInt(document.getElementById("cancelledTasks").textContent);
var myPieChart = new Chart(ctx, {
  type: 'doughnut',
  data: {
    labels: ["Pending", "Ongoing", "Completed", "Cancelled"],
    datasets: [{
      data: [pendingTasks, ongoingTasks, completedTasks, cancelledTasks],
      backgroundColor: ['#858796 ', '#f6c23e', '#1cc88a', '#e74a3b'],
      hoverBackgroundColor: ['#60616f', '#dda20a','#13855c', '#be2617'],
      hoverBorderColor: "rgba(234, 236, 244, 1)",
    }],
  },
  options: {
    maintainAspectRatio: false,
    tooltips: {
      backgroundColor: "rgb(255,255,255)",
      bodyFontColor: "#858796",
      borderColor: '#dddfeb',
      borderWidth: 1,
      xPadding: 15,
      yPadding: 15,
      displayColors: false,
      caretPadding: 10,
    },
    legend: {
      display: false
    },
    cutoutPercentage: 80,
  },
});
