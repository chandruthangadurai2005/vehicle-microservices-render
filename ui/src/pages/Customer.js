import { useEffect, useState } from "react";
import api from "../api/api";

function Customer() {
  const [data, setData] = useState([]);

  useEffect(() => {
    api.get("/customer")
      .then(res => setData(res.data))
      .catch(() => alert("Unauthorized"));
  }, []);

  return (
    <div>
      <h2>Customers</h2>
      <pre>{JSON.stringify(data, null, 2)}</pre>
    </div>
  );
}

export default Customer;
