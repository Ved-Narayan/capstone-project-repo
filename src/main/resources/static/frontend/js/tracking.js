document.addEventListener("DOMContentLoaded", () => {
    setupTracking();
});

function setupTracking() {

    const btn = document.querySelector(".btn-primary");
    const input = document.getElementById("trackingInput");

    btn.addEventListener("click", searchTracking);

    input.addEventListener("keypress", (e) => {
        if (e.key === "Enter") searchTracking();
    });
}
document.addEventListener("DOMContentLoaded", () => {
    loadAllTracking();
    setupTracking();
});

async function loadAllTracking() {

    try {

        const res = await fetch(BASE_URL + "/admin/trackings");
        const data = await res.json();

        const table = document.getElementById("trackingTable");

        if (!table) return;

        table.innerHTML = data.map(t => `
            <tr>
                <td>${t.trackingId ?? "-"}</td>
                <td>${t.trackingNumber ?? "-"}</td>
                <td>${t.status ?? "-"}</td>
                <td>${t.timestamp ?? "-"}</td>
                <td>${t.shipmentId ?? "-"}</td>
            </tr>
        `).join('');

    } catch (err) {
        console.error("Tracking load error:", err);
    }
}

async function searchTracking() {

    const trackingNumber = document.getElementById("trackingInput").value;

    if (!trackingNumber) return;

    const res = await fetch(BASE_URL + "/trackings/track/" + trackingNumber);
    const data = await res.json();

    displayTracking(data);
}


function displayTracking(data) {

    document.getElementById("trackingResult").style.display = "block";

    document.getElementById("trackNum").innerText =
        data.trackingNumber ?? "N/A";

    document.getElementById("trackDest").innerText =
        data.currentLocation ?? "-";

    const status = data.status ?? "UNKNOWN";

    const statusEl = document.getElementById("trackStatus");

    let color = "gray";

    if (status === "BOOKED") color = "blue";
    else if (status === "IN_TRANSIT") color = "orange";
    else if (status === "DELIVERED") color = "green";

    statusEl.innerHTML = `
        <span style="color:${color}; font-weight:bold;">
            ${status}
        </span>
    `;

    renderTimeline(status);
}


function renderTimeline(status) {

    const steps = ["BOOKED", "IN_TRANSIT", "OUT_FOR_DELIVERY", "DELIVERED"];

    let html = "";

    steps.forEach(step => {

        let style = "opacity:0.4";

        if (step === status) {
            style = "font-weight:bold; color:#000";
        }

        if (steps.indexOf(step) < steps.indexOf(status)) {
            style = "opacity:1; color:green";
        }

        html += `<div style="${style}">✔ ${step}</div>`;
    });

    document.getElementById("trackingTimeline").innerHTML = html;
}
document.addEventListener("DOMContentLoaded", () => {

    const dateInput = document.getElementById("deliveredDate");

    if (!dateInput) return;

    const today = new Date().toISOString().split("T")[0];

    dateInput.setAttribute("min", today);
});