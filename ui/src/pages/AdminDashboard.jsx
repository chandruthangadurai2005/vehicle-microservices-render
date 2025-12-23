import { useEffect, useState } from "react";
import api from "../api/api";

export default function AdminDashboard() {
  const [vehicleCount, setVehicleCount] = useState(0);
  const [bookingCount, setBookingCount] = useState(0);
  const [customerCount, setCustomerCount] = useState(0);
  const [revenue, setRevenue] = useState(0);

  useEffect(() => {
    const loadDashboard = async () => {
      try {
        const [
          vehiclesRes,
          bookingsRes,
          customersRes,
          revenueRes
        ] = await Promise.all([
          api.get("/vehicle"),
          api.get("/booking"),
          api.get("/customer"),
          api.get("/billing/total")
        ]);

        setVehicleCount(vehiclesRes.data?.length || 0);
        setBookingCount(bookingsRes.data?.length || 0);
        setCustomerCount(customersRes.data?.length || 0);
        setRevenue(revenueRes.data || 0);

      } catch (err) {
        console.error("Failed to load admin dashboard", err);
      }
    };

    loadDashboard();
  }, []);

  return (
    <div className="page">
      <h2 className="page-title">Admin Dashboard</h2>

      <div className="dashboard-grid">
        <div className="dashboard-card">
          <h3>Vehicles</h3>
          <p className="dashboard-number">{vehicleCount}</p>
        </div>

        <div className="dashboard-card">
          <h3>Bookings</h3>
          <p className="dashboard-number">{bookingCount}</p>
        </div>

        <div className="dashboard-card">
          <h3>Customers</h3>
          <p className="dashboard-number">{customerCount}</p>
        </div>

        <div className="dashboard-card highlight">
          <h3>Revenue</h3>
          <p className="dashboard-number">₹{revenue}</p>
        </div>
      </div>
    </div>
  );
}
