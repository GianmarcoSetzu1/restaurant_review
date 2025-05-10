import { FC, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Typography } from "@material-tailwind/react";

const Home: FC = () => {
  const navigate = useNavigate();
  const [successMessage, setSuccessMessage] = useState("");
  const username = localStorage.getItem("username");

  return (
    <>
      <section>
        <div className="pt-8 pb-8 bg-base-200">
          <div className="max-w-sm mx-auto pt-[5%]">
            <Typography variant="h4" color="blue-gray">
              Ciao {username}, le tue ultime recensioni:
            </Typography>
          </div>
        </div>
      </section>
    </>
  );
};

export default Home;
