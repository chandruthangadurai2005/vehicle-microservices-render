import { Outlet, Link, useNavigate } from "react-router-dom";

export default function UserDashboard() {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.clear();
    navigate("/login", { replace: true });
  };

  return (
    <div className="page">
      <div className="card dashboard-header">
        <h3>User Dashboard</h3>

        <nav className="nav-links">
          <Link to="vehicles">Services</Link>
          <Link to="bookings">My Requests</Link>
          <Link to="bills">Bills</Link>
          <Link to="notifications">Notifications</Link>
          <button onClick={logout}>Logout</button>
        </nav>
      </div>

      <Outlet />
    </div>
  );
}
