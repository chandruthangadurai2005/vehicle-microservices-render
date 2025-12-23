import { useEffect, useState } from "react";
import api from "../api/api";
import { useNavigate } from "react-router-dom";

const SERVICE_TYPES = [
  {
    type: "MINOR",
    title: "Minor Service",
    desc: "Oil check, basic inspection",
    price: "₹500"
  },
  {
    type: "NORMAL",
    title: "Normal Service",
    desc: "Oil change, brake check",
    price: "₹1000"
  },
  {
    type: "MAJOR",
    title: "Major Service",
    desc: "Full service & diagnostics",
    price: "₹2000"
  }
];

export default function UserVehicles() {
  const [vehicles, setVehicles] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
  api.get("/vehicle")   // ✅ keep this
    .then(res => setVehicles(res.data))
    .catch(err => console.error(err));
}, []);


  // 🔥 pick first vehicle silently (UI only)
  const defaultVehicleId = vehicles.length > 0 ? vehicles[0].id : null;

  return (
    <div className="page">
      <h2>Available Services</h2>

      {!defaultVehicleId && <p>No vehicle available</p>}

      <div className="card-grid">
        {vehicles.map(v => (
  <div key={v.id} className="service-card">
    <h3>{v.model} Service</h3>
     <p>
    Price: <strong>₹ {v.rent}</strong>
  </p>


    <button onClick={() => navigate(`/user/book?serviceId=${v.id}`)}>
  Book
</button>

  </div>
))}

      </div>
    </div>
  );
}
