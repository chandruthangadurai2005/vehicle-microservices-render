import { Navigate } from "react-router-dom";

export default function ProtectedRoute({ role, children }) {
  const token = localStorage.getItem("token");
  const userRole = localStorage.getItem("role");

  // ❌ Not logged in → go to login (FULL PAGE redirect)
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // ❌ Logged in but wrong role
  if (role && userRole !== role) {
    return <Navigate to="/unauthorized" replace />;
  }

  // ✅ Authorized
  return children;
}
