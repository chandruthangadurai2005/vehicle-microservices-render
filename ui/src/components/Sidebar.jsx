import { Link } from "react-router-dom";

const Sidebar = () => {
  return (
    <div style={styles.sidebar}>
      <h3 style={styles.title}>Admin</h3>

      <Link to="/admin/dashboard" style={styles.link}>Dashboard</Link>
      <Link to="/admin/vehicles" style={styles.link}>Vehicles</Link>
      <Link to="/admin/bookings" style={styles.link}>Bookings</Link>
      <Link to="/admin/customers" style={styles.link}>Customers</Link>
      <Link to="/admin/billing" style={styles.link}>Billing</Link>
      <Link to="/admin/notifications" style={styles.link}>Notifications</Link>
    </div>
  );
};

const styles = {
  sidebar: {
    width: "220px",
    background: "#1f2937",
    color: "#fff",
    minHeight: "100vh",
    padding: "20px",
  },
  title: {
    marginBottom: "20px",
    fontSize: "20px",
  },
  link: {
    display: "block",
    color: "#e5e7eb",
    textDecoration: "none",
    marginBottom: "12px",
  },
};

export default Sidebar;
