import { useEffect, useState } from "react";
import api from "../api/api";

const AdminCustomers = () => {
  const [customers, setCustomers] = useState([]);

  useEffect(() => {
    const fetchCustomers = async () => {
      try {
        const res = await api.get("/customer");

        console.log("CUSTOMER RESPONSE:", res.data);

        // ✅ normalize response
        const list = Array.isArray(res.data)
          ? res.data
          : res.data.data;

        setCustomers(list || []);
      } catch (err) {
        console.error("Error fetching customers", err);
      }
    };

    fetchCustomers();
  }, []);

  return (
    <div>
      <h2>👥 Customers</h2>

      <table border="1" width="100%">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Address</th>
          </tr>
        </thead>

        <tbody>
          {customers.length === 0 ? (
            <tr>
              <td colSpan="5" align="center">
                No customers found
              </td>
            </tr>
          ) : (
            customers.map((c) => (
              <tr key={c.id}>
                <td>{c.id}</td>
                <td>{c.name}</td>
                <td>{c.email}</td>
                <td>{c.phone}</td>
                <td>{c.address}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default AdminCustomers;
