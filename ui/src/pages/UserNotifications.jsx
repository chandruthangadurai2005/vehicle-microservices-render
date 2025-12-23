import { useEffect, useState } from "react";
import api from "../api/api";

export default function UserNotifications() {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    const customerId = localStorage.getItem("customerId");

    api.get("/notification/user", {
      headers: {
        "X-CUSTOMER-ID": customerId
      }
    })
    .then(res => setNotifications(res.data))
    .catch(err => console.error(err));
  }, []);

  return (
    <div>
      <h2>🔔 Notifications</h2>

      {notifications.length === 0 && <p>No notifications</p>}

      <ul>
        {notifications.map(n => (
          <li key={n.id}>
            {n.message} ({n.type})
          </li>
        ))}
      </ul>
    </div>
  );
}
