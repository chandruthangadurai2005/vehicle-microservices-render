import { useEffect, useState } from "react";
import api from "../api/api";

export default function AdminBilling() {
  const [bills, setBills] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchBills = async () => {
      try {
        const res = await api.get("/billing");
        console.log("BILLING RESPONSE:", res.data);
        setBills(Array.isArray(res.data) ? res.data : []);
      } catch (err) {
        console.error("Error fetching billing data", err);
      } finally {
        setLoading(false);
      }
    };

    fetchBills();
  }, []);

  if (loading) return <p>Loading billing records...</p>;

  return (
    <div className="page">
      <h2>💳 Billing</h2>

      <table className="table">
        <thead>
          <tr>
            <th>Bill ID</th>
            <th>Booking ID</th>
            <th>Amount</th>
            <th>Status</th>
          </tr>
        </thead>

        <tbody>
          {bills.length === 0 ? (
            <tr>
              <td colSpan="4" align="center">No billing records found</td>
            </tr>
          ) : (
            bills.map(b => (
              <tr key={b.id}>
                <td>{b.id}</td>
                <td>{b.bookingId}</td>
                <td>₹{b.amount}</td>
                <td>{b.status}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}
