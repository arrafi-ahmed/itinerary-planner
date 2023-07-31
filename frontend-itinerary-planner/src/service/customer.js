import axios from "axios";

const baseUrl = import.meta.env.VITE_API_BASE_URL;

const CustomerService = {
  register(request) {
    return new Promise((resolve, reject) => {
      axios
        .post(`${baseUrl}/customer/save`, request)
        .then((response) => resolve(response))
        .catch((err) => reject(err));
    });
  },
  login(request) {
    return new Promise((resolve, reject) => {
      axios
        .post(`${baseUrl}/auth/login`, request)
        .then((response) => resolve(response))
        .catch((err) => reject(err));
    });
  },
};
export default CustomerService;
