$(document).ready( function() {
    $('#tasksTable').DataTable({
        dom: 'frtip',
        columnDefs: [
            { orderable: false, targets: 8 },
            { searchPanes: { show: false },
                targets: "_all"
            }
        ]
    });

    $('#clientTasksTable').DataTable();
});


