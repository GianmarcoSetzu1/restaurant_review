import { FC } from "react";
import { Route, Routes } from "react-router-dom";
import { Layout } from "./Layout";
import { Register, Login, Home } from "../pages";

const App: FC = () => {
  return (
    <>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/home" element={<Home />} />
        </Route>
      </Routes>
    </>
  );
};

export default App;