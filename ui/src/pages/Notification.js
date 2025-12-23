import { useEffect, useState } from "react";
import api from "../api/api";

function Notification() {
  const [data, setData] = useState([]);

  useEffect(() => {
    api.get("/notification")
      .then(res => setData(res.data))
      .catch(() => alert("Unauthorized"));
  }, []);

  return (
    <div>
      <h2>Notifications</h2>
      <pre>{JSON.stringify(data, null, 2)}</pre>
    </div>
  );
}

export default Notification;
