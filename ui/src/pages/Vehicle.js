import { useEffect, useState } from "react";
import api from "../api/api";

function Vehicle() {
  const [data, setData] = useState([]);

  useEffect(() => {
    api.get("/vehicle")
      .then((res) => setData(res.data))
      .catch(() => alert("Unauthorized"));
  }, []);

  return (
    <div>
      <h2>Vehicle List</h2>
      <pre>{JSON.stringify(data, null, 2)}</pre>
    </div>
  );
}

export default Vehicle;
