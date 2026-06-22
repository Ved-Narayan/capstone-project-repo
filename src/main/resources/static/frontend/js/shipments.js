document.addEventListener("DOMContentLoaded", () => {
    loadShipments();
    setupForm();
    setMinDate();
});


async function loadShipments() {

    try {

        const res = await fetch(BASE_URL + "/shipments/getAllShipment");
        const data = await res.json();

        const table = document.getElementById("shipmentsTable");

        if (!table) return;

        if (!data || data.length === 0) {
            table.innerHTML = `<tr><td colspan="5">No Shipments Found</td></tr>`;
            return;
        }

        table.innerHTML = data.map(s => `

            <tr>

                <td>${s.shipmentId}</td>

                <td>${s.parcelId ?? "-"}</td>

                <td>
                    <select onchange="updateStatus(${s.shipmentId}, this.value)">

                        <option value="BOOKED"
                            ${s.status === "BOOKED" ? "selected" : ""}
                            ${["IN_TRANSIT", "OUT_FOR_DELIVERY", "DELIVERED"].includes(s.status) ? "disabled" : ""}>
                            Booked
                        </option>

                        <option value="IN_TRANSIT"
                            ${s.status === "IN_TRANSIT" ? "selected" : ""}
                            ${["OUT_FOR_DELIVERY", "DELIVERED"].includes(s.status) ? "disabled" : ""}>
                            In Transit
                        </option>

                        <option value="OUT_FOR_DELIVERY"
                            ${s.status === "OUT_FOR_DELIVERY" ? "selected" : ""}
                            ${["DELIVERED"].includes(s.status) ? "disabled" : ""}>
                            Out for Delivery
                        </option>

                        <option value="DELIVERED"
                            ${s.status === "DELIVERED" ? "selected" : ""}>
                            Delivered
                        </option>

                    </select>
                </td>

                <td>${s.expectedDelivery ?? "-"}</td>

                <td>
                    <button onclick="deleteShipment(${s.shipmentId})">
                        Delete
                    </button>
                </td>

            </tr>

        `).join('');

    } catch (err) {
        console.error(err);
    }
}


async function updateStatus(id, status) {

    await fetch(BASE_URL + "/trackings/shipment/" + id, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            status: status
        })
    });

    loadShipments();
}


function setupForm() {

    const form = document.getElementById("shipmentForm");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const payload = {
            parcelId: document.getElementById("parcelId").value,
            expectedDelivery: document.getElementById("expectedDelivery").value
        };

        await fetch(BASE_URL + "/shipments/addShipment", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        closeModal();
        form.reset();
        setMinDate();
        loadShipments();
    });
}


async function deleteShipment(id) {

    await fetch(BASE_URL + "/shipments/deleteShip/" + id, {
        method: "DELETE"
    });

    loadShipments();
}


function openModal() {
    document.getElementById("shipmentModal").style.display = "flex";
}

function closeModal() {
    document.getElementById("shipmentModal").style.display = "none";
}


function setMinDate() {
    const input = document.getElementById("expectedDelivery");
    const today = new Date().toISOString().split("T")[0];
    input.setAttribute("min", today);
}