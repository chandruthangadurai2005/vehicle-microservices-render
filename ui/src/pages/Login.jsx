import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";

export default function Login() {
  const navigate = useNavigate();

  const [isSignup, setIsSignup] = useState(false);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    try {
      localStorage.clear();

      const res = await api.post("/auth/login", {
        username,
        password
      });

      localStorage.setItem("token", res.data.token);
      localStorage.setItem("role", res.data.role);
      localStorage.setItem("customerId", res.data.customerId);

      if (res.data.role === "ADMIN") {
        navigate("/admin/dashboard");
      } else {
        navigate("/user/vehicles");
      }

    } catch (err) {
      alert("Invalid login credentials");
    }
  };

  const handleSignup = async () => {
    try {
      await api.post("/auth/register", {
        username,
        password
      });

      alert("Signup successful! Please login.");
      setIsSignup(false);
      setUsername("");
      setPassword("");

    } catch (err) {
      alert("Signup failed. Username may already exist.");
    }
  };

  return (
  <div style={{
    minHeight: "100vh",
    display: "flex",
    alignItems: "center",
    justifyContent: "center"
  }}>
    <div className="card" style={{ width: "380px" }}>
      <h2 style={{ textAlign: "center", marginBottom: "20px" }}>
        {isSignup ? "Create Account" : "Welcome Back"}
      </h2>

      <label>Username</label>
      <input
        value={username}
        onChange={e => setUsername(e.target.value)}
      />

      <label>Password</label>
      <input
        type="password"
        value={password}
        onChange={e => setPassword(e.target.value)}
      />

      <button
        style={{ width: "100%", marginTop: "10px" }}
        onClick={isSignup ? handleSignup : handleLogin}
      >
        {isSignup ? "Sign Up" : "Login"}
      </button>

      <p style={{ textAlign: "center", marginTop: "14px", fontSize: "14px" }}>
        {isSignup ? "Already have an account?" : "New user?"}{" "}
        <span
          style={{ color: "#4dabf7", cursor: "pointer" }}
          onClick={() => setIsSignup(!isSignup)}
        >
          {isSignup ? "Login" : "Sign up"}
        </span>
      </p>
    </div>
  </div>
);

}
