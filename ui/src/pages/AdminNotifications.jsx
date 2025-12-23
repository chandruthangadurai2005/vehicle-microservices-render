import { useEffect, useState } from "react";
import api from "../api/api";

const AdminNotifications = () => {
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchNotifications = async () => {
      try {
        const res = await api.get("/notification");

        console.log("NOTIFICATION RESPONSE:", res.data);

        const list = Array.isArray(res.data)
          ? res.data
          : res.data.data;

        setNotifications(list || []);
      } catch (err) {
        console.error("Error fetching notifications", err);
      } finally {
        setLoading(false);
      }
    };

    fetchNotifications();
  }, []);

  if (loading) return <p>Loading notifications...</p>;

  return (
    <div>
      <h2 className="section-title">🔔 Notifications</h2>


      <table border="1" width="100%" cellPadding="8">
        <thead>
          <tr>
            <th>ID</th>
            <th>Customer ID</th>
            <th>Message</th>
            <th>Status</th>
            <th>Type</th>
          </tr>
        </thead>

        <tbody>
          {notifications.length === 0 ? (
            <tr>
              <td colSpan="5" align="center">
                No notifications found
              </td>
            </tr>
          ) : (
            notifications.map((n) => (
              <tr key={n.id}>
                <td>{n.id}</td>
               <td>{n.customerId}</td> 
                <td>{n.message}</td>
                <td>{n.status}</td>
                <td>{n.type}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default AdminNotifications;
