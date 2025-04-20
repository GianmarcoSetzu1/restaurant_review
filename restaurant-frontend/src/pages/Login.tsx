import { FC, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useForm, SubmitHandler } from 'react-hook-form';
import { Typography } from "@material-tailwind/react";

interface FormData {
  email: string;
  password: string;
}

const Login: FC = () => {
  const {
    register,
    handleSubmit,
    formState: {errors},
    getValues,
  } = useForm<FormData>();

    const onSubmit: SubmitHandler<FormData> = async (data) => {

    };

  const navigate = useNavigate();
  const [successMessage, setSuccessMessage] = useState("");

  return (
    <>
      <section>
        <div className="pt-8 pb-8 bg-base-200">
          <div className="max-w-sm mx-auto pt-[5%]">
            <Typography variant="h4" color="blue-gray">
              Login
            </Typography>
            <Typography color="gray" className="mt-1 font-normal">
              Nice to meet you! Enter your details to log in.
            </Typography>
          </div>
          <form className="max-w-sm mx-auto pt-[5%]" onSubmit={handleSubmit(onSubmit)}>
            <div className="mb-5">
              <label htmlFor="email" className={`${getErrorClasses(errors.email?.message, false)} block mb-2 text-sm font-medium text-gray-900 `}>
                Email
              </label>
              <input type="text" id="email"
                     className="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light"
                     {...register('email', {
                       required: 'Email is required',
                       pattern: {
                         value: /\S+@\S+\.\S+/,
                         message: 'Invalid email address',
                       },
                     })}
                     placeholder="name@flowbite.com"/>
              {ShowError(errors.email?.message)}
            </div>
            <div className="mb-5">
              <label htmlFor="password" className={`${getErrorClasses(errors.password?.message, false)} block mb-2 text-sm font-medium text-gray-900 `}>
                Password
              </label>
              <input type="password" id="password"
                     {...register('password', {
                       required: 'Password is required',
                       minLength: {
                         value: 8,
                         message: 'Password must be at least 8 characters',
                       }
                     })}
                     className="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light"/>
              {ShowError(errors.password?.message)}
            </div>
            <button type="submit" className="mx-auto block px-8 py-2 bg-blue-600 text-white rounded">
              Login
            </button>
            <Typography color="gray" className="mt-4 text-center font-normal">
              Already have an account?{" "}
                <a href="/register" className="font-medium text-gray-900">
                  Create a new account
                </a>
            </Typography>
          </form>

        </div>
      </section>
    </>
  );
};

export default Login;

const ShowError = (errorMsg: string | undefined) => {
  return errorMsg ? (<p className="mt-2 text-sm text-red-600 dark:text-red-500"><span
    className="font-medium">Oops!</span> {errorMsg}</p>) : null
}

const getErrorClasses = (errorMsg: string | undefined, isField = true): string => {
  return errorMsg ? !isField ? 'text-red-700 dark:text-red-500' : ' bg-red-50 border border-red-500 text-red-900 placeholder-red-700 dark:bg-green-100 dark:border-green-400' : '';
}