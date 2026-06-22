document.addEventListener("DOMContentLoaded", loadDeliveries);

async function loadDeliveries() {

    const data = await fetchDeliveries();
    const table = document.getElementById("deliveryTable");

    if (!table) return;

    if (!data || data.length === 0) {
        table.innerHTML = `<tr><td colspan="3">No Deliveries Found</td></tr>`;
        return;
    }

    table.innerHTML = data.map(d => `
        <tr>
            <td>${d.deliveryId ?? "-"}</td>
            <td>${d.deliveredDate ?? "-"}</td>
            <td>${d.receivedBy ?? "-"}</td>
        </tr>
    `).join('');
}

function openDeliveryModal() {
    document.getElementById("deliveryModal").style.display = "flex";
}


function closeDeliveryModal() {
    document.getElementById("deliveryModal").style.display = "none";
}


document.addEventListener("DOMContentLoaded", () => {

    const form = document.getElementById("deliveryForm");

    if (!form) return;

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const payload = {
            deliveredDate: document.getElementById("deliveredDate").value,
            receivedBy: document.getElementById("receivedBy").value,
            shipmentId: document.getElementById("shipmentId").value
        };

        await fetch(BASE_URL + "/deliveries/addDelivery", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        closeDeliveryModal();
        form.reset();
        loadDeliveries(); 
    });
});
document.addEventListener("DOMContentLoaded", () => {

    const dateInput = document.getElementById("deliveredDate");

    if (!dateInput) return;

    const today = new Date().toISOString().split("T")[0];

    dateInput.setAttribute("min", today);
});