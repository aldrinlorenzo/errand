$(document).ready( function() {
    $('#tasksTable').DataTable({
        dom: 'Pfrtip',
        searchPanes: { },
        columnDefs: [
            { orderable: false, targets: 8 },
            { searchPanes: { show: false },
                targets: [0, 1, 2, 3, 4, 5, 6, 8]
            },
            { searchPanes: { show: true },
                targets: 7
            }
        ]
    });

    $('#clientTasksTable').DataTable();
});


