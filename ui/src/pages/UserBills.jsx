import { useEffect, useState } from "react";
import api from "../api/api";

export default function UserBills() {
  const [bills, setBills] = useState([]);

  const loadBills = async () => {
    try {
      const res = await api.get("/billing/my", {
        headers: {
          "X-CUSTOMER-ID": localStorage.getItem("customerId")
        }
      });
      setBills(Array.isArray(res.data) ? res.data : []);
    } catch (err) {
      console.error("Failed to load bills", err);
    }
  };

  useEffect(() => {
    loadBills();
  }, []);

  const payBill = async (id) => {
    try {
      await api.put(`/billing/${id}/pay`);
      loadBills(); // refresh after pay
    } catch (err) {
      console.error("Payment failed", err);
    }
  };

  return (
    <div className="page">
      <h2>My Bills</h2>

      {bills.length === 0 && <p>No bills found</p>}

      {bills.map(b => (
        <div key={b.id} className="card">
          <p><b>Amount:</b> ₹{b.amount}</p>
          <p><b>Status:</b> {b.status}</p>

          {b.status !== "PAID" && (
            <button className="secondary" onClick={() => payBill(b.id)}>
              Pay Now
            </button>
          )}
        </div>
      ))}
    </div>
  );
}
