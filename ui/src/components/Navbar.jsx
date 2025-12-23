import { useNavigate } from "react-router-dom";

const Navbar = () => {
  const navigate = useNavigate();

  
  const logout = () => {
  localStorage.clear();
  sessionStorage.clear();
  window.location.replace("/login");
};


  return (
    <nav className="flex justify-between items-center px-6 py-4 bg-gray-800 text-white">
      <div className="admin-header">
  <h1>Vehicle Service Admin</h1>
  <button onClick={logout}>Logout</button>
</div>

    </nav>
  );
};

export default Navbar;
