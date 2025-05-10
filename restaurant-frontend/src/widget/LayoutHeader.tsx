import { FC } from "react";
import { Utensils } from "lucide-react"
import { useNavigate } from "react-router";

const LayoutHeader: FC = () => {
  const navigate = useNavigate();
  const isLoggedIn = !!localStorage.getItem("token");

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    navigate("/");
  };

  return (
    <>
      <header className="flex items-center justify-between px-6 py-4 bg-white dark:bg-gray-900 shadow-sm border-b border-gray-200 dark:border-gray-800">
        <div className="flex items-center space-x-3">
          <Utensils className="w-6 h-6 text-red-500" />
          <h1 className="text-xl font-semibold text-gray-900 dark:text-white">
            Restaurant Review
          </h1>
        </div>
        {
          isLoggedIn &&
          <button
             className="bg-red-500 text-white rounded-md px-6 py-3 text-base font-semibold hover:bg-red-600 transition"
             onClick={handleLogout}>
             Logout
          </button>
        }
      </header>
    </>
  );
};

export { LayoutHeader };