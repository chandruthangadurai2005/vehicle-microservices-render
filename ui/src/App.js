import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Login from "./pages/Login";
import Unauthorized from "./pages/Unauthorized";
import ProtectedRoute from "./routes/ProtectedRoute";

// Admin
import AdminLayout from "./layouts/AdminLayout";
import AdminDashboard from "./pages/AdminDashboard";
import AdminVehicles from "./pages/AdminVehicles";
import AdminBookings from "./pages/AdminBookings";
import AdminCustomers from "./pages/AdminCustomers";
import AdminBilling from "./pages/AdminBilling";
import AdminNotifications from "./pages/AdminNotifications";

// User
import UserDashboard from "./pages/UserDashboard";
import UserVehicles from "./pages/UserVehicles";
import UserBookings from "./pages/UserBookings";
import UserBills from "./pages/UserBills";
import UserNotifications from "./pages/UserNotifications";
import Booking from "./pages/Booking";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* PUBLIC */}
        <Route path="/login" element={<Login />} />
        <Route path="/unauthorized" element={<Unauthorized />} />

        {/* USER */}
        <Route
  path="/user/book"
  element={
    <ProtectedRoute role="USER">
      <Booking />
    </ProtectedRoute>
  }
/>

        <Route
  path="/user"
  element={
    <ProtectedRoute role="USER">
      <UserDashboard />
    </ProtectedRoute>
  }
>
  <Route index element={<Navigate to="vehicles" replace />} />
  <Route path="vehicles" element={<UserVehicles />} />
  <Route path="book" element={<Booking />} />
   {/* ✅ ADD THIS */}
  <Route path="bookings" element={<UserBookings />} />
  <Route path="bills" element={<UserBills />} />
  <Route path="notifications" element={<UserNotifications />} />
</Route>

        {/* ADMIN */}
        <Route
          path="/admin"
          element={
            <ProtectedRoute role="ADMIN">
              <AdminLayout />
            </ProtectedRoute>
          }
        >
          <Route index element={<Navigate to="dashboard" replace />} />
          <Route path="dashboard" element={<AdminDashboard />} />
          <Route path="vehicles" element={<AdminVehicles />} />
          <Route path="bookings" element={<AdminBookings />} />
          <Route path="customers" element={<AdminCustomers />} />
          <Route path="billing" element={<AdminBilling />} />
          <Route path="notifications" element={<AdminNotifications />} />
        </Route>

        {/* FALLBACK */}
        <Route path="*" element={<Navigate to="/login" replace />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;
