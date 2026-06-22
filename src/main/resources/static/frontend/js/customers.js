let allCustomers = [];
let editingCustomerId = null;

document.addEventListener("DOMContentLoaded", () => {
    loadCustomers();
    setupCustomerForm();
    setupSearch();
    setupUpdateForm();
});



async function loadCustomers() {
    try {
        const data = await fetchCustomers();

        allCustomers = data || [];
        renderCustomers(allCustomers);

    } catch (err) {
        console.error("Load error:", err);
    }
}



function renderCustomers(data) {

    const table = document.getElementById("customersTable");

    if (!table) return;

    if (!data || data.length === 0) {
        table.innerHTML = `<tr><td colspan="6">No Customers Found</td></tr>`;
        return;
    }

    table.innerHTML = data.map(c => `
        <tr style="cursor:pointer"
            onclick="openCustomerParcels(${c.customerId})">

            <td>${c.customerId ?? "-"}</td>
            <td>${c.name ?? "-"}</td>
            <td>${c.email ?? "-"}</td>
            <td>${c.phone ?? "-"}</td>
            <td>${c.defaultAddress ?? "-"}</td>

            <td>
                <button onclick="event.stopPropagation(); openUpdateModal(
                    ${c.customerId},
                    \`${c.name ?? ""}\`,
                    \`${c.email ?? ""}\`,
                    \`${c.phone ?? ""}\`,
                    \`${c.defaultAddress ?? ""}\`
                )">
                    Update
                </button>

                <button onclick="event.stopPropagation(); deleteCustomer(${c.customerId})">
                    Delete
                </button>
            </td>
        </tr>
    `).join('');
}



function setupCustomerForm() {

    const form = document.getElementById("customerForm");
    if (!form) return;

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const payload = {
            name: document.getElementById("customerName").value,
            email: document.getElementById("customerEmail").value,
            phone: document.getElementById("customerPhone").value,
            defaultAddress: document.getElementById("customerLocation").value
        };

        await fetch(BASE_URL + "/customers/addCust", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        closeCustomerModal();
        form.reset();
        loadCustomers();
    });
}



async function deleteCustomer(id) {

    try {
        await fetch(BASE_URL + "/customers/deleteCust/" + id, {
            method: "DELETE"
        });

        loadCustomers();
    } catch (err) {
        console.error(err);
    }
}



function setupSearch() {

    const searchInput = document.getElementById("searchCustomers");
    if (!searchInput) return;

    searchInput.addEventListener("input", (e) => {

        const val = e.target.value.toLowerCase();

        const filtered = allCustomers.filter(c =>
            (c.name ?? "").toLowerCase().includes(val) ||
            (c.email ?? "").toLowerCase().includes(val) ||
            (c.phone ?? "").toLowerCase().includes(val) ||
            (c.defaultAddress ?? "").toLowerCase().includes(val)
        );

        renderCustomers(filtered);
    });
}



async function openCustomerParcels(customerId) {

    if (!customerId) {
        alert("Invalid customer selected");
        return;
    }

    try {
        const res = await fetch(BASE_URL + "/parcels/customer/" + customerId);

        if (!res.ok) {
            throw new Error("HTTP Error: " + res.status);
        }

        const data = await res.json();

        const table = document.getElementById("parcelTable");

        if (!table) return;

        
        table.innerHTML = "";

        if (!data || data.length === 0) {
            table.innerHTML = `<tr><td colspan="5">No Parcels Found</td></tr>`;
        } else {
            table.innerHTML = data.map(p => `
                <tr>
                    <td>${p.parcelId ?? "-"}</td>
                    <td>${p.weight ?? "-"}</td>
                    <td>${p.type ?? "-"}</td>
                    <td>${p.status ?? "-"}</td>
                    <td>${p.fromAddress ?? "-"} → ${p.toAddress ?? "-"}</td>
                </tr>
            `).join('');
        }

        document.getElementById("parcelModal").style.display = "flex";

    } catch (err) {
        console.error("Parcel load error:", err);
        alert("Failed to load parcels (check backend URL)");
    }
}


function openUpdateModal(id, name, email, phone, defaultAddress) {

    editingCustomerId = id;

    document.getElementById("updateCustomerName").value = name;
    document.getElementById("updateCustomerEmail").value = email;
    document.getElementById("updateCustomerPhone").value = phone;
    document.getElementById("updateCustomerLocation").value = defaultAddress;

    document.getElementById("updateCustomerModal").style.display = "flex";
}



function setupUpdateForm() {

    const form = document.getElementById("updateCustomerForm");
    if (!form) return;

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const payload = {
            name: document.getElementById("updateCustomerName").value,
            email: document.getElementById("updateCustomerEmail").value,
            phone: document.getElementById("updateCustomerPhone").value,
            defaultAddress: document.getElementById("updateCustomerLocation").value
        };

        await fetch(BASE_URL + "/customers/updateCust/" + editingCustomerId, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        closeUpdateCustomerModal();
        loadCustomers();
    });
}



function openCustomerModal() {
    document.getElementById("customerModal").style.display = "flex";
}

function closeCustomerModal() {
    document.getElementById("customerModal").style.display = "none";
}

function closeUpdateCustomerModal() {
    document.getElementById("updateCustomerModal").style.display = "none";
}



function closeParcelModal() {
    document.getElementById("parcelModal").style.display = "none";
}