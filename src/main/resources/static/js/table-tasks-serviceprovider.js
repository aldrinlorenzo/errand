$(document).ready( function() {
    $('#tasksTable').DataTable({
        columnDefs: [
            { orderable: false, targets: 8 }
        ]
    });
});