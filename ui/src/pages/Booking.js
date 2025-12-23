import { useSearchParams, useNavigate } from "react-router-dom";
import { useState } from "react";
import api from "../api/api";

export default function Booking() {
  const [params] = useSearchParams();
  const vehicleId = params.get("serviceId");
  const navigate = useNavigate();

  const [bookingDate, setBookingDate] = useState("");

  const submitBooking = async () => {
    try {
      const customerId = localStorage.getItem("customerId");

      if (!customerId) {
        alert("Please login again");
        navigate("/login");
        return;
      }

      if (!bookingDate) {
        alert("Please select a date");
        return;
      }

       await api.post("/booking", {
  vehicleId: Number(vehicleId),
  customerId: Number(customerId),
  bookingDate
});
      alert("Service booked successfully");
      navigate("/user/bookings");

    } catch (err) {
      console.error(err);
      alert("Booking failed");
    }
  };

  if (!vehicleId) {
    return <p>No service selected</p>;
  }

  return (
    <div>
      <h2>Request Service</h2>

      <p><b>Service ID:</b> {vehicleId}</p>

      <label>
        Preferred Date:
        <input
          type="date"
          value={bookingDate}
          onChange={e => setBookingDate(e.target.value)}
        />
      </label>

      <br /><br />

      <button onClick={submitBooking}>
        Submit Request
      </button>
    </div>
  );
}
