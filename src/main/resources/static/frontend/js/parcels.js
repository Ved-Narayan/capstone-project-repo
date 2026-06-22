document.addEventListener("DOMContentLoaded", loadParcels);

async function loadParcels() {

    const data = await fetchParcels();
    const table = document.getElementById("parcelsTable");

    if (!table) return;

    if (!data || data.length === 0) {
        table.innerHTML = `<tr><td colspan="5">No Parcels Found</td></tr>`;
        return;
    }

    table.innerHTML = data.map(p => `
        <tr>

            <td>${p.parcelId ?? "-"}</td>

            <td>${p.type ?? "-"}</td>

            <td>${p.weight ?? "-"}</td>



            <td>${p.bookingDate ?? "-"}</td>

        </tr>
    `).join('');
}