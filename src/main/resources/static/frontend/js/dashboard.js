document.addEventListener("DOMContentLoaded", loadDashboard);

async function loadDashboard() {

    try {

        const stats = await getDashboardStats();

        document.getElementById("customerCount").innerText = stats.customerCount;
        document.getElementById("parcelCount").innerText = stats.parcelCount;
        document.getElementById("shipmentCount").innerText = stats.shipmentCount;
        document.getElementById("deliveryCount").innerText = stats.deliveryCount;

        const shipments = await fetchShipments();
        renderShipments(shipments);

    } catch (err) {
        console.log(err);
    }
}

function renderShipments(data) {

    const table = document.getElementById("recentShipments");

    if (!data.length) {
        table.innerHTML = `<tr><td colspan="5">No Shipments Found</td></tr>`;
        return;
    }

    table.innerHTML = data.slice(0, 5).map(s => `
        <tr>
            <td>${s.shipmentId}</td>
            
            <td>${s.status || "Pending"}</td>
            <td>${s.expectedDelivery}</td>
        </tr>
    `).join('');
}