import { Link } from "react-router-dom";

function Dashboard() {
  const role = localStorage.getItem("role");

  return (
    <div>
      <h2>Dashboard</h2>

      <Link to="/vehicle">Vehicle</Link>
      <br />

      {role === "ADMIN" && <p>Admin Access Enabled</p>}
    </div>
  );
}

export default Dashboard;
