$(document).ready( function() {
    $('#tasksTable').DataTable({
        dom: 'frtip',
        searchPanes: { },
        columnDefs: [
            { orderable: false, targets: 8 },
            { searchPanes: { show: false },
                targets: "_all"
            }
            /*{ searchPanes: { show: true },
                targets: 7
            }*/
        ]
    });
});