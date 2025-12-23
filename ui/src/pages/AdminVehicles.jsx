import { useEffect, useState } from "react";
import api from "../api/api";

export default function AdminVehicles() {
  const [vehicles, setVehicles] = useState([]);

  useEffect(() => {
    api.get("/vehicle")
      .then(res => setVehicles(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <div>
      <h2>Vehicle List</h2>

      <table border="1" width="100%" cellPadding="8">
        <thead>
          <tr>
            <th>ID</th>
            <th>Brand</th>
            <th>Model</th>
            <th>Fuel</th>
            <th>Vehicle Number</th>
            <th>Year</th>
          </tr>
        </thead>
        <tbody>
          {vehicles.map(v => (
            <tr key={v.id}>
              <td>{v.id}</td>
              <td>{v.brand}</td>
              <td>{v.model}</td>
              <td>{v.fuelType}</td>
              <td>{v.vehicleNumber}</td>
              <td>{v.year}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
