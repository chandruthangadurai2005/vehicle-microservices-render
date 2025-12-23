import { useEffect, useState } from "react";
import api from "../api/api";

export default function AdminBookings() {
  const [bookings, setBookings] = useState([]);

  const loadBookings = () => {
    api.get("/booking")
      .then(res => setBookings(res.data))
      .catch(err => console.error(err));
  };

  useEffect(() => {
    loadBookings();
  }, []);

  const updateStatus = async (id, status) => {
  try {
    await api.put(`/booking/${id}/status?status=${status}`);
    alert("Status updated");
    loadBookings();
  } catch (err) {
    console.error(err);
    alert("Failed to update status");
  }
};




  return (
    <div>
      <h2>All Service Requests</h2>

      {bookings.length === 0 && <p>No bookings found</p>}
   <div className="table-container">
  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Customer ID</th>
        <th>Vehicle ID</th>
        <th>Status</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      {bookings.map(b => (
        <tr key={b.id}>
          <td>{b.id}</td>
          <td>{b.customerId}</td>
          <td>{b.vehicleId}</td>

          <td className={`status-${b.status}`}>
            {b.status}
          </td>

          <td>
            <select
              value={b.status}
              onChange={(e) =>
                updateStatus(b.id, e.target.value)
              }
            >
              <option>PENDING</option>
              <option>APPROVED</option>
              <option>IN_PROGRESS</option>
              <option>COMPLETED</option>
              <option>REJECTED</option>
            </select>
          </td>
        </tr>
      ))}
    </tbody>
  </table>
</div>

    </div>
  );
}
