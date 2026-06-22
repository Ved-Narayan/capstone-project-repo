const BASE_URL = "http://localhost:8089/api";


async function fetchCustomers() {
    const res = await fetch(BASE_URL + "/customers");
    return res.ok ? await res.json() : [];
}


async function fetchParcels() {
    const res = await fetch(BASE_URL + "/parcels/getAllParcel");
    return res.ok ? await res.json() : [];
}

async function fetchParcelsByCustomer(customerId) {
    const res = await fetch(BASE_URL + "/parcels/customer/" + customerId);
    return res.ok ? await res.json() : [];
}


async function fetchShipments() {
    const res = await fetch(BASE_URL + "/shipments/getAllShipment");
    return res.ok ? await res.json() : [];
}


async function fetchDeliveries() {
    const res = await fetch(BASE_URL + "/deliveries/getAllDelivery");
    return res.ok ? await res.json() : [];
}


async function fetchTrackings() {
    const res = await fetch(BASE_URL + "/trackings");
    return res.ok ? await res.json() : [];
}


async function fetchTrackingByShipmentId(id) {
    const res = await fetch(BASE_URL + "/trackings/shipment/" + id);
    if (!res.ok) throw new Error("Tracking not found");
    return await res.json();
}


async function fetchTracking(trackingNumber) {
    const res = await fetch(BASE_URL + "/trackings/track/" + trackingNumber);
    if (!res.ok) throw new Error("Tracking not found");
    return await res.json();
}


async function getDashboardStats() {

    const [c, p, s, d] = await Promise.all([
        fetchCustomers(),
        fetchParcels(),
        fetchShipments(),
        fetchDeliveries()
    ]);

    return {
        customerCount: c.length,
        parcelCount: p.length,
        shipmentCount: s.length,
        deliveryCount: d.length
    };
}