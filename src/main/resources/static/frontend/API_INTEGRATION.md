# API Integration Guide

## Overview

All data in this courier tracking application is pulled exclusively from the backend API. No hardcoded or demo data is used in the application.

## API Configuration

The API configuration is defined in `/frontend/js/api.js`:

```javascript
const BASE_URL = "http://localhost:8089/api";
const AUTH = "Basic " + btoa("admin:admin123");
```

**Important**: Update these values with your actual API server configuration.

## API Endpoints

### Shipments Endpoints

#### Get All Shipments
- **Method**: GET
- **Endpoint**: `/shipments/getAllShipment`
- **Headers**: `Authorization: Basic {credentials}`
- **Function**: `fetchShipments()`
- **Returns**: Array of shipment objects

#### Create Shipment
- **Method**: POST
- **Endpoint**: `/shipments/addShipment`
- **Headers**: `Authorization: Basic {credentials}`, `Content-Type: application/json`
- **Function**: `createShipment(shipmentData)`
- **Body**: `{ source, destination, weight, status, expectedDelivery }`

#### Update Shipment
- **Method**: PUT
- **Endpoint**: `/shipments/updateShipment/{id}`
- **Function**: `updateShipment(id, shipmentData)`

#### Delete Shipment
- **Method**: DELETE
- **Endpoint**: `/shipments/deleteShipment/{id}`
- **Function**: `deleteShipment(id)`

### Parcels Endpoints

#### Get All Parcels
- **Method**: GET
- **Endpoint**: `/parcels/getAllParcel`
- **Headers**: `Authorization: Basic {credentials}`
- **Function**: `fetchParcels()`
- **Returns**: Array of parcel objects with fields: `parcelId`, `weight`, `type`, `status`, `bookingDate`

#### Create Parcel
- **Method**: POST
- **Endpoint**: `/parcels/addParcel`
- **Function**: `createParcel(parcelData)`

#### Delete Parcel
- **Method**: DELETE
- **Endpoint**: `/parcels/deleteParcel/{id}`
- **Function**: `deleteParcel(id)`

### Tracking Endpoints

#### Get All Trackings
- **Method**: GET
- **Endpoint**: `/trackings/getAllTracking`
- **Headers**: `Authorization: Basic {credentials}`
- **Function**: `fetchTracking(trackingNumber)`
- **Returns**: Array of tracking objects with fields: `trackingId`, `currentLocation`, `status`, `timestamp`
- **Note**: Can filter by tracking number or location

#### Create Tracking
- **Method**: POST
- **Endpoint**: `/trackings/addTracking`
- **Function**: `createTracking(trackingData)`

### Deliveries Endpoints

#### Get All Deliveries
- **Method**: GET
- **Endpoint**: `/deliveries/getAllDelivery`
- **Headers**: `Authorization: Basic {credentials}`
- **Function**: `fetchDeliveries()`
- **Returns**: Array of delivery objects with fields: `deliveryId`, `deliveredDate`, `receivedBy`, `status`

#### Create Delivery
- **Method**: POST
- **Endpoint**: `/deliveries/addDelivery`
- **Function**: `createDelivery(deliveryData)`

#### Delete Delivery
- **Method**: DELETE
- **Endpoint**: `/deliveries/deleteDelivery/{id}`
- **Function**: `deleteDelivery(id)`

### Customers Endpoints

#### Get All Customers
- **Method**: GET
- **Endpoint**: `/customers` or `/customers/getAllCustomers`
- **Headers**: `Authorization: Basic {credentials}`
- **Function**: `fetchCustomers()` or `fetchAllCustomers()`
- **Returns**: Array of customer objects with fields: `customerId`, `name`, `email`, `phone`, `address`

#### Create Customer
- **Method**: POST
- **Endpoint**: `/customers/addCust`
- **Function**: `createCustomer(customerData)`
- **Body**: `{ name, email, phone, address }`

#### Update Customer
- **Method**: PUT
- **Endpoint**: `/customers/updateCust/{id}`
- **Function**: `updateCustomer(id, customerData)`
- **Body**: `{ name, email, phone, address }`

#### Delete Customer
- **Method**: DELETE
- **Endpoint**: `/customers/deleteCust/{id}`
- **Function**: `deleteCustomer(id)`

## Page-to-API Mapping

### Login Page (index.html)
- **auth.js**: Stores login credentials
- API calls are made from other pages after successful login

### Dashboard (dashboard.html)
- **dashboard.js**: Calls `getDashboardStats()`
- Fetches counts from: `/customers`, `/parcels`, `/shipments`, `/deliveries`
- Displays recent shipments from `/shipments/getAllShipment`

### Shipments (shipments.html)
- **shipments.js**: Calls `fetchShipments()`
- Endpoint: `/shipments/getAllShipment`
- Fields mapped: `shipmentId`, `source`, `destination`, `weight`, `status`, `expectedDelivery`

### Tracking (tracking.html)
- **tracking.js**: Calls `fetchTracking(trackingNumber)`
- Endpoint: `/trackings/getAllTracking`
- Filters by tracking number or location
- Fields mapped: `trackingId`, `currentLocation`, `status`, `timestamp`

### Parcels (parcels.html)
- **parcels.js**: Calls `fetchParcels()`
- Endpoint: `/parcels/getAllParcel`
- Fields mapped: `parcelId`, `weight`, `type`, `status`, `bookingDate`

### Delivery (delivery.html)
- **delivery.js**: Calls `fetchDeliveries()`
- Endpoint: `/deliveries/getAllDelivery`
- Fields mapped: `deliveryId`, `deliveredDate`, `receivedBy`, `status`

### Customers (customers.html)
- **customers.js**: Calls `fetchCustomers()`, `createCustomer()`, `updateCustomer()`, `deleteCustomer()`
- Endpoints: `/customers`, `/customers/addCust`, `/customers/updateCust/{id}`, `/customers/deleteCust/{id}`
- Full CRUD operations

## Authentication

All API requests use **Basic Authentication** with Base64 encoding:

```javascript
const AUTH = "Basic " + btoa("admin:admin123");
```

The credentials are sent in the `Authorization` header of every API request.

## Error Handling

All API functions include try-catch blocks and throw errors if:
- The fetch request fails
- The server returns a non-200 response
- The response cannot be parsed as JSON

Pages handle errors by:
- Logging errors to console
- Displaying user-friendly error messages via `showAlert()`
- Returning empty arrays as fallback data

## Data Field Mapping

The application handles API responses with different field names:

### Shipments
- API field `shipmentId` → display as ID
- API field `source` → display as Origin
- API field `destination` → display as Destination
- API field `weight` → display as Weight
- API field `status` → display with badge
- API field `expectedDelivery` → display as Date

### Parcels
- API field `parcelId` → display as ID
- API field `weight` → display as Weight
- API field `type` → display as Type
- API field `status` → display with badge
- API field `bookingDate` → display as Date

### Tracking
- API field `trackingId` → display as Tracking Number
- API field `currentLocation` → display as Location
- API field `status` → display with badge
- API field `timestamp` → display as Time

### Deliveries
- API field `deliveryId` → display as ID
- API field `deliveredDate` → display as Date
- API field `receivedBy` → display as Driver/Receiver
- API field `status` → display with badge

### Customers
- API field `customerId` → display as ID
- API field `name` → display as Name
- API field `email` → display as Email
- API field `phone` → display as Phone
- API field `address` → display as Address

## Testing the API Integration

### Using the Application

1. Start your backend API server on `http://localhost:8089`
2. Open the application: `http://localhost:8888/frontend/index.html`
3. Login with credentials: `admin:admin123`
4. Navigate to different pages to see data being fetched from the API

### Testing with Console

Open browser Developer Tools (F12) and check the Console tab:
- API calls will appear as `fetch()` requests
- Errors will be logged with `[v0]` prefix
- Response data will be visible in the Network tab

### Testing Individual API Endpoints

Use curl or Postman:

```bash
# Get all shipments
curl -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" \
     http://localhost:8089/api/shipments/getAllShipment

# Get all customers
curl -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" \
     http://localhost:8089/api/customers

# Create a new customer
curl -X POST \
     -H "Content-Type: application/json" \
     -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" \
     -d '{"name":"John Doe","email":"john@example.com","phone":"123-456-7890","address":"123 Main St"}' \
     http://localhost:8089/api/customers/addCust
```

## Configuration for Production

Before deploying to production:

1. **Update BASE_URL**:
   ```javascript
   const BASE_URL = "https://your-production-api.com/api";
   ```

2. **Update AUTH credentials**:
   ```javascript
   const AUTH = "Basic " + btoa("your-username:your-password");
   ```

3. **Configure CORS** on your API server to allow requests from your frontend domain

4. **Use HTTPS** for all API communications

5. **Implement proper error handling** for production scenarios

## API Response Format

The application expects API responses in the following formats:

### Array Response (Most endpoints)
```json
[
  { "id": 1, "name": "Item 1", ... },
  { "id": 2, "name": "Item 2", ... }
]
```

### Object Response (Some endpoints)
```json
{
  "id": 1,
  "name": "Item 1",
  ...
}
```

### Wrapped Response (Alternative format)
```json
{
  "data": [
    { "id": 1, "name": "Item 1", ... }
  ]
}
```

The application handles all three formats automatically.

## Adding New API Endpoints

To add a new API endpoint:

1. **Add the function to `/frontend/js/api.js`**:
   ```javascript
   async function fetchNewData() {
       try {
           const response = await fetch(`${BASE_URL}/endpoint`, {
               headers: { Authorization: AUTH }
           });
           if (!response.ok) throw new Error('Failed');
           return await response.json();
       } catch (error) {
           console.error('[v0] Error:', error);
           throw error;
       }
   }
   ```

2. **Call it from the relevant page's JavaScript file**:
   ```javascript
   const data = await fetchNewData();
   displayData(data);
   ```

3. **Test it in the browser console**

## Troubleshooting

### "Failed to fetch" error
- Check that the API server is running on the correct URL
- Verify CORS is properly configured on the API server
- Check browser console for CORS errors

### "Authorization" header error
- Verify credentials in `api.js` are correct
- Check that the API expects Basic auth (not Bearer token)
- Ensure credentials are properly Base64 encoded

### Data not displaying
- Check browser console for API errors
- Verify API is returning data in expected format
- Check Network tab to see actual API response
- Ensure field names match what application expects

### 401/403 Unauthorized
- Check that credentials in `api.js` are correct
- Verify user has permission for the API endpoint
- Check if authentication token has expired

## Best Practices

1. **Always use the API functions** from `api.js` - never hardcode API calls in pages
2. **Handle errors gracefully** - don't let API errors crash the app
3. **Log meaningful errors** - use console.error for debugging
4. **Use consistent field mapping** - map API fields to display names consistently
5. **Implement loading states** - show feedback while API requests are pending
6. **Cache data appropriately** - consider caching frequently accessed data
7. **Validate input** - validate data before sending to API
8. **Use proper HTTP methods** - GET for reading, POST for creating, PUT for updating, DELETE for deleting
