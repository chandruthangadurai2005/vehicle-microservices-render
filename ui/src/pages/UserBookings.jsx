import { useEffect, useState } from "react";
import api from "../api/api";

export default function UserBookings() {
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
  const customerId = localStorage.getItem("customerId");
  if (!customerId) return;
 

  api.get("/booking/my", {
  headers: {
    "X-CUSTOMER-ID": customerId
  }
})

  .then(res => setBookings(res.data))
  .catch(err => console.error(err));
}, []);


  return (
    <div>
      <h2>My Service Requests</h2>

      {bookings.length === 0 && <p>No bookings found</p>}

      {bookings.map(b => (
        <div className="card">
        <div key={b.id}>
          <p>Service ID: {b.vehicleId}</p>

          <p>Status: {b.status}</p>
        </div>
        </div>
      ))}
    </div>
  );
}
