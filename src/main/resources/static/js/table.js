//document.addEventListener("DOMContentLoaded", function() {
//    const element = document.querySelector("#task\\.id");
//    element.addEventListener("click", updateTaskId);
//  });

function updateTaskId(event) {
//  const row = event.target.closest("tr");
//  const id = row.getAttribute("data-id");
//  task.id = id;
    let id = event.srcElement.parentNode.parentNode.parentNode.id;
    let header = $('#unique')[0].textContent = `${id}`;
    console.log(id);
}