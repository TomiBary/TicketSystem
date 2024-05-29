// Enable tooltips
const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
const modalBody = document.getElementById('modal-body');
const baseUrl = "http://localhost:8080"; // Replace with your actual base URL
const ticketsTable = document.querySelector('#tickets-table');
const countdown = document.getElementById('countdown');

const updateDelaySeconds = 10
const updateIntervalSeconds = 0.01
let timeRemaining = updateDelaySeconds
let isPaused = false

function fetchFromApi(path, resultAction){
    fetch(`${baseUrl}/${path}`, {
        mode: 'no-cors',
        // method: 'GET',
    })
        .then(response => response.json())
        .then(result => {
            console.log(result)
            resultAction(result);
        })
        .catch(error => {
            console.error("Error fetching tickets:", error);
        });
}

const updateCountdown = () => {
    if(isPaused) return
    if (timeRemaining <= 0) {
        // Countdown finished
        createTicket()
        timeRemaining = updateDelaySeconds;
    } else {
        timeRemaining = timeRemaining - updateIntervalSeconds;
        if(timeRemaining < 0) {
            timeRemaining = 0
        }
    }
    countdown.textContent = timeRemaining.toFixed(3) + ' s';
};

function displayTickets(tickets) {
    ticketsTable.innerHTML = "";
    tickets.forEach(data => {
            const row = document.createElement('tr'); // Create a table row
            for (const key in data) {
                const cell = document.createElement('td'); // Create a table cell
                cell.textContent = data[key]; // Set cell content with data value
                row.appendChild(cell); // Append cell to the row
            }
            ticketsTable.appendChild(row);
        })
}

function getAllTickets() {
    fetchFromApi("all", tickets => {
        displayTickets(tickets);
    });
}

function createTicket() {
    fetchFromApi("create", ticket => {
        getAllTickets()
    });
}

function getFirstTicket() {
    modalBody.innerText = "Loading..."
    fetchFromApi("first", ticket => {
        modalBody.innerHTML = `
        Ticket ID: ${ticket.id}<br>
        Creation Date & Time: ${new Date(ticket.creationDateTime).toLocaleString()}<br>
        Order: ${ticket.order}
        `;
    });
}

function deleteFirstTicket() {
    fetchFromApi("deleteFirst", ticket => {
        alert('Deleted: ' + JSON.stringify(ticket))
        getAllTickets()
    });
}

function deleteLastTicket() {
    fetchFromApi("deleteLast", tickets => {
        getAllTickets()
    });
}

document.querySelector('#create-ticket-btn').onclick = createTicket;
document.querySelector('#delete-last-ticket-btn').onclick = deleteLastTicket;
document.getElementById('get-first-btn').onclick = getFirstTicket;
document.getElementById('delete-first-btn').onclick = deleteFirstTicket;
const timerPauseBtn = document.getElementById('timer-pause');

timerPauseBtn.onclick = () => {
    isPaused = !isPaused
    timerPauseBtn.classList.replace(isPaused ? 'fa-pause' : 'fa-play', isPaused ? 'fa-play' : 'fa-pause');
}

setInterval(updateCountdown, updateIntervalSeconds * 1000)
getAllTickets()